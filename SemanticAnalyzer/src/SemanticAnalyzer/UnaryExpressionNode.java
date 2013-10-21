
package SemanticAnalyzer;

/**
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class UnaryExpressionNode extends ExpressionNode {
    
    protected Token operator;
    protected ExpressionNode right;
    
    public UnaryExpressionNode(SymbolTable st, Token op, ExpressionNode exp) {
        super(st);
        operator = op;
        right = exp;
    }

    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
