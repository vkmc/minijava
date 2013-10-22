
package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.SymbolTable;

/**
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public abstract class PrimaryNode extends ExpressionNode {
    
    public PrimaryNode(SymbolTable st) {
        super(st);
    }
    
}
