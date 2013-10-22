
package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.SymbolTable;

/**
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class IfThenElseNode extends IfThenNode {

    protected SentenceNode sentenceElse;
    
    public IfThenElseNode(SymbolTable st, ExpressionNode cond, SentenceNode sentIf, SentenceNode sentElse) {
        super(st, cond, sentIf);
        sentenceElse = sentElse;
    }

    @Override
    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
