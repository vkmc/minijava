package SemanticAnalyzer.SymbolTable;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.Type.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

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
            throw new SemanticException("Linea: " + getLineNumber() + " - Error semantico: El metodo de la clase padre tiene diferente modificador.");
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
            throw new SemanticException("Linea: " + getLineNumber() + " - Error semantico: El metodo de la clase padre tiene diferente tipo de retorno.");
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
        Iterator<ParameterEntry> parameters = parametersTable.values().iterator();
        int counter = 0;
        
        if (inheritedParameters.size() != parametersTable.size()) {
            throw new SemanticException("Linea: " + getLineNumber() + " - Error semantico: La cantidad de parametros del metodo de la clase padre es distinta a la cantidad de parametros del metodo de la clase actual.");
        }
        
        for (ParameterEntry inheritedParameter : inheritedParameters) {
            ParameterEntry currentParameter = parameters.next();
            
            // Controlo nombre
            if (!inheritedParameter.getVariableName().equals(currentParameter.getVariableName())) {
               throw new SemanticException("Linea: " + getLineNumber() + " - Error semantico: Los parametros del metodo de la clase actual tienen diferente nombre a los parametros del metodo de la clase padre."); 
            }

            // Controlo tipo
            String inheritedParameterType = inheritedParameter.getType().getTypeName();
            String currentParameterType = currentParameter.getType().getTypeName();

            if (!inheritedParameterType.equals(currentParameterType)) {
                throw new SemanticException("Linea: " + getLineNumber() + " - Error semantico: El tipo de los parametros del metodo de la clase actual es diferente a el tipo de los parametros del metodo de la clase padre.");    
            }
        }
    }

    void checkMethod() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}
