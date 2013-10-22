
package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.SymbolTable;

/**
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class SeparatorNode extends SentenceNode {
    
    public SeparatorNode(SymbolTable st) {
        super(st);
    }
    
    @Override
    public void checkNode() {
        
    }
    
}
