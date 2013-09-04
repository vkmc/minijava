package SyntacticAnalyzer;

/**
 * Analizador sintáctico. Transforma su entrada en un arbol de derivación.
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class Parser {
    private Tokenizer tokenizer;
    private Token lookAhead, currentToken; 
    
    public void analize() throws SyntacticException, LexicalException {
        lookAhead = tokenizer.getToken();
        currentToken = null;
        Inicial();
    }
    private void Class() throws LexicalException, SyntacticException {
        
    }
    
//    private void Class() throws LexicalException, SyntacticException {
//        
//    }
//    
//    private void Class() throws LexicalException, SyntacticException {
//        
//    }
//    
//    private void Class() throws LexicalException, SyntacticException {
//        
//    }
//    
//    private void Class() throws LexicalException, SyntacticException {
//        
//    }
//    
//    private void Class() throws LexicalException, SyntacticException {
//        
//    }
 
}
