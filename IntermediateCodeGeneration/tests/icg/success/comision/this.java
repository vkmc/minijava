class A {

	dynamic void retvoid(char c1, A a1)
	var char c2;
	{
		return;
	}

	static void main()
	var A a1;
	var int ret;
	{
		a1 = new A();
		ret = a1.retint();
		(System.printSln("A :: main() :: ret "));
		(System.printI(ret));
	}

	dynamic int retint()
	var A a1;
	var char c1;
	var int ret;
	{
		c1 = 'a';
		(retvoid(c1, a1));
		ret = ((this).random());
		return ret;
	}
	
	dynamic A foo(){
		return new A();
	}
	
	dynamic int random(){
		return 11;
	}
}




