package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.Token;

/**
 * Representacion de un nodo return-expresion
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class ReturnExpNode extends ReturnNode {
    
    protected ExpressionNode expression;
    
    public ReturnExpNode(SymbolTable symbolTable, ExpressionNode expression, Token token) {
        super(symbolTable, token);
        this.expression = expression;
    }
    
    @Override
    public void checkNode() throws SemanticException {        
        expression.checkNode();
        
        String currentClass = symbolTable.getCurrentClass();
        String currentMethod = symbolTable.getCurrentMethod();
        
        if (!expression.getExpressionType().checkConformity(symbolTable.getClassEntry(currentClass).getMethodEntry(currentMethod).getReturnType(), symbolTable)) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: El tipo de la expresion retornada no es conforme al tipo de retorno del metodo actual. El tipo de retorno de la expresion es: '" + expression.getExpressionType().getTypeName() + ". Se esperaba: '" + symbolTable.getClassEntry(currentClass).getMethodEntry(currentMethod).getReturnType().getTypeName() + "' .");
        }
        
        this.setSentenceType(expression.getExpressionType());
    }
}
