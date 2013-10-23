package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.Token;
import java.util.LinkedList;

/**
 * Representacion de una llamada hecha sobre una expresion
 * 
 * E.g. id.method1(arg1, arg2) 
 *      id.method2() 
 *      id.method1(arg1, arg2).method2()
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class IdExpressionCallNode extends PrimaryNode {

    protected IdNode id;
    protected LinkedList<ExpressionNode> expressionList;
    protected LinkedList<CallNode> callList;

    public IdExpressionCallNode(SymbolTable symbolTable, IdNode id, Token token) {
        super(symbolTable, token);
        this.id = id;

    }

    public IdExpressionCallNode(SymbolTable symbolTable, IdNode id, LinkedList<CallNode> callList, Token token) {
        super(symbolTable, token);
        this.id = id;
        this.callList = callList;
    }

    public IdExpressionCallNode(SymbolTable symbolTable, IdNode id, LinkedList<ExpressionNode> expressionList, LinkedList<CallNode> callList, Token token) {
        super(symbolTable, token);
        this.id = id;
        this.expressionList = expressionList;
        this.callList = callList;
    }

    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}