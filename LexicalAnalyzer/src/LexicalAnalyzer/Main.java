package LexicalAnalyzer;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CLI para el analizador léxico.
 *
 * @author Ramiro Agís
 * @author Victoria Martínez de la Cruz
 */
public class Main {

    public static void main(String[] args) {

        //if (args.length == 1) {
        Tokenizer t = new Tokenizer("/home/vkmc/program");
        LinkedList tokenList = createTokenList(t);
        printTokens(tokenList);
        //} else {
        //    System.err.println("Cantidad de argumentos inválida.");
        //    System.err.println("Uso: java -jar tokenizer <source>");
        //}

    }

    private static LinkedList<Token> createTokenList(Tokenizer tokenizer) {
        Token token;
        LinkedList<Token> tokenList = new LinkedList();
        try {
            do {
                token = tokenizer.getToken();
                tokenList.add(token);
            } while (!token.getToken().equals("EOF"));
        } catch (LexicalException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tokenList;
    }

    private static void printTokens(LinkedList<Token> tokenList) {

        System.out.println(String.format("%-8s%-20s%-20s", "No.", "Token", "Lexeme"));

        for (Token t : tokenList) {
            System.out.println(t.toString());
        }

    }
}
