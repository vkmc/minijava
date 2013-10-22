package SemanticAnalyzer.SymbolTable.Type;

/**
 * Representacion de los tipos de datos
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public abstract class Type {

    protected String typeName;

    public Type(String typeName) {
        this.typeName = typeName;
    }
    
    public abstract boolean checkConformity(Type type);
}
