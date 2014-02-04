class A {
	
	
	A() {}
	
	dynamic String getString0()
	var String s1;
	{
		//return s1;  //con o sin return salta el mismo error
	}
	
	static void main()
	var A a1;
	var String s1;
	{
		a1 = new A();
		
		s1 = a1.getString0();
		(System.printSln(s1));
    	}
}
