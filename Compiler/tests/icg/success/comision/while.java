class A {
	
	var String s1;

	A (String s2)
	{
		s1 = s2; 
	
		(System.printS("A :: A() "));
		(System.printSln(s1));
	}


	dynamic void print()
	var String s2;
	{
		(System.printS("A :: print() :: s1 "));
		s2 = gets1();
		(System.printSln(s2));
		
		
	}
	
	dynamic String gets1()
	var String ret;
	{
		(System.printSln("A :: gets1() "));
		ret = s1;
		return ret;
	}

	dynamic void sets1(String s2)
	{
		(System.printSln("A :: sets1() "));
		s1 = s2;
	}
	
	dynamic int whileloop(int j)
	var int i, count;
	{
		count = 1;
		i = 20;
		(System.printSln("A :: whileloop() "));
		
		while (j < i)
		{	
			(System.printS("Iteracion "));
			(System.printIln(count));

			if (j < 5)
			{
				j = j + 2;
			} else {				
				j = j + 1;				
			}

			count = count + 1;
		}
		
		(System.printC('\n'));

		(System.printS("Valor i = "));
		(System.printI(i));

		(System.printC('\n'));

		(System.printS("Valor j = "));
		(System.printI(j));

		return i;
	}
	
	static void main()
	var A a1;
	{
		a1 = new A("Hello, world!");
		(a1.whileloop(3));
	}
}
