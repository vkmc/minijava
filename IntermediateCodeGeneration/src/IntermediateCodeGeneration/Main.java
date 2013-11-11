package IntermediateCodeGeneration;

/**
 * CLI para el analizador semantico
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
    public static void main(String[] args) throws SemanticException {
        if (args.length == 0 || args.length > 1) {
            System.err.println("Cantidad de argumentos invalida.");
            System.err.println("Uso: java -jar SemanticAnalyzer.jar <IN_FILE>");
        } else {
            SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(args[0]);
            try {
                semanticAnalyzer.checkSemantics();
            } catch (SemanticException | SyntacticException | LexicalException exc) {
                System.err.println(exc);
            }
        }
    }
}
