package main;
import java.util.HashMap;

public class SymbolTable {
    private HashMap<String,Integer> symbolTable;
    //private int symbolTableSize;

    public SymbolTable() {
        this.symbolTable = new HashMap<>();
        firstTable();

    }
    public void addEntry (String symbol , int address)
    {
        this.symbolTable.put(symbol, address);
    }
    public boolean contains (String symbol)
    {
        return this.symbolTable.containsKey(symbol);
    }
    public int getAddress (String symbol)
    {
        return this.symbolTable.get(symbol);
    }
    public String toString() {
        StringBuilder sb = new StringBuilder("main.SymbolTable:\n");
        for (String key : this.symbolTable.keySet()) {
            sb.append("Key: ").append(key).append(" | Value: ").append(this.symbolTable.get(key)).append("\n");
        }
        return sb.toString();
    }
    public void firstTable() {
        addEntry("R0" ,0);
        addEntry("R1" ,1);
        addEntry("R2" ,2);
        addEntry("R3" ,3);
        addEntry("R4" ,4);
        addEntry("R5" ,5);
        addEntry("R6" ,6);
        addEntry("R7" ,7);
        addEntry("R8" ,8);
        addEntry("R9" ,9);
        addEntry("R10" ,10);
        addEntry("R11" ,11);
        addEntry("R12" ,12);
        addEntry("R13" ,13);
        addEntry("R14" ,14);
        addEntry("R15" ,15);
        addEntry("SCREEN" ,16384);
        addEntry("KBD" ,24576);
        addEntry("SP" ,0);
        addEntry("ARG" ,2);
        addEntry("LCL" ,1);
        addEntry("THIS" ,3);
        addEntry("THAT" ,4);
    }
}
