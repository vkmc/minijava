
class A {

    A() {
    }

    static int getLimit() {
        return 10;
    }
}

class B {

    B() {
    }

    static void main()
    var B objetoB;
    {	objetoB = new B();
	(objetoB.printLimit());
    }

    dynamic String printLimit()
    var int limit;

    {
        limit = A.getLimit();
        if (limit == 10) {
            (System.printS("Imprimo 10"));
        } else {
            (System.printS("Valor incorrecto!"));

        }
    }
}
