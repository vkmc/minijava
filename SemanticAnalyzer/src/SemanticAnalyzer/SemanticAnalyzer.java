package SemanticAnalyzer;

import SemanticAnalyzer.SymbolTable.SymbolTable;

/**
 * Analizador semantico. Agrega información semantica al parse tree y crea la tabla de simbolos.
 * 
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class SemanticAnalyzer {
    private Parser parser;
    private SymbolTable symbolTable;
    
    public SemanticAnalyzer(String fileName) {
        parser = new Parser(fileName);
        symbolTable = parser.getSymbolTable();
    }
    
    public void checkSemantics() throws LexicalException, SyntacticException, SemanticException {
        parser.analize();
        declarationCheck();
        sentencesCheck();
    }

    private void declarationCheck() throws SemanticException {
        symbolTable.declarationCheckInheritance();
        symbolTable.declarationCheckReturnType();
        symbolTable.declarationCheckVariables();
        symbolTable.declarationCheckMainExistence();
    }

    private void sentencesCheck() throws SemanticException {
        symbolTable.sentenceCheck();
    }
    
}
