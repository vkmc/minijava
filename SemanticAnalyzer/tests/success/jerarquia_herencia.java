
class A {

    var int n;
    dynamic

    int getN() {
        return n;
    }
}

class B extends A {

    var int num;
    dynamic

    void printN()
    var int num;

    {
        num = (getN());
        (System.printI(num));
    }

    static void main() {
    }

    B() {
    }
    dynamic

    void setValue(int value) {
        num = value;
    }
}

class C extends B {

    dynamic

    void setValueInB(int value) {
        (setValue(value));
    }
}
