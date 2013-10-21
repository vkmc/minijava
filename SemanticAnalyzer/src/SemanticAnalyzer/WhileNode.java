
package SemanticAnalyzer;

/**
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class WhileNode extends SentenceNode {

    protected ExpressionNode condition;
    protected SentenceNode sentence;
    
    public WhileNode(SymbolTable st, ExpressionNode cond, SentenceNode sent) {
        super(st);
        condition = cond;
        sentence = sent;
    }

    @Override
    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
