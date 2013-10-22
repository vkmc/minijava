package SemanticAnalyzer.SymbolTable;

import SemanticAnalyzer.SymbolTable.Type.Type;

/**
 * Representacion de la entrada de una variable de instancia
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class InstanceVariableEntry extends VariableEntry {

    public InstanceVariableEntry(String instanceVariableName, Type instanceVariableType, int lineNumber) {
        super(instanceVariableName, instanceVariableType, lineNumber);
    }
}
