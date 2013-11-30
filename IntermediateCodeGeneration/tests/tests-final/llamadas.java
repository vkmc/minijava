class A
{
	var int i;
	
	A(int _i)
	{
		(System.printSln("A(int)"));
		i = _i;
	}
	
	dynamic B m()
	{
		(System.printSln("A.m()"));
		return new B(i);
	}
}

class B
{
	var int i;
	
	B(int _i)
	{
		(System.printSln("B(int)"));
		i = _i;
	}
	
	dynamic C m()
	{
		(System.printSln("B.m()"));
		return new C(i);
	}
}

class C
{
	var int i;
	
	C(int _i)
	{
		(System.printSln("C(int)"));
		i = _i;
	}
	
	dynamic D m()
	{
		(System.printSln("C.m()"));
		return new D(i);
	}
}

class D
{
	var int i;
	
	D(int _i)
	{
		(System.printSln("D(int)"));
		i = _i;
	}
	
	dynamic void m()
	{
		(System.printSln("D.m()"));
		(System.printIln(i));
	}
}

class Z
{
	static void main()
	var A a;
	var B b;
	var C c;
	var D d;
	{
		(System.printSln("Secuenciales"));
		a = new A(12345);
		b = a.m();
		c = b.m();
		d = c.m();
		(d.m());
		(System.println());
		(System.printSln("Anidadas"));
		(new A(54321).m().m().m().m());
	}
}

/* Autores: Calendino, Federico - Schiaffino, Martin */
