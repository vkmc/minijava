package IntermediateCodeGeneration.AST;

import IntermediateCodeGeneration.SemanticException;
import IntermediateCodeGeneration.Token;
import IntermediateCodeGeneration.SymbolTable.SymbolTable;

/**
 * Representacion de un nodo sentencia simple
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class SimpleSentenceNode extends SentenceNode {

    protected ExpressionNode expression;

    public SimpleSentenceNode(SymbolTable symbolTable, ExpressionNode expression, Token token) {
        super(symbolTable, token);
        this.expression = expression;
    }

    @Override
    public void checkNode() throws SemanticException {
        expression.checkNode();
        sentenceType = expression.getExpressionType();
    }

    @Override
    public void generateCode() throws SemanticException {
        expression.setICG(ICG);
        expression.generateCode();

        if (getSentenceType().getTypeName().equals("void")) {
            // Si la sentencia es void, no es necesario hacer un POP
        } else {
            ICG.GEN("POP", "Desapilamos el resultado de la expresion");
        }
    }
}
