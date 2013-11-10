package SemanticAnalyzer.SymbolTable;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.Type.Type;
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

    public MethodEntry(String methodName, String className, String modifier, Type returnType, int lineNumber) {
        super(methodName, className, lineNumber);
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
     * Retorna el tipo de retorno del metodo
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
            throw new SemanticException("Linea: " + getLineNumber() + " - Error semantico: El metodo '" + aMethod.getName() + "' de la clase padre tiene diferente modificador.\nEn la clase padre el modificador del metodo es " + aMethod.getModifier() + " y en la clase actual el modificador del metodo es " + getModifier() + ".");
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
            throw new SemanticException("Linea: " + getLineNumber() + " - Error semantico: El metodo '" + aMethod.getName() + "' de la clase padre tiene diferente tipo de retorno.\nEn la clase padre el tipo de retorno del metodo es " + aMethod.getReturnType().getTypeName() + " y en la clase actual el tipo de retorno del metodo es " + getReturnType().getTypeName() + ".");
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
            throw new SemanticException("Linea: " + getLineNumber() + " - Error semantico: La cantidad de parametros del metodo de la clase padre es distinta a la cantidad de parametros del metodo de la clase actual.\n'" + aMethod.getName() + "' en la clase padre tiene " + inheritedParameters.size() + " parametros y en la clase actual tiene " + parametersTable.size() + " parametros.");
        }

        for (ParameterEntry inheritedParameter : inheritedParameters) {
            ParameterEntry currentParameter = parameters.next();

            // Controlo nombre
            if (!inheritedParameter.getVariableName().equals(currentParameter.getVariableName())) {
                throw new SemanticException("Linea: " + getLineNumber() + " - Error semantico: Los parametros del metodo de la clase actual tienen diferente nombre a los parametros del metodo de la clase padre.\nEl parametro en la posicion " + counter + " del metodo '" + aMethod.getName() + "' de la clase padre se llama " + inheritedParameter.getVariableName() + " y en el mismo metodo de la clase actual el parametro de la misma posicion se llama " + currentParameter.getVariableName() + ".");
            }

            // Controlo tipo
            String inheritedParameterType = inheritedParameter.getType().getTypeName();
            String currentParameterType = currentParameter.getType().getTypeName();

            if (!inheritedParameterType.equals(currentParameterType)) {
                throw new SemanticException("Linea: " + getLineNumber() + " - Error semantico: El tipo de los parametros del metodo de la clase actual es diferente a el tipo de los parametros del metodo de la clase padre.\nEl parametro en la posicion " + counter + " del metodo '" + aMethod.getName() + "' de la clase padre es de tipo " + inheritedParameter.getType().getTypeName() + " y en mismo metodo de la clase actual el parametro de la misma posicion es de tipo " + currentParameter.getType().getTypeName() + ".");
            }
        }
    }

    public void checkMethod() throws SemanticException {
        body.checkNode();
    }
}
