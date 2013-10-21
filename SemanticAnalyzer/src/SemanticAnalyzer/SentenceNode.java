
package SemanticAnalyzer;

/**
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public abstract class SentenceNode {
    
    protected SymbolTable symbolTable;
    protected String sentenceType;
    
    public SentenceNode(SymbolTable st) {
        symbolTable = st;
    }
    
    public String getSentenceType() {
        return sentenceType;
    }
    
    public void setSentenceType(String type) {
        sentenceType = type;
    }
    
    public SymbolTable getSymbolTable() {
        return symbolTable;
    }
    
    public void setSymbolTable(SymbolTable st) {
        symbolTable = st;
    }
    
    public abstract void checkNode();
}
