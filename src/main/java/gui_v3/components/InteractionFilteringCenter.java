package gui_v3.components;

import gui_v3.logic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;

/**
 * The center panel for filtering the entries dataset.
 * @version 1.0
 * @author Cade Reynoldson
 */
public class InteractionFilteringCenter extends JPanel {

    /** Property change support to notify parent display with of changes. */
    private PropertyChangeSupport pcs;

    public InteractionFilteringCenter(PropertyChangeSupport pcs) {
        super();
        this.pcs = pcs;
        initTheme();
        initComponents();
    }

    /**
     * Initializes the theme of the panel.
     */
    private void initTheme() {
        setBackground(ProgramColors.FOREGROUND_COLOR);
    }

    /**
     * Initializes the components of the panel.
     */
    private void initComponents() {
        if (RaffleDataStorage.hasEntriesFile()) { //Initialize checkboxes.....
            setLayout(new GridBagLayout());
            add(ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.FILTER_BRIEF_DESC), ProgramDimensions.FILTER_BRIEF_DESC_CONSTRAINTS);
            add(getCheckBoxPanel(), ProgramDimensions.FILTER_CHECKBOX_CONSTRAINTS);
        } else { //Show no entries warning.
            setLayout(new GridLayout(0, 1));
            add(ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.FILTER_BRIEF_DESC));
            add(ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.FILTER_NO_ENTRIES_L1));
            add(ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.FILTER_NO_ENTRIES_L2));
        }
    }

    /**
     * Creates a checkbox panel (2x2) with a list of selected strings to display, values that are supposed to be enabled, and an action listener for when these checkboxes are selected.
     * Automatically detects the unique value counts of each column.
     * @return a JPanel which displays the the checkboxes in a 2x2 format.
     */
    private JPanel getCheckBoxPanel() {
        JPanel mainPanel = ProgramDefaults.getBlankPanel(new GridLayout(0, 2));
        //Generate sub panel.
        String[] columnNames = RaffleDataStorage.getOriginalEntriesSheet().getColumnNames();
        HashSet<String> selectedValues = RaffleDataStorage.getFilterValues();
        mainPanel.add(ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.FILTER_COLUMN_PROMPT));
        mainPanel.add(ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.FILTER_UNIQUE_VALS));
        for (int i = 0; i < columnNames.length; i++) {
            JCheckBox checkBox = ProgramDefaults.getCheckBox(columnNames[i]);
            if (selectedValues.contains(columnNames[i]))
                checkBox.setSelected(true);
            checkBox.addActionListener(this::checkboxEvent);
            int uniqueVals = RaffleDataStorage.getOriginalEntriesSheet().getUniqueValueCounts(columnNames[i]);
            mainPanel.add(checkBox);
            mainPanel.add(ProgramDefaults.getCenterAlignedInteractionLabel(Integer.toString(uniqueVals)));
        }
        return mainPanel;
    }

    /**
     * Handles a checkbox action. Notifies display frame. Display frame handles the filtering action.
     * @param actionEvent the action event from the checkbox.
     */
    private void checkboxEvent(ActionEvent actionEvent) {
        if (actionEvent.getSource() instanceof JCheckBox) {
            String actionVal = ProgramStrings.checkboxStrToStr(((JCheckBox) actionEvent.getSource()).getText());
            pcs.firePropertyChange(PropertyChangeKeys.FILTER_ACTION, null, actionVal);
        }
    }
}
