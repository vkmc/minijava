package SemanticAnalyzer.SymbolTable;

/**
 * Representacion de la entrada de un parametro
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class ParameterEntry extends VariableEntry {

    public ParameterEntry(String parameterName, String type, int lineNumber) {
        super(parameterName, type, lineNumber);
    }
}
