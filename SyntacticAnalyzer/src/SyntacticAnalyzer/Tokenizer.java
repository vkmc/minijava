package SyntacticAnalyzer;

import java.util.HashSet;

/**
 * Analizador léxico. Clase encargada de la tokenización del código fuente.
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class Tokenizer {

    private int lineNumber, currentState;
    private char currentChar;
    private InputReader reader;
    private HashSet<String> keywords;

    /**
     * Constructor de la clase Tokenizer
     *
     * Inicializa los marcadores y las estructuras de datos utilizadas.
     *
     * @param filename Path del archivo con el código fuente
     */
    public Tokenizer(String filename) {
        this.currentState = 0;
        this.lineNumber = 1;

        keywords = new HashSet<>();
        populateKeywords();

        reader = new InputReader(filename);
    }

    /**
     * Proceso de tokenización.
     *
     * Análisis léxico del archivo de código fuente.
     *
     * Devuelve un token por vez recorriendo el archivo de forma secuencial.
     *
     * @returns Token encontrado
     * @throws LexicalException
     */
    public Token getToken() throws LexicalException {
        StringBuilder lexeme = new StringBuilder();
        boolean flagZero = false;

        this.currentState = 0;

        while (true) {
            currentChar = (char) reader.readChar();

            switch (currentState) {
                case 0: {
                    if (currentChar == '_' || isASCIILetter(currentChar)) {
                        currentState = 1;
                        lexeme.append(currentChar);
                    } else if (Character.isDigit(currentChar)) {
                        if (currentChar == '0') {
                            flagZero = true;
                        }
                        currentState = 2;
                        lexeme.append(currentChar);
                    } else {
                        switch (currentChar) {
                            case ' ':
                                break;
                            case '\t':
                                break;
                            case '\n':
                                lineNumber++;
                                break;
                            case '\'':
                                currentState = 3;
                                lexeme.append(currentChar);
                                break;
                            case '"':
                                currentState = 4;
                                lexeme.append(currentChar);
                                break;
                            case '>':
                                currentState = 5;
                                lexeme.append(currentChar);
                                break;
                            case '<':
                                currentState = 6;
                                lexeme.append(currentChar);
                                break;
                            case '=':
                                currentState = 7;
                                lexeme.append(currentChar);
                                break;
                            case '!':
                                currentState = 8;
                                lexeme.append(currentChar);
                                break;
                            case '&':
                                currentState = 9;
                                lexeme.append(currentChar);
                                break;
                            case '|':
                                currentState = 10;
                                lexeme.append(currentChar);
                                break;
                            case '/':
                                currentState = 11;
                                break;
                            case '+':
                                return new Token("+", "+", lineNumber);
                            case '-':
                                return new Token("-", "-", lineNumber);
                            case '*':
                                return new Token("*", "*", lineNumber);
                            case '(':
                                return new Token("(", "(", lineNumber);
                            case ')':
                                return new Token(")", ")", lineNumber);
                            case '{':
                                return new Token("{", "{", lineNumber);
                            case '}':
                                return new Token("}", "}", lineNumber);
                            case ';':
                                return new Token(";", ";", lineNumber);
                            case ',':
                                return new Token(",", ",", lineNumber);
                            case '.':
                                return new Token(".", ".", lineNumber);
                            case '%':
                                return new Token("%", "%", lineNumber);
                            case '\0':
                                lineNumber--;
                                return new Token("EOF", "\\0", lineNumber);
                            default:
                                throw new LexicalException("Linea: " + lineNumber + " - Error lexico: Caracter no soportado (" + currentChar + ").");
                        }
                    }
                    break;
                }
                case 1:
                    if (isASCIILetter(currentChar) || Character.isDigit(currentChar) || currentChar == '_') {
                        lexeme.append(currentChar);
                    } else {
                        Token token;
                        String lexemeString = lexeme.toString();
                        if (keywords.contains(lexemeString)) {
                            // Es una palabra clave.
                            token = new Token(lexemeString, lexemeString, lineNumber);
                        } else {
                            // Es un identificador.
                            token = new Token("id", lexemeString, lineNumber);
                        }
                        checkNL(currentChar);
                        return token;
                    }
                    break;
                case 2:
                    if (isASCIILetter(currentChar)) {
                        throw new LexicalException("Linea: " + lineNumber + " - Error lexico: Número mal formado (" + lexeme + currentChar + ").");
                    } else if (Character.isDigit(currentChar)) {
                        if (flagZero) {
                            throw new LexicalException("Linea: " + lineNumber + " - Error lexico: Número mal formado. Un número no puede empezar con 0 (" + lexeme + currentChar + ").");
                        } else {
                            lexeme.append(currentChar);
                        }
                    } else {
                        Token token = new Token("intLiteral", lexeme.toString(), lineNumber);
                        checkNL(currentChar);
                        return token;
                    }
                    break;
                case 3:
                    if (currentChar != '\\' && currentChar != '\'' && currentChar != '\n') {
                        if (!isValidChar(currentChar)) {
                            throw new LexicalException("Linea: " + lineNumber + " - Error lexico: Caracter no soportado (" + currentChar + ").");
                        }
                        lexeme.append(currentChar);
                        currentState = 31;
                        break;
                    } else if (currentChar == '\\') {
                        lexeme.append(currentChar);
                        currentState = 32;
                        break;
                    } else if (currentChar == '\'') {
                        throw new LexicalException("Linea: " + lineNumber + " - Error lexico: Caracter vacio (" + lexeme + currentChar + ").");
                    } else {
                        throw new LexicalException("Linea: " + lineNumber + " - Error lexico: Caracter mal formado (" + lexeme + currentChar + ").");
                    }
                case 31:
                    if (currentChar == '\'') {
                        lexeme.append(currentChar);
                        String lexemeString = lexeme.toString();
                        return new Token("charLiteral", lexemeString, lineNumber);
                    } else {
                        throw new LexicalException("Linea: " + lineNumber + " - Error lexico: Caracter mal formado (" + lexeme + currentChar + ").");
                    }
                case 32:
                    if (currentChar != '\n' && currentChar != '\t' && currentChar != '\0') {
                        if (!isValidChar(currentChar)) {
                            throw new LexicalException("Linea: " + lineNumber + " - Error lexico: Caracter no soportado (" + currentChar + ").");
                        }
                        lexeme.append(currentChar);
                        currentState = 31;
                        break;
                    } else {
                        throw new LexicalException("Linea: " + lineNumber + " - Error lexico: Caracter no soportado (" + currentChar + ").");
                    }
                case 4:
                    if (currentChar != '\n' && currentChar != '"') {
                        if (!isValidChar(currentChar)) {
                            throw new LexicalException("Linea: " + lineNumber + " - Error lexico: Caracter no soportado (" + currentChar + ").");
                        }
                        lexeme.append(currentChar);
                        currentState = 41;
                        break;
                    } else if (currentChar == '"') {
                        lexeme.append(currentChar);
                        String lexemeString = lexeme.toString();
                        return new Token("stringLiteral", lexemeString, lineNumber);
                    } else {
                        throw new LexicalException("Linea: " + lineNumber + " - Error lexico: Cadena mal formada (" + lexeme + currentChar + ").");
                    }
                case 41:
                    if (currentChar != '\n' && currentChar != '"') {
                        if (!isValidChar(currentChar)) {
                            throw new LexicalException("Linea: " + lineNumber + " - Error lexico: Caracter no soportado (" + currentChar + ").");
                        }
                        lexeme.append(currentChar);
                        break;
                    } else if (currentChar == '"') {
                        lexeme.append(currentChar);
                        String lexemeString = lexeme.toString();
                        return new Token("stringLiteral", lexemeString, lineNumber);
                    } else {
                        throw new LexicalException("Linea: " + lineNumber + " - Error lexico: Cadena mal formada (" + lexeme + currentChar + ").");
                    }
                case 5:
                    if (currentChar == '=') {
                        return new Token(">=", ">=", lineNumber);
                    } else {
                        Token token = new Token(">", ">", lineNumber);
                        checkNL(currentChar);
                        return token;
                    }
                case 6:
                    if (currentChar == '=') {
                        return new Token("<=", "<=", lineNumber);
                    } else {
                        Token token = new Token("<", "<", lineNumber);
                        checkNL(currentChar);
                        return token;
                    }
                case 7:
                    if (currentChar == '=') {
                        return new Token("==", "==", lineNumber);
                    } else {
                        Token token = new Token("=", "=", lineNumber);
                        checkNL(currentChar);
                        return token;
                    }
                case 8:
                    if (currentChar == '=') {
                        return new Token("!=", "!=", lineNumber);
                    } else {
                        Token token = new Token("!", "!", lineNumber);
                        checkNL(currentChar);
                        return token;
                    }
                case 9:
                    if (currentChar == '&') {
                        return new Token("&&", "&&", lineNumber);
                    } else {
                        throw new LexicalException("Linea: " + lineNumber + " - Error lexico: Operador no soportado (" + lexeme + currentChar + ").");
                    }
                case 10:
                    if (currentChar == '|') {
                        return new Token("||", "||", lineNumber);
                    } else {
                        throw new LexicalException("Linea: " + lineNumber + " - Error lexico: Operador no soportado (" + lexeme + currentChar + ").");
                    }
                case 11:
                    if (currentChar == '/') {
                        proccessComment(); // S11.1
                        currentState = 0;
                        break;
                    } else if (currentChar == '*') {
                        processBlockComment(); // S11.2
                        currentState = 0;
                        break;
                    } else {
                        Token token = new Token("/", "/", lineNumber);
                        checkNL(currentChar);
                        return token;
                    }
            }
        }
    }

    // Inicializacion de estructuras
    /**
     * Palabras reservadas de MiniJava.
     *
     * Inicialización de una estructura de datos con las palabras reservadas de
     * MiniJava.
     *
     * Si un lexema es reconocido como palabra reservada, se creará el Token
     * adecuado.
     *
     */
    private void populateKeywords() {
        keywords.add("class");
        keywords.add("extends");
        keywords.add("var");
        keywords.add("static");
        keywords.add("dynamic");
        keywords.add("void");
        keywords.add("boolean");
        keywords.add("char");
        keywords.add("int");
        keywords.add("String");
        keywords.add("if");
        keywords.add("else");
        keywords.add("while");
        keywords.add("for");
        keywords.add("return");
        keywords.add("this");
        keywords.add("new");
        keywords.add("null");
        keywords.add("true");
        keywords.add("false");
    }

    // Procesamiento de comentarios
    /**
     * Procesamiento de las líneas de comentario.
     *
     * Consume la línea comentada para seguir con la tokenización.
     */
    private void proccessComment() throws LexicalException {
        currentChar = (char) reader.readChar();

        while (currentChar != '\n') {
            if (!isValidChar(currentChar)) {
                throw new LexicalException("Linea: " + lineNumber + " - Error lexico: Caracter no soportado (" + currentChar + ").");
            }

            currentChar = (char) reader.readChar();
        }

        checkNL(currentChar);
    }

    /**
     * Procesamiento de los bloques de comentarios.
     *
     * Consume el bloque de comentarios para seguir con la tokenización. Si se
     * encuentra el fin de archivo, se considera como un error y se lanzará una
     * excepción.
     *
     * @throws LexicalException
     */
    private void processBlockComment() throws LexicalException {
        boolean closeBlockComment = false;
        char nextChar;
        currentChar = (char) reader.readChar();

        nextChar = (char) reader.readChar();

        while (!closeBlockComment && nextChar != '\0') {

            if (!isValidChar(currentChar)) {
                throw new LexicalException("Linea: " + lineNumber + " - Error lexico: Caracter no soportado (" + currentChar + ").");
            }

            if (currentChar == '\n') {
                checkNL(currentChar);
            }

            if (currentChar == '*' && nextChar == '/') {
                closeBlockComment = true;
            }

            currentChar = nextChar;
            nextChar = (char) reader.readChar();
        }

        if (nextChar == '\0') {
            throw new LexicalException("Linea: " + lineNumber + " - Error lexico: El bloque de comentario no esta cerrado y se alcanzo el fin de archivo.");
        } else {
            checkNL(nextChar);
        }
    }

    // Controles de validez
    /**
     * Verificación de número bien formado.
     *
     * Para facilitar la descripción de errores en futuras etapas se previene la
     * aparición de combinaciones sintácticamente imposibles (e.g. 123hola) en
     * esta etapa.
     *
     * @returns true si el caracter encontrado determina un número mal formado,
     * false en caso contrario
     */
    private boolean notExpectedCharNumber(char currentChar) {
        if (Character.isDigit(currentChar) || currentChar == ' ' || currentChar == '\t' || currentChar == '\n' || currentChar == '+' || currentChar == '-' || currentChar == '/' || currentChar == '*' || currentChar == '%' || currentChar == ',' || currentChar == ';' || currentChar == '>' || currentChar == '<' || currentChar == '=' || currentChar == '!' || currentChar == ')') {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Controla que el caracter pasado por parametro pertenezca al conjunto de
     * letras ASCII basico.
     *
     * @param currentChar
     * @return true si el caracter pertenece al conjunto de letras ASCII basico,
     * false en caso contrario
     */
    private boolean isASCIILetter(char currentChar) {
        return (currentChar >= 'a' && currentChar <= 'z') || (currentChar >= 'A' && currentChar <= 'Z');
    }

    /**
     * Controla que el caracter pasado por parametro pertenezca al conjunto de
     * caracteres imprimibles ASCII basico.
     *
     * @param currentChar
     * @return true si el caracter pertenece al conjunt ode caracteres ASCII
     * basico, false en caso contrario
     */
    private boolean isValidChar(char currentChar) {
        return currentChar == '\n' || currentChar == '\t' || currentChar >= 32 && currentChar < 127;
    }

    /**
     * Controla el incremento de lineas en caso de que se haya cambiado de linea
     * y no llegue a procesarse.
     *
     * Si no es '\n' todavia no ocurrio un salto y debe volverse el marcador
     * hacia atras para procesar el caracter actual. Si es un '\n', ocurrio un
     * salto y no tiene sentido volver el marcador hacia atras puesto que se
     * debe procesar siguiente linea (ya actualizada por el reader)
     *
     * @param currentChar
     */
    private void checkNL(char currentChar) {
        if (currentChar == '\n') {
            lineNumber++;
        } else {
            reader.resetMark();
        }
    }
}
