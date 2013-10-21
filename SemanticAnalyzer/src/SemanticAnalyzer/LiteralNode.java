
package SemanticAnalyzer;

/**
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class LiteralNode extends PrimaryNode {

    protected Token literal;
 
    public LiteralNode(SymbolTable st, Token lit) {
        super(st);
        literal = lit;
        expressionType = literal.getLexeme();
    }
    
    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
