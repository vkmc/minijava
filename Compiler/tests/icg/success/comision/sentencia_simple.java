class A {

	dynamic A foo1()
	{
		(System.printSln("A :: foo1() "));
		('a');
		return new A();	
	}
	
	dynamic A foo2()	
	{	
		(System.printSln("A :: foo2() "));
		("Hola, mundo!");
		return new A();	
	}
	
	dynamic A foo3()	
	{	
		(System.printSln("A :: foo3() "));
		(8);
		return new A();	
	}
	
	static void main()
	var A a1, a2;
	{
	
		(System.printSln("A :: main() "));
		a1 = new A();
		(a1.foo1());
		(a1.foo1().foo2().foo3());	
	}

}

