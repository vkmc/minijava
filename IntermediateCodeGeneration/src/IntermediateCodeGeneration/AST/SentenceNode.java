package IntermediateCodeGeneration.AST;

import IntermediateCodeGeneration.ICGenerator;
import IntermediateCodeGeneration.SemanticException;
import IntermediateCodeGeneration.SymbolTable.Type.Type;
import IntermediateCodeGeneration.Token;
import IntermediateCodeGeneration.SymbolTable.SymbolTable;

/**
 * Representacion de un nodo sentencia
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public abstract class SentenceNode {

    protected SymbolTable symbolTable;
    protected ICGenerator ICG;
    protected Type sentenceType;
    protected Token token;

    public SentenceNode(SymbolTable symbolTable, Token token) {
        this.symbolTable = symbolTable;
        this.token = token;
    }

    public Type getSentenceType() {
        return sentenceType;
    }

    public void setSentenceType(Type sentenceType) {
        this.sentenceType = sentenceType;
    }

    public abstract void checkNode() throws SemanticException;

    public abstract void generateCode() throws SemanticException;

    public void setICG(ICGenerator ICG) {
        this.ICG = ICG;
    }
}
