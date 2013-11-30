class D
{
	var String str;
	
	D(String _str)
	{
		str = _str;
	}
	
	dynamic void print()
	{
		(System.printSln(str));
	}
	
	static void main()
	var String str, str2;
	{
		str = "hola mundo";
		(System.printSln(str));
		
		str2 = str;
		(System.printSln(str));
		
		(new D(str).print());
	}
}

/* Autores: Calendino, Federico - Schiaffino, Martin */
