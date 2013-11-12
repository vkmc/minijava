package IntermediateCodeGeneration.AST;

import IntermediateCodeGeneration.SemanticException;
import IntermediateCodeGeneration.Token;
import IntermediateCodeGeneration.SymbolTable.SymbolTable;

/**
 * Representacion de un nodo sentencia simple
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class SimpleSentenceNode extends SentenceNode {

    protected ExpressionNode expression;

    public SimpleSentenceNode(SymbolTable systemTable, ExpressionNode expression, Token token) {
        super(systemTable, token);
        this.expression = expression;
    }

    @Override
    public void checkNode() throws SemanticException {
        expression.checkNode();
        sentenceType = expression.getExpressionType();
    }

    @Override
    public void generateCode() throws SemanticException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
