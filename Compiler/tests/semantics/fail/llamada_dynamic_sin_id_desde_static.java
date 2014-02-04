/*
Falla en la linea: 34
Se invoca de forma incorrecta la llamada al metodo dinamico 'ret1' en el metodo estatico 'm'.
En metodos estaticos solo puede invocarse a un metodo dinamico de la forma id.metodoDinamico().
*/

class Reverse
{
	static int countDigits(int number)
	var int count;
	{
			count = 0;
			while(number > 0) {
					number = number / 10;
					count = count + 1;
			}
		   
			return count; 
	}

	static int invertir(int number, int digits)
	var int count, aux;
	{
			count = 0;
			for(aux = 0; aux < digits; aux + 1) {
					count = count * 10 + number % 10;
					number = number / 10;
			}
		   
			return count;         
	}
	
	static Reverse m(){
		(ret1());
		return new Reverse();
	}
	
	dynamic Reverse ret1(){
		return new Reverse();
	}
	
	dynamic Reverse ret2(){
		return new Reverse();
	}
	
	dynamic Reverse ret3(){
		return new Reverse();
	}
	
	dynamic void m1(Reverse inv)
	var int resu;
	{
		resu=ret1().ret2().ret3().invertir(10,2);
	}

	static void main()
	var int number, digits, reversed;
	var Reverse obj;
	{
			number = 123;
			digits = countDigits(number);
			(System.printIln(digits));
	
			obj= new Reverse();
			digits=obj.countDigits(number);
		   
			
		   
			(System.printIln(reversed));
	}
}