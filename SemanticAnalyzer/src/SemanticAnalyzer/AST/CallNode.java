package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.ParameterEntry;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.Token;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Representacion de un nodo llamada
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class CallNode extends PrimaryNode {

    protected IdNode id;
    protected LinkedList<ExpressionNode> actualArgs;

    public CallNode(SymbolTable symbolTable, IdNode id, LinkedList<ExpressionNode> actualArgs, Token token) {
        super(symbolTable, token);
        this.id = id;
        this.actualArgs = actualArgs; // actual arguments
    }

    @Override
    public void checkNode() throws SemanticException {
        id.checkNode();

        String currentClass = symbolTable.getCurrentClass();
        Collection<ParameterEntry> formalArgs = symbolTable.getClassEntry(currentClass).getMethodEntry(id.getId().getLexeme()).getParameters().values();
        Iterator<ParameterEntry> formalArgsIterator = formalArgs.iterator();
        int counter = 0;

        if (formalArgs.size() != actualArgs.size()) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: Las listas de argumentos actuales y formales para el metodo " + id.getId().getLexeme() + " de la clase " + currentClass + " tienen diferente longitud.");
        }

        while (formalArgsIterator.hasNext()) {
            ParameterEntry formalArg = formalArgsIterator.next();
            if (formalArg.getType().checkConformity(actualArgs.get(counter).getExpressionType())) {
                throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: El tipo del argumento actual no conforma con el tipo del argumento formal."
                        + " El tipo del argumento actual es " + actualArgs.get(counter).getExpressionType() + " y el tipo del argumento formal es " + formalArg.getType() + ".");
            }
        }


    }
}