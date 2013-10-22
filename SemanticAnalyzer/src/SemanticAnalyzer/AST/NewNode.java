
package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.SymbolTable;
import java.util.LinkedList;

/**
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class NewNode extends PrimaryNode {
    protected IdNode id;
    protected LinkedList<ExpressionNode> expressionList;
    protected LinkedList<CallNode> callList;
    
    public NewNode(SymbolTable st, IdNode id, LinkedList<ExpressionNode> args, LinkedList<CallNode> calls) {
        super(st);
        this.id = id;
        expressionList = args;
        callList = calls;
    }
    
    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
