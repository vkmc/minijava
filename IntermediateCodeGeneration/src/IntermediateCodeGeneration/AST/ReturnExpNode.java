package IntermediateCodeGeneration.AST;

import IntermediateCodeGeneration.SemanticException;
import IntermediateCodeGeneration.Token;
import IntermediateCodeGeneration.SymbolTable.SymbolTable;

/**
 * Representacion de un nodo return-expresion
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class ReturnExpNode extends ReturnNode {

    protected ExpressionNode expression;

    public ReturnExpNode(SymbolTable symbolTable, ExpressionNode expression, Token token) {
        super(symbolTable, token);
        this.expression = expression;
    }

    @Override
    public void checkNode() throws SemanticException {
        expression.checkNode();

        String currentClass = symbolTable.getCurrentClass();
        String currentMethod = symbolTable.getCurrentMethod();

        if (!expression.getExpressionType().checkConformity(symbolTable.getClassEntry(currentClass).getMethodEntry(currentMethod).getReturnType())) {
            if (symbolTable.getClassEntry(currentClass).getMethodEntry(currentMethod).getReturnType().getTypeName().equals("void")) {
                throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: Un método de tipo void no puede retornar un valor.");
            }

            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: El tipo de la expresion retornada no es conforme al tipo de retorno del metodo actual. El tipo de la expresion retornada es '" + expression.getExpressionType().getTypeName() + "' y el tipo de retorno del metodo es '" + symbolTable.getClassEntry(currentClass).getMethodEntry(currentMethod).getReturnType().getTypeName() + "'.");
        }

        this.setSentenceType(expression.getExpressionType());
    }
}
