class Clase 
{
	// Atributos
	var boolean b1;
	var char c1, c2;
	var int i1, i2, i3;
	var String s1;
	
	// Constructor
	Clase()
	var int l1, l2;
	{
		b1 = (3 * 4) >= (2 * 6) || (3 != 8) && (7 <= 10);
		
		c1 = '1';
		c2 = '\2';
		
		i1 = 11 * 23 + (58/13) % 21;
		i2 = 4/8 + (15*(((16)/23)) * 42);
		i3 = (potencia (i1, i2));
		
		l1 = potencia(i1, 2);
		l2 = potencia(i2, 4);
		
		/*
		Lorem ipsum
		dolor
		sit
		amet
		consectetur adipiscing elit.
		Quisque in nisl enim. Suspendisse leo elit, hendrerit gravida congue id, pretium tempor orci.
		*/
		
		s1 = "Hola mundo";
	}
	
	// Metodos
	static void main()
	{
	}
	
	dynamic String exec(int x, int y) 
	var int a, b;
	{
		a = 0;
		b = 0;
		
		while( a < x ) {
			b = y;
			a = a + 1;
		}
		
		// if(b >= 10)
		if(b >= potencia(x, y)) // esto es valido en minijava? deberia ver el retorno de potencia(x,y)?
			return "Mayor que potencia";
		else
			return "Menor que potencia";
		
		return "Un string";
	}
	
	
	
	static int potencia(int x, int y)
	//Ut semper dui sapien, nec porttitor diam cursus id. /* */
	/**/
	var int pot, aux;
	{
		if(y == 0)
			return 1;
		
		if(x == 0)
			return 0;
		
		pot = 1;
		
		return x;
	}
	
}
