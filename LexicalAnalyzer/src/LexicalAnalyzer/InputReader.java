package LexicalAnalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Lectura del archivo fuente.
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

    /*
     * Apertura del archivo fuente
     * Inicialización de variables de control para el recorrido del mismo.
     */
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

    /*
     * Cierre del archivo fuente.
     */
    private void close() {
        try {
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(InputReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * Lectura de caracteres.
     * Se procesa el archivo línea por linea
     *  - Si la línea a procesar es nula, entonces se alcanzó el fin de archivo
     *  - Si el marcador de lectura (pointer) es mayor al tamaño de la línea a procesar,
     *    se procede a leer una línea nueva y a reiniciar la marca.
     *  - Si la marcador de lectura (pointer) es menor al tamaño de la línea a procesar,
     *    se procede a leer el caracter que apunta la marca y a incrementar la marca.
     * 
     * @return c, caracter leído
     */
    public char readChar() {
        char c = ' ';

        try {
            if (currentLine == null) {
                c = '\0'; // null byte - EOF
                close();
            } else if (pointer >= currentLine.length()) { // /n
                currentLine = buffer.readLine();
                c = '\n';
                pointer = 0;
            } else {
                c = currentLine.charAt(pointer);
                pointer++;
            }

        } catch (IOException ex) {
            Logger.getLogger(InputReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return c;
    }

    /*
     * Retorna el valor actual del marcador
     */
    public int getPointer() {
        return pointer;
    }

    /*
     * Vuelve el marcador una posición hacia atrás
     */
    public void resetPointer() {
        pointer--;
    }
}