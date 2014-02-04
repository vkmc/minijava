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
	{
		a2 = new A2(7,8);
		(a2.print());
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

	}

	dynamic A getO()
	var A ret;
	{
		ret = o;
		return ret;
	}


	static void main()
	var A f1,f2;
	var A2 f3;
	{
		f3 = new A2(1,2);
		(f3.print());
		
		(f3.setO(3,4));
		f1 = f3.getO();
		(f1.print());
	
		(f1.setO(5,6));
		f2 = f1.getO();
		(f2.print());
	}

}
