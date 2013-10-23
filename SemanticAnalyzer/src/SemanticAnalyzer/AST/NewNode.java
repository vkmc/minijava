package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.Token;
import java.util.LinkedList;

/**
 * Representacion de un nodo de creacion
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class NewNode extends PrimaryNode {

    protected IdNode id;
    protected LinkedList<ExpressionNode> expressionList;
    protected LinkedList<CallNode> callList;

    public NewNode(SymbolTable symbolTable, IdNode id, LinkedList<ExpressionNode> actualArgs, LinkedList<CallNode> callList, Token token) {
        super(symbolTable, token);
        this.id = id;
        expressionList = actualArgs;
        this.callList = callList;
    }

    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
