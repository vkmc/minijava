package SemanticAnalyzer;

/**
 * Representacion de la entrada de una variable de instancia
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class InstanceVariableEntry extends VariableEntry {

    public InstanceVariableEntry(String instanceVariableName, Type type, int lineNumber) {
        super(instanceVariableName, type, lineNumber);
    }
}
