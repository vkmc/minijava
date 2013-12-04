
class A {

    var int a, b;

    A() {}

    dynamic boolean methoda1(int a, int b, int c) {}

    dynamic boolean methoda2() {}
}

class B extends A {

    var boolean a, b;

    B() {}

    static void main() {}

    dynamic boolean methoda1(int a, int b) {} // diferente cantidad de parametros
}
