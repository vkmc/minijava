package IntermediateCodeGeneration.AST;

import IntermediateCodeGeneration.SemanticException;
import IntermediateCodeGeneration.Token;
import IntermediateCodeGeneration.SymbolTable.SymbolTable;

/**
 * Representacion de un nodo while
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class WhileNode extends SentenceNode {

    protected ExpressionNode condition;
    protected SentenceNode sentence;

    public WhileNode(SymbolTable systemTable, ExpressionNode condition, SentenceNode sentence, Token token) {
        super(systemTable, token);
        this.condition = condition;
        this.sentence = sentence;
    }

    @Override
    public void checkNode() throws SemanticException {
        condition.checkNode();
        String aType = condition.getExpressionType().getTypeName();
        if (!aType.equals("boolean")) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: La condicion de la sentencia while debe ser de tipo boolean. Se encontro una expresion de tipo " + aType);
        }
        sentence.checkNode();
    }
}
