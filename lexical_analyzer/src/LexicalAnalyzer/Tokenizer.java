package LexicalAnalyzer;

import java.util.HashSet;

/**
 * Analizador léxico. Clase encargada de la tokenización del código fuente.
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class Tokenizer {

    private StringBuilder code;
    private String buffer;
    private int lineNumber;
    private InputReader reader;
    private int currentState;
    private char currentChar;
    private HashSet<String> keyWords;

    public Tokenizer(String path) {
        this.currentState = 0;
        this.lineNumber = 0;

        keyWords = new HashSet<>();
        populateKeyWords();

        reader = new InputReader(path);
    }

    public Token getToken() throws LexicalException {
        StringBuilder lexeme = new StringBuilder();
        boolean flagZero = false;

        this.currentState = 0;

        while (true) {
            currentChar = (char) reader.readChar();

            switch (currentState) {
                case 0: {
                    if (currentChar == '_' || Character.isLetter(currentChar)) {
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
                            case '*':
                                currentState = 11;
                                lexeme.append(currentChar);
                                break;
                            case '/':
                                currentState = 12;
                                lexeme.append(currentChar);
                                break;
                            case '+':
                                return new Token("+", "+", lineNumber);
                            case '-':
                                return new Token("-", "-", lineNumber);
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
                            case '\uFFFF':
                                return new Token("EOF", "\0", lineNumber);
                            default:
                                throw new LexicalException("Line: " + lineNumber + "Unsupported char.");
                        }
                    }
                    break;
                }
                case 1:
                    if (Character.isLetter(currentChar) || Character.isDigit(currentChar) || currentChar == '_') {
                        lexeme.append(currentChar);
                    } else {
                        reader.resetPointer();
                        String lexemeString = lexeme.toString();
                        if (keyWords.contains(lexemeString)) {
                            // Es una palabra clave.
                            return new Token(lexemeString, lexemeString, lineNumber);
                        } else {
                            // Es un identificador.
                            return new Token("id", lexemeString, lineNumber);
                        }
                    }
                    break;
                case 2:
                    if (Character.isDigit(currentChar)) {
                        if (flagZero) {
                            throw new LexicalException("Line: " + lineNumber + "Wrong number format. A number cannot start with 0.");
                        } else if (Character.isLetter(currentChar) || notExpectedCharNumber(currentChar)) {
                            throw new LexicalException("Line: " + lineNumber + "Wrong number format.");
                        } else {
                            lexeme.append(currentChar);
                        }
                    } else {
                        reader.resetPointer();
                        return new Token("intLiteral", lexeme.toString(), lineNumber);
                    }
                    break;
                case 3:
                    if (currentChar != '\\' && currentChar != '\'' && !isEOF(currentChar) && currentChar != '\n') {
                        lexeme.append(currentChar);
                        currentState = 31;
                        break;
                    } else if (currentChar == '\\') {
                        lexeme.append(currentChar);
                        currentState = 32;
                        break;
                    } else if (currentChar == '\'') {
                        throw new LexicalException("Line: " + lineNumber + "Empty char.");
                    } else {
                        throw new LexicalException("Line: " + lineNumber + "Wrong formed char.");
                    }
                case 31:
                    if (currentChar == '\'') {
                        lexeme.append(currentChar);
                        String lexemeString = lexeme.toString();
                        return new Token("char", lexemeString, lineNumber);
                    } else {
                        throw new LexicalException("Line: " + lineNumber + "Wrong formed char.");
                    }
                case 32:
                    if (currentChar != '\\' && currentChar != '\'' && isEOF(currentChar) && currentChar != '\n') {
                        lexeme.append(currentChar);
                        currentState = 31;
                        break;
                    } else {
                        throw new LexicalException("Line: " + lineNumber + "Wrong formed char.");
                    }
                case 4:
                    if (currentChar != '\n' && currentChar != '"') {
                        lexeme.append(currentChar);
                        currentState = 41;
                        break;
                    } else if (currentChar == '"') {
                        lexeme.append(currentChar);
                        String lexemeString = lexeme.toString();
                        return new Token("String", lexemeString, lineNumber);
                    } else {
                        throw new LexicalException("Line: " + lineNumber + "Wrong formed String.");
                    }
                case 41:
                    if (currentChar != '\n' && currentChar != '"') {
                        lexeme.append(currentChar);
                        break;
                    } else if (currentChar == '"') {
                        lexeme.append(currentChar);
                        String lexemeString = lexeme.toString();
                        return new Token("String", lexemeString, lineNumber);
                    } else {
                        throw new LexicalException("Line: " + lineNumber + "Wrong formed String.");
                    }
                case 5:
                    if (currentChar == '=') {
                        return new Token(">=", ">=", lineNumber);
                    } else {
                        reader.resetPointer();
                        return new Token(">", ">", lineNumber);
                    }
                case 6:
                    if (currentChar == '=') {
                        return new Token("<=", "<=", lineNumber);
                    } else {
                        reader.resetPointer();
                        return new Token("<", "<", lineNumber);
                    }
                case 7:
                    if (currentChar == '=') {
                        return new Token("==", "==", lineNumber);
                    } else {
                        reader.resetPointer();
                        return new Token("=", "=", lineNumber);
                    }
                case 8:
                    if (currentChar == '=') {
                        return new Token("!=", "!=", lineNumber);
                    } else {
                        reader.resetPointer();
                        return new Token("!", "!", lineNumber);
                    }
                case 9:
                    if (currentChar == '&') {
                        return new Token("&&", "&&", lineNumber);
                    } else {
                        reader.resetPointer();
                        return new Token("&", "&", lineNumber);
                    }
                case 10:
                    if (currentChar == '|') {
                        return new Token("||", "||", lineNumber);
                    } else {
                        reader.resetPointer();
                        return new Token("|", "|", lineNumber);
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
                        reader.resetPointer();
                        return new Token("/", "/", lineNumber);
                    }
            }
        }
    }

    private void populateKeyWords() {
        keyWords.add("class");
        keyWords.add("extends");
        keyWords.add("var");
        keyWords.add("static");
        keyWords.add("dynamic");
        keyWords.add("void");
        keyWords.add("boolean");
        keyWords.add("char");
        keyWords.add("int");
        keyWords.add("String");
        keyWords.add("if");
        keyWords.add("else");
        keyWords.add("while");
        keyWords.add("for");
        keyWords.add("return");
        keyWords.add("this");
        keyWords.add("new");
        keyWords.add("null");
        keyWords.add("true");
        keyWords.add("false");

        /*
         * 
         StringBuilder keyWord;
         keyWord = new StringBuilder("class");
         keyWords.add(keyWord);
         keyWord = new StringBuilder("extends");
         keyWords.add(keyWord);
         keyWord = new StringBuilder("var");
         keyWords.add(keyWord);
         keyWord = new StringBuilder("static");
         keyWords.add(keyWord);
         keyWord = new StringBuilder("dynamic");
         keyWords.add(keyWord);
         keyWord = new StringBuilder("void");
         keyWords.add(keyWord);
         keyWord = new StringBuilder("boolean");
         keyWords.add(keyWord);
         keyWord = new StringBuilder("char");
         keyWords.add(keyWord);
         keyWord = new StringBuilder("int");
         keyWords.add(keyWord);
         keyWord = new StringBuilder("String");
         keyWords.add(keyWord);
         keyWord = new StringBuilder("if");
         keyWords.add(keyWord);
         keyWord = new StringBuilder("else");
         keyWords.add(keyWord);
         keyWord = new StringBuilder("while");
         keyWords.add(keyWord);
         keyWord = new StringBuilder("for");
         keyWords.add(keyWord);
         keyWord = new StringBuilder("return");
         keyWords.add(keyWord);
         keyWord = new StringBuilder("this");
         keyWords.add(keyWord);
         keyWord = new StringBuilder("new");
         keyWords.add(keyWord);
         keyWord = new StringBuilder("null");
         keyWords.add(keyWord);
         keyWord = new StringBuilder("true");
         keyWords.add(keyWord);
         keyWord = new StringBuilder("false");
         keyWords.add(keyWord);
         */
    }

    private void proccessComment() {
        currentChar = (char) reader.readChar();

        while (currentChar != '\n') {
            currentChar = (char) reader.readChar();
        }
    }

    private void processBlockComment() {
        boolean closeBlockComment = false;
        char nextChar;
        currentChar = (char) reader.readChar();

        nextChar = (char) reader.readChar();

        while (!closeBlockComment) {
            if (currentChar == '*' && nextChar == '/') {
                closeBlockComment = true;
            }

            currentChar = nextChar;
            nextChar = (char) reader.readChar();
        }
    }

    private boolean isEOF(char c) {
        return (c == '\uFFFF');
    }

    private boolean notExpectedCharNumber(char currentChar) {
        if (currentChar != ' ' || currentChar != '+' || currentChar != '-' || currentChar != '/' || currentChar != '*' || currentChar != '%') {
            return true;
        } else {
            return false;
        }
    }
}
