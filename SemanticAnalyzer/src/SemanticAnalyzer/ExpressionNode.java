
package SemanticAnalyzer;

/**
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public abstract class ExpressionNode {
    
    protected SymbolTable symbolTable;
    protected String expressionType;
    
    public ExpressionNode(SymbolTable st) {
        symbolTable = st;
    }
    
    public String getExpressionType() {
        return expressionType;
    }
    
    public void setExpressionType(String type) {
        expressionType = type;
    }
    
    public SymbolTable getSymbolTable() {
        return symbolTable; // para que??
    }
    
    public void setSymbolTable(SymbolTable st) {
        symbolTable = st;
    }
    
    public abstract void checkNode();
}
