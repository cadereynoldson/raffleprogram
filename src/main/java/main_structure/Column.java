package main_structure;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Abstract representation of a column of a spreadsheet file. 
 * @author Cade Reynoldson
 * @version 1.0
 */
public class Column {
    
    /** The name of the column. */
    private String columnName;
    
    /** The values contained in this column. */
    private ArrayList<Particle> column;
    
    /**
     * Creates a new column with a given name. 
     * @param columnName the name of this column. 
     */
    public Column(String columnName) {
        this.columnName = columnName;
        column = new ArrayList<Particle>();
    }
    
    /**
     * Returns true if there are less unique values in this column than the given threshold, false otherwise.
     * @param threshold the threshold of unique values. 
     * @return true if there are less unique values in this column than the given threshold, false otherwise.
     */
    public boolean lessUniqueValues(int threshold) {
        if (column.size() < threshold)
            return true;
        else {
            TreeSet<Object> uniqueValues = new TreeSet<Object>();
            for (Particle p : column) {
                uniqueValues.add(p.getValue());
                if (uniqueValues.size() > threshold) //If there are more unique values than the threshold return false. 
                    return false;
            }
            return true;
        }
    }
    
    public TreeSet<Object> getUniqueValues() {
        TreeSet<Object> uniqueValues = new TreeSet<Object>();
        for (Particle p : column) {
            uniqueValues.add(p.getValue());
        }
        return uniqueValues; 
    }
    
    /**
     * Returns the particle at the given index. 
     * @param index the index to return the particle at. 
     * @return the particle at the given index. 
     */
    public Particle get(int index) {
        return column.get(index);
    }
    
    /**
     * Adds a particle to the column. 
     * @param p
     */
    public void add(Particle p) {
        column.add(p);
    }
    
    /**
     * Removes a particle at a given index. 
     * @param index
     */
    public void remove(int index) {
        column.remove(index);
    }
    
    /**
     * Returns the columns name. 
     * @return the columns name. 
     */
    public String getName() {
        return columnName;
    }
    
    
    /**
     * Returns the length of the column. 
     * @return the length of the column. 
     */
    public int getLength() {
        return column.size();
    }
}
