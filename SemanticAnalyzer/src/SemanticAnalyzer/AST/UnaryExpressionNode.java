package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.SymbolTable.Type.BooleanType;
import SemanticAnalyzer.SymbolTable.Type.Type;

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
        right.checkNode();

        if (operator.getLexeme().equals("+") || operator.getLexeme().equals("-")) {
            if (!right.getExpressionType().getTypeName().equals("int")) {
                throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: El operador unario " + token.getLexeme() + " no puede aplicarse a la expresion de tipo " + right.getExpressionType().getTypeName() + ". Se esperaba una expresion de tipo entero.");
            }
            Type aType = new BooleanType();
        } else if (operator.getLexeme().equals("!")) {
            if (!right.getExpressionType().getTypeName().equals("boolean")) {
                throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: El operador unario " + token.getLexeme() + " no puede aplicarse a la expresion de tipo " + right.getExpressionType().getTypeName() + ". Se esperaba una expresion de tipo boolean.");
            }
            Type aType = new BooleanType();
            setExpressionType(aType);
        }
    }
}
