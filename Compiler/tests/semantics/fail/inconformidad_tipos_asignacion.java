/*
Falla en la linea: 41
El tipo de la expresion del lado derecho (Padre) no conforma al tipo del identificador del lado izquierdo (Hija) de la asignacion. 
*/

class Padre {
	var int var1, var2, var3;
	
	Padre(int a, int b, int c){
		var1 = a;
		var2 = b;
		var3 = c;		
	}
	
	dynamic void test(int a, int b, int c)
	var Padre instancia;
	{
		instancia = new Padre(a, b, c);
		instancia = new Hija();
	}
	
	static void main()
	var Padre a;
	{
		(a.test(5,15, 25));
	}
}

class Hija extends Padre {

	dynamic void test(int a, int b, int c)
	var Padre instancia;
	{
		instancia = new Padre(a, b, c);
		instancia = new Hija();
	}
	
	dynamic Padre metodo()
	var Hija hija;
	{
		hija = new Padre(0,0,0);
		return hija;
	}
}