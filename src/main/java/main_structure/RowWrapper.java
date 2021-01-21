package main_structure;

import java.util.HashMap;

/**
 * Wrapper class for mapping a row to indexes and column names. This is mainly used for getting the indexes
 */
public class RowWrapper {

    /** The names of each column mapped to the index. */
    private HashMap<String, Integer> indexMap;

    /** The row to map indexes to column names. */
    Row row;

    /**
     * Creates a new instance of a row wrapper.
     * @param r
     * @param columnNames
     */
    public RowWrapper(Row r, String[] columnNames) {
        if (r.getLength() != columnNames.length)
            throw new IllegalArgumentException("Column names length and row length don't match.");
        indexMap = new HashMap<>();
        row = r;
        for (int i = 0; i < r.getLength(); i++) {
            indexMap.put(columnNames[i], i);
        }
    }

    /**
     * Returns the particle value at the index of a row given a column name.
     * @param columnName the name of the column.
     * @return the particle at the index of the corresponding column. If the column name isn't contained, return null.
     */
    public Particle getValue(String columnName) {
        if (indexMap.containsKey(columnName))
            return row.get(indexMap.get(columnName));
        else
            return null;
    }

    public Particle getValue(int index) {
        return row.get(index);
    }

    @Override
    public String toString() {
        return row.toString();
    }
}
