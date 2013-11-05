package SemanticAnalyzer.SymbolTable;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.Type.Type;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * Representacion de la entrada de clase
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class ClassEntry {

    private String className;
    private int lineNumber;
    private boolean inheritanceControl;
    private ClassEntry parent;
    private LinkedHashMap<String, ClassEntry> parentList;
    private ConstructorEntry constructor;
    private LinkedHashMap<String, InstanceVariableEntry> instanceVariablesTable;
    private LinkedHashMap<String, MethodEntry> methodsTable;

    public ClassEntry(String className, int lineNumber) {
        this.className = className;
        this.lineNumber = lineNumber;
        inheritanceControl = false;
        parent = null;
        parentList = new LinkedHashMap<>();
        constructor = null;
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
     * Retorna el numero de linea donde se declaro la clase
     *
     * @return lineNumber
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Establece el ancestro directo de la entrada de clase
     *
     * @param parent
     */
    public void setParent(ClassEntry parent) {
        this.parent = parent;
    }

    /**
     * Retorna el ancestro directo de la entrada de clase
     */
    public ClassEntry getParent() {
        return parent;
    }

    /**
     * Agrega el ancestro identificado con el nombre pasado por parametro al
     * final de la lista de ancestros
     *
     * @param parent nombre del ancestro a agregar a la lista de ancestros
     */
    public void addParent(ClassEntry parent) {
        parentList.put(parent.getName(), parent);
    }

    /**
     * Retorna la jerarquia de herencia de la entrada de clase
     *
     * @return lista con los ancestros de la clase
     */
    public LinkedHashMap<String, ClassEntry> getParents() {
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
            constructor = new ConstructorEntry(className, className, 0);
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
    public boolean hasMain() throws SemanticException {
        MethodEntry main = methodsTable.get("main");

        if (main != null) {
            if (!main.getModifier().equals("static")) {
                throw new SemanticException("Linea: " + main.getLineNumber() + " - Error semantico: El metodo main debe ser estatico");
            }

            if (!main.getParameters().isEmpty()) {
                throw new SemanticException("Linea: " + main.getLineNumber() + " - Error semantico: El metodo main no debe tener parametros");
            }
        } else {
            return false;
        }

        return true;
    }

//    public void controlInstanceVariables(String instanceVariableName) throws SemanticException {
//        if (instanceVariableName.equals(className)) {
//            throw new SemanticException("Error semantico: La clase " + className + " contiene una variable de instancia con su mismo nombre.");
//        }
//        if (methodsTable.get(instanceVariableName) != null) {
//            throw new SemanticException("Error semantico: La clase " + className + " contiene una variable de instancia " + instanceVariableName + " con el mismo nombre que uno de sus metodos.");
//        }
//    }
    
    /**
     * De ser posible, copia los metodos heredados de la clase padre (herencia
     * por copia)
     *
     * @throws SemanticException
     */
    public void controlInheritedMethods() throws SemanticException {

        if (!parent.getName().equals("Object") && !inheritanceControl) {
            // Las variables de instancia no se heredan
            // Ver documentacoin
            inheritMethods();
            inheritanceControl = true;
        }
    }

    /**
     * Copia metodos heredados y controla los metodos redefinidos
     * 
     * @throws SemanticException 
     */
    private void inheritMethods() throws SemanticException {
        Collection<MethodEntry> inheritedMethods = parent.getMethods().values();
        
        for (MethodEntry parentMethod : inheritedMethods) {
            String parentMethodName = parentMethod.getName();
            
            if (parentMethodName.equals(className)) {
                throw new SemanticException("Linea: " + getLineNumber() + " - Error semantico: La clase padre tiene un metodo con el nombre de la clase actual.");
            }
            
            if (methodsTable.get(parentMethodName) != null) {
                // Se encontro el nombre
                // Controlar si el metodo fue redefinido
                MethodEntry redefinedMethod = methodsTable.get(parentMethodName);
                redefinedMethod.compareModifier(parentMethod);
                redefinedMethod.compareReturnType(parentMethod);
                redefinedMethod.compareParameters(parentMethod);                      
            } else {
                // Se hereda el metood
                addInheritedMethod(parentMethod);
            }
        }
    }
    
    /**
     * Agrega el metodo heredado a la tabla de metodos de la clase actual
     * Previamente se controla de que el metodo agregado no colisione con una variable de instancia
     * 
     * @param parentMethod
     * @throws SemanticException 
     */
    private void addInheritedMethod(MethodEntry parentMethod) throws SemanticException {
        String parentMethodName = parentMethod.getName();
        if (instanceVariablesTable.get(parentMethodName) != null) {
            throw new SemanticException("Linea: " + getLineNumber() + " - Error semantico: La clase padre tiene un metodo con el nombre de una variable de instancia de la clase actual.");
        }
        
        methodsTable.put(parentMethodName, parentMethod);
    }

    /**
     * Control de sentencias de la clase
     * 
     * @param symbolTable 
     */
    void checkClass(SymbolTable symbolTable) {
        Collection<MethodEntry> methods = methodsTable.values();
        
        for (MethodEntry method : methods) {
            symbolTable.setCurrentMethod(method.getName());
            method.checkMethod();
        }
    }
}
