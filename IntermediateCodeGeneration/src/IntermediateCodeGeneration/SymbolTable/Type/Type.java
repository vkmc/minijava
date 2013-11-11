package IntermediateCodeGeneration.SymbolTable.Type;

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

    /**
     * Controla que haya conformidad de tipos entre el tipo que recibe el
     * mensaje y el tipo pasado por parametro
     *
     * @param type
     * @return true si hay conformidad de tipos, false en caso contrario
     */
    public abstract boolean checkConformity(Type type);

    /**
     * Retorna el nombre del tipo
     *
     * @return aTypeName
     */
    public String getTypeName() {
        return typeName;
    }

    public static boolean isPrimitiveType(Type aType) {
        String aTypeName = aType.getTypeName();
        return aTypeName.equals("boolean") || aTypeName.equals("char") || aTypeName.equals("int") || aTypeName.equals("String") || aTypeName.equals("void") || aTypeName.equals("null");
    }
}
