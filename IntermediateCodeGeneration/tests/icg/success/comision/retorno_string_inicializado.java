class A { 
	var String s;      
	
	A (String _s)
	{
		s = _s;
	}

	dynamic String getString0()
	{
		return s;
	}
      
	dynamic String getString1() 
	var String s1;
	{ 
		s1 = "Compiladores e Interpretes ";
          	return s1;
    	}
    
    	dynamic String getString2()
	{ 
		return "Minijava ";   
    	}
    
    	dynamic String getString3() 
    	var String s2;
	{
		s2 = "2013";
       		return s2;
	}
     
	static void main() 
	var A a1; 
	var String s3;
	{ 
		(System.printSln("A :: main() "));
		a1 = new A("Hola, mundo!");
		(System.printSln(a1.getString0()));
        	s3 = a1.getString1(); 
        	(System.printSln(s3));      
        	s3 = a1.getString2(); 
        	(System.printSln(s3));
        	s3 = a1.getString3(); 
        	(System.printSln(s3));		
	} 
} 
