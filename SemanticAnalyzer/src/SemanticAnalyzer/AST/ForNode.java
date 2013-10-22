
package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.SymbolTable;

/**
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class ForNode extends SentenceNode {

    protected AssignNode init;
    protected ExpressionNode condition;
    protected ExpressionNode increment;
    protected SentenceNode sentence;
    
    public ForNode(SymbolTable st, AssignNode i, ExpressionNode cond, ExpressionNode inc, SentenceNode s) {
        super(st);
        init = i;
        condition = cond;
        increment = inc;
        sentence = s;
    }

    @Override
    public void checkNode() throws SemanticException {
        init.checkNode();
        condition.checkNode();
        
        
        
        if (!condition.getExpressionType().equals("booleanLiteral")) {
            throw new SemanticException("Linea: " + operator.getLineNumber() + " - Error semantico: El operador binario " + operatorLexeme + " no puede aplicarse a la subexpresion de tipo " + left.getExpressionType() + ". Se esperaba una subexpresion de tipo entero.");
        }
    }
}
