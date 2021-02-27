package main_structure;

import java.util.HashMap;

/**
 * Wrapper class for mapping a row to indexes and column names. This is mainly used for getting the indexes
 */
public class RowWrapper {

    /** The names of each column mapped to the index. */
    private HashMap<String, Integer> indexMap;

    /** The names of the columns of the row. */
    private String[] columnNames;

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
        this.columnNames = columnNames;
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

    /**
     * Returns the row of this row wrapper.
     * @return the row of this row wrapper.
     */
    public Row getRow() {
        return row;
    }

    /**
     * Creates a shallow copy of this row wrapper with a given index ignored.
     * @param ignoreIndex the index to ignore. If out of bounds of the lengths of this row wrapper will return null.
     * @return a shallow copy of this row wrapper with a given index ignored.
     */
    public RowWrapper getCopy(int ignoreIndex) {
        String[] colNames;
        if (ignoreIndex < 0 || ignoreIndex >= row.getLength())
            colNames = new String[row.getLength()];
        else
            colNames = new String[row.getLength() - 1];
        Row r = new Row();
        int thisColIndex = 0;
        for (int i = 0; i < row.getLength(); i++) {
            if (i != ignoreIndex) {
                r.add(r.get(i));
                colNames[thisColIndex] = colNames[i];
                thisColIndex++;
            }
        }
        return new RowWrapper(r, colNames);
    }


    public String getItemString(int ignoreIndex) {
        String s = "";
        int toConvert = row.getLength() - 1; //Convert n strings
        for (String colName : indexMap.keySet()) {
            int index = indexMap.get(colName);
            if (index != ignoreIndex) {
                s += colName + ": " + row.get(index).toString();
                toConvert--;
                if (toConvert != 0)
                    s += ", ";
            }
        }
        return s;
    }

    public Particle getValue(int index) {
        return row.get(index);
    }

    @Override
    public String toString() {
        return row.toString();
    }
}
