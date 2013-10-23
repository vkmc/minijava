package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.Token;

/**
 * Representacion de un nodo while
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class WhileNode extends SentenceNode {

    protected ExpressionNode condition;
    protected SentenceNode sentence;

    public WhileNode(SymbolTable systemTable, ExpressionNode condition, SentenceNode sentence, Token token) {
        super(systemTable, token);
        this.condition = condition;
        this.sentence = sentence;
    }

    @Override
    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
