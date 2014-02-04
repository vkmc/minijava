class A {
	
	var int i1;
	var char c1;
	var String s1;
	var boolean b1;


	A (int i2, char c2, String s2, boolean b2)
	{
		i1 = i1;
		c1 = c2;
		s1 = s2;
		b1 = b2;
	
		(System.printSln("A :: A()"));
		(System.printS("Parametros: "));
		(System.printI(i1));
		(System.printS(" - "));
		(System.printC(c1));
		(System.printS(" - "));
		(System.printS(s1));
		(System.printS(" - "));
		(System.printBln(b1));
	}


	dynamic void print()
	var int i1;
	{
		(System.printS("A :: print()"));
		i1 = getInteger();
		(System.printIln(i1));		
		
	}
	
	dynamic int getInteger()
	var int ret;
	{
		(System.printSln("A :: getInteger()"));
		ret = i1;
		return ret;
	}

	dynamic void setInteger(int i2)
	{
		(System.printSln("A :: setInteger()"));
		i1 = i2;
	}
	
	dynamic void less(int n)
	var int a, b;
	{
		a = 1;
		b = 2;
		(System.printSln("A :: less()"));

		(System.printS("Valor i = "));
		(System.printIln(i1));

		(System.printS("Valor n = "));
		(System.printIln(n));
		
		if (i1 < n) {
			(System.printSln("Es maximo! n es mayor que i"));
			b = a + b;
		} else {
			(System.printSln("No es maximo... n es menor que i"));
		}

		(System.printS("Valor a = "));
		(System.printIln(a));

		(System.printS("Valor b = "));
		(System.printIln(b));	
	}
	
		
	
	
	
	static void main()
	var A f1;
	var int n, i;
	{
		n = 3;
		f1 = new A(0, 'a', "Lorem", false);
		(f1.setInteger(2));
		i = (f1.getInteger());
		(f1.print());
		(f1.less(n));
	}
}
