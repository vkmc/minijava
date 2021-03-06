package IntermediateCodeGeneration.SymbolTable.Type;

/**
 * Representacion de los tipos de datos primitivos
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public abstract class PrimitiveType extends Type {

    public PrimitiveType(String typeName) {
        super(typeName);
    }

    @Override
    public boolean checkConformity(Type type) {
        if (isPrimitiveType(type) && typeName.equals(type.getTypeName())) {
            return true;
        }
        return false;
    }
}
