package subpanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import fadingComponents.FadeCapable;
import fadingComponents.FadingBorderedPanel;
import fadingComponents.FadingButton;
import fadingComponents.FadingComponent;
import fadingComponents.FadingLabel;
import fadingComponents.FadingScrollTable;
import fadingComponents.FadingTable;
import fadingComponents.FadingTimer;
import logic.ProgramPresets;
import logic.RaffleLogic;
import logic.TextStyles;
import main_structure.Row;
import main_structure.SpreadSheet;

public class ReviewEntriesPanel extends JPanel implements FadeCapable, FadingComponent, PropertyChangeListener {

    private RaffleLogic logic;
   
    private PropertyChangeSupport pcs; 
    
    private JPanel dataDisplay;
    
    private FadingScrollTable data;
    
    private FadingScrollTable winnerData;
    
    private FadingBorderedPanel description;
    
    private FadingButton removeWinner; 
    
    private FadingButton addAsWinner; 
    
    private FadingButton removeFromRaffle;
    
    private ControlPanelListener dataListener;

    private ControlPanelListener winnerListener; 
    
    private boolean showingWinners;
    
    private FadingTimer masterTimer; 
    
    private ArrayList<FadingComponent> fadingComponents;
    
    public ReviewEntriesPanel(RaffleLogic logic, PropertyChangeListener listener) {
        this.logic = logic;
        pcs = new PropertyChangeSupport(this);
        masterTimer = new FadingTimer(5, this, "ENTRIES");
        fadingComponents = new ArrayList<FadingComponent>();
        showingWinners = false; 
        init();
    }
    
    private void init() {
        setBackground(ProgramPresets.COLOR_BACKGROUND);
        setLayout(new BorderLayout());
        description = initDescription();
        addFadingComponent(description);
        initButtons();
        add(description, BorderLayout.NORTH);
        dataDisplay = ProgramPresets.createPanel();
        add(dataDisplay, BorderLayout.CENTER);
    }
    
    private void initButtons() {
        dataListener = new ControlPanelListener(false);
        winnerListener = new ControlPanelListener(true);
        addAsWinner = new FadingButton("SET AS WINNER", TextStyles.DEFAULT);
        removeFromRaffle = new FadingButton("REMOVE FROM RAFFLE", TextStyles.DEFAULT);
        removeWinner = new FadingButton("REMOVE WINNER", TextStyles.DEFAULT);
        removeWinner.addActionListener(winnerListener);
        removeFromRaffle.addActionListener(dataListener);
        addAsWinner.addActionListener(dataListener);
        addFadingComponent(addAsWinner, removeFromRaffle, removeWinner);
    }
    
    private FadingBorderedPanel initDescription() {
        FadingBorderedPanel p = new FadingBorderedPanel("DESCRIPTION");
        p.setLayout(new GridLayout(0, 1));
        FadingLabel line1 = new FadingLabel("REVIEW THE DATA OF YOUR RAFFLE.", TextStyles.BOLD);
        FadingLabel line2 = new FadingLabel("NOTE: THIS IS OPTIONAL!", TextStyles.DEFAULT);
        FadingLabel line3 = new FadingLabel("REMOVE ENTRIES MANUALLY OR SELECT THOSE SPECIAL ENTRIES AS WINNERS!", TextStyles.DEFAULT);
        FadingLabel line4 = new FadingLabel("WINNERS YOU SELECT WILL BE HIGHLIGHTED IN BLUE!", TextStyles.DEFAULT);
        p.add(line1);
        p.add(line2);
        p.add(line3);
        p.add(line4);
        addFadingComponent(line1, line2, line3, line4);
        return p;
    }
    
    /**
     * Updates the current displayed sheet according to the contained raffle logic object. 
     */
    public void updateCurrentSheet() {
        dataDisplay.removeAll();
        dataDisplay.setLayout(new BorderLayout());
        if (logic.getCurrentSheet() != null) { //If the current sheet isnt null.
            FadingTable table = new FadingTable(logic.getCurrentSheet(), JLabel.CENTER) {
                /** Serial ID. */
                private static final long serialVersionUID = 1411361650760918228L;
                
                /** Only allows for the editing of the count cell. */
                public boolean isCellEditable(int row, int column) {                
                    return false;
                };
            };
            data = new FadingScrollTable(table);
            dataDisplay.add(data);
            //Control panel stuff
            JPanel controlPanel = ProgramPresets.createPanel();
            controlPanel.setLayout(new GridLayout(1, 0));
            controlPanel.add(removeFromRaffle);
            controlPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            controlPanel.add(addAsWinner);
            dataDisplay.add(controlPanel, BorderLayout.NORTH);
            repaint();
        }
    }
    
    @Override
    public void setAlpha(float value) {
        // TODO Auto-generated method stub
        
    }

    /**
     * Adds a fading component to the panel. 
     * @param components the components to add to the panel. 
     */
    private void addFadingComponent(FadingComponent ... components) {
        for (FadingComponent c : components) {
            fadingComponents.add(c);
        }
    }
    
    /**
     * Removes an entry from the data table. 
     * @param index
     */
    private void removeEntry(int index) {
        Object[] entry = data.getTable().getRowAsArray(index);
        if (!logic.removeEntry(entry)) {
            ProgramPresets.displayError("THERE WAS AN ERROR REMOVING THE SELECTED ENTRY!", "ENTRY REMOVAL ERROR", this);
            return;
        }
        ((DefaultTableModel) data.getTable().getModel()).removeRow(index);
    }
    
    /**
     * Moves a winner from the main data display to the winner display. 
     * @param index the index to move from the main table to the winner display. 
     */
    private void addWinner(int index) {
        if (showingWinners) {
            winnerData.getTable().addRow(data.getTable(), index);
        } else {
            showingWinners = true;
            SpreadSheet newSheet = new SpreadSheet();
            newSheet.initColumns(logic.getOriginalSheet().getColumnNames());
            newSheet.addRow(new Row(data.getTable().getRowAsArray(index)));
            winnerData = new FadingScrollTable(new FadingTable(newSheet, JLabel.CENTER));
            winnerData.getTable().addRow(data.getTable(), index);
            masterTimer.fadeOut(); //Fade out the current no winners display.  
        }
    }
    
    /**
     * Removes a winner from the winner table.
     * @param index the index of the winner to remove. 
     */
    private void removeWinner(int index) {
        winnerData.getTable().removeRow(index);
        if (winnerData.getTable().getRowCount() == 0) {
            showingWinners = false;
            masterTimer.fadeOut();
        }
    }
    
    @Override
    public ArrayList<FadingComponent> getFadingComponents() {
        return fadingComponents;
    }
    
    private class ControlPanelListener implements ActionListener {

        /** Indicates if this listener is responsible for handling the winners panel or not. */
        private boolean winners; 
        
        /**
         * Creates a new instance of the action listener for handling events from the control panel
         * for either the main control panel or the winners control panel indicated by the winners field. 
         * @param winners indicates if this listener is responsible for winners or not (t/f)
         */
        public ControlPanelListener(boolean winners) {
            this.winners = winners; 
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
             if (winners) {
                 winnerTableAction(e);
             } else {
                 generalAction(e);
             }
        }
        
        private void generalAction(ActionEvent e) {
            if (e.getSource() == removeFromRaffle) {
                String confirmString = data.getTable().getRowAsString(data.getTable().getSelectedRow());
                if (data.getTable().getSelectedRow() != -1) {
                    int value = ProgramPresets.displayYesNoConfirm("ARE YOU SURE YOU WANT TO REMOVE THE ENTRY: \n" + confirmString + "?", "CONFIRM REMOVAL", dataDisplay);
                    if (value == JOptionPane.YES_OPTION) {
                        removeEntry(data.getTable().getSelectedRow());
                    }
                } else {
                    ProgramPresets.displayError("THERE IS NO ENTRY SELECTED TO REMOVE!", "NO ENTRY REMOVED!", dataDisplay);
                }
            } else if (e.getSource() == addAsWinner){
                String confirmString = data.getTable().getRowAsString(data.getTable().getSelectedRow());
                if (data.getTable().getSelectedRow() != -1) {
                    ProgramPresets.displayMessage("SUCCESSFULLY DESIGNATED " + confirmString + "\nAS A WINNER!", "NEW WINNER", dataDisplay);
                    addWinner(data.getTable().getSelectedRow());
                } else {
                    ProgramPresets.displayError("THERE IS NO ENTRY SELECTED TO DESIGNATE AS A WINNER!", "NO WINNER ADDED!", dataDisplay);
                }
            }
        }
        
        private void winnerTableAction(ActionEvent e) {
            
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == masterTimer) {
            if ((Float) evt.getNewValue() == 0f) {
                if (showingWinners) {
                    masterTimer.removeAll();
                    dataDisplay.removeAll();
                    dataDisplay.setLayout(new BorderLayout());
                    dataDisplay.add(data, BorderLayout.CENTER);
                    //dataDisplay.add(winnerData, BorderLayout.SOUTH);
                    masterTimer.addComponent(data, winnerData);
                    masterTimer.fadeIn();
                } else {
                    
                }
            }
        }
    }
    
}
