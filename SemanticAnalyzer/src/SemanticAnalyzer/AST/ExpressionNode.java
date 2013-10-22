
package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.SymbolTable.Type.Type;

/**
 * Representacion de un nodo expresion
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public abstract class ExpressionNode {
    
    protected SymbolTable symbolTable;
    protected Type expressionType;
    
    public ExpressionNode(SymbolTable st) {
        symbolTable = st;
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
    
    public void setSymbolTable(SymbolTable st) {
        symbolTable = st;
    }
    
    public abstract void checkNode() throws SemanticException;
}
