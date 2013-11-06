class Clase
{
	var boolean b;
	var char c;
	var int i;
	var String s;
	var Clase o;
	
	Clase(boolean _b, char _c, int _i, String _s)
	{
		b = _b;
		c = _c;
		i = _i;
		s = _s;
		o = this;
	}
	
	dynamic boolean getB() 
	{
		return b;
	}
	
	dynamic char getC() 
	{
		return c;
	}
	
	dynamic int getI() 
	{
		return i;
	}
	
	dynamic String getS() 
	{
		return s;
	}
	
	dynamic Clase getO() 
	{
		return o;
	}
	
	
	static void main()
	var Clase obj, jbo;
	{
		obj = new Clase(false, 'c', 112358, "str");
		jbo = new Clase(true, 'x', 853211, "rts");
		
		(System.printSln("Equals (==)"));
		
		if(obj.getB() != false)
			(System.printSln("Booleans: NOT OK"));
		
		if(obj.getC() != 'c')
			(System.printSln("Chars: NOT OK"));
		
		if(obj.getI() != 112358)
			(System.printSln("Integers: NOT OK"));
		
		if(obj.getS() != "str")
			(System.printSln("Strings: NOT OK (EXPECTED)"));
		
		if(obj.getO() != obj)
			(System.printSln("Objects: NOT OK"));
		
		(System.printSln("Not Equals (!=)"));
		
		if(obj.getB() == jbo.getB())
			(System.printSln("Booleans: NOT OK"));
		
		if(obj.getC() == jbo.getC())
			(System.printSln("Chars: NOT OK"));
		
		if(obj.getI() == jbo.getI())
			(System.printSln("Integers: NOT OK"));
		
		if(obj.getS() == jbo.getS())
			(System.printSln("Strings:  NOT OK"));
		
		if(obj.getO() == jbo.getO())
			(System.printSln("Objects: NOT OK"));
			
		(System.printSln("Greater (>)"));
		
		if(obj.getI() * 2 <= obj.getI())
			(System.printSln("Integer: NOT OK"));

		(System.printSln("Greater Than (>=)"));
		
		if(obj.getI() * 0 < jbo.getI()*0)
			(System.printSln("Integer: NOT OK"));
		
		(System.printSln("Lesser (<)"));
		
		if(obj.getI() >= obj.getI()*2)
			(System.printSln("Integer: NOT OK"));
		
		(System.printSln("Lesser Than (<=)"));
		
		if(obj.getI() * 0 > jbo.getI() * 0)
			(System.printSln("Integer: NOT OK"));
	}
	
}

/* Autores: Calendino, Federico - Schiaffino, Martin */
