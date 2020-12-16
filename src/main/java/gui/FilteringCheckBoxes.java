package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main_structure.SpreadSheet;

/**
 * Panel for filtering with checkboxes. 
 * @author Cade Reynoldson
 */
public class FilteringCheckBoxes extends JPanel {
    
    /** SerialID */
    private static final long serialVersionUID = 5704784366325713243L;
    
    /** Placeholder for when no data exists. */
    private static final JLabel PLACEHOLDER = initPlaceHolder();
    
    /** The list of JCheckBoxes being displayed. */
    private ArrayList<JCheckBox> boxes;
    
    /** Displays the total numbers of the filtering conducted. */
    private JLabel totalRemovedLabel;
    
    /** The total number of items removed from filtering. */
    private int totalRemoved;
    
    /** The total number of entries in the current displayed spreadsheet. */
    private int totalEntries;
    
    /** Reset button for resetting the checkboxes to their original states (unchecked) */
    private JButton resetButton;
    
    /** Property change support for notifying the raffle panel. */
    private PropertyChangeSupport pcs;
    
    /**
     * Creates a new instance of the filtering checkboxes panel. 
     * @param sheet the sheet to convert to check boxes. 
     */
    public FilteringCheckBoxes(PropertyChangeListener listener) {
        pcs = new PropertyChangeSupport(this);
        pcs.addPropertyChangeListener(listener);
        boxes = new ArrayList<JCheckBox>();
        setLayout(new GridLayout(1, 1));
        setBorder(MasterDisplay.getTitledBorder("CHECK TO REMOVE DUPLICATES"));
        add(PLACEHOLDER);
        this.setBackground(Color.WHITE);
        initResetButton();
        initTotalRemoved();
        totalRemoved = 0;
    }
    
    /**
     * Updates all of the checkboxes to a new spreadsheet. 
     * @param sheet the sheet to update the checkboxes to.
     */
    public void updateCheckBoxes(SpreadSheet sheet) {
        this.removeAll();
        boxes.clear();
        setLayout(new GridLayout(sheet.getNumColumns() + 2, 1));
        initCheckBoxes(sheet);
        totalRemoved = 0;
        totalEntries = sheet.getNumRows();
        totalRemovedLabel.setText("TOTAL ENTRIES: " +  totalEntries + " - TOTAL REMOVED: " + totalRemoved + " - FINAL TOTAL: " + (totalEntries - totalRemoved));
        add(totalRemovedLabel);
        add(resetButton);
    }
    
    /**
     * Resets all of the values in this panel. 
     * Gets rid of the current displayed checkboxes. 
     */
    public void reset() {
        this.removeAll();
        boxes.clear();
        setLayout(new GridLayout(1, 1));
        add(PLACEHOLDER);
    }
    
    /**
     * Increments the total amount removed. 
     * @param amt the total amount removed. 
     */
    public void addTotalRemoved(int amt) {
        totalRemoved += amt;
        totalRemovedLabel.setText("TOTAL ENTRIES: " +  totalEntries + " - TOTAL REMOVED: " + totalRemoved + " - FINAL TOTAL: " + (totalEntries - totalRemoved)); 
    }
    
    /**
     * Returns a list of column names which are selected for individual filtering. 
     * @return a list of column names which are selected for individual filtering. 
     */
    public ArrayList<String> getSelected() {
        ArrayList<String> selected = new ArrayList<String>();
        for (JCheckBox box : boxes) 
            if (box.isSelected())
                selected.add(box.getName());
        return selected; 
    }
    
    /**
     * Initializes the check boxes to be displayed. 
     * @param sheet the sheet to display as check boxes. 
     */
    private void initCheckBoxes(SpreadSheet sheet) {
        String[] columnNames = sheet.getColumnNames();
        for (int i = 0; i < columnNames.length; i++) {
            final JCheckBox current = new JCheckBox(columnNames[i]);
            current.setHorizontalAlignment(SwingConstants.CENTER);
            boxes.add(current);
            current.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pcs.firePropertyChange("FILTER", null, current.getText());
                    current.setEnabled(false);
                }
            });
            this.add(current);
        }
    }
    
    /**
     * Initializes the total removed label. 
     */
    private void initTotalRemoved() {
        totalRemovedLabel = new JLabel();
        totalRemovedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalRemovedLabel.setFont(MasterDisplay.tabAndButtonFont);
    }
    
    /**
     * Initializes the reset button.
     */
    private void initResetButton() {
        resetButton = new JButton();
        resetButton.setText("RESET");
        resetButton.setFont(MasterDisplay.tabAndButtonFont);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetButtonPressed();
            }
        });
    }
    
    /**
     * Conducts the action of the reset button. 
     */
    private void resetButtonPressed() {
        int option = JOptionPane.showConfirmDialog(this, "This will undo any previous groupings and duplipate removals!\nAre you sure you want to continue?", 
                "Reset", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            for (JCheckBox box : boxes) {
                box.setSelected(false);
                box.setEnabled(true);
            }
            pcs.firePropertyChange("RESET", null, null);
            totalRemoved = 0;
            totalRemovedLabel.setText("TOTAL REMOVED: " + totalRemoved);
        } 
    }
    
    /**
     * Initializes the placeholder value "NO DATA" JLabel. 
     * @return the placeholder JLabel. 
     */
    private static JLabel initPlaceHolder() {
        JLabel l = new JLabel("NO DATA!");
        l.setHorizontalAlignment(SwingConstants.CENTER);
        l.setFont(MasterDisplay.tabAndButtonFont);
        return l;
    }
}
