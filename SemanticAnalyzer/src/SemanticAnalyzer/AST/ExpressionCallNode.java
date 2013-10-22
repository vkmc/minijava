
package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.SymbolTable;
import java.util.LinkedList;

/**
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class ExpressionCallNode extends PrimaryNode {
    
    protected ExpressionNode expression;
    protected LinkedList<CallNode> callList;
    
    public ExpressionCallNode(SymbolTable st, ExpressionNode exp) {
        super(st);
        expression = exp;
    }
    
    public ExpressionCallNode(SymbolTable st, ExpressionNode exp, LinkedList<CallNode> calls) {
        super(st);
        expression = exp;
        callList = calls;
    }
    
    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
