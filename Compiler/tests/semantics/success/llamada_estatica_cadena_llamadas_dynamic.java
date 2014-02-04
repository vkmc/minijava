/*
Se invoca al metodo estatico 'invertir' al final de una cadena de llamadas dinamicas.
*/

class Reverser {
	
        static int countDigits(int number)
        var int count;
        {
                count = 0;
                while(number > 0) {
                        number = number / 10;
                        count = count + 1;
                }
               
                return count; 
        }
 
        static int invertir(int number, int digits)
        var int count, aux;
        {
                count = 0;
                for(aux = 0; aux < digits; aux + 1) {
                        count = count * 10 + number % 10;
                        number = number / 10;
                }
               
                return count;         
        }
        
        static Reverser m(){
        	return new Reverser();
        }
        
        dynamic Reverser ret1(){
        	return new Reverser();
        }
		
        dynamic Reverser ret2(){
        	return new Reverser();
        }
		
        dynamic Reverser ret3(){
        	return new Reverser();
        }
        
        dynamic void m1(Reverser inv)
        var int resu;
        {
        	resu=ret1().ret2().ret3().invertir(10,2);
        }
 
        static void main()
        var int number, digits, reversed;
        var Reverser obj;
        {
                number = 123;
                digits = countDigits(number);
                (System.printIln(digits));
                (System.printIln(reversed));
        }
}
