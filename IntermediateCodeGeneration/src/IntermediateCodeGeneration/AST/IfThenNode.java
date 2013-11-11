package IntermediateCodeGeneration.AST;

import IntermediateCodeGeneration.SemanticException;
import IntermediateCodeGeneration.Token;
import IntermediateCodeGeneration.SymbolTable.SymbolTable;

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
    public void checkNode() throws SemanticException {
        condition.checkNode();

        if (!condition.getExpressionType().getTypeName().equals("boolean")) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: La condicion debe ser de tipo boolean. Se encontro: " + condition.getExpressionType().getTypeName() + " .");
        }

        sentenceIf.checkNode();
    }
}
