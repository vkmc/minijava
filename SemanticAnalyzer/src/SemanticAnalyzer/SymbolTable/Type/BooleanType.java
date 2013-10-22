package SemanticAnalyzer.SymbolTable.Type;

/**
 * Representacion de un boolean
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class BooleanType extends PrimitiveType {
    
    public BooleanType() {
        super("boolean");
    }

    @Override
    public boolean checkConformity(Type type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}