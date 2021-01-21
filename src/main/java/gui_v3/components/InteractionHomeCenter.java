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
        add(ProgramDefaults.getRaffleCheckList());
        add(getRaffleButton());
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
