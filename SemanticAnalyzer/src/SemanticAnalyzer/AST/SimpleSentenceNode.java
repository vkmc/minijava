
package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.SymbolTable;

/**
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class SimpleSentenceNode extends SentenceNode {

    protected ExpressionNode expression;
    
    public SimpleSentenceNode(SymbolTable st, ExpressionNode expr) {
        super(st);
        expression = expr;
    }

    @Override
    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
