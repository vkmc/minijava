package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.Token;

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
    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
