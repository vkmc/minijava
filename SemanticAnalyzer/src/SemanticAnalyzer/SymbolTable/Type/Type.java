package SemanticAnalyzer.SymbolTable.Type;

import SemanticAnalyzer.SymbolTable.SymbolTable;

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
     * Controla que haya conformidad de tipos entre el tipo que recibe el mensaje
     * y el tipo pasado por parametro
     *
     * @param type
     * @return true si hay conformidad de tipos, false en caso contrario
     */
    public abstract boolean checkConformity(Type type, SymbolTable symbolTable);
    
    /**
     * Retorna el nombre del tipo
     * @return typeName
     */
    public String getTypeName() {
        return typeName;
    }
    
    protected boolean isPrimitiveType(Type type) {
        String t = type.getTypeName();
        return t.equals("boolean") || t.equals("char") || t.equals("int") || t.equals("String") || t.equals("void");
    }
}
