package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main_structure.SpreadSheet;

/**
 * Allows for grouping of the datasheet for things like colorways. 
 * @author Cade Reynoldson.
 */
public class FilteringGroupBy extends JPanel {
    
    /** Serial ID. */
    private static final long serialVersionUID = -3299782240667992802L;

    /** Initializes the title of the panel. */
    public static final JPanel TITLE = initTitle();
   
    /** Generic name of the raffle items. */
    public static final String RAFFLE_ITEMS = "ITEMS";
    
    /** Generic name of the raffle entries. */
    public static final String RAFFLE_ENTRIES = "ENTRIES";
    
    /** Generic name of the raffle combo box. */
    public static final String DEFAULT_GROUP = "EMPTY";
    
    /** The entries to group by. */
    private JComboBox<String> groupByEntries;
    
    /** The items to group by. */
    private JComboBox<String> groupByItems;
    
    /** Button to press when ready to group. */
    private JButton groupButton;
    
    /** Property change support for notifying the raffle panel. */
    private PropertyChangeSupport pcs;
    
    /**
     * Creates a new filtering group by panel. 
     * @param listener the property change listener to notify changes with. 
     */
    public FilteringGroupBy(PropertyChangeListener listener) {
        pcs = new PropertyChangeSupport(listener);
        pcs.addPropertyChangeListener(listener);
        groupByEntries = new JComboBox<String>();
        groupByItems = new JComboBox<String>();
        groupButton = new JButton("GROUP");
        init();
    }
    
    /**
     * Initializes the panel. 
     */
    private void init() {
        this.setLayout(new GridLayout(4, 1));
        add(TITLE);
        JPanel entries = new JPanel();
        JPanel items = new JPanel();
        groupByEntries.addItem(DEFAULT_GROUP);
        groupByItems.addItem(DEFAULT_GROUP);
        groupByEntries.setFont(MasterDisplay.miscFont);
        groupByItems.setFont(MasterDisplay.miscFont);
        items.setLayout(new BorderLayout());
        entries.setLayout(new BorderLayout());
        items.add(groupByItems, BorderLayout.CENTER);
        entries.add(groupByEntries, BorderLayout.CENTER);
        entries.setBackground(Color.WHITE);
        entries.setBorder(MasterDisplay.getTitledBorder(RAFFLE_ENTRIES));
        items.setBackground(Color.WHITE);
        items.setBorder(MasterDisplay.getTitledBorder(RAFFLE_ITEMS));
        initGroupButton();
        this.add(items);
        this.add(entries);
        this.add(groupButton);
        this.setBackground(Color.WHITE);
        setBorder(MasterDisplay.getTitledBorder("GROUPING"));
    }
    
    /**
     * Initializes the group button.
     */
    private void initGroupButton() {
        groupButton.setFont(MasterDisplay.tabAndButtonFont);
        groupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((String) groupByEntries.getSelectedItem()).equals(DEFAULT_GROUP) || ((String) groupByItems.getSelectedItem()).equals(DEFAULT_GROUP)) {
                    JOptionPane.showMessageDialog(null, "One or more groups has the default value selected!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    pcs.firePropertyChange("GROUP", groupByItems.getSelectedItem(), groupByEntries.getSelectedItem());
                }
            }
        });
    }
    
    /**
     * Updates the entires combo box.
     * @param s the spreadsheet to put into the entires combo box.
     */
    public void updateEntries(SpreadSheet s) {
        resetEntries();
        String[] names = s.getColumnNames();
        for (int i = 0; i < names.length; i++) {
            groupByEntries.addItem(names[i]);
        }
        repaint();
    }
    
    /**
     * Updates the items in the items combo box. 
     * @param s the spreadsheet to put into the items combo box. 
     */
    public void updateItems(SpreadSheet s) {
        resetItems();
        String[] names = s.getColumnNames();
        for (int i = 0; i < names.length; i++) {
            groupByItems.addItem(names[i]);
        }
        repaint();
    }
    
    /**
     * Resets all items in this panel. 
     */
    public void reset() {
        resetEntries();
        resetItems();
    }
    
    /**
     * Resets the entries combo box. 
     */
    public void resetEntries() {
        groupByEntries.removeAllItems();
        groupByEntries.addItem(DEFAULT_GROUP);
    }
    
    /**
     * Resets the items combo box. 
     */
    public void resetItems() {
        groupByItems.removeAllItems();
        groupByItems.addItem(DEFAULT_GROUP);
    }
    
    /**
     * Initializes the title of the JPanel. 
     * @return the panel containing the title. 
     */
    private static JPanel initTitle() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2, 1));
        JLabel l2 = new JLabel("USE FOR MULTIPLE COLORWAYS");
        JLabel l3 = new JLabel("GROUP BY: ");
        l2.setFont(MasterDisplay.tabAndButtonFont);
        l3.setFont(MasterDisplay.tabAndButtonFont);
        l2.setHorizontalAlignment(SwingConstants.CENTER);
        l3.setHorizontalAlignment(SwingConstants.CENTER);
        p.add(l2);
        p.add(l3);
        p.setBackground(Color.WHITE);
        return p;
    }
}
