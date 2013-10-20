package SemanticAnalyzer;

/**
 * Representacion de la entrada de una variable local
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class LocalVariableEntry extends VariableEntry {
    
    public LocalVariableEntry(String localVariableName, Type type, int lineNumber) {
        super(localVariableName, type, lineNumber);
    }
    
}
