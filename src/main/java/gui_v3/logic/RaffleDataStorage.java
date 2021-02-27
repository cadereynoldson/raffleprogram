package gui_v3.logic;

import main_structure.*;

import java.io.File;
import java.util.*;

/**
 * Stores all of the data and handles all of the logic involved with the raffle.
 * @author Cade Reynoldson
 */
public class RaffleDataStorage {

    private static boolean hasWrittenToFile = false;

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

    /**
     * Each item to raffle (Row) mapped to a spreadsheet of entries for that item (SpreadSheet)
     * NOTE: THESE ITEMS CAN BE ALTERED AS THEY ARE DEEP COPIES OF THE ORIGINAL ITEMS DATASET!
     */
    private static final HashMap<RowWrapper, SpreadSheet> groupedSheets = new HashMap<>();

    /**
     * Created when running the raffle. Each item raffled is mapped to a spreadsheet of winners of that item.
     * NOTE: THESE ITEMS CAN BE ALTERED AS THEY ARE DEEP COPIES OF THE ORIGINAL ITEMS DATASET!
     */
    private static final HashMap<RowWrapper, SpreadSheet> winners = new HashMap<>();

    /** A data structure of indexes mapped based on the row. This Integer corresponds to the row's position in the groupedSheets data structure. */
    private static final HashMap<Row, Integer> rowToGroupedSheetIndex = new HashMap<>();

    /** A data structure of indexes mapped based on the row. This Integer corresponds to the row's position in the winners data structure. */
    private static final HashMap<Row, Integer> rowToWinnerIndex = new HashMap<>();

    /** A data structure of indexes mapped based on the row. This Integer corresponds to the row's position in the current entries data structure. */
    private static final HashMap<Row, Integer> rowToCurrentEntriesIndex = new HashMap<>();

    /** The most recently manipulated spreadsheet.
     * NOTE:
     * - THIS IS USED AS A REFERENCE FOR FILTERING.
     * - ANY UPDATES TO THIS SHEET FROM THE RUN RAFFLE PAGE SHOULD BE DONE WITH THE METHOD convertRunRaffleToCurrentEntries()
     */
    private static SpreadSheet currentEntriesSheet;

    /** The originally loaded entries sheet. */
    private static SpreadSheet originalEntriesSheet;

    /** The spreadsheet containing the items to raffle. This is NOT to be altered after initialization! */
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

    public static String getDistributionValuesString() {
        String s = "";
        for (int i = 0; i < selectedDistributionValues.size(); i++) {
            if (i != selectedDistributionValues.size() - 1) {
                s += selectedDistributionValues.get(i) + ", ";
            } else {
                s += selectedDistributionValues.get(i);
            }
        }
        return s;
    }

    public static void removeWinner(HashMap<String, Particle> itemIdentifiers, int indexToRemove, boolean generateNewWinner) {
        HashMap<String, Object> converted = new HashMap<>();
        for (String s : itemIdentifiers.keySet())
            converted.put(s, itemIdentifiers.get(s).getValue());
        RowWrapper r = getCorrespondingRowWrapper(converted, winners);
        if (r != null) {
            if (winners.get(r).getNumRows() > indexToRemove && indexToRemove >= 0)
                winners.get(r).removeRow(indexToRemove);
            r.getValue(countColumn).setValue(String.valueOf((Integer) r.getValue(countColumn).getValue() + 1));
            if (generateNewWinner) {
                SpreadSheet newWinners = groupedSheets.get(r).raffleItem((Integer) r.getValue(countColumn).getValue());
                for (int i = 0; i < newWinners.getNumRows(); i++)
                    winners.get(r).addRow(newWinners.getRow(i));
                r.getValue(countColumn).setValue(String.valueOf(0));
            }
        } else {
            //TODO: Display error.
        }
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

    public static boolean hasWinners() {
        return !winners.isEmpty();
    }

    /**
     * Removes an entry from the current raffle given an index of the current raffle.
     * @param index the index of the entry to remove from the current entries sheet.
     */
    public static void removeEntry(int index) throws IllegalArgumentException {
        Row entry = currentEntriesSheet.removeRow(index);
        RowWrapper r = getCorrespondingRowWrapper(getDistributionValues(currentEntriesSheet, index), groupedSheets);
        removeFromGroupStructure(r, entry);
        resetFinalStructureIndexes();
    }

    /**
     * Runs the raffle. Appends all of the winners generated on top of the winners already contained in the dataset.
     */
    public static void runRaffle() {
        for (RowWrapper r : groupedSheets.keySet()) {
            try {
                SpreadSheet s = groupedSheets.get(r);
                int count = (Integer) r.getValue(countColumn).getValue(); //This count of random numbers.
                Random generator = new Random();
                for (int i = 0; i < count; i++) {
                    int winnerIndex = generator.nextInt(s.getNumRows());
                    Row winner = s.getRow(winnerIndex);
                    RowWrapper item = getCorrespondingRowWrapper(getDistributionValues(s, winnerIndex), groupedSheets);
                    setAsWinner(item, winner);
                    removeFromGroupStructure(item, winner);
                    resetFinalStructureIndexes();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Error in raffling. Count column is not an integer.");
            }
        }
    }

    /**
     * Undoes the run raffle process. Removes all winners and groups them back into the items they were raffled for.
     */
    public static void undoRunRaffle() {
        for (RowWrapper item: winners.keySet()) { //For all of the winner items :
            SpreadSheet current = winners.get(item);
            int winnersOfItem = current.getNumRows();
            for (int i = 0; i < current.getNumRows(); i++) { //Add the winners back into the group they entered for.
                groupedSheets.get(item).addRow(current.getRow(i));
            }
            current.removeAllRows();
            item.getValue(countColumn).setValue(String.valueOf(winnersOfItem));
        }
        convertRunRaffleToCurrentEntries();
    }

    public static void manualSetAsWinner(int index) {
        RowWrapper item = assureKeyValid(index);
        if (item.getValue(countColumn).getValue() instanceof Integer) {
            int count = (Integer) item.getValue(countColumn).getValue();
            if (count == 0)
                throw new IllegalArgumentException("You have zero left of this item to raffle:\n" + item.getItemString(countColumn));
            Row winner = currentEntriesSheet.removeRow(index);
            setAsWinner(item, winner);
            removeFromGroupStructure(item, winner);
            resetFinalStructureIndexes();
        }
    }

    /**
     * Given a row wrapper of an item and a row, it sets the row as a winner of the item.
     * @param item the item this winner is to belong to.
     * @param r the row (entry) to add as a winner.
     */
    private static void setAsWinner(RowWrapper item, Row r) {
        winners.get(item).addRow(r);
        int value = (Integer) item.getValue(countColumn).getValue();
        item.getValue(countColumn).setValue(String.valueOf(value - 1));
    }

    /**
     * Removes an entry from the group structure. NOTE: This does NOT reset the final structure indexes!
     * @param item the item this row is grouped with.
     * @param r the row to remove.
     */
    private static void removeFromGroupStructure(RowWrapper item, Row r) {
        System.out.println("GROUPED SHEETS: " + (groupedSheets.get(item) == null) + " - ROW TO GROUPED SHEETS: " + (rowToGroupedSheetIndex.get(r) == null));
        groupedSheets.get(item).removeRow(rowToGroupedSheetIndex.get(r));
    }

    /**
     * Returns a total count of winners in the raffle.
     * @return a total count of winners in the raffle.
     */
    public static int getTotalNumWinners() {
        int count = 0;
        for (RowWrapper item : winners.keySet())
            count += winners.get(item).getNumRows();
        return count;
    }

    /**
     * Returns the list of distribution values (column names) the raffle is going to be ran based on.
     * @return the list of distribution values the raffle is going to be ran based on.
     */
    public static List<String> getDistributionValuesList() {
        return selectedDistributionValues;
    }

    /**
     * Returns the winners of an item given it's identifying (distribution) values (excluding count).
     * @param values the values of the identifying item.
     * @return the winners of an item given the items identifying (distribution) values.
     */
    public static SpreadSheet getWinners(HashMap<String, Particle> values) {
        for (RowWrapper r : winners.keySet()) { //Brute force search through the winners for the spreadsheet identical to the values.
            int counter = 0;
            SpreadSheet current = winners.get(r);
            for (String colName : values.keySet()) {
                Object rowValue = r.getValue(colName).getValue();
                Object displayValue = values.get(colName).getValue();
                if (rowValue == null || !rowValue.equals(displayValue)) //If there is no value contained under the current column name or values are not equal, break.
                    break;
                else //else increment the counter.
                    counter++;
            }
            //Check if we found the winners spreadsheet.
            if (counter == values.size()) { //Counter equals the size of the values set -> found the correct spreadsheet.
                return current;
            }
        }
        return null;
    }

    /**
     * Converts a column of the current items sheet to a string array.
     * @param columnName the name of the column to find a
     * @return
     */
    public static String[] getItemValues(String columnName) throws IllegalArgumentException {
        Column c = itemsSheet.getColumn(columnName);
        if (c == null) {
            throw new IllegalArgumentException("There has been an error fetching the values of the column: " + columnName);
        }
        String[] values = new String[c.getLength()];
        for (int i = 0; i < c.getLength(); i++)
            values[i] = c.get(i).toString();
        return values;
    }

    /**
     * Assures a key is valid for a given entries index of the current entries sheet. Ensures this entry can be properly selected as a winner.
     * @param index the index of the entry in the current entries sheet.
     * @return a rowwrapper of the group key this entry belongs to.
     * @throws IllegalArgumentException If there is no group key, winners key, or the two generated keys are not equal.
     */
    private static RowWrapper assureKeyValid(int index) throws IllegalArgumentException {
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
            Row itemRow = itemsSheet.getRow(i);
            RowWrapper itemWrapper = new RowWrapper(itemRow, itemsSheet.getColumnNames());
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
        convertRunRaffleToCurrentEntries(); //Convert the table to display to the table for
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

    /**
     * Converts the current entries (rows) in the grouped entries data structure to the current entries sheet.
     * This is the current workaround for indexing problems involving removing raffle entries and setting winners from raffle groups.
     */
    private static void convertRunRaffleToCurrentEntries() {
        currentEntriesSheet = new SpreadSheet();
        currentEntriesSheet.initColumns(originalEntriesSheet.getColumnNames());
        for (RowWrapper r : groupedSheets.keySet()) {
            SpreadSheet group = groupedSheets.get(r);
            for (int i = 0; i < group.getNumRows(); i++)
                currentEntriesSheet.addRow(group.getRow(i));
        }
        resetFinalStructureIndexes();
    }

    public static SpreadSheet getCombinedWinnersSheet() {
        SpreadSheet s = new SpreadSheet();
        s.initColumns(currentEntriesSheet.getColumnNames());
        for (RowWrapper r : winners.keySet()) {
            SpreadSheet currentWinners = winners.get(r);
            for (int i = 0; i < currentWinners.getNumRows(); i++)
                s.addRow(currentWinners.getRow(i));
        }
        return s;
    }

    public static void setWrittenToFile(boolean value) {
        hasWrittenToFile = value;
    }

    public static boolean hasWrittenToFile() {
        return hasWrittenToFile;
    }
}
