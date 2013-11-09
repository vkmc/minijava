package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.Token;

/**
 * Representacion de un nodo for
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class ForNode extends SentenceNode {

    protected AssignNode init;
    protected ExpressionNode condition;
    protected ExpressionNode increment;
    protected SentenceNode sentence;

    public ForNode(SymbolTable symbolTable, AssignNode init, ExpressionNode condition, ExpressionNode increment, SentenceNode sentence, Token token) {
        super(symbolTable, token);
        this.init = init;
        this.condition = condition;
        this.increment = increment;
        this.sentence = sentence;
    }

    @Override
    public void checkNode() throws SemanticException {
        init.checkNode();
        condition.checkNode();

        if (!condition.getExpressionType().getTypeName().equals("boolean")) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: La condicion debe ser de tipo boolean. Se encontro: " + condition.getExpressionType().getTypeName() + " .");
        }

        increment.checkNode();

        if (!increment.getExpressionType().checkConformity(init.getSentenceType(), symbolTable)) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: La operacion aplicada en el incremento no esta definida para el tipo del contador.");
        }

        sentence.checkNode();
    }
}
