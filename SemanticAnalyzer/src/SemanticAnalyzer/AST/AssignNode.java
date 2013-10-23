package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.Token;

/**
 * Representacion de la asignacion
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class AssignNode extends SentenceNode {

    protected IdNode left;
    protected ExpressionNode right;

    public AssignNode(SymbolTable symbolTable, IdNode id, ExpressionNode expr, Token token) {
        super(symbolTable, token);
        left = id;
        right = expr;
    }

    @Override
    public void checkNode() throws SemanticException {
        String currentClass = symbolTable.getCurrentClass();
        String currentMethod = symbolTable.getCurrentMethod();
        String id = left.getId().getToken();

        left.checkNode();

        if (symbolTable.getClassEntry(currentClass).getInstanceVariableEntry(id, 0) != null
                || symbolTable.getClassEntry(currentClass).getMethodEntry(currentMethod).getLocalVariableEntry(id, 0) != null
                || symbolTable.getClassEntry(currentClass).getMethodEntry(currentMethod).getParameterEntry(id, 0) != null) {
            if (!left.getExpressionType().checkConformity(right.getExpressionType())) {
                throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: No puede asignarse una expresion de tipo " + left.getExpressionType().getTypeName() + " a una variable de tipo " + right.getExpressionType().getTypeName() + ".");
            }
        } else {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: La variable " + left.getId().getLexeme() + " no esta declarada.");
        }

    }
}
