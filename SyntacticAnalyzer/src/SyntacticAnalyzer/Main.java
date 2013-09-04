/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SyntacticAnalyzer;

/**
 *
 * @author vkmc
 */
public class Main {
    
       /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         if (args.length == 0 || args.length > 1) {
            System.err.println("Cantidad de argumentos invalida.");
            System.err.println("Uso: java -jar SyntacticAnalyzer <IN_FILE>");
        } else {
            Parser parser = new Parser(args[0]);
            try {
                parser.analize();
                System.out.println("El analizador sintactico fue exitoso. El programa no contiene errores sintacticos o lexicos.\n");
            } catch (SyntacticException exc) {
                System.err.println("Error sintactico: " + exc.getMessage());
            } catch (LexicalException exc) {
                System.err.println("Error lexico: " + exc.getMessage());
            }
            
        }
    }
    
}
