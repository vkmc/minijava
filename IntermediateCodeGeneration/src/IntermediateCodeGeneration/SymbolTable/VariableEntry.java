package IntermediateCodeGeneration.SymbolTable;

import IntermediateCodeGeneration.SymbolTable.Type.Type;

/**
 * Representacion de la entrada de una variable
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public abstract class VariableEntry {

    // guardar token del tipo y de la variable
    private String variableName;
    private Type variableType;
    private int lineNumber, offset;

    public VariableEntry(String variableName, Type variableType, int lineNumber) {
        this.variableName = variableName;
        this.variableType = variableType;
        this.lineNumber = lineNumber;
        offset = 0;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public Type getType() {
        return variableType;
    }

    public void setType(Type variableType) {
        this.variableType = variableType;
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
