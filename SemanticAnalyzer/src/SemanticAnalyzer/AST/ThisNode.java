package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.*;
import SemanticAnalyzer.SymbolTable.Type.*;
import SemanticAnalyzer.Token;

/**
 * Representacion del this
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class ThisNode extends PrimaryNode {

    protected Token thisToken;

    public ThisNode(SymbolTable st, Token t) {
        super(st);
        thisToken = t;
    }

    @Override
    public void checkNode() throws SemanticException {
        String currentClass = symbolTable.getCurrentClass();
        String currentMethod = symbolTable.getCurrentMethod();

        if (symbolTable.getClassEntry(currentClass).getMethodEntry(currentMethod).getModifier().equals("static")) {
            throw new SemanticException("Linea: " + thisToken.getLineNumber() + " - Error semantico: No puede usarse la palabra reservada 'this' en el contexto de un metodo estatico");
        } else {
            Type classType = new ClassType(currentClass);
            this.setExpressionType(classType);
        }
    }
}