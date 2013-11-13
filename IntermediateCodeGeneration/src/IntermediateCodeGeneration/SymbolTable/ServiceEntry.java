package IntermediateCodeGeneration.SymbolTable;

import IntermediateCodeGeneration.AST.BlockNode;
import IntermediateCodeGeneration.ICGenerator;
import IntermediateCodeGeneration.SemanticException;
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
     * Retorna el nombre del servicio
     *
     * @return serviceName
     */
    public String getName() {
        return serviceName;
    }

    /**
     * Retorna el nombre de la clase en la que esta declarado el servicio
     *
     * @return
     */
    public String getClassName() {
        return className;
    }

    /**
     * Retorna el numero de linea en la que esta declarado el servicio
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

    /**
     * Control de sentencias del servicio. Se realiza solo si el servicio en
     * cuestion no ha sido heredado Los servicios heredados se controlan en las
     * clases en las que fueron declarados
     *
     * @throws SemanticException
     */
    public void checkService() throws SemanticException {
        if (className.equals(symbolTable.getCurrentClass())) {
            ICG.GEN(".CODE");
            ICG.GEN("; Inicializacion CI del servicio '"+ serviceName + "' de clase '"+ className +"'");
            ICG.GEN("L_MET_" + className + "_" + serviceName + ": LOADFP", "Se guarda el enlace dinamico al RA llamador");
            ICG.GEN("LOADSP", "Se apila el lugar a donde comienza el RA de la unidad");
            ICG.GEN("STOREFP", "Se actualiza el FP con el valor del tope de la pila");

            int localVariables = localVariablesTable.size();
            if (localVariables > 0) {
                ICG.GEN("RMEM", localVariables, "Reservamos espacio para las variables locales del metodo '" + serviceName + "'");
            }

            body.checkNode();
            body.setICG(ICG);
            body.generateCode();

            if (localVariables > 0) {
                ICG.GEN("FMEM", localVariables, "Liberamos espacio para las variables locales del metodo '" + serviceName + "'");
            }

            ICG.GEN("STOREFP ; actualizar el FP para que apunte al RA del llamador");
            ICG.GEN("RET", parametersTable.size() + 1);
        }
    }
}
