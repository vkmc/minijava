package IntermediateCodeGeneration.AST;

import IntermediateCodeGeneration.SemanticException;
import IntermediateCodeGeneration.SymbolTable.Type.Type;
import IntermediateCodeGeneration.Token;
import IntermediateCodeGeneration.SymbolTable.SymbolTable;

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
    public void checkNode() {
    }

    @Override
    public void generateCode() throws SemanticException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
