package SemanticAnalyzer.SymbolTable;

import SemanticAnalyzer.SymbolTable.Type.Type;

/**
 * Representacion de la entrada de una variable local
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class LocalVariableEntry extends VariableEntry {
    
    public LocalVariableEntry(String localVariableName, Type localVariableType, int lineNumber) {
        super(localVariableName, localVariableType, lineNumber);
    }
    
}
