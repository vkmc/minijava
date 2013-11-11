package SemanticAnalyzer.SymbolTable;

import SemanticAnalyzer.SymbolTable.Type.Type;
import SemanticAnalyzer.AST.BlockNode;
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
    protected int lineNumber;
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
    }

    /**
     * Retorna el numero de linea de la declaracion del metodo
     *
     * @return lineNumber
     */
    public int getLineNumber() {
        return lineNumber;
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
}
