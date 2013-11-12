package IntermediateCodeGeneration.AST;

import IntermediateCodeGeneration.SemanticException;
import IntermediateCodeGeneration.SymbolTable.InstanceVariableEntry;
import IntermediateCodeGeneration.SymbolTable.LocalVariableEntry;
import IntermediateCodeGeneration.SymbolTable.ParameterEntry;
import IntermediateCodeGeneration.SymbolTable.Type.Type;
import IntermediateCodeGeneration.Token;
import IntermediateCodeGeneration.SymbolTable.SymbolTable;
import java.util.LinkedHashMap;

/**
 * Representacion de la asignacion
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class AssignNode extends SentenceNode {

    protected Token id;
    private Type idType;
    protected ExpressionNode expression;

    public AssignNode(SymbolTable symbolTable, Token id, ExpressionNode expression, Token token) {
        super(symbolTable, token);
        this.id = id;

        this.expression = expression;
    }

    @Override
    public void checkNode() throws SemanticException {
        String currentClass = symbolTable.getCurrentClass();
        String currentMethod = symbolTable.getCurrentMethod();

        checkId();
        expression.checkNode();

        if (symbolTable.getClassEntry(currentClass).getInstanceVariableEntry(id.getLexeme()) != null
                || symbolTable.getClassEntry(currentClass).getMethodEntry(currentMethod).getLocalVariableEntry(id.getLexeme()) != null
                || symbolTable.getClassEntry(currentClass).getMethodEntry(currentMethod).getParameterEntry(id.getLexeme()) != null) {
            if (!idType.checkConformity(expression.getExpressionType())) {
                throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: No puede asignarse una expresion de tipo " + expression.getExpressionType().getTypeName() + " a una variable de tipo " + idType.getTypeName() + ".");
            }
        } else {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: La variable " + id.getLexeme() + " no esta declarada.");
        }

        setSentenceType(idType);

    }

    private void checkId() throws SemanticException {
        String currentClass = symbolTable.getCurrentClass();
        String currentMethod = symbolTable.getCurrentMethod();
        String idName = id.getLexeme();

        LinkedHashMap<String, ParameterEntry> currentMethodParameters = symbolTable.getClassEntry(currentClass).getMethodEntry(currentMethod).getParameters();
        LinkedHashMap<String, LocalVariableEntry> currentMethodLocalVariables = symbolTable.getClassEntry(currentClass).getMethodEntry(currentMethod).getLocalVariables();

        if (currentMethodParameters.containsKey(idName)) {
            // es un parametro del metodo actual
            idType = currentMethodParameters.get(idName).getType();
            return;
        } else if (currentMethodLocalVariables.containsKey(idName)) {
            // es una variable local del metodo actual
            idType = currentMethodLocalVariables.get(idName).getType();
            return;
        }

        LinkedHashMap<String, InstanceVariableEntry> currentClassInstanceVariables = symbolTable.getClassEntry(currentClass).getInstanceVariables();

        if (currentClassInstanceVariables.containsKey(idName)) {
            // es una variable de instancia de la clase actual

            if (symbolTable.getClassEntry(currentClass).getMethodEntry(currentMethod).getModifier().equals("static")) {
                throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: No puede usarse la variable de instancia '" + idName + "' en un metodo estatico.");
            }

            idType = currentClassInstanceVariables.get(idName).getType();
            return;
        }

        throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: No existe el nombre '" + idName + "' en la tabla de simbolos.");
    }

    @Override
    public void generateCode() throws SemanticException {
    }
}
