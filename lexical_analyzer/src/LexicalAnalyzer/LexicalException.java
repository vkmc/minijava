package LexicalAnalyzer;

/*
 * Excepción lanzada al ocurrir un error léxico.
 * E.g. caracter no perteneciente al alfabeto
 * 
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class LexicalException extends Exception {

    public LexicalException(String e) {
        super(e);
    }
}
