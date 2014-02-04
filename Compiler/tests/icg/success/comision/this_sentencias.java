class A {
	
	dynamic A _sentencias()
	{
		(System.printSln("A :: _sentencias()"));
		(1 + 2);
		(true || false);
		(System.printI(10));
		((1 > 2) || (2 < 3));
		(new A() != null);
		(new A());
		return new A();
	}
	
	dynamic void _this(){
		(System.printSln("A :: _this()"));
		((this).fooA());
		((this)._sentencias());
		// ((this)._this()); ciclo infinito
	
	}

	static void fooA()
	{
		(System.printSln("A :: fooA()"));
	}
	
	static void main()
	var A a1;
	var B b1;
	var String s1;
	{	
		a1 = new A();
		(a1._this());
	 	((new B()).fooB());
	}

}

class B {
	
	dynamic void fooB()
	{
		(System.printSln("B :: fooB()"));
	}
}

