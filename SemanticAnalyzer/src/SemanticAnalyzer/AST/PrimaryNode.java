package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.Token;

/**
 * Representacion de un nodo primario
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public abstract class PrimaryNode extends ExpressionNode {

    public PrimaryNode(SymbolTable symbolTable, Token token) {
        super(symbolTable, token);
    }
}
