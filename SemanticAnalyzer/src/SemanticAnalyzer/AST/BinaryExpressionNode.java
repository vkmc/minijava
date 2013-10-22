package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.Token;

/**
 * Representacion de una expresion binaria
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class BinaryExpressionNode extends ExpressionNode {

    protected Token operator;
    protected ExpressionNode left;
    protected ExpressionNode right;

    public BinaryExpressionNode(SymbolTable st, Token op, ExpressionNode exp1, ExpressionNode exp2) {
        super(st);
        operator = op;
        left = exp1;
        right = exp2;
    }

    @Override
    public void checkNode() throws SemanticException {
        String operatorLexeme = operator.getLexeme();
        if (operatorLexeme.equals("+") || operatorLexeme.equals("-") || operatorLexeme.equals("*") || operatorLexeme.equals("/") || operatorLexeme.equals("%")) {
            if (!left.getExpressionType().equals("intLiteral")) {
                throw new SemanticException("Linea: " + operator.getLineNumber() + " - Error semantico: El operador binario " + operatorLexeme + " no puede aplicarse a la subexpresion de tipo " + left.getExpressionType() + ". Se esperaba una subexpresion de tipo entero.");
            } else if (!right.getExpressionType().equals("intLiteral")) {
                throw new SemanticException("Linea: " + operator.getLineNumber() + " - Error semantico: El operador binario " + operatorLexeme + " no puede aplicarse a la subexpresion de tipo " + right.getExpressionType() + ". Se esperaba una subexpresion de tipo entero.");
            } else {
                this.setExpressionType("intLiteral");
            }
        } else if (operatorLexeme.equals("&&") || operatorLexeme.equals("||")) {
            if (!left.getExpressionType().equals("booleanLiteral")) {
                throw new SemanticException("Linea: " + operator.getLineNumber() + " - Error semantico: El operador binario " + operatorLexeme + " no puede aplicarse a la subexpresion de tipo " + left.getExpressionType() + ". Se esperaba una subexpresion de tipo boolean.");
            } else if (!right.getExpressionType().equals("booleanLiteral")) {
                throw new SemanticException("Linea: " + operator.getLineNumber() + " - Error semantico: El operador binario " + operatorLexeme + " no puede aplicarse a la subexpresion de tipo " + right.getExpressionType() + ". Se esperaba una subexpresion de tipo boolean.");
            } else {
                this.setExpressionType("booleanLiteral");
            }
        } else if (operatorLexeme.equals("==") || operatorLexeme.equals("!=")) {
            if (!symbolTable.checkConformity(left.getExpressionType(), right.getExpressionType()) && !symbolTable.checkConformity(right.getExpressionType(), left.getExpressionType())) {
                throw new SemanticException("Linea: " + operator.getLineNumber() + " - Error semantico: Los tipos de las subexpresiones no son conformantes. La subexpresion a la izquierda es de tipo " + left.getExpressionType() + " y la subexpresion a la derecha es de tipo " + right.getExpressionType());
            } else {
                this.setExpressionType("booleanLiteral");
            }
        } else if (operatorLexeme.equals(">") || operatorLexeme.equals(">=") || operatorLexeme.equals("<=") || operatorLexeme.equals("<")) {
            if (!left.getExpressionType().equals("intLiteral")) {
                throw new SemanticException("Linea: " + operator.getLineNumber() + " - Error semantico: El operador binario " + operatorLexeme + " no puede aplicarse a la subexpresion de tipo " + left.getExpressionType() + ". Se esperaba una subexpresion de tipo entero.");
            } else if (!right.getExpressionType().equals("intLiteral")) {
                throw new SemanticException("Linea: " + operator.getLineNumber() + " - Error semantico: El operador binario " + operatorLexeme + " no puede aplicarse a la subexpresion de tipo " + right.getExpressionType() + ". Se esperaba una subexpresion de tipo entero.");
            } else {
                this.setExpressionType("booleanLiteral");
            }
        }
    }
}
