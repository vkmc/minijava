package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.ParameterEntry;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.SymbolTable.Type.ClassType;
import SemanticAnalyzer.SymbolTable.Type.Type;
import SemanticAnalyzer.Token;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Representacion de un nodo de creacion
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class NewNode extends PrimaryNode {

    // Llamadas? Como seria eso? Controlar!
    protected Token id;
    protected Type idType;
    protected LinkedList<ExpressionNode> actualArgs;
    protected LinkedList<CallNode> callList;

    public NewNode(SymbolTable symbolTable, Token id, LinkedList<ExpressionNode> actualArgs, LinkedList<CallNode> callList, Token token) {
        super(symbolTable, token);
        this.id = id;
        this.actualArgs = actualArgs;
        this.callList = callList;
    }

    @Override
    public void checkNode() throws SemanticException {
        checkId(); // controlo la existencia del constructor

        for (ExpressionNode actualArg : actualArgs) {
            actualArg.checkNode();
        }

        controlFormalArgs();

        if (!callList.isEmpty()) {

            Type callerType = idType;
            for (CallNode call : callList) {
                call.setCallerType(callerType);
                call.checkNode();
                callerType = call.getCallReturnType();
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
    private void checkId() throws SemanticException {
        // debe ser un constructor
        if (symbolTable.isConstructor(id.getLexeme()) == null) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: El constructor invocado no esta declarado.");
        }

        idType = new ClassType(id.getLexeme(), symbolTable);
        setExpressionType(idType);
    }

    /**
     * Control de conformidad de tipos entre argumentos formales y argumentos
     * actuales de un metodo
     *
     * @throws SemanticException
     */
    private void controlFormalArgs() throws SemanticException {
        String currentClass = symbolTable.getCurrentClass();
        String idTypeName = idType.getTypeName();
        Collection<ParameterEntry> formalArgs = symbolTable.getClassEntry(idTypeName).getConstructorEntry().getParameters().values();
        int index = 0, counter = 1;

        if (formalArgs.size() != actualArgs.size()) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: Las listas de argumentos actuales y formales para el metodo " + id.getLexeme() + " de la clase " + currentClass + " tienen diferente longitud.");
        }

        for (ParameterEntry formalArg : formalArgs) {
            actualArgs.get(index).checkNode();
            if (!formalArg.getType().checkConformity(actualArgs.get(index).getExpressionType())) {
                throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: En la llamada al metodo '" + id.getLexeme() + "' el tipo del argumento actual en la posicion (" + counter + ") no conforma con el tipo del argumento formal."
                        + " El tipo del argumento actual es " + actualArgs.get(counter).getExpressionType().getTypeName() + " y el tipo del argumento formal es " + formalArg.getType().getTypeName() + ".");
            }
            index++;
            counter++;
        }
    }

    /**
     * Control del tipo de retorno de la expresion llamadora E.g. para un caso
     * como g().h() tenemos que asegurarnos que se pueda mandar el mensaje h()
     * al retorno de g(). Es decir, el retorno de g() debe ser de un tipo de
     * clase C tal que exista un metodo M en C.
     */
    private void controlReturnType() throws SemanticException {
        Type currentType = getExpressionType();
        Type nextType;
        String nextId;

        for (CallNode nextCall : callList) {
            nextType = nextCall.getExpressionType();
            nextId = nextCall.getId().getLexeme();

            if (symbolTable.getClassEntry(currentType.getTypeName()).getMethodEntry(nextId) == null) {
                throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: El metodo '" + nextId + "' no es un metodo de la clase '" + currentType.getTypeName() + "'.");
            }

            nextType.checkConformity(currentType);
            currentType = nextType;
        }

        // si no surge ningun error durante el control de conformidad de tipos
        // se le asigna al nodo actual el tipo del ultimo callnode en la lista

        this.setExpressionType(currentType);

    }
}
