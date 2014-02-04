class A {
	
	dynamic A ret()
	{
		return new A();
	}
	
	dynamic String getName()
	{
		(System.printSln("A :: getName() "));
		return "A";
	}
}

class A2 extends A {

	dynamic String getName()
	{
		(System.printSln("A2 :: getName() "));
		return "A2";
	}
}

class B {
	
	dynamic B ret()
	{
		return new B();
	}
	
	dynamic String getName()
	{	
		(System.printSln("B :: getName() "));
		return "B";
	}
}

class Main {
	
	dynamic Main ret()
	{
		return new Main();
	}
	
	static String getName()
	{
		(System.printSln("Main :: getName() "));
		return "Main";
	}
	
	static void main()
	var A a1;
	var A2 a2;
	var B b1;
	var Main m1;
	var String s1;
	{
		s1= (new A()).getName();

		a1 = new A();
		s1= a1.getName();
		(System.printSln(s1));

		a2 = new A2();
		s1 = a2.getName();
		(System.printSln(s1));

		b1 = new B();
		s1 = b1.getName();
		(System.printSln(s1));
		
		s1 = (a1.ret().getName());
		(System.printSln(s1));
		s1 = (a1.ret().ret().ret().ret().ret().getName());
		(System.printSln(s1));
		
		
		s1 = b1.ret().getName();
		(System.printSln(s1));
		
		s1 = (b1.ret().getName());
		(System.printSln(s1));

		s1 = (b1.ret().ret().ret().ret().ret().getName());
		(System.printSln(s1));

		s1 = ((new B()).ret().ret().ret().ret().ret().getName());
		(System.printSln(s1));
		
		s1 = ((new Main()).ret().ret().ret().ret().ret().getName());
		(System.printSln(s1));
		
		s1= new B().ret().ret().ret().ret().ret().getName();
		(System.printSln(s1));

		s1 = new Main().ret().getName();
		(System.printSln(s1));

		s1 = Main.getName();
		(System.printSln(s1));

		s1 = (new Main()).getName();
		(System.printSln(s1));
	}
}
