package IntermediateCodeGeneration.AST;

import IntermediateCodeGeneration.SemanticException;
import IntermediateCodeGeneration.SymbolTable.ClassEntry;
import IntermediateCodeGeneration.SymbolTable.ConstructorEntry;
import IntermediateCodeGeneration.SymbolTable.InstanceVariableEntry;
import IntermediateCodeGeneration.SymbolTable.LocalVariableEntry;
import IntermediateCodeGeneration.SymbolTable.MethodEntry;
import IntermediateCodeGeneration.SymbolTable.ParameterEntry;
import IntermediateCodeGeneration.SymbolTable.ServiceEntry;
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

    public Token getId() {
        return id;
    }

    @Override
    public void checkNode() throws SemanticException {
        String currentClass = symbolTable.getCurrentClass();
        String currentService = symbolTable.getCurrentService();

        ClassEntry currentClassEntry = symbolTable.getClassEntry(currentClass);
        MethodEntry currentMethodEntry = currentClassEntry.getMethodEntry(currentService);
        ConstructorEntry currentConstructorEntry = currentClassEntry.getConstructorEntry();
        ServiceEntry currentServiceEntry;

        checkId();
        expression.checkNode();

        if (currentMethodEntry != null) {
            currentServiceEntry = currentMethodEntry;
        } else if (currentConstructorEntry != null) {
            currentServiceEntry = currentConstructorEntry;
        } else {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: No esta definido el servicio '" + currentService + "' en la clase actual.");
        }

        if (currentClassEntry.getInstanceVariableEntry(id.getLexeme()) != null
                || currentServiceEntry.getLocalVariableEntry(id.getLexeme()) != null
                || currentServiceEntry.getParameterEntry(id.getLexeme()) != null) {
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
        String currentService = symbolTable.getCurrentService();
        String idName = id.getLexeme();
        LinkedHashMap<String, ParameterEntry> currentServiceParameters;
        LinkedHashMap<String, LocalVariableEntry> currentServiceLocalVariables;

        MethodEntry currentMethodEntry = symbolTable.getClassEntry(currentClass).getMethodEntry(currentService);
        ConstructorEntry currentConstructorEntry = symbolTable.getClassEntry(currentClass).getConstructorEntry();

        if (currentMethodEntry != null) {
            currentServiceParameters = currentMethodEntry.getParameters();
            currentServiceLocalVariables = currentMethodEntry.getLocalVariables();
        } else if (currentConstructorEntry != null) {
            currentServiceParameters = currentConstructorEntry.getParameters();
            currentServiceLocalVariables = currentConstructorEntry.getLocalVariables();
        } else {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: No esta definido el servicio '" + currentService + "' en la clase actual.");
        }

        if (currentServiceParameters.containsKey(idName)) {
            // es un parametro del metodo actual
            idType = currentServiceParameters.get(idName).getType();
            return;
        } else if (currentServiceLocalVariables.containsKey(idName)) {
            // es una variable local del metodo actual
            idType = currentServiceLocalVariables.get(idName).getType();
            return;
        }

        LinkedHashMap<String, InstanceVariableEntry> currentClassInstanceVariables = symbolTable.getClassEntry(currentClass).getInstanceVariables();

        if (currentClassInstanceVariables.containsKey(idName)) {
            // es una variable de instancia de la clase actual

            if (currentMethodEntry != null && currentMethodEntry.getModifier().equals("static")) {
                throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: No puede usarse la variable de instancia '" + idName + "' en un metodo estatico.");
            }

            idType = currentClassInstanceVariables.get(idName).getType();
            return;
        }

        if (currentMethodEntry != null) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: La variable '" + idName + "' no es visible en el metodo '" + currentMethodEntry.getName() + "'.");
        } else if (currentConstructorEntry != null) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: La variable '" + idName + "' no es visible en el constructor '" + currentConstructorEntry.getName() + "'.");

        }
    }

    @Override
    public void generateCode() throws SemanticException {
        expression.setICG(ICG);
        expression.generateCode();

        ICG.GEN(".CODE");

        String currentClass = symbolTable.getCurrentClass();
        String currentService = symbolTable.getCurrentService();

        ClassEntry currentClassEntry = symbolTable.getClassEntry(currentClass);
        MethodEntry currentMethodEntry = currentClassEntry.getMethodEntry(currentService);
        ConstructorEntry currentConstructorEntry = currentClassEntry.getConstructorEntry();

        if (currentMethodEntry != null) {

            if (currentMethodEntry.getLocalVariableEntry(id.getLexeme()) != null) {
                // el offset de una variable local comienza en 0 y decrece
                int localVariableOffset = currentMethodEntry.getLocalVariableEntry(id.getLexeme()).getOffset();
                ICG.GEN("STORE", localVariableOffset, "AssignNode. El lado izquierdo es una variable local del metodo '" + currentService + "'.");
            } else if (currentMethodEntry.getParameterEntry(id.getLexeme()) != null) {
                // el offset de una variable local comienza en 0 y decrece
                int parameterOffset = currentMethodEntry.getParameterEntry(id.getLexeme()).getOffset();
                ICG.GEN("STORE", parameterOffset + 3, "AssignNode. El lado izquierdo es un parametro del metodo '" + currentService + "'.");
            } else if (currentClassEntry.getInstanceVariableEntry(id.getLexeme()) != null) {
                ICG.GEN("LOAD", 3, "AssignNode. Apilamos THIS");
                ICG.GEN("SWAP", "AssignNode. Invertimos el orden del tope de la pila. STOREREF usa los parametros en orden inverso (CIR, valor).");

                int offsetInstanceVariable = currentClassEntry.getInstanceVariableEntry(id.getLexeme()).getOffset();
                ICG.GEN("STOREREF", offsetInstanceVariable, "AssignNode. El lado izquierdo es una variable de instancia de la clase '" + currentClass + "'.");
            }

        } else if (currentConstructorEntry != null) {
            if (currentConstructorEntry.getLocalVariableEntry(id.getLexeme()) != null) {
                // el offset de una variable local comienza en 0 y decrece
                int localVariableOffset = currentConstructorEntry.getLocalVariableEntry(id.getLexeme()).getOffset();
                ICG.GEN("STORE", localVariableOffset, "AssignNode. El lado izquierdo es una variable local del metodo '" + currentService + "'.");
            } else if (currentConstructorEntry.getParameterEntry(id.getLexeme()) != null) {
                // el offset de una variable local comienza en 0 y decrece
                int parameterOffset = currentConstructorEntry.getParameterEntry(id.getLexeme()).getOffset();
                ICG.GEN("STORE", parameterOffset + 3, "AssignNode. El lado izquierdo es un parametro del metodo '" + currentService + "'.");
            } else if (currentClassEntry.getInstanceVariableEntry(id.getLexeme()) != null) {
                ICG.GEN("LOAD", 3, "AssignNode. Apilamos THIS");
                ICG.GEN("SWAP", "AssignNode. Invertimos el orden del tope de la pila. STOREREF usa los parametros en orden inverso (CIR, valor).");

                int offsetInstanceVariable = currentClassEntry.getInstanceVariableEntry(id.getLexeme()).getOffset();
                ICG.GEN("STOREREF", offsetInstanceVariable, "AssignNode. El lado izquierdo es una variable de instancia de la clase '" + currentClass + "'.");
            }
        }
    }
}
