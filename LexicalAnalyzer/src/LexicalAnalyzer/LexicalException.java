package LexicalAnalyzer;

/*
 * Excepción lanzada al ocurrir un error léxico.
 * E.g. caracter no perteneciente al alfabeto
 * 
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class LexicalException extends Exception {

    /**
     * Constructor de la clase LexicalException.
     *
     * @param exc
     */
    public LexicalException(String exc) {
        super(exc);
    }
}
