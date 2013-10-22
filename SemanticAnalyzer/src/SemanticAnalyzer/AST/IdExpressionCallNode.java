
package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.Token;
import java.util.LinkedList;

/**
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class IdExpressionCallNode extends PrimaryNode {
    
    protected Token id;
    protected LinkedList<ExpressionNode> expressionList;
    protected LinkedList<CallNode> callList;
    
    public IdExpressionCallNode(SymbolTable st, Token id) {
        super(st);
        this.id = id;
        
    }

   
    public IdExpressionCallNode(SymbolTable st, Token id, LinkedList<CallNode> calls) {
        super(st);
        this.id = id;
        callList = calls;
    }

    public IdExpressionCallNode(SymbolTable st, Token id, LinkedList<ExpressionNode> expressions, LinkedList<CallNode> calls) {
        super(st);
        this.id = id;
        expressionList = expressions;
        callList = calls;
    }
    
    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}