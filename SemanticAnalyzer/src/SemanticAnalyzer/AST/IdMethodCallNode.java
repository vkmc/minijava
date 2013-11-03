package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.ClassEntry;
import SemanticAnalyzer.SymbolTable.InstanceVariableEntry;
import SemanticAnalyzer.SymbolTable.LocalVariableEntry;
import SemanticAnalyzer.SymbolTable.ParameterEntry;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.SymbolTable.Type.ClassType;
import SemanticAnalyzer.SymbolTable.Type.Type;
import SemanticAnalyzer.Token;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Representacion de una llamada a un metodo de clase
 *
 * E.g. id.method1(arg1, arg2) id.method2() id.method1(arg1, arg2).method2(),
 * method1()
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class IdMethodCallNode extends PrimaryNode {

    protected Token id;
    protected LinkedList<CallNode> callList;
    private int caseFlag;

    public IdMethodCallNode(SymbolTable symbolTable, Token id, LinkedList<CallNode> callList, Token token) {
        super(symbolTable, token);
        this.id = id;
        this.callList = callList;
    }

    @Override
    public void checkNode() throws SemanticException {
        if (!callList.isEmpty()) {
            // id y lista de llamadas
            checkNodeCase3();
        } else {
            // id
            checkNodeCase5();
        }
    }

    /**
     * X aparece a la izquierda de una expresion punto, entonces X debe ser una
     * variable de instancia, una variable local, un parametro o una clase
     * (e.g., X.z(3)).
     *
     * Para resolver los nombres en este contexto es necesario, en primer lugar,
     * saber si se hace referencia a un parametro o variable local del metodo,
     * estudiando la tabla de variables locales y parametros asociada al metodo.
     * Si no es un parametro, entonces es necesario buscar en la tabla asociada
     * a la clase en la cual se encuentra el metodo o constructor actual y ver
     * si es una variable de instancia. Sino, es necesario ver si es el nombre
     * de una clase en la tabla de simbolos de clase. En caso de no serlo,
     * ocurrira un error semantico de nombre no definido.
     *
     */
    private void checkNodeCase3() throws SemanticException {
        checkId(true);

        for (CallNode call : callList) {
            call.checkNode();
        }

        controlReturnType();
    }

    /**
     * X no es parte de una expresion punto, ni de una llamada, entonces X debe
     * ser una variable de instancia, una variable local, o un parametro.
     *
     * Para resolver los nombres en este contexto es necesario en primer lugar
     * saber si se hace referencia a un parametro o variable local del metodo,
     * estudiando la tabla de variables locales y parametros asociada al metodo.
     * Si no es un parametro, entonces es necesario buscar en la tabla asociada
     * a la clase en la cual se encuentra el metodo o constructor actual y ver
     * si es una variable de instancia. En caso de no serlo, ocurrira un error
     * semantico de nombre no definido.
     *
     */
    private void checkNodeCase5() throws SemanticException {
        checkId(false);
    }

    private void checkId(boolean checkClasses) throws SemanticException {
        String currentClass = symbolTable.getCurrentClass();
        String currentMethod = symbolTable.getCurrentMethod();
        String idName = id.getLexeme();

        LinkedHashMap<String, ParameterEntry> currentMethodParameters = symbolTable.getClassEntry(currentClass).getMethodEntry(currentMethod).getParameters();
        LinkedHashMap<String, LocalVariableEntry> currentMethodLocalVariables = symbolTable.getClassEntry(currentClass).getMethodEntry(currentMethod).getLocalVariables();

        if (currentMethodParameters.containsKey(idName)) {
            // es un parametro del metodo actual
            this.setExpressionType(currentMethodParameters.get(idName).getType());
            return;
        } else if (currentMethodLocalVariables.containsKey(idName)) {
            // es una variable local del metodo actual
            this.setExpressionType(currentMethodLocalVariables.get(idName).getType());
            return;
        }

        LinkedHashMap<String, InstanceVariableEntry> currentClassInstanceVariables = symbolTable.getClassEntry(currentClass).getInstanceVariables();

        if (currentClassInstanceVariables.containsKey(idName)) {
            // es una variable de instancia de la clase actual

            if (symbolTable.getClassEntry(currentClass).getMethodEntry(currentMethod).getModifier().equals("static")) {
                throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: No puede usarse una variable de instancia en un metodo estatico.");
            }

            this.setExpressionType(currentClassInstanceVariables.get(idName).getType());
            return;
        }
        
        if (!checkClasses) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: No existe el nombre en la tabla de simbolos.");
        }

        LinkedHashMap<String, ClassEntry> classes = symbolTable.getClasses();

        if (classes.containsKey(idName)) {
            // es una clase
            Type aType = new ClassType(idName);
            this.setExpressionType(aType);
            return;
        }

        throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: No existe el nombre en la tabla de simbolos.");
    }

    /**
     * Control del tipo de retorno de la expresion llamadora E.g. para un caso
     * como g().h() tenemos que asegurarnos que se pueda mandar el mensaje h()
     * al retorno de g(). Es decir, el retorno de g() debe ser de un tipo de
     * clase C tal que exista un metodo M en C.
     */
    private void controlReturnType() {
        Type currentType = getExpressionType();
        Type nextCallType = currentType;

        Iterator<CallNode> iteratorCallList = callList.iterator();
        
        for (CallNode nextCall : callList) {
            nextCallType = nextCall.getExpressionType();
            nextCallType.checkConformity(currentType);
        }

        // si no surge ningun error durante el control de conformidad de tipos
        // se le asigna al nodo actual el tipo del ultimo callnode en la lista

        this.setExpressionType(nextCallType);

    }
}
