class A
{
	var int x;
	
	A(int _x)
	{
		x = _x;
	}
	
	dynamic void m1()
	{
		(System.printSln("A.m1"));
		(System.printIln(x));
	}
	
	dynamic void m2()
	{
		(System.printSln("A.m2"));
		(System.printIln(x*2));	
	}
}

class B extends A
{
	var int x;
	
	B(int _x)
	{
		x = _x;
	}
	
	dynamic void m1()
	{
		(System.printSln("B.m1"));
		(System.printIln(x));
	}
}

class C extends B
{
	var int x;
	
	C(int _x)
	{
		x = _x;
	}
	
	dynamic void m2()
	{
		(System.printSln("C.m2"));
		(System.printIln(x*3));
	}
}

class D
{
	static void main()
	var A a;
	var C c;
	{
		a = new A(111);
		(a.m1());
		(a.m2());
		
		(System.println());
		
		a = new B(222);
		(a.m1());
		(a.m2());
		
		(System.println());
		
		c = new C(333);
		(c.m1());
		(c.m2());
		
		(System.println());		
	}
}

/* Autores: Calendino, Federico - Schiaffino, Martin */
