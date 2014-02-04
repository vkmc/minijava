class A {
	
	dynamic A ret()
	{
		return new A();
	}
	
	dynamic String getName()
	{
		return "A";
	}
}

class A2 extends A {
	dynamic String getName()
	{
		return "A2";
	}
}

class B {
	dynamic B ret()
	{
		return new B();
	}
	
	dynamic String getName(){
		return "B";
	}
}

class Main {

	static void main()
	var A a1;
	var A2 a2;
	var B b1;
	var Main m;
	var String s1;
	{
		s1 = (new A()).getName();
		a1 = new A();

		s1 = a1.getName();
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
	}
}
