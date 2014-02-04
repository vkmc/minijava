class A {
	
	var int i1;


	A(int i)
	{
		i1 = i;
	
		(System.printS("A :: A() "));
		(System.printIln(i1));
	}


	dynamic void print()
	var int i;
	{
		(System.printS("A :: print() :: i1 "));
		i = geti1();
		(System.printIln(i));
		
		
	}
	
	dynamic int geti1()
	var int ret;
	{
		(System.printSln("A :: geti1() "));
		return i1;
	}

	dynamic void seti1(int i)
	{
		(System.printSln("A :: seti1() "));
		i1 = i;
	}
	
	dynamic int forloop()
	var int i, j;
	var boolean flag;
	{
		j = 0;
		flag = true;
		(System.printSln("A :: forloop() "));
		
		(System.printSln("for [ "));
		
		for(i = 0; i < i1; i + 1){
			
			(System.printS("while ( "));
			while (j < 4)
			{	
				(System.printI(j));	
				(System.printS(" "));
				j = j + 1;
			}
			(System.printS(") "));
			
			j = 0;
			(System.printS("Iteracion "));
			(System.printI(i));	
			(System.printSln(" "));
		}
		
		(System.printS("]"));	
	}
	
	static void main()
	var A a1;
	{
		a1 = new A(4);
		(a1.forloop());
	}
}
