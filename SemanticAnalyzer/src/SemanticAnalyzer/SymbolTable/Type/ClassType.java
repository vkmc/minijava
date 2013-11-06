package SemanticAnalyzer.SymbolTable.Type;

import SemanticAnalyzer.SymbolTable.ClassEntry;
import SemanticAnalyzer.SymbolTable.SymbolTable;

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
    
    public boolean checkConformity(Type type, SymbolTable symbolTable) {
        // A (type) has to conform B.
        boolean conforms = false;
        if (isPrimitiveType(type)) {
            // A primitive type can't conform a class type.
            return false;
        }
        // 
        ClassEntry subtypeClass = symbolTable.getClassEntry(type.getTypeName());
	ClassEntry supertypeClass = symbolTable.getClassEntry(this.getTypeName());
        String subtypeName = subtypeClass.getName();
        String supertypeName = supertypeClass.getName();
        
        if (subtypeName.equals("Object") && (supertypeName.equals("Object"))) {
            return true;
        }
        
        while (!conforms && !subtypeName.equals("Object")) {
            // If the types are the same then it conforms.
            if (subtypeName.equals(supertypeName)) {
                conforms = true;
            } else {
                // If the types aren't the same, we check with the subtype's parent.
                subtypeClass = subtypeClass.getParent();
                subtypeName = subtypeClass.getName();
            }
        }
        return conforms;
    }
}
