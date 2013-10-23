package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.Token;

/**
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class SeparatorNode extends SentenceNode {

    public SeparatorNode(SymbolTable symbolTable, Token token) {
        super(symbolTable, token);
    }

    @Override
    public void checkNode() {
    }
}
