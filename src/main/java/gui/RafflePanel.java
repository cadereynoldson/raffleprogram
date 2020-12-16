/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import main_structure.RaffleSpecs;
import main_structure.SpreadSheet;

/**
 * Raffle panel. Handles displaying and manipulating all data invloved with generating a raffle. 
 * @author Cade
 */
public class RafflePanel extends javax.swing.JPanel implements PropertyChangeListener {

    
    /** Serial ID. 35th 4L. */
    private static final long serialVersionUID = 271704289108247783L;

    /** File panel for fetching files. */
    private FilePanel fileInputs;
    
    /** Panel for filtering data. */
    private FilteringPanel filteringPanel;
    
    /** Panel for running the raffle. */
    private RunRafflePanel runRafflePanel;
    
    /** The items spreadsheet. */
    private SpreadSheet items;
    
    /** The current entries in a spreadsheet. */
    private SpreadSheet currentEntries;
    
    /** The entries spreadsheet. */
    private SpreadSheet entries;
    
    /** The column of the grouped items (e.g.) color */
    private String groupedByColumn;
    
    /** A hashmap of spreadsheets to spreadsheets, with the key being the group identifier (e.g. colorway) */
    private HashMap<SpreadSheet, SpreadSheet> groupedSheets;
    
    /**
     * Creates new form RafflePanel
     */
    public RafflePanel() {
        initComponents();
        setName("Raffle");
        fileInputs = new FilePanel(this);
        filteringPanel = new FilteringPanel(this);
        runRafflePanel = new RunRafflePanel(this);
        this.add(fileInputs, BorderLayout.NORTH);
        this.add(filteringPanel, BorderLayout.CENTER);
        this.add(runRafflePanel, BorderLayout.SOUTH);
        items = null;
        entries = null;
    }

    /**
     * Handles property changes of the contained panels and objects. 
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String evtName = evt.getPropertyName();
        if (evtName.equals("ITEM")) { // For loading items. 
            loadItems(evt);
        } else if (evtName.equals("ENTRY")) { // For loading entires.
            loadEntries(evt);
        } else if (evtName.equals("RESET")) { //For resetting entries. 
            resetEntries();
        } else if (evtName.equals("FILTER")) { //For filtering entries. 
            filterEntries(evt);
        } else if (evtName.equals("RAFFLE")) { //Run the raffle. 
            raffle(evt);
        } else if (evtName.equals("GROUP")) { //Group Entries. 
            group(evt);
        }
    }
    
    /**
     * Conducts a raffle accoding to the raffle specs encoded in the parameterized PropertyChangeEvent
     * @param evt encoded within the new value should be a RaffleSpecs object.
     */
    private void raffle(PropertyChangeEvent evt) {
        RaffleSpecs specs = (RaffleSpecs) evt.getNewValue();
        File outputPath = fileInputs.getOutputFilePath();
        if (outputPath == null) {
            int option = JOptionPane.showConfirmDialog(this, "Theres no information for the folder to write to.\nWould you like to write to the folder this program is running out of?", 
                    "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                outputPath = new File(System.getProperty("user.dir"));
            } else {
                return;
            }
        }
        if (groupedSheets == null) { //If sheets are not grouped.
            raffleNoGrouping(specs, outputPath);
        } else { //If groupings exist. 
            raffleGrouping(specs, outputPath);
        }
    }
    
    /**
     * Raffles according to the rafflespecs and writes the winners to the corresponding output path. 
     * @param specs the specs to raffle in regards to. 
     * @param outputPath the output path to write the generated files to. 
     */
    private void raffleNoGrouping(RaffleSpecs specs, File outputPath) {
        TreeMap<Object, SpreadSheet> uniqueEntries = currentEntries.getUniqueValues(specs.getEntryRaffle());
        TreeMap<Object, SpreadSheet> uniqueItems = items.getUniqueValues(specs.getItemRaffle()); 
        int totalRaffled = 0;
        for (Object o : uniqueItems.keySet()) { //For all of the unique items, raffle them off. 
            if (!uniqueEntries.containsKey(o)) {//If unique entries doesn't contain this key, notify user!
                showError("Entries dataset doesn't contain the value \"" + o + "\" in the " + specs.getEntryRaffle() + " column!");
            } else {
                try {
                    if (o instanceof Float) {
                        SpreadSheet items = uniqueItems.get(o);
                        int count = (int) Float.parseFloat(Float.toString(((Float) items.getPartice(0, items.getIndex(specs.getCountColumn())).getValue())));
                        SpreadSheet winners = uniqueEntries.get(o).raffleItem(count);
                        if (!winners.writeToFile(fileInputs.getOutputFileName(), Float.toString((Float) o), outputPath.getAbsolutePath())) 
                            showError("Error writing file for " + fileInputs.getOutputFileName() + "_" + (String) o);
                        else
                            totalRaffled++;
                    } else if (o instanceof String) {
                        SpreadSheet items = uniqueItems.get(o);
                        int count = (int) Float.parseFloat(Float.toString(((Float) items.getPartice(0, items.getIndex(specs.getCountColumn())).getValue())));
                        SpreadSheet winners = uniqueEntries.get(o).raffleItem(count);
                        if (!winners.writeToFile(fileInputs.getOutputFileName(), (String) o, outputPath.getAbsolutePath()))
                            showError("Error writing file for " + fileInputs.getOutputFileName() + "_" + (String) o);
                        else
                            totalRaffled++;
                    } else {
                        showError("The value of the item to raffle by (" + o + ") does not contain a supported data type!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showError("Error in raffling off item " + items.getRow(0).toString() + 
                            "\n If problem persists, notify creater under the about tab.");
                }
            }
        }
        showClosingDialog(totalRaffled, outputPath);
    }
    
    /**
     * Raffles groups according to the parameterized RaffleSpecs. Writes files to the output path. 
     * @param specs the specs to raffle according to. 
     * @param outputPath the output path to write files to. 
     */
    private void raffleGrouping(RaffleSpecs specs, File outputPath) {
        int totalRaffled = 0;
        for (SpreadSheet sheet : groupedSheets.keySet()) { //For all the grouped items, raffle. 
            Object uniqueIdentifier = sheet.getColumn(sheet.getColumnIndex(groupedByColumn)).get(0);
            TreeMap<Object, SpreadSheet> uniqueEntries = groupedSheets.get(sheet).getUniqueValues(specs.getEntryRaffle());
            TreeMap<Object, SpreadSheet> uniqueItems = sheet.getUniqueValues(specs.getItemRaffle()); 
            for (Object o : uniqueItems.keySet()) {
                if (!uniqueEntries.containsKey(o)) {//If unique entries doesn't contain this key, notify user!
                    showError("Entries dataset doesn't contain the value \"" + o + "\" in the " + specs.getEntryRaffle() + " column!");
                } else {
                    if (o instanceof Float) {
                        SpreadSheet items = uniqueItems.get(o);
                        int count = (int) Float.parseFloat(Float.toString(((Float) items.getPartice(0, items.getIndex(specs.getCountColumn())).getValue())));
                        SpreadSheet winners = uniqueEntries.get(o).raffleItem(count);
                        if (!winners.writeToFile(fileInputs.getOutputFileName() + "(" + uniqueIdentifier + ")", Float.toString((Float) o), outputPath.getAbsolutePath())) 
                            showError("Error writing file for " + fileInputs.getOutputFileName() + "_" + (String) o);
                        else
                            totalRaffled++;
                    } else if (o instanceof String) {
                        SpreadSheet items = uniqueItems.get(o);
                        int count = (int) Float.parseFloat(Float.toString(((Float) items.getPartice(0, items.getIndex(specs.getCountColumn())).getValue())));
                        SpreadSheet winners = uniqueEntries.get(o).raffleItem(count);
                        if (!winners.writeToFile(fileInputs.getOutputFileName(), (String) o, outputPath.getAbsolutePath()))
                            showError("Error writing file for " + fileInputs.getOutputFileName() + "_" + (String) o);
                        else
                            totalRaffled++;
                    } else {
                        showError("The value of the item to raffle by (" + o.toString() + ") of the \n"
                                + "group (" +  uniqueIdentifier.toString() + ") does not contain a supported data type!");
                    }
                }
            }
        }
        showClosingDialog(totalRaffled, outputPath);
    }
    
    /**
     * Shows a dilog which will display data of the conducted raffle. 
     * @param totalRaffled the total number of classes of items raffled. 
     * @param filePath the path files were generated to. 
     */
    private void showClosingDialog(int totalRaffled, File filePath) {
        String message = "Raffled off a total of " + totalRaffled + " classes of items.\n"
                + "Output files located in the folder \"" + fileInputs.getFileName(filePath) + "\"\n"            ;
        JOptionPane.showMessageDialog(this, message, "Raffle Completed", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Groups the current raffle entries according to values in evt. 
     * - oldValue should be the item group column name. 
     * - newValue should be the entry group column name. 
     * @param evt the event to group by. (note javadoc)
     */
    private void group(PropertyChangeEvent evt) {
        if (groupedSheets != null) {
            int option = JOptionPane.showConfirmDialog(this, "Groups already exist. Would you like to overwrite them?", "Grouping", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (option != JOptionPane.OK_OPTION) {
                return;
            }
        }
        try {
            groupedSheets = new HashMap<SpreadSheet, SpreadSheet>();
            TreeMap<Object, SpreadSheet> splitItems = items.getUniqueValues((String) evt.getOldValue());
            TreeMap<Object, SpreadSheet> splitEntries = currentEntries.getUniqueValues((String) evt.getNewValue());
            for (Object o : splitItems.keySet()) { //For every object, group to the same values. 
                if (splitEntries.containsKey(o)) {
                    groupedSheets.put(splitItems.get(o), splitEntries.get(o));
                } else {
                    showError("No entries contianed in the entries column " + (String) evt.getNewValue() + " with the value of " + o 
                            + ". If you're seeing this error, either there are no entries for the item with the " + evt.getOldValue() + " value of " + o
                            + ", or there has been an error with the grouping columns."
                            + "\nAborting grouping.");
                    groupedSheets = null;
                    return;
                }
            } 
            groupedByColumn = (String) evt.getOldValue();
            JOptionPane.showMessageDialog(this, "Successfully grouped on the items column " + groupedByColumn + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            showError("Error grouping on " + (String) evt.getOldValue() + " to " + (String) evt.getNewValue());
            groupedSheets = null;
        }
    }
    
    /**
     * Filters entries according to the parameterized PropertyChangeEvenet. 
     * @param evt encoded in the new value should be the duplicate values to eliminate. 
     */
    private void filterEntries(PropertyChangeEvent evt) {
        if (groupedSheets == null) { //If no groups have been created, filter single sheet. 
            SpreadSheet s = currentEntries.eliminateDuplicates((String) evt.getNewValue());
            int totalRemoved = currentEntries.getNumRows() - s.getNumRows();
            currentEntries = s;
            filteringPanel.addNumRemoved(totalRemoved);
        } else { //Else groups have been created, filter individual groups. 
            int totalRemoved = 0;
            HashMap<SpreadSheet, SpreadSheet> filteredGroups = new HashMap<SpreadSheet, SpreadSheet>();
            for (SpreadSheet s : groupedSheets.keySet()) { //For all of the grouped spread sheets, filter the entries for the item. 
                SpreadSheet current = groupedSheets.get(s);
                SpreadSheet filtered = current.eliminateDuplicates((String) evt.getNewValue());
                totalRemoved += current.getNumRows() - filtered.getNumRows();
                filteredGroups.put(s, filtered);
            }
            groupedSheets = filteredGroups;
            filteringPanel.addNumRemoved(totalRemoved);
        }
    }
    
    /**
     * Resets the entries to the originally loaded file. 
     */
    private void resetEntries() {
        currentEntries = entries;
        groupedSheets = null;
    }
    
    /**
     * Loads the items to raffle. 
     * @param evt encoded in the new value should be the filepath of the items.
     */
    private void loadItems(PropertyChangeEvent evt) {
        SpreadSheet s = SpreadSheet.readCSV(((File) evt.getNewValue()).toString());
        if (s == null) { //S is null, display invalid csv. 
            fileInputs.resetValues(FilePanel.ITEM_CODE);
            filteringPanel.resetItems();
            runRafflePanel.resetItems();
            showError("Error reading in raffle items spreadsheet!");
        } else {
            items = s;
            filteringPanel.updateItems(items);
            runRafflePanel.updateItems(items);
            groupedSheets = null;
        }
    }
    
    /**
     * Loads entries for the raffle. 
     * @param evt encoded in the new value should be the filepath of the entries. 
     */
    private void loadEntries(PropertyChangeEvent evt) {
        SpreadSheet s = SpreadSheet.readCSV(((File) evt.getNewValue()).toString());
        if (s == null) {
            fileInputs.resetValues(FilePanel.ENTRY_CODE);
            filteringPanel.resetEntries();
            showError("Error reading in entries spreadsheet!");
        } else {
            entries = s;
            currentEntries = entries; 
            groupedSheets = null;
            filteringPanel.updateEntries(entries);
            runRafflePanel.updateEntries(entries);
        }
    }
    
    /**
     * Displays an error message. 
     * @param message the message to display as an error. 
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    
    /*********************************************************************
     *                  AUTOMATICALLY GENERATED CODE:                    *
     *********************************************************************/
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(2147483647, 300));
        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
