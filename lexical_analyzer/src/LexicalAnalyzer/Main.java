/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LexicalAnalyzer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vkmc
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Tokenizer t = new Tokenizer("/home/vkmc/program");

        System.out.println("Imprimo tokens");
        printTokens(t);
    }

    private static void printTokens(Tokenizer tokenizer) {
        Token token = null;

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
