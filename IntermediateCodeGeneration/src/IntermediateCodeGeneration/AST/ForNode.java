package IntermediateCodeGeneration.AST;

import IntermediateCodeGeneration.SemanticException;
import IntermediateCodeGeneration.SymbolTable.ClassEntry;
import IntermediateCodeGeneration.SymbolTable.ConstructorEntry;
import IntermediateCodeGeneration.SymbolTable.MethodEntry;
import IntermediateCodeGeneration.SymbolTable.ServiceEntry;
import IntermediateCodeGeneration.Token;
import IntermediateCodeGeneration.SymbolTable.SymbolTable;

/**
 * Representacion de un nodo for
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class ForNode extends SentenceNode {

    protected AssignNode init;
    protected ExpressionNode condition;
    protected ExpressionNode increment;
    protected SentenceNode sentence;

    public ForNode(SymbolTable symbolTable, AssignNode init, ExpressionNode condition, ExpressionNode increment, SentenceNode sentence, Token token) {
        super(symbolTable, token);
        this.init = init;
        this.condition = condition;
        this.increment = increment;
        this.sentence = sentence;
    }

    @Override
    public void checkNode() throws SemanticException {
        init.checkNode();
        condition.checkNode();

        if (!condition.getExpressionType().getTypeName().equals("boolean")) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: La condicion de la sentencia for debe ser de tipo boolean. Se encontro una expresion de tipo " + condition.getExpressionType().getTypeName() + " .");
        }

        increment.checkNode();

        if (!increment.getExpressionType().checkConformity(init.getSentenceType())) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: La operacion aplicada en el incremento no esta definida para el tipo del contador.");
        }

        sentence.checkNode();
    }

    @Override
    public void generateCode() throws SemanticException {
        String label = ICG.generateLabel();

        ICG.GEN(".CODE");

        init.setICG(ICG);
        init.generateCode();

        ICG.GEN("L_COND_FOR_" + label + "_" + symbolTable.getCurrentService() + "_" + symbolTable.getCurrentClass() + ": NOP");

        condition.setICG(ICG);
        condition.generateCode();

        ICG.GEN("BF L_ENDFOR_" + label + "_" + symbolTable.getCurrentService() + "_" + symbolTable.getCurrentClass());

        sentence.setICG(ICG);
        sentence.generateCode();

        increment.setICG(ICG);
        increment.generateCode();

        // Almaceno el valor del incremento

        Token incrementValue = init.getId();

        String currentClass = symbolTable.getCurrentClass();
        String currentService = symbolTable.getCurrentService();
        ClassEntry currentClassEntry = symbolTable.getClassEntry(currentClass);
        MethodEntry currentMethodEntry = currentClassEntry.getMethodEntry(currentService);
        ConstructorEntry currentConstructorEntry = currentClassEntry.getConstructorEntry();
        ServiceEntry currentServiceEntry;

        if (currentMethodEntry != null) {
            currentServiceEntry = currentMethodEntry;
        } else if (currentConstructorEntry != null) {
            currentServiceEntry = currentConstructorEntry;
        } else {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: No esta definido el servicio '" + currentService + "' en la clase actual.");
        }

        if (currentServiceEntry.getLocalVariableEntry(incrementValue.getLexeme()) != null) {
            // el offset de una variable local comienza en 0 y decrece
            int localVariableOffset = currentServiceEntry.getLocalVariableEntry(incrementValue.getLexeme()).getOffset();
            ICG.GEN("STORE", localVariableOffset, "For. Asignacion. El lado izquierdo es una variable local del metodo '" + currentService + "'");
        } else if (currentServiceEntry.getParameterEntry(incrementValue.getLexeme()) != null) {
            // el offset de una variable local comienza en 0 y decrece
            int parameterOffset = currentServiceEntry.getLocalVariableEntry(incrementValue.getLexeme()).getOffset();
            ICG.GEN("STORE", parameterOffset + 3, "For. Asignacion. El lado izquierdo es un parametro del metodo '" + currentService + "'");
        } else if (currentClassEntry.getInstanceVariableEntry(incrementValue.getLexeme()) != null) {
            ICG.GEN("LOAD", 3, "Apilamos THIS");
            ICG.GEN("SWAP", "Invertimos el orden del tope de la pila. STOREREF usa los parametros en orden inverso (CIR, valor).");

            int offsetInstanceVariable = currentClassEntry.getInstanceVariableEntry(incrementValue.getLexeme()).getOffset();
            ICG.GEN("STOREREF", offsetInstanceVariable, "For. Asignacion . El lado izquierdo es una variable de instancia de la clase '" + currentClass + "'.");
        }

        ICG.GEN("JUMP L_COND_FOR_" + label + "_" + symbolTable.getCurrentService() + "_" + symbolTable.getCurrentClass());
        ICG.GEN("L_ENDFOR_" + label + "_" + symbolTable.getCurrentService() + "_" + symbolTable.getCurrentClass() + ": NOP");

    }
}
