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
    public static void main(String[] args) {
//        if (args.length != 2) {
//            System.err.println("Cantidad de argumentos inválida.");
//            System.err.println("Uso: java -jar ICG.jar <IN_FILE> [<OUT_FILE>]");
//            return;
//        }

        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer("/home/vkmc/NetBeansProjects/minijava/IntermediateCodeGeneration/tests/success/clase_atributo.java", "/home/vkmc/NetBeansProjects/minijava/IntermediateCodeGeneration/tests/success/clase_atributo.txt");
        try {
            semanticAnalyzer.checkSemantics();
        } catch (SemanticException | SyntacticException | LexicalException exc) {
            System.err.println(exc);
        }
    }
}
