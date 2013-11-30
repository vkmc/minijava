class Empleado
{
	var int b;
	
	Empleado(int base)
	{
		(setBase(base));
	}
	
	dynamic void setBase(int base)
	{
		b = base;
	}
	
	dynamic int getBase()
	{
		return b;
	}
	
	dynamic int getSueldo()
	{
		return b;
	}
	
	dynamic String getNombre()
	{
		return "Empleado";
	}
}

class EmpleadoPorComision extends Empleado
{
	var int v, c;
	
	EmpleadoPorComision(int comision)
	{
		(setBase(0));
		(setVentas(0));
		(setComision(comision));
	}
	
	dynamic void setVentas(int ventas)
	{
		v = ventas;
	}
	
	dynamic void setComision(int comision)
	{
		c = comision;
	}
	
	dynamic int getVentas()
	{
		return v;
	}
	
	dynamic int getComision()
	{
		return c;
	}
	
	dynamic int getSueldo()
	var int b, v, c;
	{
		return  getBase() + getVentas() * getComision();
	}

	dynamic String getNombre()
	{
		return "EmpleadoPorComision";
	}
}

class EmpleadoPorComisionConBase extends EmpleadoPorComision
{
	EmpleadoPorComisionConBase(int base, int comision)
	{
		(setBase(base));
		(setVentas(0));
		(setComision(comision));
	}

	dynamic String getNombre()
	{
		return "EmpleadoPorComisionConBase";
	}
}

class Main
{
	static void main()
	var Empleado e;
	var EmpleadoPorComision c, b;
	{
		e = new Empleado(1000);
		(System.printS(e.getNombre()));
		(System.printC(' '));
		(System.printC(' '));
		(System.printC(' '));
		(System.printIln(e.getSueldo()));
		
		c = new EmpleadoPorComision(250);
/*		(c.setVentas(10));

		e = c;
		
		(System.printS(e.getNombre()));
		(System.printC(' '));
		(System.printC(' '));
		(System.printIln(e.getSueldo()));
		
		b = new EmpleadoPorComisionConBase(1000, 100);
		(b.setVentas(5));
		
		e = b;
		
		(System.printS(e.getNombre()));
		(System.printC(' '));
		(System.printIln(e.getSueldo())); */
	}
}

/* Autores: Calendino, Federico - Schiaffino, Martin */
