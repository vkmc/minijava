package lexical_analyzer;

/**
 * Representación interna de un Token
 * Un Token es una cadena de caracteres con significado propio.
 * 
 * @author Ramiro Agís
 * @author Victoria Martínez de la Cruz
 */
public class Token {
    private String lexeme, token;
    private int lineNumber;

    public Token(String token, String lexeme, int lineNumber){
        this.token = token;
        this.lexeme = lexeme;
        this.lineNumber = lineNumber;
    }
    
    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String l) {
        lexeme = l;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String t) {
        token = t;
    }

    public int getLine() {
        return lineNumber;
    }

    public void setLine(int n) {
        lineNumber = n;
    }
    
    /**
     * Retorna una cadena conteniendo el número de línea, el token
     * y el lexema.
     * 
     * @return cadena conteniendo el número de línea, el token y el lexema
     */
    @Override
    public String toString() {
        return "["+lineNumber+"]"+" "+token+" "+" "+lexeme+" ";
    }
    
    
}
