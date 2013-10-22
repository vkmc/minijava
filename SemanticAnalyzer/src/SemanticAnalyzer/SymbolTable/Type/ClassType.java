package SemanticAnalyzer.SymbolTable.Type;

/**
 * Representacion de los tipos de datos definidos por el programador
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class ClassType extends Type {

    public ClassType(String typeName) {
        super(typeName);
    }

    @Override
    public boolean checkConformity(Type type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
