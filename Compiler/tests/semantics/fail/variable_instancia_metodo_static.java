
class A {

    static boolean methodb1(Objeto o1, Objeto o2)
    var Objeto o3, o4;
    var String s1, s2;
    {}

    dynamic int methodi1() {}
}

class Objeto {

    var int n;

    static void main()
    var Objeto o;
    var int m;
    {
        n = o.getN(); // no puede accederse a una variable de instancia en el contexto de un metodo estatico
    }

    dynamic int getN() {
        return n;
    }
}
