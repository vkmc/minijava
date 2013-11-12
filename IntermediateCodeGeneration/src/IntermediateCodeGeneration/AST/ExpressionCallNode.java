package IntermediateCodeGeneration.AST;

import IntermediateCodeGeneration.ICGenerator;
import IntermediateCodeGeneration.SemanticException;
import IntermediateCodeGeneration.SymbolTable.Type.Type;
import IntermediateCodeGeneration.Token;
import IntermediateCodeGeneration.SymbolTable.SymbolTable;
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
        // deberia crear una lista vacia para que no falle el foreach?
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

        setExpressionType(expression.getExpressionType());

        Type callerType = getExpressionType();

        for (CallNode call : callList) {
            call.setCallerType(callerType);
            call.checkNode();
            callerType = call.getCallReturnType();
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

        for (CallNode nextCall : callList) {
            nextType = nextCall.getExpressionType();
            nextType.checkConformity(currentType);

            currentType = nextType;
        }

        // si no surge ningun error durante el control de conformidad de tipos
        // se le asigna al nodo actual el tipo del ultimo callnode en la lista

        this.setExpressionType(currentType);

    }

    @Override
    public void generateCode() throws SemanticException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
