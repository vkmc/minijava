
package SemanticAnalyzer;

/**
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class ReturnNode extends SentenceNode {

    protected Token returnToken;
    
    public ReturnNode(SymbolTable st, Token t) {
        super(st);
        returnToken = t;
    }

    @Override
    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
