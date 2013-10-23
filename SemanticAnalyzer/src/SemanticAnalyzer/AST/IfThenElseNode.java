package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
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
    public void checkNode() throws SemanticException {
        condition.checkNode();
        
        if (!condition.getExpressionType().getTypeName().equals("boolean")) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: La condicion debe ser de tipo boolean. Se encontro: " + condition.getExpressionType().getTypeName() + " .");
        }
        
        sentenceIf.checkNode();
        sentenceElse.checkNode();
    }
}
