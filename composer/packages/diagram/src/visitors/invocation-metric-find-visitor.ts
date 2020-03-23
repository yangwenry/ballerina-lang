import { ASTNode, Invocation, Visitor } from "@ballerina/ast-model";

export class InvocationMetricsVisitor implements Visitor {
    public invocations: Invocation[] = [];
    constructor(public parent: ASTNode) {

    }
    public beginVisitInvocation(node: Invocation) {
        this.invocations.push(node);
    }

    public getMetricsNode() {
        return this.invocations.find((node) => ((node as any).metrics !== undefined));
    }
}
