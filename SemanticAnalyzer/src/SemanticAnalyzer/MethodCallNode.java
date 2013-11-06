package SemanticAnalyzer;

import SemanticAnalyzer.AST.CallNode;
import SemanticAnalyzer.AST.ExpressionNode;
import SemanticAnalyzer.AST.PrimaryNode;
import SemanticAnalyzer.SymbolTable.ParameterEntry;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.SymbolTable.Type.Type;
import java.util.Collection;
import java.util.Iterator;
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
        
        for (CallNode call : callList) {
            call.checkNode();
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
        String idName = id.getLexeme();

        if (currentClass.equals(idName)) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: No puede realizarse una llamada a un constructor.");
        } else if (symbolTable.getClassEntry(currentClass).getMethodEntry(idName) == null) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: No existe el metodo '" + idName + "' en la clase " + currentClass + ".");
        } else {
            Type methodReturnType = symbolTable.getClassEntry(currentClass).getMethodEntry(idName).getReturnType();
            this.setExpressionType(methodReturnType);
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
        Iterator<ParameterEntry> formalArgsIterator = formalArgs.iterator();
        int counter = 0;

        if (formalArgs.size() != actualArgs.size()) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: Las listas de argumentos actuales y formales para el metodo " + id.getLexeme() + " de la clase " + currentClass + " tienen diferente longitud.");
        }

        while (formalArgsIterator.hasNext()) {
            ParameterEntry formalArg = formalArgsIterator.next();
            if (!formalArg.getType().checkConformity(actualArgs.get(counter).getExpressionType(), symbolTable)) {
                throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: El tipo del argumento actual no conforma con el tipo del argumento formal."
                        + " El tipo del argumento actual es " + actualArgs.get(counter).getExpressionType().getTypeName() + " y el tipo del argumento formal es " + formalArg.getType().getTypeName() + ".");
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
        Type nextCallType = currentType;

        Iterator<CallNode> iteratorCallList = callList.iterator();

        for (CallNode nextCall : callList) {
            nextCallType = nextCall.getExpressionType();
            nextCallType.checkConformity(currentType, symbolTable);
        }

        // si no surge ningun error durante el control de conformidad de tipos
        // se le asigna al nodo actual el tipo del ultimo callnode en la lista

        this.setExpressionType(nextCallType);

    }
}
