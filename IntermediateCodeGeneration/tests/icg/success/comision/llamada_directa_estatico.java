class A {
	
	var int i1, i2;
	var A o;

	A (int i3, int i4)
	{
		i1 = i3;
		i2 = i4;
	
		(System.printSln("A :: A()"));
		(System.printIln(i1));
		(System.printIln(i2));
	}


	dynamic void print()
	var int i1, i2;
	{
		(System.printSln("A :: print() :: i1"));
		i1 = geti1();
		(System.printIln(i1));
		(System.printSln("A :: print() :: i2"));
		i2 = geti2();
		(System.printIln(i2));
	}
	
	dynamic int geti1()
	var int ret;
	{
		(System.printSln("A :: geti1()"));
		ret = i1;
		return ret;
	}

	dynamic int geti2()
	var int ret;
	{
		(System.printSln("A :: geti2()"));
		ret = i2;
		return ret;
	}
	
	dynamic void setO(int a1, int a2)

	{	(System.printSln("A :: setO()"));
		o = new A(a1,a2);

	}

	dynamic A getO()
	var A ret;
	{
		ret = o;
		return ret;
	}

	
	static void main()
	var A a1;
	var A2 a2;
	var int i1, i2;
	{
		(System.printSln("A1 :: main()"));
		a1 = new A(1,2);
		a2 = new A2(3,4);
		(a2.print());
		(A2.main());
	}
}
		
class A2 {
	
	var int i1, i2;
	var A o;

	A2 (int i3, int i4)
	{
		i1 = i3;
		i2 = i4;
		o = null;

		(System.printSln("A2 :: A2()"));
		(System.printIln(i1));
		(System.printIln(i2));
	}


	dynamic void print()
	var int i1, i2;
	{
		(System.printSln("A2 :: print() :: i1"));
		i1 = geti1();
		(System.printIln(i1));
		(System.printSln("A2 :: print() :: i2"));
		i2 = geti2();
		(System.printIln(i2));
	}
	
	dynamic int geti1()
	var int ret;
	{
		(System.printSln("A2 :: geti1()"));
		ret = i1;
		return ret;
	}

	dynamic int geti2()
	var int ret;
	{
		(System.printSln("A2 :: geti2()"));
		ret = i2;
		return ret;
	}
	
	dynamic void setO(int a1, int a2)
	{	
		(System.printSln("A2 :: setO()"));
		o = new A(a1,a2);
		(o.print());

	}


	static int num(){
		return 5;
	}

	static void main()
	var A a1;
	var A2 a2;
	var int v1,v2, n;
	{
		(System.printSln("A2 :: main()"));
		n = num();
		(System.printIln(n));
	}

}
