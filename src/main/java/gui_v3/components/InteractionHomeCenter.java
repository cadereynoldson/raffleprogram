package gui_v3.components;

import gui_v3.logic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeSupport;

/**
 * Serves as the center of the interaction panel for the home page.
 */
public class InteractionHomeCenter extends JPanel {

    private PropertyChangeSupport pcs;
    public InteractionHomeCenter(PropertyChangeSupport pcs) {
        this.pcs = pcs;
        setBackground(ProgramColors.FOREGROUND_COLOR);
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(0, 1));
        add(ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.INTERACTION_HOME_BRIEF_DESCRIPTION));
        add(getCheckList());
        add(getRaffleButton());
    }

    /**
     * Creates the "Check list" section of this panel. Will display what necessary steps the user
     * has conducted to run their raffle.
     * @return
     */
    private JPanel getCheckList() {
        JPanel checklistPanel = ProgramDefaults.getBlankPanel();
        checklistPanel.setLayout(new BorderLayout());
        JLabel checkListLabel = ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.HOME_CHECKLIST_TITLE);
        JLabel entriesImage, itemsImage, filterImage;
        //Entries image.
        if (RaffleDataStorage.hasEntriesFile())
            entriesImage = new JLabel(ProgramDefaults.getCheckIcon());
        else
            entriesImage = new JLabel(ProgramDefaults.getXIcon());
        if (RaffleDataStorage.hasItems())
            itemsImage = new JLabel(ProgramDefaults.getCheckIcon());
        else
            itemsImage = new JLabel(ProgramDefaults.getXIcon());
        if (RaffleDataStorage.hasFiltered())
            filterImage = new JLabel(ProgramDefaults.getCheckIcon());
        else
            filterImage = new JLabel(ProgramDefaults.getXIcon());
        entriesImage.setHorizontalAlignment(SwingConstants.CENTER);
        itemsImage.setHorizontalAlignment(SwingConstants.CENTER);
        filterImage.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel entriesText = ProgramDefaults.getLeftAlignedInteractionLabel(ProgramStrings.HOME_CHECKLIST_ENTRIES);
        JLabel itemsText = ProgramDefaults.getLeftAlignedInteractionLabel(ProgramStrings.HOME_CHECKLIST_ITEMS);
        JLabel filterText =  ProgramDefaults.getLeftAlignedInteractionLabel(ProgramStrings.HOME_CHECKLIST_FILTER);
        JPanel checklistItemsContainer = ProgramDefaults.getBlankPanel();
        checklistItemsContainer.setLayout(new GridLayout(0, 2));
        checklistItemsContainer.add(entriesImage);
        checklistItemsContainer.add(entriesText);
        checklistItemsContainer.add(itemsImage);
        checklistItemsContainer.add(itemsText);
        checklistItemsContainer.add(filterImage);
        checklistItemsContainer.add(filterText);
        checklistPanel.add(checkListLabel, BorderLayout.NORTH);
        checklistPanel.add(checklistItemsContainer, BorderLayout.CENTER);
        return checklistPanel;
    }

    private JPanel getRaffleButton() {
        JButton b = ProgramDefaults.getButton(ProgramStrings.HOME_RAFFLE_BUTTON);
        b.addActionListener(this::raffleButtonClicked);
        JPanel p = ProgramDefaults.createSpacedPanel(b);
        return p;
    }

    private void raffleButtonClicked(ActionEvent actionEvent) {
        pcs.firePropertyChange(PropertyChangeKeys.RUN_RAFFLE, null, null);
    }


}
