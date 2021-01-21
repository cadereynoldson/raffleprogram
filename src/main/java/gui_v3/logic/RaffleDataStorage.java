package gui_v3.logic;

import main_structure.Column;
import main_structure.Row;
import main_structure.RowWrapper;
import main_structure.SpreadSheet;

import java.io.File;
import java.util.*;

/**
 * Stores all of the data and handles all of the logic involved with the raffle.
 * @author Cade Reynoldson
 */
public class RaffleDataStorage {

    /** The file dump the winners into. */
    private static File outputFile = new File(System.getProperty("user.dir"));

    /** The display string of the entries sheet. */
    private static String entriesSheetFileDisplay;

    /** The display string of the items sheet. */
    private static String itemsSheetFileDisplay;

    /** Indicates whether this raffle is using auto detect for selecting items. */
    private static boolean autoDetect = true;

    /** The auto detect unique values threshold. */
    private static int autoDetectThreshold = 20;

    /** Selected filters for the data! */
    private static final ArrayList<String> chosenFilters = new ArrayList<>();

    /**
     * Selected values used in creating an auto detect count sheet
     * --OR--
     * used in the instance of manual item loading for the distribute by values.
     */
    private static final ArrayList<String> selectedDistributionValues = new ArrayList<>();

    /**
     * Priority queue stored in reverse order for proper removal of entries prior to conducting the raffle!
     * Reverse order retains original indexing when removing.
     */
    private static final PriorityQueue<Integer> indexesToRemove = initIndexesQueue();

    /** Each item to raffle (Row) mapped to a spreadsheet of entries for that item (SpreadSheet) */
    private static final HashMap<RowWrapper, SpreadSheet> groupedSheets = new HashMap<>();

    /** Created when running the raffle. Each item raffled is mapped to a spreadsheet of winners of that item. */
    private static final HashMap<RowWrapper, SpreadSheet> winners = new HashMap<>();

    /** A data structure of indexes mapped based on the row. This Integer corresponds to the row's position in the groupedSheets data structure. */
    private static final HashMap<Row, Integer> rowToGroupedSheetIndex = new HashMap<>();

    private static final HashMap<Row, Integer> rowToWinnerIndex = new HashMap<>();

    /** The most recently manipulated spreadsheet. */
    private static SpreadSheet currentEntriesSheet;

    /** The originally loaded entries sheet. */
    private static SpreadSheet originalEntriesSheet;

    /** The spreadsheet containing the items to raffle. */
    private static SpreadSheet itemsSheet;

    /** The index of count column of the items spreadsheet. Default is one.*/
    private static int countColumn = -1;

    /** Indicates if multiple entries are to be kicked */ //TODO: IMPLEMENT!
    private static boolean kickMultipleEntries;

    public RaffleDataStorage() {
        throw new IllegalArgumentException("Raffle data storage is intended to be used as a static class. Cannot instantiate!");
    }

    private static PriorityQueue<Integer> initIndexesQueue() {
        return new PriorityQueue<>(20, Comparator.reverseOrder());
    }

    public static void resetData() {
        originalEntriesSheet = null;
        itemsSheet = null;
        chosenFilters.clear();
        entriesSheetFileDisplay = null;
        indexesToRemove.clear();
    }

    public static void setEntriesSheet(File f) {
        SpreadSheet s = SpreadSheet.readCSV(f.toString());
        if (s == null) {
            originalEntriesSheet = null;
            currentEntriesSheet = null;
            entriesSheetFileDisplay = ProgramStrings.ENTRIES_INFORMATION_FILE_STATUS_NO_FILE_LOADED;
            resetItemsData();
            throw new IllegalArgumentException();
        } else {
            originalEntriesSheet = s;
            currentEntriesSheet = originalEntriesSheet;
            entriesSheetFileDisplay = ProgramStrings.getLongFileName(f);
            resetItemsData();
        }
    }

    /**
     * Resets all entries dependent values stored in this class to their original state.
     */
    public static void resetItemsData() {
        selectedDistributionValues.clear();
        chosenFilters.clear();
        itemsSheet = null;
        itemsSheetFileDisplay = ProgramStrings.ITEMS_MANUAL_FILE_NO_FILE;
        countColumn = -1;
    }

    /**
     * Sets the items sheet from a file path. (Manual item input).
     * @param f the file to create and set the items spreadsheet with.
     */
    public static void setItemsSheet(File f) {
        SpreadSheet s = SpreadSheet.readCSV(f.toString());
        if (s == null) {
            resetItemsData();
            throw new IllegalArgumentException();
        } else {
            itemsSheet = s;
            itemsSheetFileDisplay = ProgramStrings.getLongFileName(f);
        }
    }

    /**
     * Sets the items sheet from the data of parsed from a JTable. (Auto-Detect)
     * @param o a 2d array of objects to convert into a spreadsheet.
     * @param colNames the names of each column.
     */
    public static void setItemsSheet(Object[][] o, String[] colNames) {
        SpreadSheet s = new SpreadSheet();
        s.initColumns(colNames);
        for (int i = 0; i < o.length; i++) {
            System.out.println(Arrays.toString(o[i]));
            s.addRow(new Row(o[i]));
        }
        itemsSheetFileDisplay = "Generated With Auto-Detect";
        itemsSheet = s;
    }

    /**
     * Returns a reference to the items sheet.
     * @return a reference to the items sheet.
     */
    public static SpreadSheet getItemsSheet() {
        return itemsSheet;
    }

    /**
     * Returns a reference to the original entries sheet.
     * @return a reference to the original entries sheet.
     */
    public static SpreadSheet getOriginalEntriesSheet() { return originalEntriesSheet; }

    /**
     * Returns a reference to the current entries sheet.
     * @return a reference to the current entries sheet.
     */
    public static SpreadSheet getCurrentEntriesSheet() {
        return currentEntriesSheet;
    }

    /**
     * Returns the string to display for the entries file.
     * @return the string to display for the entries file.
     */
    public static String getEntriesFileString() {
        return entriesSheetFileDisplay;
    }

    /**
     * Returns the string to display for the items file.
     * @return the string to display for the items file.
     */
    public static String getItemsFileString() { return itemsSheetFileDisplay; }

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

    /**
     * Changes the auto detect value.
     * @param val the new boolean auto detect value.
     */
    public static void setAutoDetect(boolean val) {
        autoDetect = val;
        selectedDistributionValues.clear();
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

    /**
     * Returns true or false based on if the storage has items to raffle.
     * This is indicated ALWAYS by the count column not being equal to -1 AND having distribution values!
     * - This is because the CURRENT version of the GUI requires a count column and selected distribution values for the raffle algorithm to run.
     * @return true or false if the items file has all necessary item values for the raffle.
     */
    public static boolean hasItems() {
        return countColumn != -1 && hasDistributionValues() && itemsSheet != null;
    }

    /**
     * Returns true or false based on having proper storage of the selected distribution values.
     * @return true/false based on having distribution values.
     */
    public static boolean hasDistributionValues() {
        return selectedDistributionValues.size() > 0;
    }

    /**
     * Clears the selected distribution values.
     */
    public static void clearDistributionValues() {
        selectedDistributionValues.clear();
    }

    public static boolean usingAutodetect() { return autoDetect; }

    /**
     * Creates an item count data sheet for the stored auto detect values.
     * @return an item count sheet for the selected column.
     */
    public static SpreadSheet createItemCountSheet() {
        SpreadSheet s = new SpreadSheet();
        if (selectedDistributionValues.size() == 1)
            return createItemCountSheet(selectedDistributionValues.get(0));
        if (selectedDistributionValues.size() == 2) { //If there are only two columns to map together
            Column c1 = originalEntriesSheet.getColumn(originalEntriesSheet.getColumnIndex(selectedDistributionValues.get(0)));
            Column c2 = originalEntriesSheet.getColumn(originalEntriesSheet.getColumnIndex(selectedDistributionValues.get(1)));
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
            String[] names = {selectedDistributionValues.get(0), selectedDistributionValues.get(1), ProgramStrings.QUANTITY_COLUMN_NAME};
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
        String[] names = {columnName, ProgramStrings.QUANTITY_COLUMN_NAME};
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

    /**
     * Sets the count column field given an given a string name. This method assures that the count column contains a number.
     * @param columnName the name of the column to mark as the count column.
     * @throws IllegalArgumentException In the case of the column containing a non number value.
     */
    public static void setCountColumn(String columnName) throws IllegalArgumentException {
        int col =  itemsSheet.getColumnIndex(columnName);
        for (int i = 0; i < itemsSheet.getNumRows(); i++) {
            if (!(itemsSheet.getParticle(i, col).getValue() instanceof Number)) {
                clearDistributionValues();
                countColumn = -1;
                throw new IllegalArgumentException(ProgramStrings.DIALOGUE_ITEMS_MANUAL_COUNT_COL_ERR);
            }
        }
        countColumn = col;
    }

    /**
     * Sets the selected columns in which to distribute items based off of.
     * @param columnNames a collection of column names. These must be equal to the
     * @throws IllegalArgumentException
     */
    public static void setSelectedDistributionValues(Collection<String> columnNames) throws IllegalArgumentException {
        selectedDistributionValues.clear();
        for (String columnName : columnNames) {
            if (!autoDetect) { //If auto detect is off - ASSURE VALUES ARE COMPATIBLE!!
                int entriesIndex = originalEntriesSheet.getColumnIndex(columnName);
                if (entriesIndex == -1) {//Make sure the original entries sheet has a column with the same name. This is easiest.
                    selectedDistributionValues.clear();
                    throw new IllegalArgumentException(ProgramStrings.DIALOGUE_ITEMS_MANUAL_NO_ENTRIES_COL_ERR + columnName);
                }
                TreeSet<Object> entriesColValues = originalEntriesSheet.getColumn(entriesIndex).getUniqueValues();
                Column itemsCol = itemsSheet.getColumn(columnName);
                for (int i = 0; i < itemsCol.getLength(); i++) {
                    try {
                        if (!entriesColValues.contains(itemsCol.get(i).getValue())) {
                            selectedDistributionValues.clear();
                            throw new IllegalArgumentException("The column (" + columnName + ") in the entries dataset\ndoesn't contain the value: " + itemsCol.get(i).getValue());
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException("The column (" + columnName + ") in the entries dataset\ndoesn't contain the value: " + itemsCol.get(i).getValue());
                    }
                }
            } //Endif
            selectedDistributionValues.add(columnName);
        }
    }

    /**
     * Adds or removes a filter conducted on the entries dataset.
     * @param filterValue the filter value to add or remove from this dataset.
     */
    public static void updateFilter(String filterValue) {
        synchronized (chosenFilters) { //Synchronized on chosen filters. Make sure any thread altering actions wait here for the current chosen filter action to be completed.
            if (chosenFilters.contains(filterValue)) { //Check for filters list containing.
                //Remove filter and re-set current filtering data.
                chosenFilters.remove(filterValue);
                currentEntriesSheet = originalEntriesSheet;
                for (String filter : chosenFilters)
                    currentEntriesSheet = currentEntriesSheet.eliminateDuplicates(filter);
            } else {
                //Add filter and update current sheet.
                chosenFilters.add(filterValue);
                currentEntriesSheet = currentEntriesSheet.eliminateDuplicates(filterValue);
            }
        }
    }

    /**
     * Returns the current filters of the entries dataset in the form of a hashset.
     * @return the current filters of the entries dataset in the form of a hashset.
     */
    public static HashSet<String> getFilterValues() {
        return new HashSet<>(chosenFilters);
    }

    /**
     * Removes an entry from the current raffle given an index of the current raffle.
     * @param index the index of the entry to remove from the current entries sheet.
     */
    public static void removeEntry(int index) throws IllegalArgumentException {
        RowWrapper r = assureKeyExists(index);
        executeRemoval(r, index);
    }

    public static void setAsWinner(int index) throws IllegalArgumentException {
        RowWrapper item = assureKeyExists(index);
        if (item.getValue(countColumn).getValue() instanceof Integer) {
            int count = (Integer) item.getValue(countColumn).getValue();
            if (count == 0)
                throw new IllegalArgumentException("You have zero left of this item to raffle:\n" + item.toString());
            System.out.println("ERROR CHECKING COMPLETED -- TODO: SET AS WINNER");
        } else {
            throw new IllegalArgumentException("There has been an error with the underlying data structure. (COUNT COLUMN NOT INTEGER).\nContact the developer of the program if this problem persists.");
        }
        Row winner = currentEntriesSheet.getRow(index);
        winners.get(item).addRow(winner);
    }


    private static void decrementItemCount(RowWrapper item) {

    }

    /**
     * Will remove an entry from the current entries dataset alongside its entry within the grouped entries data structure. Performs no checking for valid inputs.
     * @param groupKey the key of the group this entry belongs to.
     * @param index the index of the row to remove. This is the row contained in the CURRENT ENTRIES SHEET DATASET!
     */
    private static void executeRemoval(RowWrapper groupKey, int index) {
        Row r = currentEntriesSheet.removeRow(index);
        groupedSheets.get(groupKey).removeRow(rowToGroupedSheetIndex.get(r));
        resetFinalStructureIndexes();
    }

    /**
     *
     * @param index
     * @return
     * @throws IllegalArgumentException
     */
    private static RowWrapper assureKeyExists(int index) throws IllegalArgumentException {
        HashMap<String, Object> distributionValues = getDistributionValues(currentEntriesSheet, index);
        RowWrapper groupKey = getCorrespondingRowWrapper(distributionValues, groupedSheets);
        RowWrapper winnerKey = getCorrespondingRowWrapper(distributionValues, winners);
        if (groupKey  == null || winnerKey == null || groupKey != winnerKey)
            throw new IllegalArgumentException("Entry could not be found or there are inconsistencies inside of underlying data structure.\nContact program developer if this problem persists.");
        return groupKey;
    }

    /**
     * Resets the final structure index structures. Due to the inconsistencies of removing and adding entries this is the current work-around.
     */
    private static void resetFinalStructureIndexes() {
        rowToGroupedSheetIndex.clear();
        rowToWinnerIndex.clear();
        for (RowWrapper r : groupedSheets.keySet()) {
            SpreadSheet group = groupedSheets.get(r);
            for (int i = 0; i < group.getNumRows(); i++)
                rowToGroupedSheetIndex.put(group.getRow(i), i);
        }
        for (RowWrapper r : winners.keySet()) {
            SpreadSheet win = winners.get(r);
            for (int i = 0; i < win.getNumRows(); i++)
                rowToWinnerIndex.put(win.getRow(i), i);
        }
    }

    /**
     * Initializes all data for preparing to conduct a raffle.
     * BEWARE THIS IS ABSOLUTELY A TIME CONSUMING PROCESS!
     * - This method ensures consistency of row wrappers. This meaning these row wrappers are used as keys in the groupedSheets and winners datastructures.
     */
    public static void initPreRaffle() {
        if (!hasItems())
            throw new IllegalArgumentException("Cannot group items if they haven't been initialized.");
        else if (!hasEntriesFile())
            throw new IllegalArgumentException("Cannot group items if there is no entries.");
        groupedSheets.clear(); // Clear grouped sheets.
        winners.clear();
        for (int i = 0; i < itemsSheet.getNumRows(); i++) { // For every item to raffle - Put each row into grouping & winners data structure with a blank spreadsheet.
            SpreadSheet entries = new SpreadSheet();
            SpreadSheet itemWinners = new SpreadSheet();
            entries.initColumns(currentEntriesSheet.getColumnNames());
            itemWinners.initColumns(currentEntriesSheet.getColumnNames());
            RowWrapper itemWrapper = new RowWrapper(itemsSheet.getRow(i), itemsSheet.getColumnNames());
            groupedSheets.put(itemWrapper, entries);
            winners.put(itemWrapper, itemWinners);
        }
        //Save time - Don't do in nested loop.
        for (int i = 0; i < currentEntriesSheet.getNumRows(); i++) { //For every entry in the current sheet - add it to the hash map of items -> entries for that item:
            RowWrapper r = getCorrespondingRowWrapper(getDistributionValues(currentEntriesSheet, i), groupedSheets);
            if (r != null) { //Add current row to the found row wrappers dataset.
                groupedSheets.get(r).addRow(currentEntriesSheet.getRow(i));
            }
        }
        resetFinalStructureIndexes();
    }

    /**
     * Allows the fetching of a RowWrapper key from one of the final structures (groupedSheets or winners).
     * @param rowValues the "Distribution values" of a given row. This can be fetched using the method getDistributionValues()
     * @param finalStructure the final structure to fetch the data of. NOTE: THIS IS ONLY TESTED WITH the "groupedSheets AND winners" VARIABLES.
     * @return the RowWrapper key from the final structure which have the same row values as rowValues. Returns null if no value is found.
     */
    public static RowWrapper getCorrespondingRowWrapper(HashMap<String, Object> rowValues, HashMap<RowWrapper, SpreadSheet> finalStructure) {
        RowWrapper r  = null;
        for (RowWrapper currentWrapper : finalStructure.keySet()) { //For every row rapper in this structure.
            Iterator<String> distroIterator = rowValues.keySet().iterator();
            while (distroIterator.hasNext()) { //For all of the distribution values, find the corresponding row.
                String distroValue = distroIterator.next();
                Object groupedSheetVal = currentWrapper.getValue(distroValue).getValue();
                if (!groupedSheetVal.equals(rowValues.get(distroValue))) //If the current value doesn't match, break.
                    break;
                else if (!distroIterator.hasNext()) { //If we've gotten to the last element in the distribution iterator, we've found the selected distribution row.
                    r = currentWrapper;
                }
            }
        }
        return r;
    }

    /**
     * Fetches the distribution values of a given row index. Maps every BASE OBJECT value of the row of the
     * given spreadsheet to the column it was fetched from.
     * @param sheet the spreadsheet to fetch the object
     * @param rowIndex the index of the the row to fetch the distribution values of.
     * @return a hash map of distribution values mapped to the name of the column of their origin (Distribution column).
     */
    public static HashMap<String, Object> getDistributionValues(SpreadSheet sheet, int rowIndex) {
        HashMap<String, Object> distroValues = new HashMap<>();
        for (int i = 0; i < selectedDistributionValues.size(); i++) //For every distribution value, map it to the corresponding row.
            distroValues.put(selectedDistributionValues.get(i), sheet.getColumn(selectedDistributionValues.get(i)).get(rowIndex).getValue());
        return distroValues;
    }

    /**
     * Returns a total count of items that have been loaded in to raffle.
     * @return a total count of items that have been loaded in to raffle.
     */
    public static int getTotalNumItems() {
        int count = 0;
        if (hasItems())
            for (int i = 0; i < itemsSheet.getNumRows(); i++)
                count += (Integer) itemsSheet.getRow(i).get(countColumn).getValue();
        else
            count = -1;
        return count;
    }


}
