package SemanticAnalyzer;

/**
 * Representacion de la entrada de un parametro
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class ParameterEntry extends VariableEntry {

    public ParameterEntry(String parameterName, Type type, int lineNumber) {
        super(parameterName, type, lineNumber);
    }
}
