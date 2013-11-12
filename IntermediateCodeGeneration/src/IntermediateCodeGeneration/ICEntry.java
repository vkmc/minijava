package IntermediateCodeGeneration;

/**
 * Entrada de generacion de codigo
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class ICEntry {

    private String instruction;
    private String comment;
    private Integer offset;

    public ICEntry(String instruction, Integer offset, String comment) {
        this.instruction = instruction;
        this.offset = offset;
        this.comment = comment;
    }

    public String generateInstruction() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(instruction);
        if (offset != null) {
            buffer.append(" ").append(offset.intValue());
        }
        if (comment != null) {
            buffer.append("  ; ").append(comment).append(" ");
        }
        return buffer.toString();
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int off) {
        offset = new Integer(off);
    }

    public void setInstruction(String s) {
        instruction = instruction + " " + s;
    }
}
