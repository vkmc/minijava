package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.InstanceVariableEntry;
import SemanticAnalyzer.SymbolTable.LocalVariableEntry;
import SemanticAnalyzer.SymbolTable.ParameterEntry;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.SymbolTable.Type.ClassType;
import SemanticAnalyzer.SymbolTable.Type.Type;
import SemanticAnalyzer.Token;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Representacion de una llamada hecha sobre una expresion
 *
 * E.g. id.method1(arg1, arg2) id.method2() id.method1(arg1, arg2).method2()
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class IdExpressionCallNode extends PrimaryNode {

    protected IdNode id;
    protected LinkedList<ExpressionNode> expressionList;
    protected LinkedList<CallNode> callList;
    private int caseFlag;

    public IdExpressionCallNode(SymbolTable symbolTable, IdNode id, LinkedList<CallNode> callList, Token token) {
        super(symbolTable, token);
        this.id = id;
        this.callList = callList;
        caseFlag = 1;
    }

    public IdExpressionCallNode(SymbolTable symbolTable, IdNode id, Token token) {
        super(symbolTable, token);
        this.id = id;
        caseFlag = 2;

    }

    public IdExpressionCallNode(SymbolTable symbolTable, IdNode id, LinkedList<ExpressionNode> expressionList, LinkedList<CallNode> callList, Token token) {
        super(symbolTable, token);
        this.id = id;
        this.expressionList = expressionList;
        this.callList = callList;
        caseFlag = 3;
    }

    @Override
    public void checkNode() throws SemanticException {
        if (caseFlag == 1) {
            // id y lista de llamadas
            checkNodeCase3();
        } else if (caseFlag == 2) {
            // id
            checkNodeCase5();
        } else if (caseFlag == 3 && callList.isEmpty()) {
            // id, lista de expresiones y lista de llamadas (vacia)
            checkNodeCase1();
        } else if (caseFlag == 3 && !callList.isEmpty()) {
            // id, lista de expresiones y lista de llamadas (no vacia)
            checkNodeCase2();
        }
    }

    /**
     * X es solo parte de una llamada a metodo, entonces X debe ser un metodo de
     * la clase C (e.g., X(2)).
     *
     * Como la herencia implementada es por copia - i.e. todos los metodos
     * heredados de una clase padre se copian a la clase hijo - simplemente
     * deberemos buscar en la tabla de la clase donde se esta usando el nombre.
     *
     * @throws SemanticException
     */
    private void checkNodeCase1() throws SemanticException {
        // control de existencia del nombre
        String currentClass = symbolTable.getCurrentClass();
        String idName = id.getId().getLexeme();

        if (currentClass.equals(idName)) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: No puede realizarse una llamada a un constructor.");
        } else if (symbolTable.getClassEntry(currentClass).getMethodEntry(idName) == null) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: No existe el metodo '" + idName + "' en la clase " + currentClass + ".");
        } else {
            Type methodReturnType = symbolTable.getClassEntry(currentClass).getMethodEntry(idName).getReturnType();
            this.setExpressionType(methodReturnType);
        }

        // control de argumentos

        for (ExpressionNode expression : expressionList) {
            expression.checkNode();
        }

        controlFormalArgs();
    }

    /**
     * X aparece a la derecha de una expresion punto y es parte de una llamada a
     * metodo, entonces X debe ser un metodo de la clase del tipo de retorno del
     * metodo de la izquierda (e.g., g().X(2)).
     *
     * Primero es necesario determinar la clase en la cual hay que buscar el
     * metodo. Esta clase se determina por el tipo (estatico) de la parte
     * izquierda de la expresion punto. El tipo de la izquierda debe ser una
     * clase (no un tipo primitivo o void), o de lo contrario se reportara un
     * error semantico. Una vez identificada la clase de la izquierda se buscara
     * el metodo de manera similar al caso del inciso (1) pero en esa clase.
     *
     * @throws SemanticException
     */
    private void checkNodeCase2() throws SemanticException {
        checkNodeCase1();

        for (CallNode call : callList) {
            call.checkNode();
        }

        controlReturnType();
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
        checkNameExistence(false);

        for (CallNode call : callList) {
            call.checkNode();
        }

        controlReturnType();
    }

    // checkNodeCase4() se realiza en la clase NewNode
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
        checkNameExistence(true);
    }

    /**
     * Controla que el identificador sea - Un parametro o variable local del
     * metodo actual, o bien - Una variable de instancia de la clase actual, o
     * bien - El nombre de una clase
     *
     * @throws SemanticException
     */
    private void checkNameExistence(boolean isCase5) throws SemanticException {
        String currentClass = symbolTable.getCurrentClass();
        String currentMethod = symbolTable.getCurrentMethod();
        String idName = id.getId().getLexeme();

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
            this.setExpressionType(currentClassInstanceVariables.get(idName).getType());
            return;
        }

        // Si se trata del caso 5, no buscamos que exista una entrada de clase
        // Para el caso 3 si lo hacemos
        if (isCase5) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: No existe el nombre en la tabla de simbolos.");
        }

        LinkedHashMap classes = symbolTable.getClasses();

        if (classes.containsKey(idName)) {
            // es una clase
            Type aType = new ClassType(idName);
            this.setExpressionType(aType);
            return;
        }

        throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: No existe el nombre en la tabla de simbolos.");
    }

    /**
     * Control de conformidad de tipos entre argumentos formales y argumentos
     * actuales de un metodo
     *
     * @throws SemanticException
     */
    private void controlFormalArgs() throws SemanticException {
        String currentClass = symbolTable.getCurrentClass();
        Collection<ParameterEntry> formalArgs = symbolTable.getClassEntry(currentClass).getMethodEntry(id.getId().getLexeme()).getParameters().values();
        Iterator<ParameterEntry> formalArgsIterator = formalArgs.iterator();
        int counter = 0;

        if (formalArgs.size() != expressionList.size()) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: Las listas de argumentos actuales y formales para el metodo " + id.getId().getLexeme() + " de la clase " + currentClass + " tienen diferente longitud.");
        }

        while (formalArgsIterator.hasNext()) {
            ParameterEntry formalArg = formalArgsIterator.next();
            if (formalArg.getType().checkConformity(expressionList.get(counter).getExpressionType())) {
                throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: El tipo del argumento actual no conforma con el tipo del argumento formal."
                        + " El tipo del argumento actual es " + expressionList.get(counter).getExpressionType() + " y el tipo del argumento formal es " + formalArg.getType() + ".");
            }
        }
    }

    /**
     * Control del tipo de retorno de la expresion llamadora E.g. para un caso
     * como g().h() tenemos que asegurarnos que se pueda mandar el mensaje h()
     * al retorno de g(). Es decir, el retorno de g() debe ser de un tipo de
     * clase C tal que exista un metodo M en C.
     */
    private void controlReturnType() {
        Type currentType = getExpressionType();
        Type nextType;

        Iterator<CallNode> iteratorCallList = callList.iterator();

        while (iteratorCallList.hasNext()) {
            CallNode nextCall = iteratorCallList.next();

            nextType = nextCall.getExpressionType();
            nextType.checkConformity(currentType);

            currentType = nextType;
        }

        // si no surge ningun error durante el control de conformidad de tipos
        // se le asigna al nodo actual el tipo del ultimo callnode en la lista

        this.setExpressionType(currentType);

    }
}