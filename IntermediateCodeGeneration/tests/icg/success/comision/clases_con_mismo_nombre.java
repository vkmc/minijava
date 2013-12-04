class A {
	
	var int i1;
	
	dynamic int m()
	{
		(System.printSln("A :: m() "));
		return 1;
	}
	
	A (int i2) {
		(System.printSln("A :: A() "));
		i1 = 2;
	}
}


class a {
	
	var int i1;
	
	a (int i2)
	{	
		(System.printSln("a :: a() "));
		i1 = 3;
	}

	static void main()
	var a a1; 
	{	
		(System.printIln(((new A(5)).m())));
		(System.printIln(((new a(5)).m())));
	}
	
	dynamic int m()
	{
		(System.printSln("a :: m() "));
		return 4;
	}	
}
