package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.Token;
import java.util.LinkedList;

/**
 * Representacion un nodo bloque
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class BlockNode extends SentenceNode {

    protected LinkedList<SentenceNode> sentenceList;

    public BlockNode(SymbolTable symbolTable, LinkedList<SentenceNode> sentences, Token token) {
        super(symbolTable, token);
        sentenceList = sentences;
    }

    @Override
    public void checkNode() throws SemanticException {
        // controlar si este bloque tiene return
        // controlar cada sentencia de este nodo
        for (SentenceNode sentence : sentenceList) {
            sentence.checkNode();
        }
    }

    public LinkedList<SentenceNode> getSentenceList() {
        return sentenceList;
    }
}
