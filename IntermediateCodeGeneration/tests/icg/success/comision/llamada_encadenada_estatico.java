class A {

	static void main()
	var A a1;
	var int calc;
	{
		a1 = new A();
		calc = a1.calc();
	}

	dynamic int calc()
	var int ret, val;
	var String s1;
	{
		
		ret = (A.random());
		(System.printS("A :: calc() :: A.random() = "));
		(System.printIln(ret));
		
		ret = (A2.random());
		(System.printS("A :: calc() :: A2.random() = "));
		(System.printIln(ret));
		
		s1 = (A3.retA3().hello());
		(System.printS("A :: calc() :: A3.retA3().hello() = "));
		(System.printSln(s1));
		
		val = foo1().foo2().foo3();
		(System.printS("A :: calc() :: foo1().foo2().foo3() = "));
		(System.printIln(val));
		
		return ret;
	}
	
	dynamic A foo1(){
		return new A();
	}
	
	dynamic A2 foo2(){
		return new A2();
	}
	
	static int random(){
		return 11;
	}
	
	
}

class A2 {
	
	static int random()
	{
		return 100;
	}

	static int foo3()
	{
		return 120;
	}
}


class A3 {
	
	dynamic String hello(){
		return "Hello, world!";
	}
	
	static A3 retA3(){
		return new A3();
	}
}
