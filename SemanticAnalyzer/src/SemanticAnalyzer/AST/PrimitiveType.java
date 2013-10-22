package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.Type;

/**
 * Representacion de los tipos de datos primitivos
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class PrimitiveType extends Type {
    
    public PrimitiveType(String typeName) {
        super(typeName);
    }
    
}
