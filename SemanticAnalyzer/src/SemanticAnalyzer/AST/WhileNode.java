package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.Token;

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
        String foundType = condition.getExpressionType().getTypeName();
        if (!foundType.equals("boolean")) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: El tipo de la condición para la sentencia While no es valido. Se esperaba un tipo boolean, se encontrun tipo " + foundType);
        }
        sentence.checkNode();
    }
}
