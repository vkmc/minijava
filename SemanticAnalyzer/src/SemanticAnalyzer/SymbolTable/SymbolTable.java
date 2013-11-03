package SemanticAnalyzer.SymbolTable;

import SemanticAnalyzer.SemanticException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;

/**
 * Representacion de la tabla de simbolos para la generacion de codigo
 * intermedio
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class SymbolTable {

    private LinkedHashMap<String, ClassEntry> classTable;
    private String currentClass, currentMethod;

    /**
     * Constructor de la tabla de simbolos Crea las estructuras e inicializa los
     * atributos
     */
    public SymbolTable() {
        classTable = new LinkedHashMap<>();
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
     * Retorna el conjunto de clases presentes en la tabla de simbolos
     *
     * @return classTable
     */
    public LinkedHashMap getClasses() {
        return classTable;
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
    public void controlInheritance(String className) throws SemanticException {

        // TENER EN CUENTA LO QUE DIJO LA DOC
        ClassEntry aClassEntry = getClassEntry(className);
        LinkedList<String> parents = aClassEntry.getParents();
        if (parents.contains(className)) {
            // Una clase se tiene a si misma en la lista de ancestros.
            throw new SemanticException("Error semantico: Herencia circular. La clase " + className + " no puede heredar de si misma.");
        }
    }

    /**
     * Controla que exista un metodo principal sin retorno (void) y estatico
     * (static) en alguna de las clases presentes en el conjunto de clases
     */
    public void controlMain() throws SemanticException {
        Set<String> classes = classTable.keySet();
        for (String aClass : classes) {
            if (getClassEntry(aClass).hasMain()) {
                // Si se encuentra una clase con el método main el control tiene éxito.
                return;
            }
        }
        // No se encontró una clase con método main.
        throw new SemanticException("Error semantico: El metodo main no fue declarado en ninguna de las clases.");
    }

    public String isConstructor(String id) {
        Iterator<String> classes = getClasses().keySet().iterator();

        while (classes.hasNext()) {
            String aClass = classes.next();
            if (aClass.equals(id)) {
                return aClass;
            }
        }

        return null;
    }

    public String isMethod(String id) {
        Iterator<String> classes = getClasses().keySet().iterator();

        while (classes.hasNext()) {
            String aClass = classes.next();
            Set<String> methods = getClassEntry(aClass).getMethods().keySet();

            if (methods.contains(id)) {
                return aClass;
            }
        }

        return null;
    }
}
