package LexicalAnalyzer;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Lectura del source code.
 *
 * @author Ramiro Agís
 * @author Victoria Martínez de la Cruz
 */
public class InputReader {

    private String filename, currentLine;
    private File file;
    private InputStreamReader reader;
    private BufferedReader buffer;
    private InputStream in;
    private int pointer, currentLineNumber;

    public InputReader(String filename) {
        this.filename = filename;
        open();
    }

    private void open() {
        try {
            file = new File(filename);
            in = new FileInputStream(file);
            reader = new InputStreamReader(in);
            buffer = new BufferedReader(reader);

            currentLine = buffer.readLine();
            currentLineNumber = 0;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(InputReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InputReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void close() {
        try {
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(InputReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public char readChar() {
        char c = ' ';

        try {
            if (currentLine == null) {
                c = '\uFFFF'; // EOF
            } else if (pointer >= currentLine.length()) { // /n
                currentLine = buffer.readLine();
                pointer = 0;
                c = '\n';
            } else {
                c = currentLine.charAt(pointer);
                pointer++;
            }

        } catch (IOException ex) {
            Logger.getLogger(InputReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return c;
    }

    public void resetPointer() {
        pointer--;
    }

    public int getPointer() {
        return pointer;
    }
}
