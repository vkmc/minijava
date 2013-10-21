
package SemanticAnalyzer;

import java.util.LinkedList;

/**
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class IdExpressionNode extends PrimaryNode {
    
    protected Token id;
    protected LinkedList<CallNode> callList;
    protected LinkedList<ExpressionNode> expressionList;
    
    public IdExpressionNode(SymbolTable st, Token id) {
        super(st);
        this.id = id;
        
    }

    
    public IdExpressionNode(SymbolTable st, LinkedList<CallNode> calls) {
        super(st);
        callList = calls;
    }
    
        
    public IdExpressionNode(SymbolTable st, LinkedList<CallNode> calls, LinkedList<ExpressionNode> expressions) {
        super(st);
        callList = calls;
        expressionList = expressions;
    }
    
    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}