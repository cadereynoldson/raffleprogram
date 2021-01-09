package gui_v3.components;

import gui_v3.logic.*;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeSupport;

public class InteractionEntriesCenter extends JPanel {
    private PropertyChangeSupport pcs;

    private JLabel loadedFileText;

    public InteractionEntriesCenter(PropertyChangeSupport pcs) {
        super();
        this.pcs = pcs;
        setBackground(ProgramColors.FOREGROUND_COLOR);
        initComponents();
    }

    public void setLoadedFileText(String text) {
        loadedFileText.setText(ProgramStrings.strToHTML("<u>" + text + "</u>"));
    }

    private void initComponents() {
        setLayout(new GridLayout(0, 1));
        JPanel selectedFilePanel = ProgramDefaults.getBlankPanel();
        JLabel fileLoadPrompt = ProgramDefaults.getRightAlignedInteractionLabel(ProgramStrings.ENTRIES_SELECTED_FILE_PROMPT);
        if (RaffleDataStorage.hasEntriesFile()) //If an entries file has been loaded previous to being in this page.
            loadedFileText = ProgramDefaults.getUnderlineLabelLarge(RaffleDataStorage.getEntriesFileString());
        else
            loadedFileText = ProgramDefaults.getUnderlineLabelLarge(ProgramStrings.ENTRIES_INFORMATION_FILE_STATUS_NO_FILE_LOADED);
        selectedFilePanel.setLayout(new GridLayout(1, 0));
        selectedFilePanel.add(fileLoadPrompt);
        selectedFilePanel.add(loadedFileText);
        //Add files
        add(ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.ENTRIES_BRIEF_DESCRIPTION));
        add(selectedFilePanel);
        add(getButtonPanel());
    }

    private JPanel getButtonPanel() {
        JPanel p = ProgramDefaults.getBlankPanel();
        p.setLayout(new GridLayout(1, 0));
        JButton selectButton = ProgramDefaults.getButton(ProgramStrings.ENTRIES_INFORMATION_BUTTON_SELECT_FILE);
        selectButton.addActionListener(event -> pcs.firePropertyChange(PropertyChangeKeys.LOAD_ENTRIES, null, null));
        JButton resetButton = ProgramDefaults.getButton(ProgramStrings.ENTRIES_INFORMATION_BUTTON_RESET_FILE);
        resetButton.addActionListener(event -> pcs.firePropertyChange(PropertyChangeKeys.RESET_ENTRIES, null, null));
        p.add(ProgramDefaults.createSpacedPanel(resetButton));
        p.add(ProgramDefaults.createSpacedPanel(selectButton));
        return p;
    }
}
