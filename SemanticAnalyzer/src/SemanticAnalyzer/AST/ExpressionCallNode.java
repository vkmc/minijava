package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.SymbolTable.Type.Type;
import SemanticAnalyzer.Token;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Representacion de un nodo expresion-llamada
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class ExpressionCallNode extends PrimaryNode {

    protected ExpressionNode expression;
    protected LinkedList<CallNode> callList;

    public ExpressionCallNode(SymbolTable symbolTable, ExpressionNode expression, Token token) {
        super(symbolTable, token);
        this.expression = expression;
        this.callList = null;
    }

    public ExpressionCallNode(SymbolTable symbolTable, ExpressionNode expression, LinkedList<CallNode> callList, Token token) {
        super(symbolTable, token);
        this.expression = expression;
        this.callList = callList;
    }

    @Override
    public void checkNode() throws SemanticException {
        expression.checkNode();

        this.setExpressionType(expression.getExpressionType());

        for (CallNode call : callList) {
            call.checkNode();
        }

        controlReturnType();
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
