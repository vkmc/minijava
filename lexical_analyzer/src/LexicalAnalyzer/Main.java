package LexicalAnalyzer;

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
        Tokenizer t = new Tokenizer("/home/vkmc/program");

        System.out.println("Imprimo tokens");
        printTokens(t);
    }

    private static void printTokens(Tokenizer tokenizer) {
        Token token;

        try {
            do {
                token = tokenizer.getToken();
                System.out.println(token.toString());
            } while (!token.getToken().equals("EOF"));
        } catch (LexicalException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
