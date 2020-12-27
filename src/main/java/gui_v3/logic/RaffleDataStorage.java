package gui_v3.logic;

import main_structure.Column;
import main_structure.Row;
import main_structure.SpreadSheet;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Stores all of the data and handles all of the logic involved with the raffle.
 * @author Cade Reynoldson
 */
public class RaffleDataStorage {

    /** The file dump the winners into. */
    private static File outputFile = new File(System.getProperty("user.dir"));

    private static String entriesSheetFileDisplay;

    /** Indicates whether this raffle is using auto detect for selecting items. */
    private static boolean autoDetect = true;

    /** The auto detect unique values threshold. */
    private static int autoDetectThreshold = 20;

    /** Selected filters for the data! */
    private static ArrayList<String> chosenFilters = new ArrayList<>();

    private static ArrayList<String> selectedAutoDetectValues = new ArrayList<>();

    /** The most recently manipulated spreadsheet. */
    private static SpreadSheet currentEntriesSheet;

    /** The spreadsheet containing the items to raffle. */
    private static SpreadSheet itemsSheet;

    /** The originally loaded entries sheet. */
    private static SpreadSheet originalEntriesSheet;

    /** Indicates if multiple entries are to be kicked */ //TODO: IMPLEMENT!
    private static boolean kickMultipleEntries;

    public RaffleDataStorage() {
        throw new IllegalArgumentException("Raffle data storage is intended to be used as a static class. Cannot instantiate!");
    }

    public static void resetData() {
        originalEntriesSheet = null;
        itemsSheet = null;
        chosenFilters.clear();
        entriesSheetFileDisplay = null;
    }

    public static void setEntriesSheet(File f) {
        SpreadSheet s = SpreadSheet.readCSV(f.toString());
        if (s == null) {
            originalEntriesSheet = null;
            currentEntriesSheet = null;
            entriesSheetFileDisplay = null;
            chosenFilters.clear();
            itemsSheet = null;
            throw new IllegalArgumentException();
        } else {
            originalEntriesSheet = s;
            currentEntriesSheet = originalEntriesSheet;
            entriesSheetFileDisplay = ProgramDefaults.getFileName(f);
            chosenFilters.clear();
            itemsSheet = null;
        }
    }

    public static void setItemsSheet(File f) {
        SpreadSheet s = SpreadSheet.readCSV(f.toString());
        if (s == null) {
            itemsSheet = null;
            throw new IllegalArgumentException();
        } else {
            itemsSheet = s;
        }
    }

    public static String getEntriesFileString() {
        return entriesSheetFileDisplay;
    }

    /**
     * Returns true/false depending on the program having a loaded entries file.
     * @return true/false depending on the program having a loaded entries file.
     */
    public static boolean hasEntriesFile() {
        return originalEntriesSheet != null;
    }

    /**
     * Returns true/false depending on the program having a loaded items file.
     * @return true/false depending on the program having a loaded items file.
     */
    public static boolean hasItemsFile() {
        return itemsSheet != null;
    }

    /**
     * Returns true/false if there are any filters on the contained entries dataset.
     * @return true/false if there are any filters on the contained entries dataset.
     */
    public static boolean hasFiltered() {
        return chosenFilters.size() > 0;
    }

    public static void setAutoDetect(boolean val) {
        autoDetect = val;
        if (!autoDetect)
            selectedAutoDetectValues.clear();
    }

    /**
     * Automatically detects items that are capable of being raffled by checking for a
     * limited amount of unique entries in each column of the original entries sheet.
     * @return An array list of column names detected which contained less than the maximum unique values. Default = 20.
     */
    public static ArrayList<String> autoDetect() {
        return autoDetect(autoDetectThreshold);
    }

    /**
     * Automatically detects items that are capable of being raffled by checking for a
     * limited amount of unique entries in each column of the original entries sheet.
     * @param maxUniqueValues the maximum number of unique values for each column.
     * @return An array list of column names detected which contained less than the maximum unique values. Default = 20.
     */
    public static ArrayList<String> autoDetect(int maxUniqueValues) {
        ArrayList<String> uniqueValues = new ArrayList<String>();
        for (int i = 0; i < originalEntriesSheet.getNumColumns(); i++) {
            if (originalEntriesSheet.getColumn(i).lessUniqueValues(maxUniqueValues))
                uniqueValues.add(originalEntriesSheet.getColumn(i).getName());
        }
        return uniqueValues;
    }

    public static void addAutoDetectFilter(String filter) {
        selectedAutoDetectValues.add(filter.trim());
    }

    public static void removeAutoDetectFilter(String filter) {
        selectedAutoDetectValues.remove(filter);
    }

    public static ArrayList<String> getSelectedAutoDetectValues() {
        return selectedAutoDetectValues;
    }

    /**
     * Creates an item count data sheet for the stored auto detect values.
     * @return an item count sheet for the selected column.
     */
    public static SpreadSheet createItemCountSheet() {
        SpreadSheet s = new SpreadSheet();
        if (selectedAutoDetectValues.size() == 1)
            return createItemCountSheet(selectedAutoDetectValues.get(0));
        if (selectedAutoDetectValues.size() == 2) { //If there are only two columns to map together
            Column c1 = originalEntriesSheet.getColumn(originalEntriesSheet.getColumnIndex(selectedAutoDetectValues.get(0)));
            Column c2 = originalEntriesSheet.getColumn(originalEntriesSheet.getColumnIndex(selectedAutoDetectValues.get(1)));
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
            String[] names = {selectedAutoDetectValues.get(0), selectedAutoDetectValues.get(1), "Quantity"};
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

    /**
     * Creates an item count sheet for one column which has the specified column name.
     * @param columnName the column name to use for creating a count spreadsheet with.
     * @return an item count sheet for one column.
     */
    public static SpreadSheet createItemCountSheet(String columnName) {
        SpreadSheet s = new SpreadSheet();
        String[] names = {columnName, "Quantity"};
        s.initColumns(names);
        Column c = originalEntriesSheet.getColumn(originalEntriesSheet.getColumnIndex(columnName));
        TreeSet<Object> uniqueValues = c.getUniqueValues();
        for (Object o : uniqueValues) {
            Row r = new Row();
            r.add(o.toString());
            r.add(Integer.toString(0));
            s.addRow(r);
        }
        return s;
    }

}
