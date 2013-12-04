class A {
	
	var int i1;
	
	A() {
		i1 = 1;
	}
	
	dynamic A foo1()
	var A a1;
	{
		return a1;		
	}
	
	dynamic int geti1()
	{
		return i1;
	}
		
	static void main()
	var A a1, a2;
	var int i1;
	{
		a1 = new A();
		
		a2 = a1.foo1();
		(System.printS("Null pointer coming!"));
		i1 = a2.geti1(); // error en tiempo de ejecucion
    	}
}
