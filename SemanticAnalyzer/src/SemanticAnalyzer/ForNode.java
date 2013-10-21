
package SemanticAnalyzer;

import java.util.LinkedList;

/**
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class ForNode extends SentenceNode {

    protected AssignNode init;
    protected ExpressionNode condition;
    protected ExpressionNode increment;
    protected SentenceNode sentence;
    
    public ForNode(SymbolTable st, AssignNode i, ExpressionNode cond, ExpressionNode inc, SentenceNode s) {
        super(st);
        init = i;
        condition = cond;
        increment = inc;
        sentence = s;
    }

    @Override
    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
