package gui_v3.BaseComponents;

import gui_v3.logic.*;
import main_structure.SpreadSheet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/**
 * Serves as the center panel for auto detecting items to raffle.
 */
public class InteractionItemsCenter extends JPanel {

    /** Used to notify the main frame of property changes. */
    private PropertyChangeSupport pcs;

    /** The item navigation location displayed. */
    private NavigationLocations locationDisplayed;

    /** Checkbox for toggling auto detect. */
    private JCheckBox autoDetectToggle;

    /** JTable for auto detect part 2. */
    private JTable autoDetectTable;

    /**
     * Creates a new instance of the interaction items center panel.
     * @param pcs property change support to notify the the master display of changes.
     * @param location the original location to display for this step.
     */
    public InteractionItemsCenter(PropertyChangeSupport pcs, NavigationLocations location) {
        super();
        this.pcs = pcs;
        locationDisplayed = location;
        initAutoDetectToggle();
        initTheme();
        setLocationDisplayed();
    }

    /**
     * Initializes the auto detect toggle checkbox.
     */
    private void initAutoDetectToggle() {
        autoDetectToggle = ProgramDefaults.getCheckBox(ProgramStrings.ITEMS_AUTO_DETECT_TOGGLE);
        autoDetectToggle.setFont(ProgramFonts.DEFAULT_FONT_LARGE);
        if (locationDisplayed == NavigationLocations.ITEMS_AUTO_DETECT_PT1 || locationDisplayed == NavigationLocations.ITEMS_AUTO_DETECT_PT2)
            autoDetectToggle.setSelected(true);
        else
            autoDetectToggle.setSelected(false);
        autoDetectToggle.addActionListener(this::toggleAutoDetect);
    }

    /**
     * Handles auto detect toggle events.
     * @param actionEvent the action event of the source item.
     */
    private void toggleAutoDetect(ActionEvent actionEvent) {
        if (((JCheckBox) actionEvent.getSource()).isSelected()) {
            RaffleDataStorage.setAutoDetect(true);
            setAutoDetect_P1();
        } else {
            RaffleDataStorage.setAutoDetect(false);
            setManual_P1();
        }
        repaint();
    }

    /**
     * Initializes the components of this panel.
     */
    private void initTheme() {
        setBackground(ProgramColors.FOREGROUND_COLOR);
    }

    /**
     * Sets the location displayed of this panel. Location is ONLY changed via interacting with this panel! (No parameters)
     */
    private void setLocationDisplayed() {
        if (locationDisplayed == NavigationLocations.ITEMS_AUTO_DETECT_PT1) {
            setAutoDetect_P1();
        } else if (locationDisplayed == NavigationLocations.ITEMS_AUTO_DETECT_PT2) {
            setAutoDetect_P2();
        } else if (locationDisplayed == NavigationLocations.ITEMS_MANUAL_PT1) {
            setManual_P1();
        } else if (locationDisplayed == NavigationLocations.ITEMS_MANUAL_PT2) {
            setManual_P2();
        } else if (locationDisplayed == NavigationLocations.ITEMS_MANUAL_PT3) {
            setManual_P3();
        }
    }

    /**
     * Sets the location displayed by this panel.
     * @param newLocation the new location to display.
     */
    public void setLocationDisplayed(NavigationLocations newLocation) {
        locationDisplayed = newLocation;
        setLocationDisplayed();
    }

    /* AUTO DETECT DISPLAY METHODS *****************************************/

    /**
     * Sets the current displayed information to auto detect part 1.
     */
    private void setAutoDetect_P1() {
        removeAll();
        setLayout(new GridLayout(0, 1));
        RaffleDataStorage.getSelectedAutoDetectValues().clear(); //Clear selected values.
        add(ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.ITEMS_AD_BRIEF_DESCRIPTION_P1));
        if (!RaffleDataStorage.hasEntriesFile()) {
            add(autoDetectToggle);
            add(ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.ITEMS_AD_NO_ENTRIES_FILE));
        } else {
            ((Runnable)  ()-> { //Can take a second to compute. Run on separate thread.
                add(autoDetectToggle);
                add(getAutoDetectChecklistPanel());
                JButton contButton = ProgramDefaults.getButton(ProgramStrings.ITEMS_AD_CONTINUE_BUTTON);
                contButton.addActionListener(event -> { //Fire property change to navigate to items change part 2.
                    pcs.firePropertyChange(PropertyChangeKeys.ITEMS_NAV_CHANGE, null, NavigationLocations.ITEMS_AUTO_DETECT_PT2);
                });
                add(ProgramDefaults.createSpacedPanel(contButton, 100, 20));
            }).run();
        }
    }

    /**
     * Creates the checklist panel for selecting identifying features of raffle items.
     * @return a JPanel containing checkboxes.
     */
    private JPanel getAutoDetectChecklistPanel() {
        ArrayList<String> detectedValues = RaffleDataStorage.autoDetect();
        JPanel checklistPanel = ProgramDefaults.getBlankPanel();
        checklistPanel.setLayout(new GridLayout(0, 1));
        checklistPanel.add(ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.ITEMS_AD_DETECTED_PROMPT));
        int num2x2;
        if (detectedValues.size() % 2 == 0)
            num2x2 = detectedValues.size();
        else
            num2x2 = detectedValues.size() - 1;
        JPanel subPanel = ProgramDefaults.getBlankPanel();
        subPanel.setLayout(new GridLayout(1, 0));
        //Generate sub panel.
        for (int i = 0; i < num2x2; i++) {
            if (i != 0 && (i % 2 == 0)) { //For every two checkboxes added, add sub panel to the checklist panel, create new panel.
                checklistPanel.add(subPanel);
                subPanel = ProgramDefaults.getBlankPanel();
                subPanel.setLayout(new GridLayout(1, 0));
            }
            JCheckBox autoDetectOption = ProgramDefaults.getCheckBox(detectedValues.get(i));
            autoDetectOption.addActionListener(this::autoDetectOptionSelected);
            if (RaffleDataStorage.getSelectedAutoDetectValues().contains(autoDetectOption.getText()))
                autoDetectOption.setSelected(true);
            subPanel.add(autoDetectOption);
        }
        //Last panel has been generated. Add to checklist panel. Check for size being larger than one. Don't want to add blank panel.
        if (detectedValues.size() > 1)
            checklistPanel.add(subPanel);
        if (num2x2 != detectedValues.size()) { //If the 2x2 panel size is odd, add the last value in the list to checkbox panel.
            JCheckBox autoDetectOption = ProgramDefaults.getCheckBox(detectedValues.get(detectedValues.size() - 1));
            if (RaffleDataStorage.getSelectedAutoDetectValues().contains(autoDetectOption.getText()))
                autoDetectOption.setSelected(true);
            autoDetectOption.addActionListener(this::autoDetectOptionSelected);
            checklistPanel.add(autoDetectOption);
        }
        return checklistPanel;
    }

    /**
     * Handles an auto detect identifier being selected.
     * @param event the action event supplied by the checkbox.
     */
    private void autoDetectOptionSelected(ActionEvent event) {
        if (event.getSource() instanceof  JCheckBox) {
            if (((JCheckBox) event.getSource()).isSelected()) {
                RaffleDataStorage.addAutoDetectFilter(((JCheckBox) event.getSource()).getText());
            } else {
                RaffleDataStorage.removeAutoDetectFilter(((JCheckBox) event.getSource()).getText());
            }
        }
    }


    public void setAutoDetect_P2() {
        if (RaffleDataStorage.getSelectedAutoDetectValues().isEmpty()) {
            //TODO: Do error handling for setting this page. Jeez you really fucked up if this is where you got to.
        } else { //If there are selected auto detect values
            ((Runnable) () -> { //Compute calculation on separate thread.
                SpreadSheet s;
                if (RaffleDataStorage.hasItemsFile())
                    s = RaffleDataStorage.getItemsSheet();
                else
                    s = RaffleDataStorage.createItemCountSheet();
                autoDetectTable = ProgramDefaults.getTable(s, s.getNumColumns() - 1);
                setAutoDetect_P2_layout();
            }).run();
        }
    }

    /**
     * Applies layout changes for set auto detect part 2. Called after thread is completed.
     */
    private void setAutoDetect_P2_layout() {
        removeAll();
        setLayout(new GridBagLayout());
        add(ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.ITEMS_AD_BRIEF_DESCRIPTION_P2), ProgramDimensions.ITEMS_ADP2_BRIEF_DESC_CONSTRAINTS);
        JScrollPane scrollPane = ProgramDefaults.getTableScrollPane(autoDetectTable);
        add(scrollPane, ProgramDimensions.ITEMS_ADP2_TABLE_CONSTRAINTS);
        JButton back = ProgramDefaults.getButton(ProgramStrings.ITEMS_AD_BACK);
        back.addActionListener(event -> pcs.firePropertyChange(PropertyChangeKeys.ITEMS_NAV_CHANGE, null, NavigationLocations.ITEMS_AUTO_DETECT_PT1));
        JButton confirm = ProgramDefaults.getButton(ProgramStrings.ITEMS_AD_CONFIRM_QUANTITIES);
        confirm.addActionListener(this::confirmQuantities);
        add(ProgramDefaults.createSpacedPanel(back), ProgramDimensions.ITEMS_ADP2_BACK_CONSTRAINTS);
        add(ProgramDefaults.createSpacedPanel(confirm), ProgramDimensions.ITEMS_ADP2_CONFIRM_QUANTITIES);
        repaint();
    }

    /**
     * An action listener for confirming quantities of the raffle.
     * Will check for valid inputs (all integers) and for any inputs of zero.
     * @param actionEvent the action event. Not used in this method.
     */
    private void confirmQuantities(ActionEvent actionEvent) {
        //Check for valid quantities input!
        int quantitiesIndex = autoDetectTable.getColumn(ProgramStrings.QUANTITY_COLUMN_NAME).getModelIndex();
        int zeroCounts = 0;
        for (int i = 0; i < autoDetectTable.getRowCount(); i++) {
            try {
                if (Integer.parseInt(autoDetectTable.getValueAt(i, quantitiesIndex).toString()) == 0)
                    zeroCounts++;
            } catch (Exception e) {
                displayQuantitiesError();
                return;
            }
        }
        //If any zeros are present in the input column of the table, display a warning method.
        if (zeroCounts > 0)
            if (displayQuantitiesWarning() != JOptionPane.YES_OPTION)
                return;
        //All error handling completed. Create table and set values.
        ((Runnable) () -> {
            //Confirm that valid quantities are set for the program.
            Object[][] data = new Object[autoDetectTable.getRowCount()][autoDetectTable.getColumnCount()];
            for (int i = 0; i < autoDetectTable.getRowCount(); i++)
                for (int j = 0; j < autoDetectTable.getColumnCount(); j++)
                    data[i][j] = autoDetectTable.getValueAt(i, j).toString();
            String[] colNames = new String[autoDetectTable.getColumnCount()];
            for (int i = 0; i < colNames.length; i++)
                colNames[i] = autoDetectTable.getColumnName(i);
            RaffleDataStorage.setItemsSheet(data, colNames);
            displayQuantitiesConfirmed();
        }).run();
    }

    /**
     * Displays a message the the quantities have been confirmed!
     */
    private void displayQuantitiesConfirmed() {
        pcs.firePropertyChange(PropertyChangeKeys.ITEMS_AD_QUANTITIES_CONFIRMED, null, null);
        ProgramDefaults.displayMessage(ProgramStrings.DIALOGUE_ITEMS_AD_QUANTITIES_SUCCESS, ProgramStrings.DIALOGUE_SUCCESS_TITLE, this);
    }

    /**
     * Display a warning that there are zeros present in the inputted table.
     * @return an integer consistent with JOptionPane return values (Yes, no)
     */
    private int displayQuantitiesWarning() {
        return ProgramDefaults.displayYesNoConfirm(ProgramStrings.DIALOGUE_ITEMS_AD_QUANTITIES_WARNING, ProgramStrings.DIALOGUE_RESET_WARNING_TITLE, this);
    }

    /**
     * Display an error message letting the user know that there are one or more entries in the quantities column that are not integers.
     */
    private void displayQuantitiesError() {
        ProgramDefaults.displayError(ProgramStrings.DIALOGUE_ITEMS_AD_QUANTITIES_ERROR, ProgramStrings.DIALOGUE_LOAD_ERROR_TITLE, this);
    }

    /* MANUAL INPUT METHODS ********************************************************************************/

    private void setManual_P1() {

    }

    private void setManual_P2() {

    }

    private void setManual_P3() {

    }
}
