/*
Falla en la linea: 31
Se intenta invocar al metodo dinamico 'toString' enviandole un mensaje a la clase 'D'. 
Solo los metodos estaticos se pueden llamar de esta forma.
*/

class A {

	static void main()
	var A instance;
	var int output;
	{
		instance = new A();
		output = instance.calcular();
	}

	dynamic int calcular()
	var int output;
	var String cadena;
	var int valor;
	{
		
		output=(A.num());
		(System.printS(" el valor de output es: "));
		(System.printIln(output));
		
		output=(C.num1());
		(System.printS(" el valor de output es: "));
		(System.printIln(output));
		
		cadena=(D.toString());
		(System.printS(" el valor de la cadena es: "));
		(System.printSln(cadena));
		
		valor=m1().m2().m3();
		(System.printS(" el valor de output es: "));
		(System.printIln(valor));
		
		return output;
	}
	
	dynamic A m1(){
		return new A();
	}
	
	dynamic A m2(){
		return new A();
	}
	
	dynamic int m3(){
		return 555;
	}
	static int num(){
		return 11;
	}
}

class C{
	static int num1()
	var int output;
	{
		return 777;
	}
}

class D{
	dynamic String toString(){
		return "TEST";
	}
}
