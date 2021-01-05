package gui_v3.components;

import gui_v3.logic.RaffleDataStorage;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeSupport;

public class InteractionFilteringCenter extends JPanel {

    private PropertyChangeSupport pcs;

    public InteractionFilteringCenter(PropertyChangeSupport pcs) {
        this.pcs = pcs;
        initComponents();
    }

    private void initComponents() {
        if (RaffleDataStorage.hasEntriesFile()) { //Initialize checkboxes.....
            setLayout(new GridBagLayout());


        } else { //Show no entries warning.
            //TODO: SHOW NO ENTRIES WARNING.
        }
    }

    private void resetValues() {

    }
}
