package gui_v3.BaseComponents;

import gui_v3.logic.ProgramColors;
import gui_v3.logic.ProgramDefaults;
import gui_v3.logic.ProgramStrings;

import javax.swing.*;
import java.awt.*;

public class DescriptionPanel extends JPanel {

    private JLabel descriptionTitle;
    private JTextArea descriptionText;


    public DescriptionPanel(String description) {
        this(ProgramStrings.DESCRIPTION_TITLE, description);
    }

    public DescriptionPanel(String title, String description) {
        super();
        descriptionTitle = ProgramDefaults.getCenteredTitle(title);
        descriptionText = ProgramDefaults.getDescriptionTextArea(description);
        initComponents();
    }

    /**
     * Initializes the components of the panel.
     */
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(ProgramColors.FOREGROUND_COLOR);
        setBorder(BorderFactory.createLineBorder(ProgramColors.TEXT_ON_FG_COLOR));
        add(descriptionTitle, BorderLayout.NORTH);
        add(descriptionText, BorderLayout.CENTER);
    }
}
