class A {

	A() {}
          
	dynamic String getName()
        var String s1;
	{
		return s1;
	}

        static void main()
        var A a1;
        var String s1;
        {
	      	a1 = new A();
	       	s1 = a1.getName();
	       	(System.printSln(s1)); // trash - locacion de memoria no implementada

        }
}
