class A {
	
	var int i1, i2;

	A (int i3, int i4)
	{
		i1 = i3;
		i2 = i4;
	}

	dynamic void print()
	var int i;
	{
		{
		(System.printS("A :: print() :: i1 "));
		(System.printIln(i1));}
		
		{(System.printS("A :: print() :: i2 "));
		(System.printIln(i2));}
		
	}
	
	dynamic int geti1()
	{
		return i1;
	}	
}

class A2 extends A {
	
	var int i1, i2;

	A2 (int i3, int i4)
	{
		{ (System.printSln("A2 :: A2()")); }
		{i1 = i3;}
		{i2 = i4;}
		{
			if (i1 == 1) {				
				{ (System.printS("i1 == ")); }
				{ (System.printIln(i1)); }
				{ (System.printS("i2 == ")); }	
				{ (System.printIln(i2)); }
			}
		}
	}


	dynamic void print()
	var int i;
	{
	
		{ (System.printS("B :: print() :: i1 "));
		  (System.printIln(i1)); }
	
		{ (System.printS("B :: print() :: i2 "));
		  (System.printIln(i2)); }
	} 

	static void main()
	var A a1;
	var A2 a2;
	{
		a1 = null;
		a1 = new A(1,4);
		(a1.print());
		a2 = null;
		a2 = new A2(1,6);
		(a2.print());
	}
}
