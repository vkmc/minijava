package LexicalAnalyzer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 * CLI para el analizador léxico.
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class Main {

    /**
     * Constructor de la clase Main.
     *
     * @param args
     */
    public static void main(String[] args) {
        if (args.length == 0 || args.length > 2) {
            System.err.println("Cantidad de argumentos invalida.");
            System.err.println("Uso: java -jar LexicalAnalyzer.jar <IN_FILE> [<OUT_FILE>]");
            return;
        }
        Tokenizer tokenizer = new Tokenizer(args[0]);
        LinkedList tokenList = createTokenList(tokenizer);

        if (args.length == 1) {
            printTokens(tokenList);
            System.out.println("El analizador lexico ha terminado.");
        } else if (args.length == 2) {
            writeTokens(tokenList, args[1]);
            System.out.println("El analizador lexico ha terminado. Resultado volcado en el archivo \"" + args[1] + "\"");
        }
    }

    /**
     * Lista de tokens generados al procesar un archivo con código fuente.
     *
     * @param tokenizer
     * @return lista de Tokens
     */
    private static LinkedList<Token> createTokenList(Tokenizer tokenizer) {
        Token token;
        LinkedList<Token> tokenList = new LinkedList();
        try {
            do {
                token = tokenizer.getToken();
                tokenList.add(token);
            } while (!token.getToken().equals("EOF"));
        } catch (LexicalException ex) {
            System.err.println(ex);
        }

        return tokenList;
    }

    /**
     * Muestra por pantalla la lista de tokens recibida como parámetro.
     *
     * @param tokenList
     */
    private static void printTokens(LinkedList<Token> tokenList) {
        System.out.println(String.format("%-8s%-20s%-20s", "No.", "Token", "Lexeme"));
        for (Token t : tokenList) {
            System.out.println(t.toString());
        }
    }

    /**
     * Escribe en el archivo destino la lista de tokens recibida como parámetro.
     * Si el archivo existe reemplaza completamente su contenido. Si el archivo
     * no existe, lo crea.
     *
     * @param tokenList
     * @param filename
     */
    private static void writeTokens(LinkedList<Token> tokenList, String filename) {
        File file = new File(filename);
        BufferedWriter buffer;

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            buffer = new BufferedWriter(new FileWriter(file));
            buffer.write(String.format("%-8s%-20s%-20s\n", "No.", "Token", "Lexeme"));
            for (Token t : tokenList) {
                buffer.write(String.format("%-8s%-20s%-20s\n", t.getToken(), t.getLexeme(), t.getLineNumber()));
            }
            buffer.close();
        } catch (IOException ex) {
            System.out.println("Error al escribir en el archivo de salida.");
        }
    }
}
