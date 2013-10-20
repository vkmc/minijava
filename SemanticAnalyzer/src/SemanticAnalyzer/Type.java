package SemanticAnalyzer;

/**
 * Representacion de los tipos de datos
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public abstract class Type {

    private String typeName;

    public Type(String typeName) {
        this.typeName = typeName;
    }
}
