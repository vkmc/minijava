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

    protected Token id;
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
        
        System.out.println("VIVA HITLER");
        for (ExpressionNode actualArg : actualArgs) {
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
    private void checkId() throws SemanticException {
        // debe ser un constructor
        if (symbolTable.isConstructor(id.getLexeme()) == null) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: El constructor invocado no esta declarado.");
        }

        Type aType = new ClassType(id.getLexeme());
        setExpressionType(aType);
    }

    /**
     * Control de conformidad de tipos entre argumentos formales y argumentos
     * actuales de un metodo
     *
     * @throws SemanticException
     */
    private void controlFormalArgs() throws SemanticException {
        String currentClass = symbolTable.getCurrentClass();
        Collection<ParameterEntry> formalArgs = symbolTable.getClassEntry(currentClass).getConstructorEntry().getParameters().values();
        /*if (currentClass.equals(id.getLexeme())) {
            // Constructor call.
            
        }  else {
            // Method call.
            formalArgs = symbolTable.getClassEntry(currentClass).getMethodEntry(id.getLexeme()).getParameters().values();
        }*/
   
  
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
     * Control del tipo de retorno de la expresion llamadora E.g. para un caso
     * como g().h() tenemos que asegurarnos que se pueda mandar el mensaje h()
     * al retorno de g(). Es decir, el retorno de g() debe ser de un tipo de
     * clase C tal que exista un metodo M en C.
     */
    private void controlReturnType() {
        Type currentType = getExpressionType();
        Type nextType;

        for (CallNode nextCall : callList) {
            nextType = nextCall.getExpressionType();
            nextType.checkConformity(currentType, symbolTable);

            currentType = nextType;
        }

        // si no surge ningun error durante el control de conformidad de tipos
        // se le asigna al nodo actual el tipo del ultimo callnode en la lista
        this.setExpressionType(currentType);

    }
}
