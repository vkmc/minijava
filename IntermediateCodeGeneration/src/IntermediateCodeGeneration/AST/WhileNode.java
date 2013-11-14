package IntermediateCodeGeneration.AST;

import IntermediateCodeGeneration.SemanticException;
import IntermediateCodeGeneration.Token;
import IntermediateCodeGeneration.SymbolTable.SymbolTable;

/**
 * Representacion de un nodo while
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class WhileNode extends SentenceNode {

    protected ExpressionNode condition;
    protected SentenceNode sentence;

    public WhileNode(SymbolTable symbolTable, ExpressionNode condition, SentenceNode sentence, Token token) {
        super(symbolTable, token);
        this.condition = condition;
        this.sentence = sentence;
    }

    @Override
    public void checkNode() throws SemanticException {
        condition.checkNode();
        String aType = condition.getExpressionType().getTypeName();
        if (!aType.equals("boolean")) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: La condicion de la sentencia while debe ser de tipo boolean. Se encontro una expresion de tipo " + aType);
        }
        sentence.checkNode();
    }

    @Override
    public void generateCode() throws SemanticException {
        String label = ICG.generateLabel();
        String currentClass = symbolTable.getCurrentClass();
        String currentMethod = symbolTable.getCurrentService();

        ICG.GEN(".CODE");
        ICG.GEN("L_WHILE_" + label + "_" + currentMethod + "_" + currentClass + ": NOP");

        condition.setICG(ICG);
        condition.generateCode();

        ICG.GEN("BF L_ENDWHILE_" + label + "_" + currentMethod + "_" + currentClass);

        sentence.setICG(ICG);
        sentence.generateCode();

        ICG.GEN("JUMP L_WHILE_" + label + "_" + currentMethod + "_" + currentClass);
        ICG.GEN("L_ENDWHILE_" + label + "_" + currentMethod + "_" + currentClass + ": NOP");
    }
}
