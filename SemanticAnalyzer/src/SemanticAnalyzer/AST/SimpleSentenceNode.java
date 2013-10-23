package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.Token;

/**
 * Representacion de un nodo sentencia simple
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class SimpleSentenceNode extends SentenceNode {

    protected ExpressionNode expression;

    public SimpleSentenceNode(SymbolTable systemTable, ExpressionNode expression, Token token) {
        super(systemTable, token);
        this.expression = expression;
    }

    @Override
    public void checkNode() throws SemanticException {
        expression.checkNode();
        sentenceType = expression.getExpressionType();
    }
}
