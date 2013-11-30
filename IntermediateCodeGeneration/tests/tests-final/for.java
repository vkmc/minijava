/**
La intencion de dicho test es probar el correcto funcionamiento del for
Salida esperada:
FOR:
0-1-2-3-4-5
**/
class A{
	static void met1(int j)
		var int i;	
	{
		for(i = 0; i < j; i+1){
			(System.printI(i));		
			(System.printS("-"));
		}
		(System.printI(j));
	}

	static void main()
		var int a;	
	{
		(System.printSln("FOR:"));
		(met1(5));
	}
}
