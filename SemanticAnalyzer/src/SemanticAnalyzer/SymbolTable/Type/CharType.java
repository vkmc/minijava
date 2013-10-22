package SemanticAnalyzer.SymbolTable.Type;

/**
 * Representacion de un char
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class CharType extends PrimitiveType {
    
    public CharType() {
        super("char");
    }

    @Override
    public boolean checkConformity(Type type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
