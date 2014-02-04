
class A extends B {	// herencia circular

    var int a, b;

    A() {}

    dynamic boolean methoda1() {}
}

class B extends A {

    var boolean a, b;

    B() {}

    static void main() {}

    dynamic boolean methoda1() {}
}

class C extends B {
}
