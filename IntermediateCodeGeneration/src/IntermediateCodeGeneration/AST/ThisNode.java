package IntermediateCodeGeneration.AST;

import IntermediateCodeGeneration.SemanticException;
import IntermediateCodeGeneration.SymbolTable.MethodEntry;
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

    public ThisNode(SymbolTable symbolTable, Token token) {
        super(symbolTable, token);
        thisToken = token;
    }

    @Override
    public void checkNode() throws SemanticException {
        String currentClass = symbolTable.getCurrentClass();
        String currentService = symbolTable.getCurrentService();

        MethodEntry currentMethodEntry = symbolTable.getClassEntry(currentClass).getMethodEntry(currentService);

        if (currentMethodEntry != null && currentMethodEntry.getModifier().equals("static")) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: No se puede hacer referencia al objeto actual usando la palabra reservada 'this' en el contexto de un metodo estatico.");
        } else {
            Type classType = new ClassType(currentClass, symbolTable);
            this.setExpressionType(classType);
        }
    }

    @Override
    public void generateCode() throws SemanticException {
        ICG.GEN(".CODE");
        ICG.GEN("LOAD", 3, "ThisNode. Apilamos THIS");
    }
}
