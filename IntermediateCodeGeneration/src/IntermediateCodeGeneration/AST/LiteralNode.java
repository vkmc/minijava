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

        if (expressionType.getTypeName().equals("String")) {
            String label = ICG.generateLabel();

            ICG.GEN(".DATA");
            ICG.GEN("lString" + label + "_" + symbolTable.getCurrentMethod() + "_" + symbolTable.getCurrentClass() + ": DW " + literal.getLexeme() + ", 0");

            ICG.GEN(".CODE");
            ICG.GEN("PUSH lString" + label + "_" + symbolTable.getCurrentMethod() + "_" + symbolTable.getCurrentClass(), "Apilamos el label del String '" + literal.getLexeme() + "'.");
        } else if (literal.getLexeme().equals("true")) {
            ICG.GEN(".CODE");
            ICG.GEN("PUSH 1", "Apilamos 'true'");
        } else if (literal.getLexeme().equals("false")) {
            ICG.GEN(".CODE");
            ICG.GEN("PUSH 0", "Apilamos 'false'");
        } else if (literal.getLexeme().equals("null")) {
            ICG.GEN(".CODE");
            ICG.GEN("PUSH 0", "Apilamos 'null'");
        } else {
            ICG.GEN(".CODE");
            ICG.GEN("PUSH " + literal.getLexeme());
        }
    }
}
