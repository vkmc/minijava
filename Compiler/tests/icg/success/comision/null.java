class A {
	
	var String s1,s2;
	A (String s3, String s4)
	{
		s1 = s3;
		s2 = s4;

	}
	
	dynamic void print()
	var int n1;
	{
		(System.printS("A :: print() :: s1 "));
		(System.printSln(s1));
		(System.printS("A :: print() :: s2 "));
		(System.printSln(s2));
		
	}
	
	dynamic String getString()
	{
		return s1;
	}

	dynamic void setString(String s)
	{
		s1 = s;
	}
	
}

class A2 extends A {
	
	var int i1, i2;

	A2 (int i3,int i4)
	{
		i1 = i3;
		i2 = i4;
	}


	dynamic void print()
	var int n2;
	{
	
		(System.printS("A2 :: print() :: i1 "));
		(System.printIln(i1));
		(System.printS("A2 :: print() :: i2 "));
		(System.printIln(i2));
	} 

	static void main()
	var A a1;
	var A2 a2;
	{
		a1 = null;
		a1 = new A("Hola", "Mundo");
		(a1.print());
		a2 = null;
		a2 = new A2(4,6);
		(a2.print());
	}

}
