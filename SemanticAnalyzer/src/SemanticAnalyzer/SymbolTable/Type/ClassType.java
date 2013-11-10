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

    @Override
    public boolean checkConformity(Type type, SymbolTable symbolTable) {
        // El tipo A (quien recibe el mensaje) conforma B el tipo pasado por parametro

        boolean conforms = false;

        if (isPrimitiveType(type)) {
            // El unico tipo primitivo que puede conformar con un tipo clase
            // es null
            if (type.getTypeName().equals("null")) {
                return true;
            }
            // Un tipo primitivo no puede conformar con uno de tipo clase
            return false;
        }

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
                subtypeClass = symbolTable.getClassEntry(subtypeClass.getParent());
                subtypeName = subtypeClass.getName();
            }
        }
        return conforms;
    }
}
