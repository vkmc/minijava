package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.Token;

/**
 * Representacion de un Identificador
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class IdNode extends PrimaryNode {

    protected Token id;

    public IdNode(SymbolTable symbolTable, Token id) {
        super(symbolTable, id);
        this.id = id;
    }

    public void checkNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Token getId() {
        return id;
    }
}
