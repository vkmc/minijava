class A {
	var char c1;
	
	A (char c){
		c1 = c;
	}
	
	dynamic char getChar() {
		return c1;
	}
	
	dynamic A foo1()
	{
		return (new A('c'));
	}
	
	dynamic A foo2()	
	{	
		return (new A('e'));
	}
	
	dynamic A foo3()	
	{	
		return (new A('i'));
	}
	
	static void main()
	var A o1, o2;
	{
	
		o1 = (new A('@'));
		(System.printC(o1.getChar()));
		
		
		o1 = (o1.foo1());
		(System.printC(o1.getChar()));
		
		o2 = o1.foo1().foo2();
		(System.printC(o2.getChar()));
		
		o2 = o1.foo1().foo2().foo3();
		(System.printC(o2.getChar()));
		
	}

}

