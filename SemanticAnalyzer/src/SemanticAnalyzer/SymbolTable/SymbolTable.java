package SemanticAnalyzer.SymbolTable;

import SemanticAnalyzer.SymbolTable.Type.Type;
import java.util.HashMap;

/**
 * Representacion de la tabla de simbolos para la generacion de codigo
 * intermedio
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class SymbolTable {

    private HashMap<String, ClassEntry> classTable;
    private String currentClass, currentMethod;

    /**
     * Constructor de la tabla de simbolos
     * Crea las estructuras e inicializa los atributos
     */
    public SymbolTable() {
        classTable = new HashMap<>();
        currentClass = null;
        currentMethod = null;
    }
    
    /**
     * Inicializa la tabla de simbolos
     */
    public void initSymbolTable() {
        addClassObject();
        addClassSystem();
    }

    private void addClassObject() {
        ClassEntry Object = new ClassEntry("Object", 0);
        classTable.put("Object", Object);
    }

    private void addClassSystem() {
        ClassEntry System = new ClassEntry("System", 0);
        classTable.put("System", System);
    }

    /**
     * Retorna la entrada de clase de una clase deseada
     *
     * @param className nombre de la entrada de clase a recuperar
     * @return ClassEntry si la clase de nombre className se encuentra en el
     * diccionario de clases, null en caso contrario
     */
    public ClassEntry getClassEntry(String className) {
        return classTable.get(className);
    }

    /**
     * Crea e inserta una nueva entrada de clase en la tabla de simbolos
     *
     * @param className nombre de la entrada de clase a agregar
     */
    public void addClassEntry(String className, int lineNumber) {
        ClassEntry aClass = new ClassEntry(className, lineNumber);
        classTable.put(className, aClass);
    }

    /**
     * Establece la clase actual
     *
     * @param className nombre de la clase actual
     */
    public void setCurrentClass(String className) {
        currentClass = className;
    }

    /**
     * Retorna la clase actual
     *
     * @return clase actual
     */
    public String getCurrentClass() {
        return currentClass;
    }

    /**
     * Establece el metodo actual
     *
     * @param methodName nombre del metodo actual
     */
    public void setCurrentMethod(String methodName) {
        currentMethod = methodName;
    }

    /**
     * Retorna el metodo actual
     *
     * @return
     */
    public String getCurrentMethod() {
        return currentMethod;
    }

    /**
     * Controla que no exista herencia circular entre las clases
     *
     * @param className
     * @return
     */
    public boolean controlInheritance(String className) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Controla que exista un metodo principal sin retorno (void) y estatico
     * (static) en alguna de las clases presentes en el conjunto de clases
     */
    public void controlMain() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Controla que haya conformidad de tipos entre los tipos type1 y type2
     *
     * @param type1
     * @param type2
     * @return true si hay conformidad de tipos, false en caso contrario
     */
    public boolean checkConformity(Type type1, Type type2) {
        return false;
    }

    public boolean checkConformity(String expressionType, String expressionType0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
