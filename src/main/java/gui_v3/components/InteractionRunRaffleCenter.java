package gui_v3.components;

import gui_v3.logic.*;
import main_structure.Row;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeSupport;

public class InteractionRunRaffleCenter extends JPanel {

    /** Property change support to notify parent display with of changes. */
    private PropertyChangeSupport pcs;

    private JTable entriesTable;

    private NavigationLocations locationDisplayed;

    public InteractionRunRaffleCenter(PropertyChangeSupport pcs, NavigationLocations location) {
        super();
        this.pcs = pcs;
        locationDisplayed = location;
        initTheme();
        setLocationDisplayed();
    }

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
    
    private void setLocationDisplayed() {
        if (RaffleDataStorage.hasEntriesFile() && RaffleDataStorage.hasItems()) {
            if (locationDisplayed == NavigationLocations.RUN_RAFFLE_REVIEW) {
                setRaffleOverview();
            } else if (locationDisplayed == NavigationLocations.RUN_RAFFLE_SHOW_WINNERS) {
                setRaffleWinners();
            }
        } else {
            setNotReadyForRaffle();
        }
    }

    private void setNotReadyForRaffle() {
        removeAll();
        setLayout(new GridLayout(0, 1));
        add(ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.RAFFLE_REVIEW_NOT_READY));
        add(ProgramDefaults.getRaffleCheckList());
        add(Box.createRigidArea(new Dimension(0, 0)));
    }

    private void setRaffleOverview() {
        removeAll();
        setLayout(new GridBagLayout());
        entriesTable = ProgramDefaults.getTable(RaffleDataStorage.getCurrentEntriesSheet());
        RaffleDataStorage.initPreRaffle();
        JButton remove = ProgramDefaults.getButton(ProgramStrings.RAFFLE_REVIEW_REMOVE);
        remove.addActionListener(this::removeFromRaffle);
        JButton winner = ProgramDefaults.getButton(ProgramStrings.RAFFLE_REVIEW_WINNER);
        winner.addActionListener(this::addAsWinner);
        JButton runRaffle = ProgramDefaults.getButton(ProgramStrings.RAFFLE_REVIEW_RUN_RAFFLE);
        JPanel buttonPanel = ProgramDefaults.getBlankPanel(new GridLayout(1, 0));
        buttonPanel.add(ProgramDefaults.createSpacedPanel(remove));
        buttonPanel.add(ProgramDefaults.createSpacedPanel(winner));
        buttonPanel.add(ProgramDefaults.createSpacedPanel(runRaffle));
        add(ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.RAFFLE_REVIEW_BRIEF_DESC), ProgramDimensions.RAFFLE_REVIEW_BRIEF_DESC_CONSTRAINTS);
        add(ProgramDefaults.getTableScrollPane(entriesTable, "Raffle Entries"), ProgramDimensions.RAFFLE_TABLE_CONSTRAINTS);
        add(buttonPanel, ProgramDimensions.RAFFLE_REVIEW_BUTTON_PANEL_CONSTRAINTS);
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
            //TODO: DISPLAY ERROR. PRE SET LOGIC FOR FUTURE CADE:
//            if (propertyChangeKey.equals(PropertyChangeKeys.REMOVE_ENTRY)) {
//
//            } else if ((propertyChangeKey.equals(PropertyChangeKeys.SET_AS_WINNER)) {
//
//            }
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

    private void setRaffleWinners() {

    }

    /**
     * Removes an item from the table. This is called VIA the property change handler of display frame based on success of removal and such.
     * @param modelIndex the index of the model to remove - NOT THE INDEX OF THE CURRENT VIEW!
     */
    public void removeFromTable(int modelIndex) {
        ((DefaultTableModel) entriesTable.getModel()).removeRow(modelIndex);
    }
}
