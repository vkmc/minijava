package SemanticAnalyzer.SymbolTable;

import java.util.HashMap;
import java.util.LinkedList;


/**
 * Representacion de la entrada de clase
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class ClassEntry {
    
    private String name;
    private String parent;
    private LinkedList<String> parentList;
    private ConstructorEntry constructor;
    private HashMap<String, InstanceVariableEntry> instanceVariablesTable;
    private HashMap<String, MethodEntry> methodsTable;    

    public ClassEntry(String className) {
        name = className;
        instanceVariablesTable = new HashMap<>();
        methodsTable = new HashMap<>();
    }
    
    /**
     * Retorna el nombre de la clase asociada a la entrada de clase
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Establece el nombre pasado por parametro a la entrada de clase
     * @param name nuevo nombre de la entrada de clase
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Establece el ancestro directo de la entrada de clase
     * @param parent
     */
    public void setParent(String parent) {
        this.parent = parent;
    }
    
    /**
     * Retorna el ancestro directo de la entrada de clase
     */
    public String getParent() {
        return parent;
    }
    
    /**
     * Agrega el ancestro identificado con el nombre pasado por parametro al final de la lista de ancestros
     * @param parent nombre del ancestro a agregar a la lista de ancestros
     */
    public void addParent(String parent) {
        parentList.addLast(parent);
    }

    /**
     * Retorna la jerarquia de herencia de la entrada de clase
     * @return lista con los ancestros de la clase
     */
    public LinkedList<String> getParents() {
        return parentList;
    }
    

    /**
     * Retorna el constructor de la entrada de clase
     * @return constructor
     */
    public ConstructorEntry getConstructorEntry() {
        return constructor;
    }

    /**
     * Establece el constructor pasado por parametro como constructor de la clase
     * @param constructor 
     */
    public void setConstructorEntry(String constructorName, int lineNumber) {
        ConstructorEntry constructorEntry = new ConstructorEntry(constructorName, lineNumber);
        this.constructor = constructorEntry;
    }
    
    /**
     * Controla si existe un constructor. En caso de no existir, agrega un constructor default
     */    
    public void controlDefaultConstructor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Crea e inserta una nueva entrada de metodo en la tabla de metodos 
     * @param methodName nombre del metodo a insertar
     * @param type tipo del metodo a insertar
     * @param modificator modificador del metodo a insertar
     */
    public void addMethodEntry(String methodName, String typeName, String modificator, int lineNumber) {
        // falta hacer contro del tipo
        // dType type = new Type(typeName);
        // MethodEntry method = new MethodEntry(methodName, modificator, type, lineNumber);
        // methodsTable.put(methodName, method);
    }
    
    /**
     * Retorna la tabla de metodos
     * @return methodsTable
     */
    public HashMap<String, MethodEntry> getMethods() {
        return methodsTable;
    }
    
    /**
     * Retorna la entrada de metodo de un metodo deseado
     * @param methodName
     */
    public MethodEntry getMethodEntry(String methodName) {
        return methodsTable.get(methodName);
    }    
    
    /**
     * Crea e inserta una nueva entrada de variable de instancia en la tabla de variables de instancia
     * @param instanceVariableName nombre de la variable de instancia a insertar
     * @param type tipo de la variable de instancia a insertar
     * @param lineNumber numero de linea
     */
    public void addInstanceVariableEntry(String instanceVariableName, String type, int lineNumber) {
        InstanceVariableEntry instanceVariable = new InstanceVariableEntry(instanceVariableName, type, lineNumber);
        instanceVariablesTable.put(instanceVariableName, instanceVariable);
    }
    
     /**
     * Retorna la tabla de variables de instancia
     * @return instanceVariablesTable
     */
    public HashMap<String, InstanceVariableEntry> getInstanceVariables() {
        return instanceVariablesTable;
    }   
    
    /**
     * Retorna la entrada de variable de instancia de una variable de instancia deseada
     * @param instanceVariableName
     * @param lineNumber 
     * @return instanceVariable
     */
    public InstanceVariableEntry getInstanceVariableEntry(String instanceVariableName, int lineNumber) {
        return instanceVariablesTable.get(instanceVariableName);
    }
    
    /**
     * Controla que exista un metodo principal sin retorno (void) y estatico (static)
     * @return true si la clase tiene un metodo main void y static, false en caso contrario
     */
    public boolean hasMain() {
        return false;
    }
    
    public void controlInstanceVariables(String instanceVariableName) {
        
    }
    
    /**
     * Realiza un control de herencia y copia los metodos heredados
     */
    public void controlInheritedMethods() {
        
    }
    
}
