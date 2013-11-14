package IntermediateCodeGeneration;

import IntermediateCodeGeneration.SymbolTable.SymbolTable;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Generacion de codigo intermedio.
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class ICGenerator {

    ArrayList<ICEntry> intermadiateCode;
    private String outputFile; // CeIASM source code output file.
    private int labelNumber;

    public ICGenerator(String outputFile) {
        this.intermadiateCode = new ArrayList<>();
        this.outputFile = outputFile;
        this.labelNumber = 0;
    }

    /*
     * Given a CeIASM instruction, an offset and a comment, generates an ICEntry.
     */
    public ICEntry GEN(String instruction, int offset, String comment) {
        ICEntry newEntry = new ICEntry(instruction, new Integer(offset), comment);
        intermadiateCode.add(newEntry);
        return newEntry;
    }

    /*
     * Given a CeIASM instruction and an offset, generates an ICEntry without comment.
     */
    public ICEntry GEN(String instruction, int offset) {
        ICEntry newEntry = new ICEntry(instruction, new Integer(offset), null);
        intermadiateCode.add(newEntry);
        return newEntry;
    }

    /*
     * Given a CeIASM instruction and a comment, generates an ICEntry without offset.
     */
    public ICEntry GEN(String instruction, String comment) {
        ICEntry newEntry = new ICEntry(instruction, null, comment);
        intermadiateCode.add(newEntry);
        return newEntry;
    }

    /*
     * Given a CeIASM instruction, an offset and a comment, generates an ICEntry without offset nor comment.
     */
    public ICEntry GEN(String instruction) {
        ICEntry newEntry = new ICEntry(instruction, null, null);
        intermadiateCode.add(newEntry);
        return newEntry;
    }

    /*
     * Writes the generated intermadiate code inside the output file.
     */
    public void generateOutput() {
        try {
            FileWriter fstream = new FileWriter(outputFile);
            BufferedWriter bufferedStream = new BufferedWriter(fstream);
            for (ICEntry ice : intermadiateCode) {
                bufferedStream.write(ice.generateInstruction() + "\n");
            }
            bufferedStream.close();
        } catch (IOException e) {
            System.err.println("Intermadiate code generation error: " + e.getMessage());
        }
    }

    public void setup(SymbolTable symbolTable) {
        GEN(".DATA");

        // Object class doesn't have methods so it isn't necessary to create a VT for it.
        GEN("VT_Object: NOP");

        // System class VT.
        GEN("VT_System: DW L_MET_System_Ctor, L_MET_System_read, "
                + "L_MET_System_printI, L_MET_System_printC, L_MET_System_printB, "
                + "L_MET_System_printS, L_MET_System_println, L_MET_System_printBln, "
                + "L_MET_System_printCln, L_MET_System_printIln, L_MET_System_printSln ");

        GEN(".CODE");
        GEN("PUSH L_SIMPLE_INIT_HEAP", "Inicializamos el heap");
        GEN("CALL");

        // Main method.
        String mainClass = symbolTable.getMainClass();
        GEN("RMEM", 1, "Reservamos memoria para el this");
        GEN("PUSH L_MET_" + mainClass + "_main", "Apilamos el label del main de la Clase Principal del Programa"); //PUSH VT_A (Si A es la clase Ppal del programa)
        GEN("CALL");
        GEN("HALT");

        // System class constructor.
        GEN("L_MET_System_Ctor: NOP", "Constructor de system");
        GEN("LOADFP");
        GEN("LOADSP");
        GEN("STOREFP");
        GEN("STOREFP");
        GEN("RET", 1);

        // System methods.
        // System.read()
        GEN("L_MET_System_read: NOP");
        GEN("LOADFP");
        GEN("LOADSP");
        GEN("STOREFP");
        GEN("READ");
        GEN("STORE", 4);
        GEN("STOREFP");
        GEN("RET", 1);

        // System.printi()
        GEN("L_MET_System_printI: NOP");
        GEN("LOADFP");
        GEN("LOADSP");
        GEN("STOREFP");
        GEN("LOAD", 4);
        GEN("IPRINT");
        GEN("STOREFP");
        GEN("RET", 2);

        // System.printc()
        GEN("L_MET_System_printC: NOP");
        GEN("LOADFP");
        GEN("LOADSP");
        GEN("STOREFP");
        GEN("LOAD", 4);
        GEN("CPRINT");
        GEN("STOREFP");
        GEN("RET", 2);

        // System.printb()
        GEN("L_MET_System_printB: NOP");
        GEN("LOADFP");
        GEN("LOADSP");
        GEN("STOREFP");
        GEN("LOAD", 4);
        GEN("BPRINT");
        GEN("STOREFP");
        GEN("RET", 2);

        // System.prints()
        GEN("L_MET_System_printS: NOP");
        GEN("LOADFP");
        GEN("LOADSP");
        GEN("STOREFP");
        GEN("LOAD", 4);
        GEN("SPRINT");
        GEN("STOREFP");
        GEN("RET", 2);

        // System.println()
        GEN("L_MET_System_println: NOP");
        GEN("LOADFP");
        GEN("LOADSP");
        GEN("STOREFP");
        GEN("PRNLN");
        GEN("STOREFP");
        GEN("RET", 1);

        // System.printbln()
        GEN("L_MET_System_printBln: NOP");
        GEN("LOADFP");
        GEN("LOADSP");
        GEN("STOREFP");
        GEN("LOAD", 4);
        GEN("BPRINT");
        GEN("PRNLN");
        GEN("STOREFP");
        GEN("RET", 2);

        // System.printcln()
        GEN("L_MET_System_printCln: NOP");
        GEN("LOADFP");
        GEN("LOADSP");
        GEN("STOREFP");
        GEN("LOAD", 4);
        GEN("CPRINT");
        GEN("PRNLN");
        GEN("STOREFP");
        GEN("RET", 2);

        // System.printiln()
        GEN("L_MET_System_printIln: NOP");
        GEN("LOADFP");
        GEN("LOADSP");
        GEN("STOREFP");
        GEN("LOAD", 4);
        GEN("IPRINT");
        GEN("PRNLN");
        GEN("STOREFP");
        GEN("RET", 2);

        // System.printsln()
        GEN("L_MET_System_printSln: NOP");
        GEN("LOADFP");
        GEN("LOADSP");
        GEN("STOREFP");
        GEN("LOAD", 4);
        GEN("SPRINT");
        GEN("PRNLN");
        GEN("STOREFP");
        GEN("RET", 2);

        // Simple malloc.
        GEN("L_SIMPLE_MALLOC: LOADFP", "Inicializacion unidad");
        GEN("LOADSP");
        GEN("STOREFP", "Finaliza inicializacion del RA");
        GEN("LOADHL", "hl");
        GEN("DUP", "hl");
        GEN("PUSH", 1, "1");
        GEN("ADD", "hl+1");
        GEN("STORE", 4, "Guarda resultado (puntero a la base del bloque)");
        GEN("LOAD", 3, "Carga cantidad de celdas a alojar (parametro) ");
        GEN("ADD");
        GEN("STOREHL", "Mueve el heap limit (hl)");
        GEN("STOREFP");
        GEN("RET", 1, "Retorna eliminando el parametro");

        // Simple init heap.
        GEN("L_SIMPLE_INIT_HEAP: RET", 0, "Inicializacion simplificada del .heap");
    }

    /**
     * Genera un numero de etiqueta Usado en las estructuras condicionales y de
     * repeticion
     *
     * @return labelNumber
     */
    public String generateLabel() {
        labelNumber++;
        return "" + labelNumber;
    }
}
