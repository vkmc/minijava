
package SemanticAnalyzer;

/**
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class AssignNode extends SentenceNode {

    protected IdNode left;
    protected ExpressionNode right;
    
    public AssignNode(SymbolTable st, IdNode id, ExpressionNode expr) {
        super(st);
        left = id;
        right = expr;
    }

    @Override
    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
