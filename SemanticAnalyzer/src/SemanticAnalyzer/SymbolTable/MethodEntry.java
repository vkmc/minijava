package SemanticAnalyzer.SymbolTable;

import SemanticAnalyzer.SymbolTable.Type.Type;
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
    public boolean compareModifier(MethodEntry methodEntry) {
        return true;
    }

    /**
     * Compara el retorno del metodo receptor con el metodo pasado por parametro
     *
     * @param methodEntry
     */
    public boolean compareReturn(MethodEntry methodEntry) {
        return true;
    }

    /**
     * Compara los parametros (tipo y cantidad) del metodo receptor con el
     * metodo pasado por parametro
     *
     * @param methodEntry
     */
    public boolean compareParameters(MethodEntry methodEntry) {
        return true;
    }
   
}
