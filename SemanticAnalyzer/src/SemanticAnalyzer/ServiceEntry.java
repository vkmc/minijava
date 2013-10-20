package SemanticAnalyzer;

import java.util.Hashtable;

/**
 * Representacion de la entrada de servicio
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public abstract class ServiceEntry {
    
    private String serviceName;
    private int lineNumber;
    private Hashtable<String, ParameterEntry> parametersTable;
    private Hashtable<String, LocalVariableEntry> localVariablesTable;
    private BlockNode body;
    
    public ServiceEntry(String serviceName, int lineNumber) {
        this.serviceName = serviceName;
        this.lineNumber = lineNumber;
        parametersTable = new Hashtable<>();
        localVariablesTable = new Hashtable<>();
        body = null;
    }
    
    /**
     * Retorna la lista de parametros del servicio
     * @return lista de parametros del servicio
     */
    public Hashtable<String, ParameterEntry> getParameters() {
        return parametersTable;
    }
    
    /**
     * Retorna la lista de variables locales del servicio
     * @return lista de variables locales del servicio 
     */
    public Hashtable<String, LocalVariableEntry> getLocalVariables() {
        return localVariablesTable;
    }
    
    /**
     * Crea e inserta una nueva entrada de parametro en la tabla de parametros
     * @param parameterName nombre del parametro a insertar
     * @param type tipo del parametro a insertar
     * @param lineNumber numero de linea
     */
    public void addParameterEntry(String parameterName, Type type, int lineNumber) {
        ParameterEntry parameter = new ParameterEntry(parameterName, type, lineNumber);
        parametersTable.put(parameterName, parameter);
    }
    
    /**
     * Retorna la entrada de parametro de un parametro deseado
     * @param parameterName
     * @return entrada del parametro deseado si este existe, null en caso contrario
     */
    public ParameterEntry getParameterEntry(String parameterName, String lineNumber) {
        return parametersTable.get(parameterName);
    }
    
    /**
     * Crea e inserta una nueva entrada de variable local en la tabla de variables locales
     * @param localVariableName nombre de la variable local a insertar
     * @param type tipo del parametro a insertar
     * @param lineNumber numero de linea
     */
    public void addLocalVariableEntry(String localVariableName, Type type, int lineNumber) {
        LocalVariableEntry localVariable = new LocalVariableEntry(localVariableName, type, lineNumber);
        localVariablesTable.put(localVariableName, localVariable);
    }
    
    /**
     * Retorna la entrada de variable local de una variable local deseada
     * @param localVariableName
     * @return entrada de la variable local deseada si este existe, null en caso contrario
     */
    public LocalVariableEntry getParameterEntry(String localVariableName, int lineNumber) {
        return localVariablesTable.get(localVariableName);
    }
    
    /**
     * Establece el cuerpo del servicio
     * @param body 
     */
    public void setBody(BlockNode body) {
        this.body = body;
    }

    

    
    
}
