/**
OBS: Los numeros son en base a la table ascii por lo que a los
que ingresen deberan sumarles 48 para saber si el resultado es el correcto.
**/
class A{
	static int mayor(int v1, int v2){
		
		if(v1 > v2) return v1;
		else return v2;		
	}

	static void main()
		var int a,b;	
	{
		(System.printSln("Ingrese un num:"));
		a = System.read();
		(System.read());
		(System.printSln("Ingrese otro num:"));
		(System.read());
		b = System.read();		
		(System.printSln("El mayor numero es: "));
		(System.printIln(mayor(a,b)-48));	
	}
}
