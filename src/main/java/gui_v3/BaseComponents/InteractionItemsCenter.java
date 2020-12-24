package gui_v3.BaseComponents;

import gui_v3.logic.*;

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

    private JCheckBox autoDetectToggle;

    public InteractionItemsCenter(PropertyChangeSupport pcs, NavigationLocations location) {
        super();
        this.pcs = pcs;
        locationDisplayed = location;
        initAutoDetectToggle();
        initTheme();
        setLocationDisplayed();
    }

    private void initAutoDetectToggle() {
        autoDetectToggle = ProgramDefaults.getCheckBox(ProgramStrings.ITEMS_AUTO_DETECT_TOGGLE);
        autoDetectToggle.setFont(ProgramFonts.DEFAULT_FONT_LARGE);
        if (locationDisplayed == NavigationLocations.ITEMS_AUTO_DETECT_PT1 || locationDisplayed == NavigationLocations.ITEMS_AUTO_DETECT_PT2)
            autoDetectToggle.setSelected(true);
        else
            autoDetectToggle.setSelected(false);
        autoDetectToggle.addActionListener(this::toggleAutoDetect);
    }

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

    private void setAutoDetect_P1() {
        removeAll();
        setLayout(new GridLayout(0, 1));
        add(ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.ITEMS_AD_BRIEF_DESCRIPTION_P1));
        add(autoDetectToggle);
        if (!RaffleDataStorage.hasEntriesFile()) {
            //TODO: No entries file, blah blah blah
        } else {
            add(getAutoDetectChecklistPanel());
            JButton contButton = ProgramDefaults.getButton(ProgramStrings.ITEMS_AD_CONTINUE_BUTTON);
            contButton.addActionListener(event -> { //Fire property change to navigate to items change part 2.
                pcs.firePropertyChange(PropertyChangeKeys.ITEMS_NAV_CHANGE, null, NavigationLocations.ITEMS_AUTO_DETECT_PT2);
            });
            add(ProgramDefaults.createSpacedPanel(contButton, 100, 20));
        }
    }

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

    private void autoDetectOptionSelected(ActionEvent event) {
        if (((JCheckBox) event.getSource()).isSelected()) {
            RaffleDataStorage.addAutoDetectFilter(((JCheckBox) event.getSource()).getText());
        } else {
            RaffleDataStorage.removeAutoDetectFilter(((JCheckBox) event.getSource()).getText());
        }
    }


    private void setAutoDetect_P2() {

    }

    private void setManual_P1() {

    }

    private void setManual_P2() {

    }

    private void setManual_P3() {

    }
}
