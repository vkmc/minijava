package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.Token;

/**
 * Representacion de las expresiones unarias
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class UnaryExpressionNode extends ExpressionNode {

    private Token operator;
    private ExpressionNode right;

    public UnaryExpressionNode(SymbolTable systemTable, Token operator, ExpressionNode expression, Token token) {
        super(systemTable, token);
        this.operator = operator;
        right = expression;
    }

    @Override
    public void checkNode() throws SemanticException {
        if (operator.getLexeme().equals("+") || operator.getLexeme().equals("-")) {
            if (!right.getExpressionType().equals("intLiteral")) {
                throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: El operador unario " + token.getLexeme() + " no puede aplicarse a la expresion de tipo " + right.getExpressionType() + ". Se esperaba una expresion de tipo entero.");
            }
        } else if (operator.getLexeme().equals("!")) {
            if (!right.getExpressionType().equals("booleanLiteral")) {
                throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: El operador unario " + token.getLexeme() + " no puede aplicarse a la expresion de tipo " + right.getExpressionType() + ". Se esperaba una expresion de tipo boolean.");
            }
        }
    }
}
