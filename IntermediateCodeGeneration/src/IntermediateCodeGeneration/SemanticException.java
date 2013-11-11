package IntermediateCodeGeneration;

/*
 * Excepción lanzada al ocurrir un error semántico.
 * 
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class SemanticException extends Exception {

    /**
     * Constructor de la clase SemanticException.
     *
     * @param exc
     */
    public SemanticException(String exc) {
        super(exc);
    }
}