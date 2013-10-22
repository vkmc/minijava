package SemanticAnalyzer.SymbolTable;

/**
 * Representacion de la entrada de una variable de instancia
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class InstanceVariableEntry extends VariableEntry {

    public InstanceVariableEntry(String instanceVariableName, String type, int lineNumber) {
        super(instanceVariableName, type, lineNumber);
    }
}
