/*
Teniamos un bug en el acceso a las VTs que causaba un stack overflow en la ejecucion de la mayoria de los programas que involucraban herencia.
Esto se debia a la forma en que se insertaban los metodos en la VT.
Al realizar la consolidacion de herencia luego de la insercion de todos los metodos de la clase y tambien debido a la estructura de datos utilizada
para llevar los metodos, los metodos heredados se insertaban pero debian estar ordenados segun su offset.

Debe imprimirse:
1
2
3

*/
class Padre {
	dynamic int foo() {
		return 1;
	}
}

class Hijo extends Padre {
	dynamic int foo() {
		return 2;
	}
	
	dynamic int bar() {
		return 3;
	}
}

class Principal {
	static void main()
	var Padre x, y;
	var Hijo z;
	{
		x = new Padre();
		y = new Hijo();
		z = new Hijo();
		(System.printIln(x.foo()));
		(System.printIln(y.foo()));
		(System.printI(z.bar()));
	}
}