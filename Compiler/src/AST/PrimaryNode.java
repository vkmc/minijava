package IntermediateCodeGeneration.AST;

import IntermediateCodeGeneration.Token;
import IntermediateCodeGeneration.SymbolTable.SymbolTable;

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
