package SemanticAnalyzer.SymbolTable;

import SemanticAnalyzer.SymbolTable.Type.Type;

/**
 * Representacion de la entrada de un parametro
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class ParameterEntry extends VariableEntry {

    public ParameterEntry(String parameterName, Type parameterType, int lineNumber) {
        super(parameterName, parameterType, lineNumber);
    }
}
