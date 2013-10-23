package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.SymbolTable.Type.*;
import SemanticAnalyzer.Token;

/**
 * Representacion de un nodo sentencia
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public abstract class SentenceNode {

    protected SymbolTable symbolTable;
    protected Type sentenceType;
    protected Token token;

    public SentenceNode(SymbolTable systemTable, Token token) {
        this.symbolTable = systemTable;
        this.token = token;
    }

    public Type getSentenceType() {
        return sentenceType;
    }

    public void setSentenceType(Type sentenceType) {
        this.sentenceType = sentenceType;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public abstract void checkNode() throws SemanticException;
}
