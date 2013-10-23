package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.ClassEntry;
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
 * Representacion de un nodo de creacion
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class NewNode extends PrimaryNode {

    protected IdNode id;
    protected LinkedList<ExpressionNode> expressionList;
    protected LinkedList<CallNode> callList;

    public NewNode(SymbolTable symbolTable, IdNode id, LinkedList<ExpressionNode> actualArgs, LinkedList<CallNode> callList, Token token) {
        super(symbolTable, token);
        this.id = id;
        expressionList = actualArgs;
        this.callList = callList;
    }

    @Override
    public void checkNode() throws SemanticException {
        checkConstructor(); // controlo la existencia del constructor

        for (ExpressionNode actualArg : expressionList) {
            actualArg.checkNode();
        }

        controlFormalArgs();

        if (!callList.isEmpty()) {

            for (CallNode call : callList) {
                call.checkNode();
            }

            controlReturnType();
        }
    }

    /**
     * Controla que el identificador corresponda a una clase presente de la
     * tabla de simbolos No es necesario realizar otros controles puesto que una
     * clase tiene solo un constructor y, de no ser definido por el programador,
     * se inserta un constructor por defecto.
     *
     * @throws SemanticException
     */
    private void checkConstructor() throws SemanticException {

        LinkedHashMap<String, ClassEntry> classes = symbolTable.getClasses();
        String idName = id.getId().getLexeme();

        if (!classes.containsKey(idName)) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: El constructor invocado no se encuentra en la tabla de simbolos.");
        }

        Type aType = new ClassType(idName);
        this.setExpressionType(aType);
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
