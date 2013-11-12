package IntermediateCodeGeneration.AST;

import IntermediateCodeGeneration.SemanticException;
import IntermediateCodeGeneration.Token;
import IntermediateCodeGeneration.SymbolTable.SymbolTable;

/**
 * Representacion de un nodo If-Then-Else
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class IfThenElseNode extends IfThenNode {

    protected SentenceNode sentenceElse;

    public IfThenElseNode(SymbolTable symbolTable, ExpressionNode condition, SentenceNode sentenceIf, SentenceNode sentenceElse, Token token) {
        super(symbolTable, condition, sentenceIf, token);
        this.sentenceElse = sentenceElse;
    }

    @Override
    public void checkNode() throws SemanticException {
        condition.checkNode();

        if (!condition.getExpressionType().getTypeName().equals("boolean")) {
            throw new SemanticException("Linea: " + token.getLineNumber() + " - Error semantico: La condicion de la sentencia if-then-else debe ser de tipo boolean. Se encontro una expresion de tipo " + condition.getExpressionType().getTypeName() + " .");
        }

        sentenceIf.checkNode();
        sentenceElse.checkNode();
    }

    @Override
    public void generateCode() throws SemanticException {
        String label = ICG.generateLabel();

        condition.setICG(ICG);
        condition.generateCode();

        ICG.GEN(".CODE");

        ICG.GEN("BF lEndIf" + label + "_" + symbolTable.getCurrentMethod() + "_" + symbolTable.getCurrentClass());

        sentenceIf.setICG(ICG);
        sentenceIf.generateCode();

        ICG.GEN("JUMP lEndElse" + label + "_" + symbolTable.getCurrentMethod() + "_" + symbolTable.getCurrentClass());
        ICG.GEN("lEndIf" + label + "_" + symbolTable.getCurrentMethod() + "_" + symbolTable.getCurrentClass() + ": NOP");

        sentenceElse.setICG(ICG);
        sentenceElse.generateCode();

        ICG.GEN("lEndElse" + label + "_" + symbolTable.getCurrentMethod() + "_" + symbolTable.getCurrentClass() + ": NOP");
    }
}
