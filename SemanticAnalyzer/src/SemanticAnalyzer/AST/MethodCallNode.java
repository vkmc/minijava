package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.MethodEntry;
import SemanticAnalyzer.SymbolTable.ParameterEntry;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.SymbolTable.Type.Type;
import SemanticAnalyzer.Token;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Representacion de un nodo llamada a metodo e.g. id(), id(1,2,c),
 * id1().id2(1,2,c)
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class MethodCallNode extends PrimaryNode {

    protected Token id;
    protected Type idType;
    protected LinkedList<ExpressionNode> actualArgs;
    protected LinkedList<CallNode> callList;

    public MethodCallNode(SymbolTable symbolTable, Token id, LinkedList<ExpressionNode> actualArgs, LinkedList<CallNode> callList, Token token) {
        super(symbolTable, token);
        this.id = id;
        this.actualArgs = actualArgs;
        this.callList = callList;   // Puede ser vacia - Caso 1
    }

    /**
     * Casos a controlar
     *
     * Caso 1: X es solo parte de una llamada a metodo, entonces X debe ser un
     * metodo de la clase C (e.g., X(2)).
     *
     * Como la herencia implementada es por copia - i.e. todos los metodos
     * heredados de una clase padre se copian a la clase hijo - simplemente
     * deberemos buscar en la tabla de la clase donde se esta usando el nombre.
     *
     * Caso 2: X aparece a la derecha de una expresion punto y es parte de una
     * llamada a metodo, entonces X debe ser un metodo de la clase del tipo de
     * retorno del metodo de la izquierda (e.g., g().X(2)).
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
    @Override
    public void checkNode() throws SemanticException {
        // control de nombre

        checkId();

        // control de argumentos
        for (ExpressionNode arg : actualArgs) {
            arg.checkNode();
        }

        controlFormalArgs();

        // control de llamadas

        Type callerType = idType;
        for (CallNode call : callList) {
            call.setCallerType(callerType);
            call.checkNode();
            callerType = call.getCallReturnType();
        }

        controlReturnType();
    }

    /**
     * Controla que sea un metodo de la clase actual Si es un constructor o no
     * esta definido, ocurrira un error
     *
     * @throws SemanticException
     */
    private void checkId() throws SemanticException {
        // o bien es un metodo de la clase actual
        // o bien es un metodo de alguna clase ancestro de la clase actual
        // si es un constructor, ocurrira un error
        String currentClass = symbolTable.getCurrentClass();
        String currentMethod = symbolTable.getCurrentMethod();
        String idName = id.getLexeme();

        if (currentClass.equals(idName)) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: No puede realizarse una llamada a un constructor.");
        }

        MethodEntry methodEntry = symbolTable.getClassEntry(currentClass).getMethodEntry(idName);
        MethodEntry currentMethodEntry = symbolTable.getClassEntry(currentClass).getMethodEntry(currentMethod);

        if (methodEntry == null) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: No existe el metodo '" + idName + "' en la clase " + currentClass + ".");
        } else if (methodEntry.getModifier().equals("dynamic") && currentMethodEntry.getModifier().equals("static")) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: No puede hacerse una invocacion al metodo dinamico '" + idName + "' en la clase " + currentClass + " en el contexto del metodo estatico '" + currentMethod + "'.");
        } else {
            idType = symbolTable.getClassEntry(currentClass).getMethodEntry(idName).getReturnType();
            setExpressionType(idType);
        }
    }

    /**
     * Control de conformidad de tipos entre argumentos formales y argumentos
     * actuales de un metodo
     *
     * @throws SemanticException
     */
    private void controlFormalArgs() throws SemanticException {
        String currentClass = symbolTable.getCurrentClass();
        Collection<ParameterEntry> formalArgs = symbolTable.getClassEntry(currentClass).getMethodEntry(id.getLexeme()).getParameters().values();
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
        Type nextType = currentType;
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

        this.setExpressionType(nextType);

    }
}
