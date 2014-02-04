// Falla porque los parametros actuales de la llamada a conformityTest no conforman a sus parametros formales.

class Test {
	static void main()
	var Test x;
	{
		x = new Test();
		(x.conformityTest(777, "will fail"));
	}

	static void conformityTest(String a, int b) {
	
	}
}