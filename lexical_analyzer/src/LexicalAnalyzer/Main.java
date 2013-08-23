/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LexicalAnalyzer;

/**
 *
 * @author vkmc
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int salto = '\n';
        int eof = '\uFFFF';
        System.out.println(salto+" "+eof);
        
        Reader r = new Reader("/home/vkmc/hola");
        
        char c = r.getChar();
        
        while (c > 0) {
            System.out.print(c);
            c = r.getChar();
        }
    }
}
