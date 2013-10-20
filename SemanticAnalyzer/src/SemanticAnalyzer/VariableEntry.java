package SemanticAnalyzer;

/**
 * Representacion de la entrada de una variable
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */

public abstract class VariableEntry {
    
    private String variableName;
    private Type type;
    private int lineNumber, offset;
    
    public VariableEntry(String variableName, Type type, int lineNumber) {
        this.variableName = variableName;
        this.type = type;
        this.lineNumber = lineNumber;
        offset = 0;               
    }   
}
