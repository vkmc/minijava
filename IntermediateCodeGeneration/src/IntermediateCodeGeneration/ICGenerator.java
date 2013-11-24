package IntermediateCodeGeneration;

import IntermediateCodeGeneration.SymbolTable.ClassEntry;
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
        GEN("VT_Object0: NOP");

        // System class VT.
        GEN("VT_System1: DW L_MET_System1_Ctor, L_MET_System1_read, "
                + "L_MET_System1_printI, L_MET_System1_printC, L_MET_System1_printB, "
                + "L_MET_System1_printS, L_MET_System1_println, L_MET_System1_printBln, "
                + "L_MET_System1_printCln, L_MET_System1_printIln, L_MET_System1_printSln ");

        GEN(".CODE");
        GEN("PUSH L_SIMPLE_INIT_HEAP");
        GEN("CALL");

        // Main method.
        String mainClass = symbolTable.getMainClass();
        ClassEntry mainClassEntry = symbolTable.getClassEntry(mainClass);
        int mainOffset = mainClassEntry.getMethodEntry("main").getOffset();
        GEN("RMEM", 1);
        GEN("PUSH L_MET_" + mainClass + mainClassEntry.getClassNumber() + "_main" + mainOffset);
        GEN("CALL");
        GEN("HALT");

        // System class constructor.
        GEN("L_MET_System1_Ctor: NOP", "Constructor de system");
        GEN("LOADFP");
        GEN("LOADSP");
        GEN("STOREFP");
        GEN("STOREFP");
        GEN("RET", 1);

        // System methods.
        // System.read()
        GEN("L_MET_System1_read: NOP");
        GEN("LOADFP");
        GEN("LOADSP");
        GEN("STOREFP");
        GEN("READ");
        GEN("STORE", 4);
        GEN("STOREFP");
        GEN("RET", 1);

        // System.printi()
        GEN("L_MET_System1_printI: NOP");
        GEN("LOADFP");
        GEN("LOADSP");
        GEN("STOREFP");
        GEN("LOAD", 4);
        GEN("IPRINT");
        GEN("STOREFP");
        GEN("RET", 2);

        // System.printc()
        GEN("L_MET_System1_printC: NOP");
        GEN("LOADFP");
        GEN("LOADSP");
        GEN("STOREFP");
        GEN("LOAD", 4);
        GEN("CPRINT");
        GEN("STOREFP");
        GEN("RET", 2);

        // System.printb()
        GEN("L_MET_System1_printB: NOP");
        GEN("LOADFP");
        GEN("LOADSP");
        GEN("STOREFP");
        GEN("LOAD", 4);
        GEN("BPRINT");
        GEN("STOREFP");
        GEN("RET", 2);

        // System.prints()
        GEN("L_MET_System1_printS: NOP");
        GEN("LOADFP");
        GEN("LOADSP");
        GEN("STOREFP");
        GEN("LOAD", 4);
        GEN("SPRINT");
        GEN("STOREFP");
        GEN("RET", 2);

        // System.println()
        GEN("L_MET_System1_println: NOP");
        GEN("LOADFP");
        GEN("LOADSP");
        GEN("STOREFP");
        GEN("PRNLN");
        GEN("STOREFP");
        GEN("RET", 1);

        // System.printbln()
        GEN("L_MET_System1_printBln: NOP");
        GEN("LOADFP");
        GEN("LOADSP");
        GEN("STOREFP");
        GEN("LOAD", 4);
        GEN("BPRINT");
        GEN("PRNLN");
        GEN("STOREFP");
        GEN("RET", 2);

        // System.printcln()
        GEN("L_MET_System1_printCln: NOP");
        GEN("LOADFP");
        GEN("LOADSP");
        GEN("STOREFP");
        GEN("LOAD", 4);
        GEN("CPRINT");
        GEN("PRNLN");
        GEN("STOREFP");
        GEN("RET", 2);

        // System.printiln()
        GEN("L_MET_System1_printIln: NOP");
        GEN("LOADFP");
        GEN("LOADSP");
        GEN("STOREFP");
        GEN("LOAD", 4);
        GEN("IPRINT");
        GEN("PRNLN");
        GEN("STOREFP");
        GEN("RET", 2);

        // System.printsln()
        GEN("L_MET_System1_printSln: NOP");
        GEN("LOADFP");
        GEN("LOADSP");
        GEN("STOREFP");
        GEN("LOAD", 4);
        GEN("SPRINT");
        GEN("PRNLN");
        GEN("STOREFP");
        GEN("RET", 2);

        // Simple malloc.
        GEN("L_SIMPLE_MALLOC: LOADFP");
        GEN("LOADSP");
        GEN("STOREFP");
        GEN("LOADHL");
        GEN("DUP");
        GEN("PUSH", 1);
        GEN("ADD");
        GEN("STORE", 4);
        GEN("LOAD", 3);
        GEN("ADD");
        GEN("STOREHL");
        GEN("STOREFP");
        GEN("RET", 1);

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
