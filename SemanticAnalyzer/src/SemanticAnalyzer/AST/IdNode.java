package SemanticAnalyzer.AST;

import SemanticAnalyzer.SemanticException;
import SemanticAnalyzer.SymbolTable.InstanceVariableEntry;
import SemanticAnalyzer.SymbolTable.LocalVariableEntry;
import SemanticAnalyzer.SymbolTable.ParameterEntry;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.SymbolTable.Type.ClassType;
import SemanticAnalyzer.SymbolTable.Type.Type;
import SemanticAnalyzer.Token;
import java.util.LinkedHashMap;

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

    @Override
    public void checkNode() throws SemanticException {
        String currentClass = symbolTable.getCurrentClass();
        String currentMethod = symbolTable.getCurrentMethod();
        String idName = id.getLexeme();

        LinkedHashMap<String, ParameterEntry> currentMethodParameters = symbolTable.getClassEntry(currentClass).getMethodEntry(currentMethod).getParameters();
        LinkedHashMap<String, LocalVariableEntry> currentMethodLocalVariables = symbolTable.getClassEntry(currentClass).getMethodEntry(currentMethod).getLocalVariables();

        if (currentMethodParameters.containsKey(idName)) {
            // es un parametro del metodo actual
            this.setExpressionType(currentMethodParameters.get(idName).getType());
            return;
        } else if (currentMethodLocalVariables.containsKey(idName)) {
            // es una variable local del metodo actual
            this.setExpressionType(currentMethodLocalVariables.get(idName).getType());
            return;
        }

        LinkedHashMap<String, InstanceVariableEntry> currentClassInstanceVariables = symbolTable.getClassEntry(currentClass).getInstanceVariables();

        if (currentClassInstanceVariables.containsKey(idName)) {
            // es una variable de instancia de la clase actual
            this.setExpressionType(currentClassInstanceVariables.get(idName).getType());
            return;
        }

        LinkedHashMap classes = symbolTable.getClasses();

        if (classes.containsKey(idName)) {
            // es una clase
            Type aType = new ClassType(idName);
            this.setExpressionType(aType);
            return;
        }

        throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: No existe el nombre en la tabla de simbolos.");
    }

    public Token getId() {
        return id;
    }
}
