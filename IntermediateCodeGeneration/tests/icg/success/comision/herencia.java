class A {
	
	var int i1;


	A (int i2)
	{
		i1 = i2;

		(System.printSln("A :: A() :: i1 "));
		(System.printIln(i1));
	}

	dynamic void printA()
	var int i2;
	{
		(System.printS("A :: printA() :: i1 "));
		i2 = geti1();
		(System.printIln(i2));
	}
	
	dynamic int geti1()
	{
		(System.printSln("A :: geti1() "));
		return i1;
	}

	dynamic void seti1 (int i2)
	{
		(System.printSln("A :: seti1() "));
		i1 = i2;
	}
}

class A2 extends A {
	
	var int i1, i3;

	A2 (int i2, int i4)
	{
		i1 = i2;
		i3 = i4;
	
		(System.printSln("A2 :: A2() :: i1 "));
		(System.printI(i1));
		(System.printSln("A2 :: A2() :: i2 "));
		(System.printIln(i3));
	}

	dynamic int geti1()
	var int ret;
	{
		(System.printSln("A2 :: geti1() "));
		ret = i1;
		return ret;
	}

	dynamic int geti3()
	var int ret;
	{
		(System.printSln("A2 :: geti3() "));
		ret = i3;
		return ret;
	}

	dynamic void seti1(int i2)
	{
	
		(System.printSln("A2 :: seti1() "));
		(System.printS("Valor anterior de i1: "));
		(System.printIln(i1));
		(System.printS("Valor actual de i1: "));
		(System.printIln(i2));
	
		i1 = i2;

		(System.printS("Resultado de la asignacion: "));
		(System.printIln(i1));
	}

	dynamic void seti3 (int i4)
	{
		(System.printSln("A2 :: seti3() "));
		i3 = i4;
	}

	dynamic void print()
	var int i2, i4;
	{
		(System.printS("A2 :: print() :: i1 "));
		i2 = geti1();
		(System.printIln(i2));

		(System.printS("A2 :: print() :: i3 "));
		i4 = geti3();
		(System.printIln(i4));
	} 
}

class A3 extends A2 {
	
	var int i1, i3, i5;

	A3 (int i2,int i4,int i6)
	{
		i1 = i2;
		i3 = i4;
		i5 = i6;
	
		(System.printSln("A3 :: A3() :: i1 "));
		(System.printI(i1));
		(System.printC('\n'));
		(System.printSln("A3 :: A3() :: i3 "));
		(System.printI(i3));
		(System.printC('\n'));
		(System.printSln("A3 :: A3() :: i5 "));
		(System.printIln(i5));
		(System.printC('\n'));
	}



	dynamic int geti1()
	var int ret;
	{
		(System.printSln("A3 :: geti1() :: i1"));
		ret = i1;
		return ret;	
	}

	dynamic int geti3()
	var int ret;
	{
		(System.printSln("A3 :: geti3() :: i3 "));
		ret = i3;
		return ret;
	}

	dynamic int geti5()
	var int ret;
	{
		(System.printSln("A3 :: geti5() :: i5 "));
		ret = i5;
		return ret;
	}

	dynamic void seti1(int i2)
	{
		(System.printSln("A3 :: seti1() "));
		i1 = i2;
	}

	dynamic void seti3(int i4)
	{
		(System.printSln("A3 :: seti3() "));
		i3 = i4;
	}	

	dynamic void seti5(int i6)
	{
		(System.printSln("A3 :: seti5() "));
		i5 = i6;
	}

	dynamic void print()
	var int i2, i4, i6;
	{

		(System.printSln("A3 :: print() :: i1 "));
		i2 = geti1();
		(System.printIln(i2));

		(System.printC('\n'));

		(System.printSln("A3 :: print() :: i3 "));
		i4 = geti3();
		(System.printIln(i4));

		(System.printC('\n'));

		(System.printSln("A3 :: print() :: i5 "));
		i6 = geti5();
		(System.printIln(i6));

		(System.printC('\n'));
	} 

	dynamic A ret()
	{
		return new A3(1,2,3);
	}

	static void main()
	var A3 a3;
	{
		a3 = new A3(3,4,5);
		(a3.print());
	
		(a3.ret().printA());
	}
}
