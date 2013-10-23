package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.Token;

/**
 * Representacion de un nodo If-Then-Else
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class IfThenElseNode extends IfThenNode {

    protected SentenceNode sentenceElse;

    public IfThenElseNode(SymbolTable symbolTable, ExpressionNode condition, SentenceNode sentenceIf, SentenceNode sentenceElse, Token token) {
        super(symbolTable, condition, sentenceIf, token);
        this.sentenceElse = sentenceElse;
    }

    @Override
    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
