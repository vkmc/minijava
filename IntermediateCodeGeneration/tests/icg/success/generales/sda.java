
class A{
	var int a;

	A(int _a){
		(constA(_a));
	}

	dynamic void constA(int _a){
		a = _a;
	} 

	dynamic int getA(){
		return a;
	}

	dynamic void imprimir(){
		(System.printSln("Objeto A: "));
		(System.printS("VariableI A: "));
		(System.printIln(getA()));
		(System.println());
	}
 	
}

class B extends A{
	var int b;

	B(int _a, int _b){
		(constA(_a));
		(constB(_b));
	}

	dynamic void constB(int _b){
		b = _b;
	}
	
	dynamic int getB(){
		return b;
	}	
	
	dynamic void imprimir(){
		(System.printSln("Objeto B (extiende a A): "));
		(System.printS("VariableI A: "));
		(System.printIln(getA()));
		(System.printS("VariableI B: "));
		(System.printIln(getB()));
		(System.println());
	}

}

class C extends B{
	var int c;

	C(int _a, int _b, int _c){
		(constA(_a));
		(constB(_b));
		(constC(_c));
	}

	dynamic void constC(int _c){
		c = _c;
	}

	dynamic int getC(){
		return c;
	}

	dynamic void imprimir(){
		(System.printSln("Objeto C (extiende a B):"));
		(System.printS("VariableI A: "));
		(System.printIln(getA()));
		(System.printS("VariableI B: "));
		(System.printIln(getB()));
		(System.printS("VariableI C: "));
		(System.printIln(getC()));		
		(System.println());
	}
}

class MAIN{
	static void main()
		var A a;
		var B b;
		var C c;
	{
		a = new A(1);
		b = new B(1,2);
		c = new C(1,2,3);
		(a.imprimir());	
		(b.imprimir());
		(c.imprimir());	
	}
}
