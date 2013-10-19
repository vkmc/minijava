package SyntacticAnalyzer;

/*
 * Excepción lanzada al ocurrir un error sintáctico.
 * E.g. regla mal formada
 * 
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
class SyntacticException extends Exception {

    /**
     * Constructor de la clase SyntacticException.
     *
     * @param exc
     */
    public SyntacticException(String exc) {
        super(exc);
    }
}
