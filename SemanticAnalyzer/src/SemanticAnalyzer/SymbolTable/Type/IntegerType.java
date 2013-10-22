package SemanticAnalyzer.SymbolTable.Type;

/**
 * Representacion de un entero
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class IntegerType extends PrimitiveType {
    
    public IntegerType() {
        super("int");
    }

    @Override
    public boolean checkConformity(Type type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
