package IntermediateCodeGeneration.SymbolTable;

import IntermediateCodeGeneration.AST.BlockNode;
import IntermediateCodeGeneration.ICGenerator;
import IntermediateCodeGeneration.SymbolTable.Type.Type;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * Representacion de la entrada de servicio
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public abstract class ServiceEntry {

    protected String serviceName;
    protected String className;
    protected SymbolTable symbolTable;
    protected ICGenerator ICG;
    protected int lineNumber;
    protected int offset;
    protected LinkedHashMap<String, ParameterEntry> parametersTable;
    protected LinkedHashMap<String, LocalVariableEntry> localVariablesTable;
    protected BlockNode body;

    public ServiceEntry(String serviceName, String className, SymbolTable symbolTable, int lineNumber) {
        this.serviceName = serviceName;
        this.className = className;
        this.symbolTable = symbolTable;
        this.lineNumber = lineNumber;
        parametersTable = new LinkedHashMap<>();
        localVariablesTable = new LinkedHashMap<>();
        body = null;
        offset = -1;
    }

    /**
     * Retorna el numero de linea de la declaracion del metodo
     *
     * @return lineNumber
     */
    public int getLineNumber() {
        return lineNumber;
    }
    
    public String getClassName() {
        return className;
    }

    /**
     * Crea e inserta una nueva entrada de parametro en la tabla de parametros
     *
     * @param parameterName nombre del parametro a insertar
     * @param type tipo del parametro a insertar
     * @param lineNumber numero de linea
     */
    public void addParameterEntry(String parameterName, Type parameterType, int lineNumber) {
        ParameterEntry parameter = new ParameterEntry(parameterName, parameterType, lineNumber);
        parametersTable.put(parameterName, parameter);
    }

    /**
     * Retorna el conjunto de parametros del metodo
     *
     * @return parametersTable
     */
    public LinkedHashMap<String, ParameterEntry> getParameters() {
        return parametersTable;
    }

    /**
     * Retorna la entrada de parametro de un parametro deseado
     *
     * @param parameterName
     * @param lineNumber
     * @return entrada del parametro deseado si este existe, null en caso
     * contrario
     */
    public ParameterEntry getParameterEntry(String parameterName) {
        return parametersTable.get(parameterName);
    }

    /**
     * Crea e inserta una nueva entrada de variable local en la tabla de
     * variables locales
     *
     * @param localVariableName nombre de la variable local a insertar
     * @param type tipo del parametro a insertar
     * @param lineNumber numero de linea
     */
    public void addLocalVariableEntry(String localVariableName, Type localVariableType, int lineNumber) {
        LocalVariableEntry localVariable = new LocalVariableEntry(localVariableName, localVariableType, lineNumber);
        localVariablesTable.put(localVariableName, localVariable);
    }

    /**
     * Retorna el conjunto de variables locales del metodo
     *
     * @return parametersTable
     */
    public LinkedHashMap<String, LocalVariableEntry> getLocalVariables() {
        return localVariablesTable;
    }

    /**
     * Retorna la entrada de variable local de una variable local deseada
     *
     * @param localVariableName
     * @return entrada de la variable local deseada si este existe, null en caso
     * contrario
     */
    public LocalVariableEntry getLocalVariableEntry(String localVariableName) {
        return localVariablesTable.get(localVariableName);
    }

    /**
     * Establece el cuerpo del servicio
     *
     * @param body
     */
    public void setBody(BlockNode body) {
        this.body = body;
    }

    /**
     * Retorna el offset del metodo
     *
     * @return offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Establece el valor pasado por parametro como offset del metodo
     *
     * @param offset
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * Establece el offset de las variables locales del metodo actual
     */
    public void setLocalVariablesOffset() {
        Collection<LocalVariableEntry> localVariables = localVariablesTable.values();
        int localVariableOffset = 0;

        for (LocalVariableEntry aLocalVariable : localVariables) {
            aLocalVariable.setOffset(localVariableOffset);
            localVariableOffset--;
        }
    }

    /**
     * Establece el offset de los parametros del metodo actual
     */
    public void setParametersOffset() {
        Collection<ParameterEntry> parameters = parametersTable.values();
        int parameterOffset = parameters.size();

        for (ParameterEntry aParameter : parametersTable.values()) {
            aParameter.setOffset(parameterOffset);
            parameterOffset--;
        }
    }

    /**
     * Establece el handler de generacion de codigo intermedio
     *
     * @param ICG
     */
    public void setICG(ICGenerator ICG) {
        this.ICG = ICG;
    }
}
