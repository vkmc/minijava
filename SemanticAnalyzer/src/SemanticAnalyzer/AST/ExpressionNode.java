package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.SymbolTable.Type.Type;
import SemanticAnalyzer.Token;

/**
 * Representacion de un nodo expresion
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public abstract class ExpressionNode {

    protected SymbolTable symbolTable;
    protected Type expressionType;
    protected Token token;

    public ExpressionNode(SymbolTable symbolTable, Token token) {
        this.symbolTable = symbolTable;
        this.token = token;
    }

    public Type getExpressionType() {
        return expressionType;
    }

    public void setExpressionType(Type expressionType) {
        this.expressionType = expressionType;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable; // para que?? al cuete >:o
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public abstract void checkNode() throws SemanticException;
}
