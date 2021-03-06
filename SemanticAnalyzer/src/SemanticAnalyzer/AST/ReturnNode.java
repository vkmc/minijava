package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.SymbolTable.Type.Type;
import SemanticAnalyzer.SymbolTable.Type.VoidType;
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
    public void checkNode() throws SemanticException {
        String currentClass = symbolTable.getCurrentClass();
        String currentMethod = symbolTable.getCurrentMethod();

        String returnType = symbolTable.getClassEntry(currentClass).getMethodEntry(currentMethod).getReturnType().getTypeName();

        if (!returnType.equals("void")) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: Se esperaba retornar una expresion de tipo " + returnType + ".");
        }

        Type aType = new VoidType();
        this.setSentenceType(aType);
    }
}
