package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.Token;
import java.util.LinkedList;

/**
 * Representacion de un nodo expresion-llamada
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class ExpressionCallNode extends PrimaryNode {

    protected ExpressionNode expression;
    protected LinkedList<CallNode> callList;

    public ExpressionCallNode(SymbolTable symbolTable, ExpressionNode expression, Token token) {
        super(symbolTable, token);
        this.expression = expression;
    }

    public ExpressionCallNode(SymbolTable symbolTable, ExpressionNode expression, LinkedList<CallNode> callList, Token token) {
        super(symbolTable, token);
        this.expression = expression;
        this.callList = callList;
    }

    @Override
    public void checkNode() {
    }
}
