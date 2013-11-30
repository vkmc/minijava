
class A {

    var int n;

    dynamic int getN() {
	n = 10;
        return n;
    }
}

class B extends A {

    var int num;

    dynamic void printN()
    var int num;
    {
        num = (getN());
        (System.printI(num));
    }

    static void main()
    var B objetoB;
    {
	objetoB = new B();
	(objetoB.printN());
    }

    B() {}
    dynamic void setValue(int value) {
        num = value;
    }

    dynamic int getN() {
	return 123;
    }
}

class C extends B {

    dynamic void setValueInB(int value) {
        (setValue(value));
    }
}
