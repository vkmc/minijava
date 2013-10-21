
package SemanticAnalyzer;

/**
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class ReturnExpNode extends ReturnNode {

    protected ExpressionNode expression;
    
    public ReturnExpNode(SymbolTable st, Token t, ExpressionNode expr) {
        super(st, t);
        expression = expr;
    }

    @Override
    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
