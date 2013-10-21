
package SemanticAnalyzer;

import java.util.LinkedList;

/**
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class CallNode extends PrimaryNode {

    protected Token id;
    protected LinkedList<ExpressionNode> expressionList;
    
    public CallNode(SymbolTable st, Token id, LinkedList<ExpressionNode> args) {
        super(st);
        this.id = id;
        expressionList = args;
    }
    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
