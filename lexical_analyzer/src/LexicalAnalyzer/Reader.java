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
    
    private String filePath;
    private File file;
    private BufferedReader buffer;
    
    public Reader (String path) {    
        filePath = path;
        open();
    }
    
    private void open () {
        try {
            file = new File(filePath);
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
    
    public char getChar () {
        char readChar = 0;
        try {                        
            readChar = (char) buffer.read();
                        
            if (readChar == '\uFFFF') { // eof
                close();
                readChar = 0;
            }
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            close();
        }
        return readChar;
    }
}
