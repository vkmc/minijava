package SemanticAnalyzer;

import SemanticAnalyzer.SymbolTable.SymbolTable;

/**
 * Analizador semantico. Agrega información semantica al parse tree y crea la tabla de simbolos.
 * 
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class SemanticAnalyzer {
    private SymbolTable symbolTable;
    
    public SemanticAnalyzer() {
        symbolTable = new SymbolTable();
    }
    
    public void checkSemantics() {
        declarationCheck();
        sentencesCheck();
    }

    private void declarationCheck() {
    }

    private void sentencesCheck() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
