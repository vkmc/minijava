package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.SymbolTable.Type.*;
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

    public BinaryExpressionNode(SymbolTable symbolTable, Token op, ExpressionNode exp1, ExpressionNode exp2, Token token) {
        super(symbolTable, token);
        operator = op;
        left = exp1;
        right = exp2;
    }

    @Override
    public void checkNode() throws SemanticException {
        left.checkNode();
        right.checkNode();

        String operatorLexeme = operator.getLexeme();
        if (operatorLexeme.equals("+") || operatorLexeme.equals("-") || operatorLexeme.equals("*") || operatorLexeme.equals("/") || operatorLexeme.equals("%")) {
            if (!left.getExpressionType().getTypeName().equals("int")) {
                throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: El operador binario " + operatorLexeme + " no puede aplicarse a la subexpresion de tipo " + left.getExpressionType().getTypeName() + ". Se esperaba una subexpresion de tipo entero.");
            } else if (!right.getExpressionType().getTypeName().equals("int")) {
                throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: El operador binario " + operatorLexeme + " no puede aplicarse a la subexpresion de tipo " + right.getExpressionType().getTypeName() + ". Se esperaba una subexpresion de tipo entero.");
            } else {
                Type type = new IntegerType();
                this.setExpressionType(type);
            }
        } else if (operatorLexeme.equals("&&") || operatorLexeme.equals("||")) {
            if (!left.getExpressionType().getTypeName().equals("boolean")) {
                throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: El operador binario " + operatorLexeme + " no puede aplicarse a la subexpresion de tipo " + left.getExpressionType().getTypeName() + ". Se esperaba una subexpresion de tipo boolean.");
            } else if (!right.getExpressionType().getTypeName().equals("boolean")) {
                throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: El operador binario " + operatorLexeme + " no puede aplicarse a la subexpresion de tipo " + right.getExpressionType().getTypeName() + ". Se esperaba una subexpresion de tipo boolean.");
            } else {
                Type type = new BooleanType();
                this.setExpressionType(type);
            }
        } else if (operatorLexeme.equals("==") || operatorLexeme.equals("!=")) {
            if (!left.getExpressionType().checkConformity(right.getExpressionType(), symbolTable) && !right.getExpressionType().checkConformity(left.getExpressionType(), symbolTable)) {
                throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: Los tipos de las subexpresiones no son conformantes. La subexpresion a la izquierda es de tipo " + left.getExpressionType().getTypeName() + " y la subexpresion a la derecha es de tipo " + right.getExpressionType().getTypeName());
            } else {
                Type type = new BooleanType();
                this.setExpressionType(type);
            }
        } else if (operatorLexeme.equals(">") || operatorLexeme.equals(">=") || operatorLexeme.equals("<=") || operatorLexeme.equals("<")) {
            if (!left.getExpressionType().getTypeName().equals("int")) {
                throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: El operador binario " + operatorLexeme + " no puede aplicarse a la subexpresion de tipo " + left.getExpressionType().getTypeName() + ". Se esperaba una subexpresion de tipo entero.");
            } else if (!right.getExpressionType().getTypeName().equals("int")) {
                throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: El operador binario " + operatorLexeme + " no puede aplicarse a la subexpresion de tipo " + right.getExpressionType().getTypeName() + ". Se esperaba una subexpresion de tipo entero.");
            } else {
                Type type = new BooleanType();
                this.setExpressionType(type);
            }
        }
    }
}
