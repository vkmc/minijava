package SyntacticAnalyzer;

/**
 * CLI para el analizador sintáctico.
 * 
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class Main {
    
    /**
     * Constructor de la clase Main.
     * 
     * @param args
     */
    public static void main(String[] args) {
         if (args.length == 0 || args.length > 1) {
            System.err.println("Cantidad de argumentos invalida.");
            System.err.println("Uso: java -jar SyntacticAnalyzer.jar <IN_FILE>");
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
