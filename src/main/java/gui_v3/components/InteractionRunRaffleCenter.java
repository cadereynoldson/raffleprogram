package gui_v3.components;

import gui_v3.logic.*;
import main_structure.Particle;
import main_structure.Row;
import main_structure.SpreadSheet;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.List;

public class InteractionRunRaffleCenter extends JPanel {

    /** Property change support to notify parent display with of changes. */
    private PropertyChangeSupport pcs;

    /** The current run raffle location to display. */
    private NavigationLocations locationDisplayed;

    /** The JLabel to display in the center of the screen if no winners are generated for an item. */
    private final JLabel noWinnersLabel;

    /** Table for displaying the entries of the raffle. Can be manipulated by selecting pre determined winners. */
    private JTable entriesTable;

    /** A table for displaying the winners of a displayed item. */
    private JTable winnersTable;

    /** The JScrollPane to display the winners table in. */
    private JScrollPane winnersTableDisplayPane;

    /** The boxes to display for selecting the winners to display. Combo Boxes have names which indicate the identifier it represents! */
    private List<JComboBox<String>> displayedBoxes;

    /**
     * Initializes a new instance of this panel.
     * @param pcs The property change support for notifying the master display frame.
     * @param location the location to display for this panel.
     */
    public InteractionRunRaffleCenter(PropertyChangeSupport pcs, NavigationLocations location) {
        super();
        this.pcs = pcs;
        locationDisplayed = location;
        noWinnersLabel = ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.RAFFLE_WINNERS_NO_WINNERS);
        initTheme();
        setLocationDisplayed();
    }

    /**
     * Initializes the theme of this program.
     */
    private void initTheme() {
        setBackground(ProgramColors.FOREGROUND_COLOR);
    }

    /**
     * Sets the location displayed by this panel.
     * @param newLocation the new location to display.
     */
    public void setLocationDisplayed(NavigationLocations newLocation) {
        locationDisplayed = newLocation;
        setLocationDisplayed();
    }

    /**
     * Sets the location displayed for the panel.
     */
    private void setLocationDisplayed() {
        if (RaffleDataStorage.hasEntriesFile() && RaffleDataStorage.hasItems()) {
            if (locationDisplayed == NavigationLocations.RUN_RAFFLE_REVIEW) {
                setRaffleOverview();
            } else if (locationDisplayed == NavigationLocations.RUN_RAFFLE_SHOW_WINNERS) {
                System.out.println("SETTING WINNERS DISPLAY!");
                setRaffleWinnersDisplay();
            }
        } else {
            setNotReadyForRaffle();
        }
    }

    /**
     * Sets a display to notify the user that they are not ready to run the raffle.
     */
    private void setNotReadyForRaffle() {
        removeAll();
        setLayout(new GridLayout(0, 1));
        add(ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.RAFFLE_REVIEW_NOT_READY));
        add(ProgramDefaults.getRaffleCheckList());
        add(Box.createRigidArea(new Dimension(0, 0)));
    }

    /**
     * Displays the first step of the run raffle process. Allows the user to remove entries, manually add winners, and run the raffle.
     */
    private void setRaffleOverview() {
        removeAll();
        setLayout(new GridBagLayout());
        RaffleDataStorage.initPreRaffle();
        entriesTable = ProgramDefaults.getTable(RaffleDataStorage.getCurrentEntriesSheet());
        JButton remove = ProgramDefaults.getButton(ProgramStrings.RAFFLE_REVIEW_REMOVE);
        remove.addActionListener(this::removeFromRaffle);
        JButton winner = ProgramDefaults.getButton(ProgramStrings.RAFFLE_REVIEW_WINNER);
        winner.addActionListener(this::addAsWinner);
        JButton runRaffle = ProgramDefaults.getButton(ProgramStrings.RAFFLE_REVIEW_RUN_RAFFLE);
        runRaffle.addActionListener(this::runRaffle);
        JPanel buttonPanel = ProgramDefaults.getBlankPanel(new GridLayout(1, 0));
        buttonPanel.add(ProgramDefaults.createSpacedPanel(remove));
        buttonPanel.add(ProgramDefaults.createSpacedPanel(winner));
        buttonPanel.add(ProgramDefaults.createSpacedPanel(runRaffle));
        add(ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.RAFFLE_REVIEW_BRIEF_DESC), ProgramDimensions.RAFFLE_REVIEW_BRIEF_DESC_CONSTRAINTS);
        add(ProgramDefaults.getTableScrollPane(entriesTable, "Raffle Entries"), ProgramDimensions.RAFFLE_TABLE_CONSTRAINTS);
        add(buttonPanel, ProgramDimensions.RAFFLE_REVIEW_BUTTON_PANEL_CONSTRAINTS);
    }

    private void runRaffle(ActionEvent actionEvent) {
        pcs.firePropertyChange(PropertyChangeKeys.RUN_RAFFLE, null, null);
    }

    private void removeFromRaffle(ActionEvent actionEvent) {
        handleRemoveOrWinnerAction(PropertyChangeKeys.REMOVE_ENTRY);
    }

    private void addAsWinner(ActionEvent actionEvent) {
        handleRemoveOrWinnerAction(PropertyChangeKeys.SET_AS_WINNER);
    }

    private void handleRemoveOrWinnerAction(String propertyChangeKey) {
        int selectedRow = entriesTable.getSelectedRow();
        if (selectedRow == -1) { //No row selected, beep
            java.awt.Toolkit.getDefaultToolkit().beep();
        } else if (assureTableSpreadsheetEquality(selectedRow)) {
            int translatedRow = entriesTable.getRowSorter().convertRowIndexToModel(selectedRow);
            pcs.firePropertyChange(propertyChangeKey, null, translatedRow);
        } else {
            String message = "Entries manipulation error (01)\n";
            if (propertyChangeKey.equals(PropertyChangeKeys.REMOVE_ENTRY)) {
                message = ProgramStrings.RAFFLE_REMOVE_ENTRY_ERROR;
            } else if (propertyChangeKey.equals(PropertyChangeKeys.SET_AS_WINNER)) {
                message = ProgramStrings.RAFFLE_SET_WINNER_ERROR;
            }
            ProgramDefaults.displayError(message, ProgramStrings.DIALOGUE_ERROR_TITLE, this);
        }
    }

    /**
     * Handles assuring that the row selected in the entries table is equal to the corresponding currently stored in the "currentEntriesSheet" of raffle data storage.
     * @param selectedRow the VIEW index of the selected row.
     * @return
     */
    private boolean assureTableSpreadsheetEquality(int selectedRow) {
        int translatedRow = entriesTable.getRowSorter().convertRowIndexToModel(selectedRow);
        Row r = RaffleDataStorage.getCurrentEntriesSheet().getRow(translatedRow);
        for (int i = 0; i < r.getLength(); i++) {
            if (!entriesTable.getValueAt(selectedRow, i).equals(r.get(i).getValue())) { //If value at index does not equal a value in the row, return false.
                return false;
            }
        }
        return true;
    }

    /**
     * Removes an item from the table. This is called VIA the property change handler of display frame based on success of removal and such.
     * @param modelIndex the index of the model to remove - NOT THE INDEX OF THE CURRENT VIEW!
     */
    public void removeFromTable(int modelIndex) {
        ((DefaultTableModel) entriesTable.getModel()).removeRow(modelIndex);
    }

    /**
     * Sets the view to display raffle winners. This method will display winners alongside comboboxes for choosing the winners to display.
     */
    private void setRaffleWinnersDisplay() {
        if (RaffleDataStorage.hasWinners()) {
            removeAll();
            List<String> distroValues = RaffleDataStorage.getDistributionValuesList();
            displayedBoxes = new ArrayList<>();
            setLayout(new GridBagLayout());
            //Build ComboBox Panel
            JPanel comboBoxPanel = ProgramDefaults.getBlankPanel(new GridLayout(1, 0));
            for (String distroVal : distroValues) { //Build a combo box for each distribution value.
                String[] values = convertToSorted(RaffleDataStorage.getItemValues(distroVal));
                JComboBox<String> box = ProgramDefaults.getComboBox(values);
                box.setName(distroVal);
                box.addActionListener(event -> updateDisplayedWinners());
                displayedBoxes.add(box);
                JPanel boxDisplay = ProgramDefaults.getBlankPanel();
                boxDisplay.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), distroVal, TitledBorder.CENTER, TitledBorder.ABOVE_TOP, ProgramFonts.DEFAULT_FONT_SMALL));
                boxDisplay.add(box);
                comboBoxPanel.add(boxDisplay);
            }
            JButton back = ProgramDefaults.getButton(ProgramStrings.ITEMS_AD_BACK);
            back.addActionListener(event -> pcs.firePropertyChange(PropertyChangeKeys.UNDO_RAFFLE, null, null));
            JButton removeWinner = ProgramDefaults.getButton(ProgramStrings.RAFFLE_WINNERS_REMOVE_WINNER);
            removeWinner.addActionListener(this::handleRemoveWinnerAction);
            JButton exportWinners = ProgramDefaults.getButton(ProgramStrings.RAFFLE_WINNERS_EXPORT_WINNER);
            exportWinners.addActionListener(event -> pcs.firePropertyChange(PropertyChangeKeys.EXPORT_WINNERS, null, null));
            JPanel buttonPanel = ProgramDefaults.getBlankPanel(new GridLayout(1, 0));
            buttonPanel.add(ProgramDefaults.createSpacedPanel(back));
            buttonPanel.add(ProgramDefaults.createSpacedPanel(removeWinner));
            buttonPanel.add(ProgramDefaults.createSpacedPanel(exportWinners));
            JLabel briefDec = ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.RAFFLE_WINNERS_BRIEF_DESC);
            add(briefDec, ProgramDimensions.RAFFLE_WINNERS_BRIEF_DESC_CONSTRAINTS);
            add(comboBoxPanel, ProgramDimensions.RAFFLE_COMBO_BOX_CONSTRAINTS);
            winnersTable = ProgramDefaults.getTable(RaffleDataStorage.getWinners(getWinnerItemIdentifiers()));
            if (winnersTable.getRowCount() == 0) //If there are no rows for this item, add the no winners label.
                add(noWinnersLabel, ProgramDimensions.RAFFLE_WINNERS_TABLE_CONSTRAINTS);
            else {
                winnersTableDisplayPane = ProgramDefaults.getTableScrollPane(winnersTable, getWinnerTitleString());
                add(winnersTableDisplayPane, ProgramDimensions.RAFFLE_WINNERS_TABLE_CONSTRAINTS);
            }
            add(buttonPanel, ProgramDimensions.RAFFLE_WINNERS_BUTTON_PANEL_CONSTRAINTS);
        } else {
            //TODO: DISPLAY ERROR.
        }
    }

    private String[] convertToSorted(String[] list) {
        int[] intArr = new int[list.length];
        double[] doubleArr = new double[list.length];
        for (int i = 0; i < list.length; i++) { //Try to parse int array.
            try {
                intArr[i] = Integer.parseInt(list[i]);
            } catch (Exception e) {
                intArr = null;
            }
        }
        for (int i = 0; i < list.length; i++) { //Try to parse double array.
            try {
                doubleArr[i] = Double.parseDouble(list[i]);
            } catch (Exception e) {
                doubleArr = null;
            }
        }
        String[] sorted;
        if (intArr != null) { //If an int array was parsed.
            TreeSet<Integer> sortedInts = new TreeSet<>();
            for (int i = 0; i < intArr.length; i++)
                sortedInts.add(intArr[i]);
            sorted = new String[sortedInts.size()];
            int cnt = 0;
            for (Integer i : sortedInts)
                sorted[cnt++] = String.valueOf(i);
        } else if (doubleArr != null) { //If a double array was parsed.
            TreeSet<Double> sortedInts = new TreeSet<>();
            for (int i = 0; i < doubleArr.length; i++)
                sortedInts.add(doubleArr[i]);
            sorted = new String[sortedInts.size()];
            int cnt = 0;
            for (Double i : sortedInts)
                sorted[cnt++] = String.valueOf(i);
        } else {
            TreeSet<String> sortedStrings = new TreeSet<>(Arrays.asList(list));
            int cnt = 0;
            sorted = new String[sortedStrings.size()];
            for (String s : sortedStrings) {
                sorted[cnt++] = s;
            }
        }
        return sorted;
    }


    private void handleRemoveWinnerAction(ActionEvent actionEvent) {
        int row = winnersTable.getSelectedRow();
        if (row == -1) {
            java.awt.Toolkit.getDefaultToolkit().beep();
        } else {
            int translatedRow = winnersTable.getRowSorter().convertRowIndexToModel(row);
            pcs.firePropertyChange(PropertyChangeKeys.REMOVE_WINNER, getWinnerItemIdentifiers(), translatedRow);
        }
    }

    public void updateDisplayedWinnersTable() {
        updateDisplayedWinners();
    }

    /**
     * Searches through the displayed combo boxes to find the selected identifiers of the table to display.
     */
    private HashMap<String, Particle> getWinnerItemIdentifiers() {
        HashMap<String, Particle> identifiersOfItem = new HashMap<>(); //Identifiers of the item to display.
        for (JComboBox c : displayedBoxes)
            identifiersOfItem.put(c.getName(), new Particle(String.valueOf(c.getSelectedItem())));
        return identifiersOfItem;
    }

    /**
     * Creates a string to display as the table title.
     * @return a string to display as the table title.
     */
    private String getWinnerTitleString() {
        String s = "Winners of: ";
        HashMap<String, Particle> identifiersOfItem = getWinnerItemIdentifiers();
        int count = 1;
        for (String value : identifiersOfItem.keySet()) {
            s += value + " " + identifiersOfItem.get(value).getValue();
            if (count++ != identifiersOfItem.size())
                s += ", ";
        }
        return s;
    }

    /**
     * Searches through the displayed combo boxes to find the selected identifiers of the table to display.
     */
    private void updateDisplayedWinners() {
        HashMap<String, Particle> identifiersOfItem = getWinnerItemIdentifiers();
        SpreadSheet s = RaffleDataStorage.getWinners(identifiersOfItem);
        if (winnersTable.getRowCount() == 0) //If there are no rows in the current table, remove the no winners label.
            remove(noWinnersLabel);
        else
            remove(winnersTableDisplayPane);
        winnersTable = ProgramDefaults.getTable(s);
        if (winnersTable.getRowCount() == 0) //If there are no rows for this item, add the no winners label.
            add(noWinnersLabel, ProgramDimensions.RAFFLE_WINNERS_TABLE_CONSTRAINTS);
        else {
            winnersTableDisplayPane = ProgramDefaults.getTableScrollPane(winnersTable, getWinnerTitleString());
            add(winnersTableDisplayPane, ProgramDimensions.RAFFLE_WINNERS_TABLE_CONSTRAINTS);
        }
        revalidate();
        repaint();
    }
}
