struct Person {
    string firstName;
    string lastName;
    int age;
    string city;
}

struct Employee {
    string name;
    int age;
    string address;
}

function varInTransform() (string, int, string){
    Person p = {firstName:"John", lastName:"Doe", age:30, city:"London"};
    Employee e = <Employee; Foo()> p;
    return e.name, e.age, e.address;
}

function getPrefixedName(string a) (string) {
    return "";
}

transformer <Person p, Employee e> Foo() {
    var temp = getPrefixedName(p.firstName);
    e.name = temp;
    temp = p.firstName;
}