
package SemanticAnalyzer;

/**
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class BinaryExpressionNode extends ExpressionNode {
    
    protected Token operator;
    protected ExpressionNode left;
    protected ExpressionNode right;
    
    public BinaryExpressionNode(SymbolTable st, Token op, ExpressionNode exp1, ExpressionNode exp2) {
        super(st);
        operator = op;
        left = exp1;
        right = exp2;
    }

    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
