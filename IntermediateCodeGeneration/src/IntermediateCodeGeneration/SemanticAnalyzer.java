package IntermediateCodeGeneration;

import IntermediateCodeGeneration.SymbolTable.SymbolTable;

/**
 * Analizador semantico. Agrega información semantica al parse tree y crea la
 * tabla de simbolos.
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class SemanticAnalyzer {

    private Parser parser;
    private SymbolTable symbolTable;
    private ICGenerator ICG;

    public SemanticAnalyzer(String input, String output) {
        parser = new Parser(input);
        symbolTable = parser.getSymbolTable();
        ICG = new ICGenerator(output);
    }

    public void checkSemantics() throws LexicalException, SyntacticException, SemanticException {
        parser.analize();
        declarationCheck();
        ICG.setup(symbolTable);
        sentencesCheck();
        System.out.println("El analizador semantico termino exitosamente");
    }

    private void declarationCheck() throws SemanticException {
        symbolTable.declarationCheckInheritance();
        symbolTable.consolidateInheritance();
        symbolTable.declarationCheckReturnType();
        symbolTable.declarationCheckVariables();
        symbolTable.declarationCheckMainExistence();
    }

    private void sentencesCheck() throws SemanticException {
        symbolTable.setICG(ICG);
        symbolTable.sentenceCheck();
    }
}
