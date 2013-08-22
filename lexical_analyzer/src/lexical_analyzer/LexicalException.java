package lexical_analyzer;

/*
 * Excepción lanzada al ocurrir un error léxico.
 * E.g. caracter no perteneciente al alfabeto
 * 
 * @author Ramiro Agís
 * @author Victoria Martínez de la Cruz
 */
public class LexicalException extends Exception {
    
    public LexicalException(String exc) {
        super(exc);
    }
    
}
