package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.ParameterEntry;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.SymbolTable.Type.Type;
import SemanticAnalyzer.Token;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Representacion de un nodo llamada
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class CallNode extends PrimaryNode {

    protected Token id;
    // callerType representa el tipo del llamador - e.g. id.method1(), la clase de Id.
    // callReturnType representa el tipo de retorno del metodo - e.g. si method1() retorna un objeto de clase Clase
    protected Type callerType, callReturnType;
    protected LinkedList<ExpressionNode> actualArgs;

    public CallNode(SymbolTable symbolTable, Token id, LinkedList<ExpressionNode> actualArgs, Token token) {
        super(symbolTable, token);
        this.id = id;
        this.actualArgs = actualArgs; // actual arguments
    }

    @Override
    public void checkNode() throws SemanticException {
        checkId();

        for (ExpressionNode actualArg : actualArgs) {
            actualArg.checkNode();
        }

        // Compatibilidad con argumentos formales

        String currentClass = symbolTable.getCurrentClass();
        String callerTypeName = callerType.getTypeName();

        Collection<ParameterEntry> formalArgs = symbolTable.getClassEntry(callerTypeName).getMethodEntry(id.getLexeme()).getParameters().values();
        int counter = 0;
        if (formalArgs.size() != actualArgs.size()) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: Las listas de argumentos actuales y formales para el metodo " + id.getLexeme() + " de la clase " + currentClass + " tienen diferente longitud.");
        }

        for (ParameterEntry formalArg : formalArgs) {
            if (!formalArg.getType().checkConformity(actualArgs.get(counter).getExpressionType(), symbolTable)) {
                throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: El tipo del argumento actual no conforma con el tipo del argumento formal."
                        + " El tipo del argumento actual es " + actualArgs.get(counter).getExpressionType().getTypeName() + " y el tipo del argumento formal es " + formalArg.getType().getTypeName() + ".");
            }
            counter++;
        }
    }

    /**
     * Controla que sea un metodo de una clase (cualquiera en la tabla de
     * simbolos) Si es un constructor o no esta definido, ocurrira un error Se
     * utiliza en el nodo de (CallNode)
     *
     * @throws SemanticException
     */
    private void checkId() throws SemanticException {
        // o bien es un metodo de la clase (una clase cualquiera de la tabla de simbolos)
        // o bien representa a un constructor (lo que seria un error)
        String callerTypeName = callerType.getTypeName();

        if (symbolTable.isMethodInClass(callerTypeName, id.getLexeme())) {
            callReturnType = symbolTable.getClassEntry(callerTypeName).getMethodEntry(id.getLexeme()).getReturnType();
            setExpressionType(callReturnType);
        } else if (symbolTable.isConstructor(id.getLexeme()) != null) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: No puede realizarse una llamada a un constructor.");
        } else {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: El metodo invocado no esta declarado en clase " + callerTypeName);
        }
    }

    public void setCallerType(Type callerType) {
        this.callerType = callerType;
    }

    public Type getCallReturnType() {
        return callReturnType;
    }
}