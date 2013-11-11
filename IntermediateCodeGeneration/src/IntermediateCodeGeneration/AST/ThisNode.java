package IntermediateCodeGeneration.AST;

import IntermediateCodeGeneration.SemanticException;
import IntermediateCodeGeneration.SymbolTable.Type.*;
import IntermediateCodeGeneration.Token;
import IntermediateCodeGeneration.SymbolTable.SymbolTable;

/**
 * Representacion del nodo this
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class ThisNode extends PrimaryNode {

    protected Token thisToken;

    public ThisNode(SymbolTable systemTable, Token token) {
        super(systemTable, token);
        thisToken = token;
    }

    @Override
    public void checkNode() throws SemanticException {
        String currentClass = symbolTable.getCurrentClass();
        String currentMethod = symbolTable.getCurrentMethod();

        if (symbolTable.getClassEntry(currentClass).getMethodEntry(currentMethod).getModifier().equals("static")) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: No puede usarse la palabra reservada 'this' en el contexto de un metodo estatico.");
        } else {
            Type classType = new ClassType(currentClass, symbolTable);
            this.setExpressionType(classType);
        }
    }
}