/*
Dado que MiniJava es case-sensitive y CeIASM no lo es, cambiamos el formato de las etiquetas agregando labels secuenciales a las clases y a los metodos asi evitar la colision de nombres.
Con esta modificacion, por ejemplo, es posible declarar una clase de nombre "Clase" y otra de nombre "clase", y definir un metodo de nombre "foo" y otro de nombre "Foo".
*/

class clase {
	dynamic void foo() {
	
	}
	
	dynamic void Foo() {
	
	}
}

class Clase {
	static void main() {
	
	}
}