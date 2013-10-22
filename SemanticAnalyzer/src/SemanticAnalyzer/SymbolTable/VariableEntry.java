package SemanticAnalyzer.SymbolTable;

/**
 * Representacion de la entrada de una variable
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */

public abstract class VariableEntry {
    
    private String variableName;
    private String type;
    private int lineNumber, offset;
    
    public VariableEntry(String variableName, String type, int lineNumber) {
        this.variableName = variableName;
        this.type = type;
        this.lineNumber = lineNumber;
        offset = 0;               
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
    
    
}
