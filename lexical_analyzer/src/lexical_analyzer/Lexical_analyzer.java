/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lexical_analyzer;

import java.util.HashSet;

/**
 *
 * @author vkmc
 */
public class Lexical_analyzer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HashSet<String> jorge = new HashSet<>();
        jorge.add("choripan");
        StringBuilder x = new StringBuilder("choripan");
        if (jorge.contains(x.toString()))
            System.out.println("anduvo!");
        else
            System.out.println("no anduvo!");
        
    }
}
