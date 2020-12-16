package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main_structure.RaffleSpecs;
import main_structure.SpreadSheet;

public class RunRafflePanel extends JPanel {
    
    /** Serial id. */
    private static final long serialVersionUID = -750450064748028302L;

    private static final JLabel TITLE = initTitle();
    
    private static final String DEFAULT_ITEM = "EMPTY";
    
    private JComboBox<String> selectRaffleEntries;
    
    private JComboBox<String> selectRaffleItems;
    
    private JComboBox<String> selectItemCount;
    
    private JButton runRaffle;
    
    private PropertyChangeSupport pcs; 
    
    public RunRafflePanel(PropertyChangeListener listener) {
        pcs = new PropertyChangeSupport(this);
        pcs.addPropertyChangeListener(listener);
        selectRaffleEntries = new JComboBox<String>();
        selectRaffleItems = new JComboBox<String>();
        selectItemCount = new JComboBox<String>();
        runRaffle = new JButton("RUN RAFFLE");
        init();
    }
    
    public void updateEntries(SpreadSheet s) {
        resetEntries();
        String[] colNames = s.getColumnNames();
        for (int i = 0; i < colNames.length; i++)
            selectRaffleEntries.addItem(colNames[i]);
    }
    
    public void updateItems(SpreadSheet s) {
        resetItems();
        String[] colNames = s.getColumnNames();
        for (int i = 0; i < colNames.length; i++) {
            selectRaffleItems.addItem(colNames[i]);
            selectItemCount.addItem(colNames[i]);
        }
    }
    
    
    public void reset() {
        resetItems();
        resetEntries();
    }
    
    public void resetItems() {
        selectRaffleItems.removeAllItems();
        selectItemCount.removeAllItems();
        selectRaffleItems.addItem(DEFAULT_ITEM);
        selectItemCount.addItem(DEFAULT_ITEM);
    }
    
    public void resetEntries() {
        selectRaffleEntries.removeAllItems();
        selectRaffleEntries.addItem(DEFAULT_ITEM);
    }
    
    private void runRaffle() {
        boolean val = ((String) selectRaffleEntries.getSelectedItem()).equals(DEFAULT_ITEM);
        val = val || ((String) selectRaffleItems.getSelectedItem()).equals(DEFAULT_ITEM);
        val = val || ((String) selectItemCount.getSelectedItem()).equals(DEFAULT_ITEM);
        if (val) { //If there are one or more default values, show error message. 
            JOptionPane.showMessageDialog(this, "One or more raffle values are still set to \"EMPTY\"!", "Error", JOptionPane.ERROR_MESSAGE);
        } else { //else, proceed with raffle. 
            pcs.firePropertyChange("RAFFLE", null, new RaffleSpecs((String) selectRaffleEntries.getSelectedItem(), (String) selectRaffleItems.getSelectedItem(), (String) selectItemCount.getSelectedItem())); 
        }
    }
    
    private void init() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(TITLE, BorderLayout.NORTH);
        selectRaffleEntries.addItem(DEFAULT_ITEM);
        selectRaffleEntries.setFont(MasterDisplay.miscFont);
        selectRaffleItems.addItem(DEFAULT_ITEM);
        selectRaffleItems.setFont(MasterDisplay.miscFont);
        selectItemCount.addItem(DEFAULT_ITEM);
        selectItemCount.setFont(MasterDisplay.miscFont);
        runRaffle.setFont(MasterDisplay.tabAndButtonFont);
        runRaffle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runRaffle();   
            }
        });
        JPanel panel = new JPanel(new GridLayout(1, 3));
        panel.setBackground(Color.WHITE);
        panel.add(initRaffleByPanel());
        panel.add(initItemCountPanel());
        panel.add(runRaffle);
        add(panel, BorderLayout.CENTER);
    }
    
    private JPanel initItemCountPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(MasterDisplay.getTitledBorder("SELECT ITEM COUNT COLUMN"));
        p.add(selectItemCount, BorderLayout.CENTER);
        return p;
    }
    
    private JPanel initRaffleByPanel() {
        JPanel p = new JPanel(new GridLayout(1, 2));
        p.setBackground(Color.WHITE);
        p.setBorder(MasterDisplay.getTitledBorder("SELECT RAFFLE BY"));
        JPanel p1 = new JPanel(new BorderLayout());
        p1.setBackground(Color.WHITE);
        p1.add(selectRaffleItems);
        p1.setBorder(MasterDisplay.getTitledBorder("ITEM COLUMN"));
        JPanel p2 = new JPanel(new BorderLayout());
        p2.setBackground(Color.WHITE);
        p2.add(selectRaffleEntries);
        p2.setBorder(MasterDisplay.getTitledBorder("ENTRY COLUMN"));
        p.add(p1);
        p.add(p2);
        return p;
    }
    
    private static JLabel initTitle() {
        JLabel l = new JLabel("RUN RAFFLE");
        l.setFont(MasterDisplay.titleFont);
        l.setHorizontalAlignment(SwingConstants.CENTER);
        return l;
    }
    
}
