package SyntacticAnalyzer;

/**
 * Representación lógica de un Token. Un Token es una cadena de caracteres con
 * significado propio.
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class Token {

    private String lexeme, token;
    private int lineNumber;

    /**
     * Constructor de la clase Token.
     *
     * Inicializa un nuevo token con un nombre, un lexema y el número de línea
     * en el que se encuentra.
     */
    public Token(String token, String lexeme, int lineNumber) {
        this.token = token;
        this.lexeme = lexeme;
        this.lineNumber = lineNumber;
    }

    /**
     * Retorna el lexema asociado al Token.
     *
     * @return lexeme
     */
    public String getLexeme() {
        return lexeme;
    }

    /**
     * Retorna el nombre asociado al Token.
     *
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * Retorna el número de línea asociado al Token.
     *
     * @return lineNumber
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Retorna una cadena conteniendo el número de línea, el token y el lexema.
     *
     * @return cadena conteniendo el número de línea, el token y el lexema
     */
    @Override
    public String toString() {
        return String.format("%-8s%-20s%-20s", lineNumber, token, lexeme);
    }

    /**
     * Compara el token con una cadena pasada como parametro.
     *
     * Requerido por: Analizador sintactico
     *
     * @param s
     * @return true si el token es igual a la cadena pasada como parametro,
     * false en caso contrario.
     */
    public boolean equals(String s) {
        return token.equals(s);
    }
}
