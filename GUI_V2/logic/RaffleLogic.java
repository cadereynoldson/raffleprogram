<<<<<<< HEAD:GUI_V2/logic/RaffleLogic.java
package logic;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import main_structure.Column;
import main_structure.Particle;
import main_structure.Row;
import main_structure.SpreadSheet;

/**
 * Class handling all of the logic involved with a raffle. 
 * @author Cade Reynoldson. 
 */
public class RaffleLogic {
    
    public static final String LOAD_ENTRIES_KEY = "LOAD_ENTRIES";
    
    public static final String LOAD_ITEMS_KEY = "LOAD_ITEMS";
    
    public static final String SET_OUTPUT_KEY = "SET_OUTPUT";
    
    public static final String AUTO_DETECT_KEY = "AUTO_DETECT";
    
    public static final String GENERATE_OUTPUT_KEY = "GENERATE_OUTPUT";
    
    public static final String FILTER_KEY = "FILTER";
    
    public static final String NEXT_KEY = "NEXT";
    
    public static final String PREVIOUS_KEY = "PREVIOUS";
    
    public static final String RESET_KEY = "RESET";
    
    private File outputFile; 
    
    private SpreadSheet currentSheet;
    
    private SpreadSheet itemsSheet; 
    
    private SpreadSheet originalSheet; 
    
    private HashMap<SpreadSheet, SpreadSheet> groupedSheets;
    
    private int threshold; 
    
    /** Indicates if the auto detect is enabled or not (true/false). */
    private boolean autoDetect;
    
    private boolean generateOutput;
    
    private boolean grouping; 
    
    private boolean kickMultipleEntries;
    
    public RaffleLogic() {
        autoDetect = true;
        generateOutput = false;
        grouping = false; 
        outputFile = new File(System.getProperty("user.dir"));
        threshold = 20; 
    }
    
    public void setDetectionThreshold(int newValue) {
        threshold = newValue; 
    }
    
    public int getDetectionThreshold() {
        return threshold; 
    }
    
    public void setAutoDetect(boolean newValue) {
        autoDetect = newValue; 
    }
    
    public void setGenerateOutput(boolean newValue) {
        generateOutput = newValue;
    }
    
    public void setOutputFile(File f) {
        outputFile = f; 
    }
    
    public SpreadSheet getItems() {
        return itemsSheet; 
    }
    
    /**
     * Returns the current progress towards running a raffle. 
     * @return
     */
    public int getProgress() {
        int progress = 0;
        if (originalSheet != null)
            progress += 50;
        if (autoDetect && originalSheet != null)
            progress += 20;
        else if (!autoDetect && itemsSheet != null)
            progress += 30;
        return progress;
    }
    
    public boolean hasNecessaryFiles() {
        if (originalSheet == null) //If the original sheet is null return false. 
            return false;
        else if (!autoDetect && itemsSheet == null) //If auto detect is off and no items are loaded in, return false. 
            return false; 
        return true; 
    }
    
    /**
     * Automatically detects items that are capable of being raffled by checking for a 
     * limited amount of unique entries in each column of the original entries sheet. 
     * @return An array list of column names detected which contained less than the maximum unique values. Default = 20.
     */
    public ArrayList<String> autoDetect() {
        return autoDetect(threshold);
    }
    
    /**
     * Automatically detects items that are capable of being raffled by checking for a 
     * limited amount of unique entries in each column of the original entries sheet. 
     * @param maxUniqueValue the maximum number of unique values for each column. 
     * @return An array list of column names detected which contained less than the maximum unique values. Default = 20.
     */
    public ArrayList<String> autoDetect(int maxUniqueValues) {
        ArrayList<String> uniqueValues = new ArrayList<String>(); 
        for (int i = 0; i < originalSheet.getNumColumns(); i++) {
            if (originalSheet.getColumn(i).lessUniqueValues(maxUniqueValues))
                uniqueValues.add(originalSheet.getColumn(i).getName());
        }
        return uniqueValues;
    }
    
    /**
     * Filters the current saved sheet by the column. 
     * @param byColumn the column to filter by. 
     */
    public void filter(String byColumn) {
        currentSheet = currentSheet.eliminateDuplicates(byColumn);
    }
    
    /**
     * Voids the current sheet. Filters by all column names or indexes contained in the list. 
     * @param byColumns a list of columns to filter by. 
     */
    public void filter(List<?> byColumns) {
        currentSheet = originalSheet;
        for (Object o : byColumns) {
            if (o instanceof String)
                currentSheet = currentSheet.eliminateDuplicates((String) o);
            else if (o instanceof Integer)
                currentSheet = currentSheet.eliminateDuplicates((Integer) o);
        }
    }
    
    /**
     * Removes an entry from the CURRENT sheet if the to string values of the values in 
     * the parameterized object array are equal to the entries in the current sheet toString()'s values. 
     * @param o the array to check for toString() equality. 
     */
    public boolean removeEntry(Object[] o) {
        boolean found = false;
        for (int i = 0; i < currentSheet.getNumRows(); i++) {
            Row r = currentSheet.getRow(i);
            for (int j = 0; j < r.getLength(); j++) {
                if (r.get(j).toString().equals(o[j].toString())) {
                    break;
                } 
                if (j == r.getLength() - 1)
                    found = true;
            }
            if (found) {
                currentSheet.removeRow(i);
                return true;
            }
        }
        return false; 
    }
    
    /**
     * Returns true if auto detect is enabled, false otherwise. 
     * @return
     */
    public boolean autoDetectEnabled() {
        return autoDetect;
    }
    
    public SpreadSheet getOriginalSheet() {
        return originalSheet;
    }
    
    public SpreadSheet getCurrentSheet() {
        return currentSheet;
    }
    
    /**
     * Creates an item count sheet for the columnName.
     * @param columnName
     * @return an item count sheet for the columnName.
     */
    public SpreadSheet createItemCountSheet(String columnName) {
        SpreadSheet s = new SpreadSheet();
        String[] names = {columnName, "Quantity"};
        s.initColumns(names);
        Column c = originalSheet.getColumn(originalSheet.getColumnIndex(columnName));
        TreeSet<Object> uniqueValues = c.getUniqueValues();
        for (Object o : uniqueValues) {
            Row r = new Row();
            r.add(o.toString());
            r.add(Integer.toString(0));
            s.addRow(r);
        }
        return s; 
    }
    
    public SpreadSheet createItemCountSheet(ArrayList<String> columns) {
        SpreadSheet s = new SpreadSheet();
        if (columns.size() == 1)
            return createItemCountSheet(columns.get(0));
        if (columns.size() == 2) { //If there are only two columns to map together
            Column c1 = originalSheet.getColumn(originalSheet.getColumnIndex(columns.get(0)));
            Column c2 = originalSheet.getColumn(originalSheet.getColumnIndex(columns.get(1)));
            TreeMap<Object, TreeSet<Object>> values = new TreeMap<Object, TreeSet<Object>>();
            for (int i = 0; i < c1.getLength(); i++) {
                Object v1 = c1.get(i).getValue();
                Object v2 = c2.get(i).getValue(); 
                if (values.containsKey(v1)) {
                    values.get(v1).add(v2);
                } else {
                    TreeSet<Object> v1_values = new TreeSet<Object>();
                    v1_values.add(v2);
                    values.put(v1, v1_values);
                }
            }
            String[] names = {columns.get(0), columns.get(1), "Quantity"}; 
            s.initColumns(names);
            for (Object rowValue : values.keySet()) {
                for (Object subValue : values.get(rowValue)) {
                    Row r = new Row();
                    r.add(rowValue.toString());
                    r.add(subValue.toString());
                    r.add(Integer.toString(0));
                    s.addRow(r);
                }
            }
        }
        return s;
    }
    
    public void setEntriesSheet(File f) {
        SpreadSheet s = SpreadSheet.readCSV(f.toString());
        if (s == null) {
            originalSheet = null;
            currentSheet = null;
            throw new IllegalArgumentException();
        } else {
            originalSheet = s;
            currentSheet = originalSheet; 
        }
    }
    
    public void setItemsSheet(File f) {
        SpreadSheet s = SpreadSheet.readCSV(f.toString());
        if (s == null) {
            itemsSheet = null;
            throw new IllegalArgumentException();
        } else {
            itemsSheet = s;
        }
    }
}
=======
package logic;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import main_structure.Column;
import main_structure.Particle;
import main_structure.Row;
import main_structure.SpreadSheet;

/**
 * Class handling all of the logic involved with a raffle. 
 * @author Cade Reynoldson. 
 */
public class RaffleLogic {
    
    public static final String LOAD_ENTRIES_KEY = "LOAD_ENTRIES";
    
    public static final String LOAD_ITEMS_KEY = "LOAD_ITEMS";
    
    public static final String SET_OUTPUT_KEY = "SET_OUTPUT";
    
    public static final String AUTO_DETECT_KEY = "AUTO_DETECT";
    
    public static final String GENERATE_OUTPUT_KEY = "GENERATE_OUTPUT";
    
    public static final String FILTER_KEY = "FILTER";
    
    public static final String NEXT_KEY = "NEXT";
    
    public static final String PREVIOUS_KEY = "PREVIOUS";
    
    public static final String RESET_KEY = "RESET";
    
    private File outputFile; 
    
    private SpreadSheet currentSheet;
    
    private SpreadSheet itemsSheet; 
    
    private SpreadSheet originalSheet; 
    
    private HashMap<SpreadSheet, SpreadSheet> groupedSheets;
    
    private int threshold; 
    
    /** Indicates if the auto detect is enabled or not (true/false). */
    private boolean autoDetect;
    
    private boolean generateOutput;
    
    private boolean grouping; 
    
    private boolean kickMultipleEntries;
    
    public RaffleLogic() {
        autoDetect = true;
        generateOutput = false;
        grouping = false; 
        outputFile = new File(System.getProperty("user.dir"));
        threshold = 20; 
    }
    
    public void setDetectionThreshold(int newValue) {
        threshold = newValue; 
    }
    
    public int getDetectionThreshold() {
        return threshold; 
    }
    
    public void setAutoDetect(boolean newValue) {
        autoDetect = newValue; 
    }
    
    public void setGenerateOutput(boolean newValue) {
        generateOutput = newValue;
    }
    
    public void setOutputFile(File f) {
        outputFile = f; 
    }
    
    public SpreadSheet getItems() {
        return itemsSheet; 
    }
    
    /**
     * Returns the current progress towards running a raffle. 
     * @return
     */
    public int getProgress() {
        int progress = 0;
        if (originalSheet != null)
            progress += 50;
        if (autoDetect && originalSheet != null)
            progress += 20;
        else if (!autoDetect && itemsSheet != null)
            progress += 30;
        return progress;
    }
    
    public boolean hasNecessaryFiles() {
        if (originalSheet == null) //If the original sheet is null return false. 
            return false;
        else if (!autoDetect && itemsSheet == null) //If auto detect is off and no items are loaded in, return false. 
            return false; 
        return true; 
    }
    
    /**
     * Automatically detects items that are capable of being raffled by checking for a 
     * limited amount of unique entries in each column of the original entries sheet. 
     * @return An array list of column names detected which contained less than the maximum unique values. Default = 20.
     */
    public ArrayList<String> autoDetect() {
        return autoDetect(threshold);
    }
    
    /**
     * Automatically detects items that are capable of being raffled by checking for a 
     * limited amount of unique entries in each column of the original entries sheet. 
     * @param maxUniqueValue the maximum number of unique values for each column. 
     * @return An array list of column names detected which contained less than the maximum unique values. Default = 20.
     */
    public ArrayList<String> autoDetect(int maxUniqueValues) {
        ArrayList<String> uniqueValues = new ArrayList<String>(); 
        for (int i = 0; i < originalSheet.getNumColumns(); i++) {
            if (originalSheet.getColumn(i).lessUniqueValues(maxUniqueValues))
                uniqueValues.add(originalSheet.getColumn(i).getName());
        }
        return uniqueValues;
    }
    
    /**
     * Filters the current saved sheet by the column. 
     * @param byColumn the column to filter by. 
     */
    public void filter(String byColumn) {
        currentSheet = currentSheet.eliminateDuplicates(byColumn);
    }
    
    /**
     * Voids the current sheet. Filters by all column names or indexes contained in the list. 
     * @param byColumns a list of columns to filter by. 
     */
    public void filter(List<?> byColumns) {
        currentSheet = originalSheet;
        for (Object o : byColumns) {
            if (o instanceof String)
                currentSheet = currentSheet.eliminateDuplicates((String) o);
            else if (o instanceof Integer)
                currentSheet = currentSheet.eliminateDuplicates((Integer) o);
        }
    }
    
    /**
     * Removes an entry from the CURRENT sheet if the to string values of the values in 
     * the parameterized object array are equal to the entries in the current sheet toString()'s values. 
     * @param o the array to check for toString() equality. 
     */
    public boolean removeEntry(Object[] o) {
        boolean found = false;
        for (int i = 0; i < currentSheet.getNumRows(); i++) {
            Row r = currentSheet.getRow(i);
            for (int j = 0; j < r.getLength(); j++) {
                if (r.get(j).toString().equals(o[j].toString())) {
                    break;
                } 
                if (j == r.getLength() - 1)
                    found = true;
            }
            if (found) {
                currentSheet.removeRow(i);
                return true;
            }
        }
        return false; 
    }
    
    /**
     * Returns true if auto detect is enabled, false otherwise. 
     * @return
     */
    public boolean autoDetectEnabled() {
        return autoDetect;
    }
    
    public SpreadSheet getOriginalSheet() {
        return originalSheet;
    }
    
    public SpreadSheet getCurrentSheet() {
        return currentSheet;
    }
    
    /**
     * Creates an item count sheet for the columnName.
     * @param columnName
     * @return an item count sheet for the columnName.
     */
    public SpreadSheet createItemCountSheet(String columnName) {
        SpreadSheet s = new SpreadSheet();
        String[] names = {columnName, "Quantity"};
        s.initColumns(names);
        Column c = originalSheet.getColumn(originalSheet.getColumnIndex(columnName));
        TreeSet<Object> uniqueValues = c.getUniqueValues();
        for (Object o : uniqueValues) {
            Row r = new Row();
            r.add(o.toString());
            r.add(Integer.toString(0));
            s.addRow(r);
        }
        return s; 
    }
    
    public SpreadSheet createItemCountSheet(ArrayList<String> columns) {
        SpreadSheet s = new SpreadSheet();
        if (columns.size() == 1)
            return createItemCountSheet(columns.get(0));
        if (columns.size() == 2) { //If there are only two columns to map together
            Column c1 = originalSheet.getColumn(originalSheet.getColumnIndex(columns.get(0)));
            Column c2 = originalSheet.getColumn(originalSheet.getColumnIndex(columns.get(1)));
            TreeMap<Object, TreeSet<Object>> values = new TreeMap<Object, TreeSet<Object>>();
            for (int i = 0; i < c1.getLength(); i++) {
                Object v1 = c1.get(i).getValue();
                Object v2 = c2.get(i).getValue(); 
                if (values.containsKey(v1)) {
                    values.get(v1).add(v2);
                } else {
                    TreeSet<Object> v1_values = new TreeSet<Object>();
                    v1_values.add(v2);
                    values.put(v1, v1_values);
                }
            }
            String[] names = {columns.get(0), columns.get(1), "Quantity"}; 
            s.initColumns(names);
            for (Object rowValue : values.keySet()) {
                for (Object subValue : values.get(rowValue)) {
                    Row r = new Row();
                    r.add(rowValue.toString());
                    r.add(subValue.toString());
                    r.add(Integer.toString(0));
                    s.addRow(r);
                }
            }
        }
        return s;
    }
    
    public void setEntriesSheet(File f) {
        SpreadSheet s = SpreadSheet.readCSV(f.toString());
        if (s == null) {
            originalSheet = null;
            currentSheet = null;
            throw new IllegalArgumentException();
        } else {
            originalSheet = s;
            currentSheet = originalSheet; 
        }
    }
    
    public void setItemsSheet(File f) {
        SpreadSheet s = SpreadSheet.readCSV(f.toString());
        if (s == null) {
            itemsSheet = null;
            throw new IllegalArgumentException();
        } else {
            itemsSheet = s;
        }
    }
}
>>>>>>> parent of f93266f... Small refactor. Preparing for new GUI development.:src/main/java/gui_v2/logic/RaffleLogic.java
