package IntermediateCodeGeneration.SymbolTable;

import IntermediateCodeGeneration.SemanticException;
import IntermediateCodeGeneration.SymbolTable.Type.Type;
import java.util.Collection;
import java.util.Iterator;

/**
 * Representacion de la entrada de metodo
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class MethodEntry extends ServiceEntry {

    private String modifier;
    private Type returnType;

    public MethodEntry(String methodName, String className, String modifier, Type returnType, SymbolTable symbolTable, int lineNumber) {
        super(methodName, className, symbolTable, lineNumber);
        this.modifier = modifier;
        this.returnType = returnType;
    }

    /**
     * Retorna el nombre del metodo
     *
     * @return serviceName
     */
    public String getName() {
        return serviceName;
    }

    /**
     * Establece el modificador del metodo
     *
     * @param modifier modificador del metodo
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * Establece el tipo de retorno del metodo
     *
     * @param returnType tipo de retorno del metodo
     */
    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    /**
     * Retorna el modificador asociado al metodo
     *
     * @return modifier
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * Retorna el tipo de retorno d return returnType;el metodo
     *
     * @return returnType
     */
    public Type getReturnType() {
        return returnType;
    }

    /**
     * Compara los modificadores del metodo receptor con el metodo pasado por
     * parametro
     *
     * @param methodEntry
     */
    public void compareModifier(MethodEntry aMethod) throws SemanticException {
        if (!modifier.equals(aMethod.getModifier())) {
            throw new SemanticException("Linea: " + getLineNumber() + " - Error semantico: El modificador del metodo '" + aMethod.getName() + "' de la clase padre '" + aMethod.getClassName() + "' es diferente al modificador del metodo " + serviceName + " de la clase hija '" + className + "'.\nEn la clase padre el modificador del metodo es " + aMethod.getModifier() + " y en la clase actual es " + getModifier() + ".");
        }
    }

    /**
     * Compara el retorno del metodo receptor con el metodo pasado por parametro
     *
     * @param methodEntry
     */
    public void compareReturnType(MethodEntry aMethod) throws SemanticException {
        String thisMethodReturn = returnType.getTypeName();
        String aMethodReturn = aMethod.getReturnType().getTypeName();

        if (!thisMethodReturn.equals(aMethodReturn)) {
            throw new SemanticException("Linea: " + getLineNumber() + " - Error semantico:  El tipo de retorno del metodo '" + aMethod.getName() + "' de la clase padre '" + aMethod.getClassName() + "' es diferente al tipo de retorno del metodo '" + serviceName + "' de la clase hija '" + className + "'.\nEn la clase padre el tipo de retorno del metodo es " + aMethod.getReturnType().getTypeName() + " y en la clase hija es " + getReturnType().getTypeName() + ".");
        }
    }

    /**
     * Compara los parametros (tipo y cantidad) del metodo receptor con el
     * metodo pasado por parametro
     *
     * @param methodEntry
     */
    public void compareParameters(MethodEntry aMethod) throws SemanticException {
        Collection<ParameterEntry> inheritedParameters = aMethod.getParameters().values();
        int counter = 1;
        Iterator<ParameterEntry> parameters = parametersTable.values().iterator();

        if (inheritedParameters.size() != parametersTable.size()) {
            throw new SemanticException("Linea: " + getLineNumber() + " - Error semantico: La cantidad de parametros del metodo '" + aMethod.getName() + "' de la clase padre '" + aMethod.getClassName() + "' es diferente a la cantidad de parametros del metodo '" + serviceName + "' de la clase hija '" + className + ".\nEn la clase padre la cantidad de parametros del metodo es " + inheritedParameters.size() + " y en la clase hija es " + parametersTable.size() +".");
        }

        for (ParameterEntry inheritedParameter : inheritedParameters) {
            ParameterEntry currentParameter = parameters.next();

            // Controlo nombre
            if (!inheritedParameter.getVariableName().equals(currentParameter.getVariableName())) {
                throw new SemanticException("Linea: " + getLineNumber() + " - Error semantico: Los nombres de los parametros del metodo '" + aMethod.getName() + " de la clase padre '" + aMethod.getClassName() + "' son diferentes a los nombres de los parametros del metodo '" + serviceName + "' de la clase hija '" + className + ".\nEn la clase padre el parametro en la posicion " + counter + " del metodo se llama " + inheritedParameter.getVariableName() + " y en la clase hija se llama " + currentParameter.getVariableName() + ".");
            }

            // Controlo tipo
            String inheritedParameterType = inheritedParameter.getType().getTypeName();
            String currentParameterType = currentParameter.getType().getTypeName();

            if (!inheritedParameterType.equals(currentParameterType)) {
                throw new SemanticException("Linea: " + getLineNumber() + " - Error semantico: Los tipos de los parametros del metodo '" + aMethod.getName() + " de la clase padre '" + aMethod.getClassName() + "' son diferentes a los nombres de los parametros del metodo '" + serviceName + "' de la clase hija '" + className + ".\nEn la clase padre el parametro en la posicion " + counter + " del metodo es de tipo " + inheritedParameter.getType().getTypeName() + " y en la clase hija es de tipo " + currentParameter.getType().getTypeName() + ".");
            }
        }
    }
}
