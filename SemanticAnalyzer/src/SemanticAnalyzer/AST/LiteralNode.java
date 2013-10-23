package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.Token;
import SemanticAnalyzer.SymbolTable.Type.Type;

/**
 * Representacion de un nodo literal
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class LiteralNode extends PrimaryNode {

    protected Token literal;

    public LiteralNode(SymbolTable symbolTable, Token literal, Type type) {
        super(symbolTable, literal);
        this.literal = literal;
        expressionType = type;
    }

    @Override
    public void checkNode() {}
}
