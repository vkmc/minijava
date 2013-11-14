package IntermediateCodeGeneration.AST;

import IntermediateCodeGeneration.ICGenerator;
import IntermediateCodeGeneration.SemanticException;
import IntermediateCodeGeneration.SymbolTable.Type.Type;
import IntermediateCodeGeneration.Token;
import IntermediateCodeGeneration.SymbolTable.SymbolTable;

/**
 * Representacion de un nodo expresion
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public abstract class ExpressionNode {

    protected SymbolTable symbolTable;
    protected ICGenerator ICG;
    protected Type expressionType;
    protected Token token;

    public ExpressionNode(SymbolTable symbolTable, Token token) {
        this.symbolTable = symbolTable;
        this.token = token;
    }

    public Type getExpressionType() {
        return expressionType;
    }

    public void setExpressionType(Type expressionType) {
        this.expressionType = expressionType;
    }

    public abstract void checkNode() throws SemanticException;

    public abstract void generateCode() throws SemanticException;

    public void setICG(ICGenerator ICG) {
        this.ICG = ICG;
    }
}
