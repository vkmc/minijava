package LexicalAnalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Lectura del source code.
 * @author Ramiro Agís
 * @author Victoria Martínez de la Cruz
 */

public class Reader {
    
    private String filepath;
    private File file;
    private BufferedReader buffer;
    
    public Reader (String path) {    
        filepath = path;
        open();
    }
    
    private void open () {
        try {
            file = new File(filepath);
            buffer = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void close () {
        try {
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getChar () {
        int readchar = 0;
        try {                        
            readchar = buffer.read();
            
            if (readchar <= 0) {
                close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            close();
        }
        return readchar;
    }
}
