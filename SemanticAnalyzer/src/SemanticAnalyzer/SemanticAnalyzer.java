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
    //private ICG icg;
    
    public SemanticAnalyzer(String fileName) {
        parser = new Parser(fileName);
        symbolTable = parser.getSymbolTable(); 
        // icg = new ICG("output_file.txt");
    }
    
    public void checkSemantics() throws LexicalException, SyntacticException, SemanticException {
        parser.analize();
        declarationCheck();
        sentencesCheck();
        
        // Uncomment to begin shitstorm.
        //icg.setup(symbolTable);
        //icg.generateOutput();
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
