package org.ballerinalang.testerina.natives.test;

import org.ballerinalang.jvm.BallerinaErrors;
import org.ballerinalang.jvm.StringUtils;
import org.ballerinalang.jvm.scheduling.Scheduler;
import org.ballerinalang.jvm.scheduling.Strand;
import org.ballerinalang.jvm.scheduling.StrandMetadata;
import org.ballerinalang.jvm.types.BRecordType;
import org.ballerinalang.jvm.values.AbstractObjectValue;
import org.ballerinalang.jvm.values.ArrayValue;
import org.ballerinalang.jvm.values.ErrorValue;
import org.ballerinalang.jvm.values.IteratorValue;
import org.ballerinalang.jvm.values.ObjectValue;
import org.ballerinalang.jvm.values.StringValue;
import org.ballerinalang.jvm.values.connector.Executor;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

import static org.ballerinalang.testerina.natives.test.MockConstants.MOCK_STRAND_NAME;

/**
 * Class that contains inter-op function related to function mocking.
 */
public class FunctionMock {

    public static ErrorValue thenReturn(ObjectValue caseObj) {
        ObjectValue mockFunctionObj = caseObj.getObjectValue(StringUtils.fromString("mockFuncObj"));
        ArrayValue args = caseObj.getArrayValue(StringUtils.fromString("args"));
        Object returnVal = caseObj.get(StringUtils.fromString("returnValue"));
        MockRegistry.getInstance().registerCase(mockFunctionObj, null, args, returnVal);
        return null;
    }

    public static Object mockHandler(ObjectValue mockFuncObj, ArrayValue args) {
        List<String> caseIds = getCaseIds(mockFuncObj, args);
        String originalFunction = mockFuncObj.getStringValue(StringUtils.fromString("functionToMock")).toString();
        Object returnVal = null;
        for (String caseId : caseIds) {
            if (MockRegistry.getInstance().hasCase(caseId)) {
                returnVal = MockRegistry.getInstance().getReturnValue(caseId);
                if ((returnVal instanceof StringValue)
                        && returnVal.toString().contains(MockConstants.FUNCTION_CALL_PLACEHOLDER)) {
                    return callFunction(originalFunction, returnVal.toString(), args);
                }
                break;
            }
        }
        if (returnVal == null) {
            String detail = "no return value or action registered for function";
            return BallerinaErrors.createDistinctError(MockConstants.FUNCTION_CALL_ERROR, MockConstants.TEST_PACKAGE_ID,
                    detail);
        }
        return returnVal;
    }

    private static Object callFunction(String originalFunction, String returnVal, ArrayValue args) {
        int prefixPos = returnVal.indexOf(MockConstants.FUNCTION_CALL_PLACEHOLDER);
        String methodName = returnVal.substring(prefixPos + MockConstants.FUNCTION_CALL_PLACEHOLDER.length());
        Strand strand = Scheduler.getStrand();

        String className;
        String orgName;
        String packageName;
        String version;

        // Set project info
        try {
            String[] projectInfo = Thread.currentThread().getStackTrace()[4].getClassName().split(Pattern.quote("."));
            orgName = projectInfo[0];
            packageName = projectInfo[1];
            version = projectInfo[2].replace("_", ".");
            className = "tests." + getClassName(methodName, orgName, packageName, version, originalFunction);
        } catch (IOException | ClassNotFoundException e) {
            return BallerinaErrors.createDistinctError(MockConstants.FUNCTION_CALL_ERROR, MockConstants.TEST_PACKAGE_ID,
                    e.getMessage());
        }

        List<Object> argsList = new ArrayList<>();
        IteratorValue argIterator = args.getIterator();
        while (argIterator.hasNext()) {
            argsList.add(argIterator.next());
        }

        ClassLoader classLoader = FunctionMock.class.getClassLoader();
        StrandMetadata metadata = new StrandMetadata(orgName, packageName, version, methodName);
        return Executor.executeFunction(strand.scheduler, MOCK_STRAND_NAME, metadata, classLoader, orgName,
                                        packageName, version, className, methodName, argsList.toArray());
    }

    private static String getClassName(String mockMethodName, String orgName, String packageName, String version,
                                       String originalMethodName) throws IOException, ClassNotFoundException {
        String jarName = orgName + "-" + packageName + "-" + version + "-testable.jar";
        Path jarPath = Paths.get(System.getProperty("user.dir"), "target", "caches", "jar_cache", orgName,
                packageName, version, jarName);

        try (JarFile jar = new JarFile(jarPath.toString())) {
            // Method definition
            Method mockMethod = null;
            Method originalMethod = null;

            for (Enumeration<JarEntry> entries = jar.entries(); entries.hasMoreElements();) {
                String file = entries.nextElement().getName();

                // Get .class files but dont contain '..Frame.class'
                if (file.endsWith(".class") && !file.contains("Frame.class") && !file.contains("__init")) {

                    // Find mock method if still null
                    if (file.contains("/tests/") && mockMethod == null) {
                        mockMethod = getClassDeclaredMethod(file, mockMethodName);
                    }

                    // Find original method if still null
                    if (!file.contains("/tests/") && originalMethod == null) {
                        originalMethod = getClassDeclaredMethod(file, originalMethodName);
                    }
                }

                if (mockMethod != null && originalMethod != null) {
                    break;
                }
            }
            validateFunctionSignature(mockMethod, originalMethod, mockMethodName);
            return  mockMethod.getDeclaringClass().getSimpleName();
        }
    }

    private static void validateFunctionSignature(Method mockMethod, Method originalMethod, String mockMethodName) {
        // Validation
        if (mockMethod != null && originalMethod != null) {
            // Methods type and parameters
            Class<?> mockMethodType = mockMethod.getReturnType();
            Class<?>[] mockMethodParameters = mockMethod.getParameterTypes();
            Class<?> originalMethodType = originalMethod.getReturnType();
            Class<?>[] originalMethodParameters = originalMethod.getParameterTypes();

            // Validate Return types
            if (mockMethodType != originalMethodType) {
                throw BallerinaErrors.createDistinctError(MockConstants.FUNCTION_SIGNATURE_MISMATCH_ERROR,
                        MockConstants.TEST_PACKAGE_ID, "Return Types do not match");
            }

            // Validate if param number is the same
            if (mockMethodParameters.length != originalMethodParameters.length) {
                throw BallerinaErrors.createDistinctError(MockConstants.FUNCTION_SIGNATURE_MISMATCH_ERROR,
                        MockConstants.TEST_PACKAGE_ID, "Parameter types do not match");
            }

            // Validate each param
            for (int i = 0; i < mockMethodParameters.length; i++) {
                if (mockMethodParameters [i] != originalMethodParameters[i]) {
                    throw BallerinaErrors.createDistinctError(MockConstants.FUNCTION_SIGNATURE_MISMATCH_ERROR,
                            MockConstants.TEST_PACKAGE_ID, "Parameter types do not match");
                }
            }

        } else {
            throw BallerinaErrors.createDistinctError(MockConstants.FUNCTION_NOT_FOUND_ERROR,
                    MockConstants.TEST_PACKAGE_ID,
                    "Specified Mock function \'" + mockMethodName + "\' cannot be found");
        }
    }

    private static Method getClassDeclaredMethod(String file, String methodName) throws ClassNotFoundException {
        String className = file.replace('/', '.').substring(0, file.length() - 6);
        Class<?> clazz = Class.forName(className);

        for (Method method : clazz.getDeclaredMethods()) {
            if (methodName.equals(method.getName())) {
                return method;
            }
        }

        return null;
    }

    private static List<String> getCaseIds(ObjectValue mockObj, ArrayValue args) {
        List<String> caseIdList = new ArrayList<>();
        StringBuilder caseId = new StringBuilder();

        // add case for function without args
        caseId.append(mockObj.hashCode());
        caseIdList.add(caseId.toString());

        // add case for function with ANY specified for objects and records
        IteratorValue it = args.getIterator();
        while (it.hasNext()) {
            Object arg = it.next();
            caseId.append("-");
            if (arg instanceof AbstractObjectValue || arg instanceof BRecordType) {
                caseId.append(MockRegistry.ANY);
            } else {
                caseId.append(arg);
            }
        }
        // skip if entry exists in list
        if (!caseIdList.contains(caseId.toString())) {
            caseIdList.add(caseId.toString());
        }
        // reversing the list to prioritize cases that have arguments specified
        Collections.reverse(caseIdList);

        return caseIdList;
    }
}

