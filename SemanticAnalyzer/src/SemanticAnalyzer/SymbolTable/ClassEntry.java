package SemanticAnalyzer.SymbolTable;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.Type.Type;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;

/**
 * Representacion de la entrada de clase
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class ClassEntry {

    private String className;
    private int lineNumber;
    private String parent;
    private LinkedList<String> parentList;
    private ConstructorEntry constructor;
    private LinkedHashMap<String, InstanceVariableEntry> instanceVariablesTable;
    private LinkedHashMap<String, MethodEntry> methodsTable;

    public ClassEntry(String className, int lineNumber) {
        this.className = className;
        this.lineNumber = lineNumber;
        instanceVariablesTable = new LinkedHashMap<>();
        methodsTable = new LinkedHashMap<>();
    }

    /**
     * Retorna el nombre de la clase asociada a la entrada de clase
     *
     * @return name
     */
    public String getName() {
        return className;
    }

    /**
     * Establece el ancestro directo de la entrada de clase
     *
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
     * Agrega el ancestro identificado con el nombre pasado por parametro al
     * final de la lista de ancestros
     *
     * @param parent nombre del ancestro a agregar a la lista de ancestros
     */
    public void addParent(String parent) {
        parentList.addLast(parent);
    }

    /**
     * Retorna la jerarquia de herencia de la entrada de clase
     *
     * @return lista con los ancestros de la clase
     */
    public LinkedList<String> getParents() {
        return parentList;
    }

    /**
     * Retorna el constructor de la entrada de clase
     *
     * @return constructor
     */
    public ConstructorEntry getConstructorEntry() {
        return constructor;
    }

    /**
     * Establece el constructor pasado por parametro como constructor de la
     * clase
     *
     * @param constructor
     */
    public void setConstructorEntry(String constructorName, int lineNumber) {
        ConstructorEntry constructorEntry = new ConstructorEntry(constructorName, className, lineNumber);
        this.constructor = constructorEntry;
    }

    /**
     * Controla si existe un constructor. En caso de no existir, agrega un
     * constructor default
     */
    public void controlDefaultConstructor() {
        if (getConstructorEntry() == null) {
            constructor = new ConstructorEntry(className, className,-1);
        }
    }

    /**
     * Crea e inserta una nueva entrada de metodo en la tabla de metodos
     *
     * @param methodName nombre del metodo a insertar
     * @param type tipo del metodo a insertar
     * @param modificator modificador del metodo a insertar
     */
    public void addMethodEntry(String methodName, Type returnType, String modificator, int lineNumber) {
        MethodEntry method = new MethodEntry(methodName, className, modificator, returnType, lineNumber);
        methodsTable.put(methodName, method);
    }

    /**
     * Retorna la tabla de metodos
     *
     * @return methodsTable
     */
    public LinkedHashMap<String, MethodEntry> getMethods() {
        return methodsTable;
    }

    /**
     * Retorna la entrada de metodo de un metodo deseado
     *
     * @param methodName
     */
    public MethodEntry getMethodEntry(String methodName) {
        return methodsTable.get(methodName);
    }

    /**
     * Crea e inserta una nueva entrada de variable de instancia en la tabla de
     * variables de instancia
     *
     * @param instanceVariableName nombre de la variable de instancia a insertar
     * @param type tipo de la variable de instancia a insertar
     * @param lineNumber numero de linea
     */
    public void addInstanceVariableEntry(String instanceVariableName, Type instanceVariableType, int lineNumber) {
        InstanceVariableEntry instanceVariable = new InstanceVariableEntry(instanceVariableName, instanceVariableType, lineNumber);
        instanceVariablesTable.put(instanceVariableName, instanceVariable);
    }

    /**
     * Retorna la tabla de variables de instancia
     *
     * @return instanceVariablesTable
     */
    public LinkedHashMap<String, InstanceVariableEntry> getInstanceVariables() {
        return instanceVariablesTable;
    }

    /**
     * Retorna la entrada de variable de instancia de una variable de instancia
     * deseada
     *
     * @param instanceVariableName
     * @param lineNumber
     * @return instanceVariable
     */
    public InstanceVariableEntry getInstanceVariableEntry(String instanceVariableName) {
        return instanceVariablesTable.get(instanceVariableName);
    }

    /**
     * Controla que exista un metodo principal sin retorno (void) y estatico
     * (static)
     *
     * @return true si la clase tiene un metodo main void y static, false en
     * caso contrario
     */
    public boolean hasMain() {
        MethodEntry main = methodsTable.get("main");
        if (main != null) {
            if (main.getModifier().equals("static") && main.getParameters().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public void controlInstanceVariables(String instanceVariableName) throws SemanticException {
        if (instanceVariableName.equals(className)) {
            throw new SemanticException("Error semantico: La clase " + className + " contiene una variable de instancia con su mismo nombre.");
        }
        if (methodsTable.get(instanceVariableName) != null) {
            throw new SemanticException("Error semantico: La clase " + className + " contiene una variable de instancia " + instanceVariableName + " con el mismo nombre que uno de sus metodos.");
        }
    }

    /**
     * Realiza un control de herencia y copia los metodos heredados
     */
    public void controlInheritedMethods(SymbolTable symbolTable) throws SemanticException {
        Set<String> parentMethods = symbolTable.getClassEntry(parent).getMethods().keySet();
        
        for (String parentMethod: parentMethods) {
            // Buscamos el método en la clase hijo.
            MethodEntry childMethodEntry = getMethodEntry(parentMethod);
            MethodEntry parentMethodEntry = symbolTable.getClassEntry(parent).getMethodEntry(parentMethod);
            if (childMethodEntry != null) {
                // El método del padre se encuentra en la clase hijo.
                
                /*if (childMethodEntry.compareModifier(parentMethodEntry) &&
                    childMethodEntry.compareParameters(parentMethodEntry) &&
                    childMethodEntry.compareReturn(parentMethodEntry)) {
                    // Es una redefinición válida.     
                    
                } else {
                    throw new SemanticException("Error semantico: El metodo " + parentMethod + " de la clase " + parent + " es sobrecargado en la clase " + parent + ".");
                }*/
            } else {
                // El método del padre no se encuentra en la clase hijo. Se copia en la subclase (herencia por copia).
                if (className.equals(parentMethod)) {
                    throw new SemanticException("Error semantico: El metodo " + parentMethod + " de la clase " + parent + " tiene el mismo nombre que la clase " + className + " que lo hereda.");
                }
                if (instanceVariablesTable.get(parentMethod) != null) {
                    throw new SemanticException("Error semantico: El metodo " + parentMethod + " de la clase " + parent + " heredado por la clase " + className + " tiene el mismo nombre que una de sus variables de instancia");
                }
                methodsTable.put(parentMethod, parentMethodEntry);
            }
        }
    }

    void checkClass() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
