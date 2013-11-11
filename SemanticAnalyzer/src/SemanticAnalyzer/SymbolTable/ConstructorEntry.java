package SemanticAnalyzer.SymbolTable;

/**
 * Representacion de la entrada de constructor
 *
 * @author Ramiro Agis
 * @author Victoria Martinez de la Cruz
 */
public class ConstructorEntry extends ServiceEntry {

    public ConstructorEntry(String constructorName, String className, SymbolTable symbolTable, int lineNumber) {
        super(constructorName, className, symbolTable, lineNumber);
    }
}
