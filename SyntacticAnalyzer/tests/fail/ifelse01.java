class foo {

	ctor (int n1)
		var int n2;
		{
			if (n1 != 2) ;
			else
				if (n2 % 2 == 0) {
					j = 22;
				} else
					// bloque mal cerrado, la } se toma como si fuera de afuera y el parser alcanza EOF
					(new prueba(n1,n2));
				}
		}
}

