
package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.ParameterEntry;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class CallNode extends PrimaryNode {

    protected IdNode id;
    protected LinkedList<ExpressionNode> actualArgs;
    
    public CallNode(SymbolTable symbolTable, IdNode id, LinkedList<ExpressionNode> actualArgs) {
        super(symbolTable);
        this.id = id;
        this.actualArgs = actualArgs; // actual arguments
    }
    
    @Override
    public void checkNode() {
        id.checkNode();
        
        String currentClass = symbolTable.getCurrentClass();
        LinkedHashMap<String, ParameterEntry> formalArgs = symbolTable.getClassEntry(currentClass).getMethodEntry(id.getId().getLexeme()).getParameters();
        
        int counter = 0;
        boolean match = true;
        
        while (match && counter < formalArgs.size()) {
            
        }
        
    }
    
}
