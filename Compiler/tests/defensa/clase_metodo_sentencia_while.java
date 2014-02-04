
class A {

    dynamic void methodb1(int a, int b)
    var String s1, s2;
    var boolean condicion;

    {
	condicion = false;

        while (!condicion) {

            while (a == b) {
                s1 = "una cadena";
		(System.printSln(s1));
		a = a + 1;
            }

            condicion = true;
        }
    }
}

class Objeto {

    static void main()
    var A a1;
    {
	a1 = new A();
	(a1.methodb1(1,1));
    }
}
