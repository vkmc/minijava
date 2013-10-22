package SemanticAnalyzer.SymbolTable.Type;

/**
 * Representacion de un Void
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class VoidType extends PrimitiveType {
    
    public VoidType(String typeName) {
        super(typeName);
    }

    @Override
    public boolean checkConformity(Type type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
