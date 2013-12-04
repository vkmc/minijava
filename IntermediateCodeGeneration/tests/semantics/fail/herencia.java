
class A {

    var int n;
    
    dynamic int getN() {
        return n;
    }
}

class B extends A {

    dynamic void printN(int num)
    {
        n = num;		// las variables de instancia heredadas
			        // no pueden accederse
    }

    static void main() {}
}
