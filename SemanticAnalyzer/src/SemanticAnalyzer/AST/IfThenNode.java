package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.Token;

/**
 * Representacion de un nodo If-Then
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class IfThenNode extends SentenceNode {

    protected ExpressionNode condition;
    protected SentenceNode sentenceIf;

    public IfThenNode(SymbolTable symbolTable, ExpressionNode condition, SentenceNode sentenceIf, Token token) {
        super(symbolTable, token);
        this.condition = condition;
        this.sentenceIf = sentenceIf;
    }

    @Override
    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
