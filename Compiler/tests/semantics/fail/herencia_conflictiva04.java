
class A {

    var int a, b;

    A() {}

    dynamic boolean methoda1(int a, int b) {}

    dynamic boolean methoda2() {}
}

class B extends A {

    var boolean a, b;
    var int methoda2;

    B() {}

    static void main() {}

    dynamic boolean methoda1(int a, int b) {} // la clase B tiene una variable de instancia con el mismo nombre que un metodo de A
}
