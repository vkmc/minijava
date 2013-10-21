
package SemanticAnalyzer;

import java.util.LinkedList;

/**
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class BlockNode extends SentenceNode {

    protected LinkedList<SentenceNode> sentenceList;
    
    public BlockNode(SymbolTable st, LinkedList<SentenceNode> sentences) {
        super(st);
        sentenceList = sentences;
    }

    @Override
    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
