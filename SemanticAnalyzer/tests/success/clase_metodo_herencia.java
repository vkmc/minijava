
class A extends B {

    static boolean methodb1(Objeto o1, Objeto o2)
    var Objeto o3, o4;
    var String s1, s2;
    {}

    dynamic int methodi1() {
    }
}

class B {

    var boolean flag;

    static boolean methodb1(Objeto o1, Objeto o2) {
    } // redefinicion

    static void methodi2() {}

    dynamic boolean methodb2()
    var Objeto o3;
    var B b1;
    var boolean flag;
    {
        b1 = this;
    }

    dynamic boolean methodb3() {
        return flag;
    }
}

class C {

    static void main() 
    var B b2;
    {
	b2 = new B();
    }
}

class Objeto {

    var String nombre ;
    var int linea;
}
