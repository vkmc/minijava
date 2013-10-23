package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.Token;

/**
 * Representacion de un nodo return
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class ReturnNode extends SentenceNode {

    protected Token returnToken;

    public ReturnNode(SymbolTable systemTable, Token token) {
        super(systemTable, token);
        returnToken = token;
    }

    @Override
    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
