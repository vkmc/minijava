class invertir_numero
{
	static int contarDigitos(int numero)
	var int cuenta;
	{
		cuenta = 0;
		while(numero > 0) {
			numero = numero / 10;
			cuenta = cuenta + 1;
		}
		
		return cuenta;	
	}

	static int invertir(int numero, int digitos)
	var int cuenta, aux;
	{
		cuenta = 0;
		for(aux = 0; aux < digitos; aux + 1) {
			cuenta = cuenta * 10 + numero % 10;
			numero = numero / 10;
		}
		
		return cuenta;		
	}

	static void main()
	var int numero, digitos, invertido;
	{
		numero = 1123581321;
		digitos = contarDigitos(numero);
	
		invertido = invertir(numero, digitos);
		
		(System.printIln(invertido));
	}
}
