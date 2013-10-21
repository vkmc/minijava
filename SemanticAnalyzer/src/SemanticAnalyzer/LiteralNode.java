
package SemanticAnalyzer;

/**
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class LiteralNode extends PrimaryNode {

    protected Token literal;
 
    public LiteralNode(SymbolTable st, Token lit, String type) {
        super(st);
        literal = lit;
    }
    
    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
