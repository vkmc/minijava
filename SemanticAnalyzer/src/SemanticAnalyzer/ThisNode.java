
package SemanticAnalyzer;

/**
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class ThisNode extends PrimaryNode {

    protected Token thisToken;
 
    public ThisNode(SymbolTable st, Token t) {
        super(st);
        thisToken = t;
    }
    
    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}