package IntermediateCodeGeneration.AST;

import IntermediateCodeGeneration.SemanticException;
import IntermediateCodeGeneration.Token;
import IntermediateCodeGeneration.SymbolTable.SymbolTable;

/**
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class SeparatorNode extends SentenceNode {

    public SeparatorNode(SymbolTable symbolTable, Token token) {
        super(symbolTable, token);
    }

    @Override
    public void checkNode() {
    }

    @Override
    public void generateCode() throws SemanticException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
