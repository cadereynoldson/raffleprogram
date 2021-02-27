package main_structure;

import java.util.ArrayList;

/**
 * Abstract representation of a Row of a spreadsheet. 
 * @author Cade Reynoldson
 * @version 1.0
 */
public class Row {
    
    /** The values contained in this row. */
    private ArrayList<Particle> row;
    
    /**
     * Creates a new instance of a row. 
     */
    public Row() {
        row = new ArrayList<Particle>();
    }
    
    /**
     * Creates a new row out of a given object array. 
     * @param o
     */
    public Row(Object[] o) {
        this();
        for (int i = 0; i < o.length; i++) 
            add(o[i].toString());
    }
    
    /**
     * Adds a new particle to the row. 
     * @param o
     */
    public void add(String o) {
        row.add(new Particle (o));
    }
    
    /**
     * Adds a new previously instantiated particle to the row. 
     * @param p the new particle to add. 
     */
    public void add(Particle p) {
        row.add(p);
    }
    
    /**
     * Returns the value at the given index. 
     * @param index the index to return.
     * @return the value at the given index. 
     */
    public Particle get(int index) {
        return row.get(index);
    }
    
    /**
     * Prints this row. 
     */
    public void printRow() {
        for (int i = 0; i < row.size(); i++)
            if (i != row.size() - 1)
                System.out.print(row.get(i).toString() + ", ");
            else
                System.out.println(row.get(i).toString());
    }
    
    
    /**
     * Creates a representation of this row as a string. 
     */
    public String toString() {
        String str = "";
        for (int i = 0; i < row.size(); i++)
            if (i != row.size() - 1)
                str += row.get(i).toString() + ", ";
            else
                str += row.get(i).toString();
        return str;
    }
    
    /**
     * Returns the length of this row. 
     * @return the length of this row. 
     */
    public int getLength() {
        return row.size();
    }

    public Row deepCopy() {
        Row r = new Row();
        for (Particle p : row) {
            r.add(p.deepCopy());
        }
        return r;
    }

    public void setValue(int index, Particle value) {

    }
}
