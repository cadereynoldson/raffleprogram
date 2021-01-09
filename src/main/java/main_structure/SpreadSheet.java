package main_structure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Representation of a spreadsheet in a manipulable ArrayList based object.
 * - RULES:
 * - 1. All storing of strings will be lowercase.
 * Process of building a spreadsheet follows:
 * - 1. Initialize columns
 * - 2. Build dataset row by row!
 * @author Cade Reynoldson
 * @version 1.0
 */
public class SpreadSheet {
    
    /** Columns of the spreadsheet. */
    private ArrayList<Column> columns;
    
    /** Rows of the spreadsheet. */
    private ArrayList<Row> rows;
    
    /**
     * Initializes a blank spreadsheet. 
     */
    public SpreadSheet() {
        columns = new ArrayList<Column>();
        rows = new ArrayList<Row>();
    }
    
    public Particle getParticle(int row, int column) {
        return rows.get(row).get(column);
    }
    
    public Row getRow(int row) {
        return rows.get(row);
    }
    
    public Column getColumn(int columnIndex) {
        return columns.get(columnIndex);
    }

    public Column getColumn(String columnName) { return columns.get(getColumnIndex(columnName)); }

    /**
     * Eliminates duplicates of a given column name.
     * @return duplicates of a given column eliminated.
     */
    public SpreadSheet eliminateDuplicates(String columnName) {
        int columnIndex = getColumnIndex(columnName);
        if (columnIndex == -1) 
            return this;
        return eliminateDuplicates(columnIndex);
    }
    
    /**
     * Creates a blank spreadsheet based on the columns already existing in this spreadsheet.  
     * @return a blank spreadsheet based on the columns already existing in this spreadsheet. 
     */
    public SpreadSheet getBlankSpreadSheet() {
        SpreadSheet newSheet = new SpreadSheet();
        for (Column c : columns) 
            newSheet.columns.add(new Column(c.getName()));
        return newSheet;
    }
    
    /**
     * Eliminates the duplicates of a column at a given index. 
     * @param index the index of the row to eliminate the duplicates of. 
     * @return
     */
    public SpreadSheet eliminateDuplicates(int index) {
        HashMap<Object, Row> duplicatesRemoved = new HashMap<Object, Row>();
        for (int i = 0; i < rows.size(); i++)
            duplicatesRemoved.put(rows.get(i).get(index).getValue(), rows.get(i));
        SpreadSheet newSheet = new SpreadSheet();
        newSheet.initColumns(this.getColumnNames());
        for (Object o : duplicatesRemoved.keySet())
            newSheet.addRow(duplicatesRemoved.get(o));
        return newSheet;
    }
    
    /**
     * Creates a hashmap of unique values mapped to spreadsheets which only contain rows that have a unique value in the specified column.
     * - NOTE: This creates a soft copy to optimize memory usage. 
     * @param column the name of the column to split on unique values. 
     * @return a hashmap of unique values mapped to spreadsheets which only contain rows that have their values.
     */
    public TreeMap<Object, SpreadSheet> getUniqueValues(String column) {
        int columnIndex = getColumnIndex(column);
        if (columnIndex == -1)
            return null;
        return getUniqueValues(columnIndex);
    }
    
    /**
     * Creates a hashmap of unique values mapped to spreadsheets which only contain rows that have their values.
     * - NOTE: This creates a soft copy to optimize memory usage. 
     * @param index the index of the column to split on unique values. 
     * @return a hashmap of unique values mapped to spreadsheets which only contain rows that have their values.
     */
    public TreeMap<Object, SpreadSheet> getUniqueValues(int index) {
        TreeMap<Object, SpreadSheet> mapping = new TreeMap<Object, SpreadSheet>();
        for (int rowNum = 0; rowNum < rows.size(); rowNum++) {
            Object value = rows.get(rowNum).get(index).getValue();
            if (mapping.containsKey(value)) {
                mapping.get(value).addRow(rows.get(rowNum));
            } else {
                SpreadSheet blankSheet = getBlankSpreadSheet();
                blankSheet.addRow(rows.get(rowNum));
                mapping.put(value, blankSheet);
            }
        }
        return mapping;
    }

    public int getUniqueValueCounts(int index) {
        Column c = columns.get(index);
        return c.getUniqueValues().size();
    }

    public int getUniqueValueCounts(String columnName) {
        int columnIndex = getColumnIndex(columnName);
        if (columnIndex == -1)
            return -1;
        return getUniqueValueCounts(columnIndex);
    }
    
    /**
     * Returns the index of a column given its name. 
     * @param columnName the name of the column to find the index of. 
     * @return the index of a column, -1 if column name does not exist. 
     */
    public int getColumnIndex(String columnName) {
        for (int i = 0; i < columns.size(); i++)
            if (columns.get(i).getName().equals(columnName))
                return i;
        return -1;
    }
    
    /**
     * Creates a spreadsheet of n entries (indicated by itemCount) of random rows contained in this spreadsheet. 
     * @param itemCount the number of items to raffle. 
     * @return a spreadsheet of n winners of the raffle. 
     */
    public SpreadSheet raffleItem(int itemCount) {
        if (itemCount >= rows.size()) //If there are less entries than items to raffle off. 
            return this;
        SpreadSheet winners = getBlankSpreadSheet();
        Random numberGenerator = new Random();
        for (int i = 0; i < itemCount; i++) {
            int winningIndex = numberGenerator.nextInt(rows.size());
            winners.addRow(removeRow(winningIndex));
        }
        return winners;
    }
    
    /**
     * Removes a row at the given index. 
     * @param index the index of the row. 
     */
    public Row removeRow(int index) {
        for (Column c : columns) {
            c.remove(index);
        }
        return rows.remove(index);
    }
    
    /**
     * Adds a row to the spreadsheet. 
     * @param r the row to add. 
     */
    public void addRow(Row r) {
        rows.add(r);
        for (int i = 0; i < r.getLength(); i++) {
            columns.get(i).add(r.get(i));
        }
    }
    
    /**
     * Returns the index of a column name. Ignores case of the column. 
     * @param colName the column to return the index of.  
     * @return the index of a column name. -1 if column name is not found. 
     */
    public int getIndex(String colName) {
        for (int i = 0; i < columns.size(); i++)
            if (columns.get(i).getName().equalsIgnoreCase(colName))
                return i;
        return -1;
    }
    
    /**
     * Initializes the columns with given names. 
     * @param names
     */
    public void initColumns(String[] names) {
        HashSet<String> s = new HashSet<>(Arrays.asList(names));
        if (s.size() != names.length)
            throw new IllegalArgumentException("Names of each column must be unique.");
        for (int i = 0; i < names.length; i++) {
            columns.add(new Column(names[i]));
        }
    }
    
    /**
     * Returns the name of each column in the form of a string array. 
     * @return an array of the column names.
     */
    public String[] getColumnNames() {
        String[] names = new String[columns.size()];
        for (int i = 0; i < columns.size(); i++) {
            names[i] = columns.get(i).getName();
        }
        return names;
    }
    
    /**
     * Returns a 2D array of objects that are contained in this spreadsheet. 
     * @return a 2D array of objects that are contained in this spreadsheet. 
     */
    public Object[][] getObjectRepresentation() {
        Object[][] sheet = new Object[rows.size()][columns.size()];
        for (int i = 0; i < rows.size(); i++) {
            Row current = rows.get(i);
            for (int j = 0; j < columns.size(); j++) {
                sheet[i][j] = current.get(j).getValue();
            }
        }
        return sheet; 
    }
    
    /**
     * Returns the number of columns contained in this spreadsheet. 
     * @return the number of columns contained in this spreadsheet. 
     */
    public int getNumColumns() {
        return columns.size();
    }
    
    /**
     * Returns the number of rows contained in this sheet. 
     * @return the number of rows contained in this sheet. 
     */
    public int getNumRows() {
        return rows.size();
    }
    
    /**
     * Prints this sheet. 
     */
    public void printSheet() {
        for (int i = 0; i < columns.size(); i++)
            System.out.print(columns.get(i).getName() + " ");
        System.out.println();
        for (int i = 0; i < rows.size(); i++)
            rows.get(i).printRow();
    }
    
    /**
     * Reads in a dataframe as a CSV. 
     * @param path the filepath.
     * @return a dataframe. 
     */
    public static SpreadSheet readCSV(String path) {
        try {
            SpreadSheet df = new SpreadSheet();
            BufferedReader csvReader = new BufferedReader(new FileReader(path));
            String r = csvReader.readLine();
            String[] vals = r.split(",");
            df.initColumns(vals);
            while ((r = csvReader.readLine()) != null) {
                vals = r.split(",");
                Row row = new Row();
                for (int i = 0; i < vals.length; i++) {
                    row.add(vals[i].trim());
                }
                df.addRow(row);
            }
            csvReader.close();
            return df;
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Writes this spreadsheet to a file with a ".txt" extension. 
     * @param name the name of the new file. 
     * @param unique the unique identifier of the file. 
     * @param folderPath the folder path to write to. 
     * @return true if the file has successfully been written to, false otherwise. 
     */
    public boolean writeToFile(String name, String unique, String folderPath) {
        String fileName = folderPath + "\\" + name + "_" + unique + "_winners";
        File f = new File(fileName + ".txt");
        try {
            int counter = 1; 
            while (!f.createNewFile()) //while file cannot be created. 
                f = new File(fileName + "(" + counter++ + ").txt");
            FileWriter w = new FileWriter(f);
            String[] names = getColumnNames();
            for (int i = 0; i < names.length; i++)
                w.append(names[i] + ", ");
            w.append("\n");
            for (Row r : rows) {
                w.append(r.toString() + ", \n");
            }
            w.close();
            return true; 
        } catch (IOException e) {
            return false; 
        }
        
    }
    
    /**
     * Returns an array list of the contianed rows. 
     * @return an array list of the contianed rows. 
     */
    public ArrayList<Row> getRows() {
        return rows; 
    }
    
    
    public static void main(String[] args) throws IOException {
        SpreadSheet ss = SpreadSheet.readCSV("testFiles/mock_entries_colors.csv");
        SpreadSheet newSheet = ss.getBlankSpreadSheet();
        for (Row r : ss.getRows()) {
            Row newRow = new Row();
            for (int i = 0; i < r.getLength(); i++) {
                if (r.get(i).getValue().equals(1f)) {
                    newRow.add("Yellow");
                } else if (r.get(i).getValue().equals(2f)) {
                    newRow.add("Green");
                } else if (r.get(i).getValue().equals(3f)) {
                    newRow.add("Orange");
                } else {
                    newRow.add(r.get(i));
                }
            }
            newSheet.addRow(newRow);
        }
        boolean s = 
        newSheet.writeToFile("mock_entries", "colors", "testFiles/TestOutput");
        System.out.println(s);
    }
}
