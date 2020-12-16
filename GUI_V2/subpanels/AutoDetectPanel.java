package subpanels;

import java.awt.BorderLayout;
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

import fadingComponents.FadeCapable;
import fadingComponents.FadingBorderedPanel;
import fadingComponents.FadingButton;
import fadingComponents.FadingCheckBox;
import fadingComponents.FadingComponent;
import fadingComponents.FadingLabel;
import fadingComponents.FadingLabeledSlider;
import fadingComponents.FadingScrollTable;
import fadingComponents.FadingTable;
import fadingComponents.FadingTimer;
import logic.ProgramPresets;
import logic.RaffleLogic;
import logic.TextStyles;

public class AutoDetectPanel extends FadingBorderedPanel implements FadingComponent, FadeCapable, PropertyChangeListener {

    private ArrayList<FadingComponent> fadingComponents;
    
    private ArrayList<FadingCheckBox> detectedFilters; 
    
    private ArrayList<String> confirmedIdentifiers; 
    
    private PropertyChangeSupport pcs;
    
    private FadingScrollTable countInput;
    
    private RaffleLogic logic; 
    
    private FadingTimer currentTimer;
    
    private float alpha; 
    
    private boolean showingCountInput;
    
    public AutoDetectPanel(RaffleLogic logic, PropertyChangeListener listener) {
        super("DETECTED IDENTIFIERS");
        pcs = new PropertyChangeSupport(this);
        pcs.addPropertyChangeListener(listener);
        this.logic = logic;
        showingCountInput = false; 
        alpha = 1f;
        detectedFilters = new ArrayList<FadingCheckBox>();
        fadingComponents = new ArrayList<FadingComponent>();
        currentTimer = new FadingTimer(5, this, "AUTO");
        init();
    }
    
    private void init() {
        setLayout(new BorderLayout());
        autoDetect();
    }
    
    /**
     * Refreshes the components contained in this panel.
     * NOTE: This method can be computationally and space intensive!
     */
    public void autoDetect() {
        currentTimer.removeAll();
        fadingComponents.clear();
        removeAll();
        if (logic.getOriginalSheet() != null) {
            showingCountInput = false; 
            detectedFilters = new ArrayList<FadingCheckBox>();
            ArrayList<String> detected = logic.autoDetect();
            if (detected.isEmpty()) //If detected items is empty, set the failed detection panel. 
                setFailedDetectionPanel();
            else { //Otherwise, set the detected filters.
                setLayout(new BorderLayout());
                JPanel p = ProgramPresets.createPanel();
                p.setLayout(new GridLayout(0, 1));
                for (String s : detected) {
                    FadingCheckBox b = new FadingCheckBox(s, TextStyles.BOLD);
                    b.setSelected(true);
                    detectedFilters.add(b);
                    p.add(b);
                    addFadingComponent(b);
                    currentTimer.addComponent(b);
                }
                add(p, BorderLayout.CENTER);
                p = ProgramPresets.createPanel();
                FadingButton b = new FadingButton("CONFIRM IDENTIFIERS", TextStyles.DEFAULT);
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        confirmIdentifiers();
                    }
                });
                Dimension d = new Dimension(10, 0);
                p.add(Box.createRigidArea(d));
                p.add(b);
                p.add(Box.createRigidArea(d));
                add(p, BorderLayout.SOUTH);
                currentTimer.addComponent(b);
                pcs.firePropertyChange(RaffleControlPanel.NEXT_DISABLE_KEY, null, null);
                repaint();
            }
        }
    }
    
    /**
     * Notifies items panel if auto detection failed. 
     */
    private void setFailedDetectionPanel() {
        pcs.firePropertyChange("FAILED", null, null);
        setLayout(new GridLayout(0, 1));
        removeAll();
        FadingLabel l = new FadingLabel("THERE HAS BEEN AN ERROR IN AUTO DETECTING ITEMS!", TextStyles.TITLE);
        FadingLabel l2 = new FadingLabel("THERE ARE NO COLUMNS THAT CONTAIN " + logic.getDetectionThreshold() + " OR LESS UNIQUE VALUES.", TextStyles.LARGE);
        FadingLabel l3 = new FadingLabel("ADJUST SLIDER FOR THE NEW UNIQUE VALUE COUNT", TextStyles.LARGE);
        final FadingLabeledSlider slider = new FadingLabeledSlider(0, 100, logic.getDetectionThreshold(), TextStyles.DEFAULT);
        slider.setTickSpacing(5, 10);
        FadingButton retryDetectionButton = new FadingButton("RETRY DETECTION", TextStyles.BOLD);
        retryDetectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logic.setDetectionThreshold(slider.getSelectedValue());
                autoDetect();
            }
        });
        add(l);
        add(l2);
        add(l3);
        add(slider);
        Dimension d = new Dimension(10, 0);
        JPanel p = ProgramPresets.createPanel();
        p.add(Box.createRigidArea(d));
        p.add(retryDetectionButton);
        p.add(Box.createRigidArea(d));
        fadingComponents.clear();
        currentTimer.removeAll();
        add(p);
        addFadingComponent(l, l2, l3, slider, retryDetectionButton);
        currentTimer.addComponent(l, l2, l3, slider);
        JOptionPane.showMessageDialog(this, "There has been an error auto detecting the item identifiers!", "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Method for confirming the identifiers. Transitions to the count panel if more than one
     * identifier is confirmed. 
     */
    private void confirmIdentifiers() {
        confirmedIdentifiers = getIdentifiers();
        if (confirmedIdentifiers.isEmpty())
            ProgramPresets.displayError("There are no identifiers detected!\nSelect an identifier and try again.", "No Identifiers", this);
        else {//fade out current displayed. 
            showingCountInput = true; 
            currentTimer.fadeOut();
        }
    }
    
    /**
     * Resets the detected filters. 
     * @param filters
     */
    public void resetDetectedFilters(List<String> filters) {
        detectedFilters = new ArrayList<FadingCheckBox>();
        for (String s : filters) {
            detectedFilters.add(new FadingCheckBox(s, TextStyles.BOLD));
        }
    }
    
    public ArrayList<String> getIdentifiers() {
        ArrayList<String> confirmedValues = new ArrayList<String>();
        for (FadingCheckBox b : detectedFilters) {
            if (b.isSelected())
                confirmedValues.add(b.getText());
        }
        return confirmedValues;
    }
    
    @Override
    public void setAlpha(float value) {
        alpha = value;
        for (FadingComponent c : fadingComponents)
            c.setAlpha(alpha);
        repaint();
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
    
    @Override
    public ArrayList<FadingComponent> getFadingComponents() {
        return fadingComponents;
    }

    /**
     * Handle property changes from the current timer (among others if implemented)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == currentTimer) {
            float alpha = (Float) evt.getNewValue();
            if (alpha == 0) {
                if (showingCountInput) { //show the count input.
                    setCountPanel();
                } else { //Otherwise, show the confirm indicators panel. 
                    autoDetect();
                }
            }
        }
    }
    
    private void setCountPanel() {
        removeAll();
        setLayout(new BorderLayout());
        FadingTable table = new FadingTable(logic.createItemCountSheet(confirmedIdentifiers), JLabel.CENTER) {
            /** Serial ID. */
            private static final long serialVersionUID = 1411361650760918228L;
            
            /** Only allows for the editing of the count cell. */
            public boolean isCellEditable(int row, int column) {                
                if (column == confirmedIdentifiers.size())
                    return true;
                return false;
            };
        };
        countInput = new FadingScrollTable(table);
        FadingLabel l = new FadingLabel("FILL IN THE QUANTITY YOU HAVE OF EACH ITEM.", TextStyles.BOLD);
        add(l, BorderLayout.NORTH);
        add(countInput, BorderLayout.CENTER);
        FadingButton b = new FadingButton("CONFIRM QUANTITIES", TextStyles.DEFAULT);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmQuantities();
            }
        });
        FadingButton prev = new FadingButton("BACK TO SELECT IDENTIFIERS", TextStyles.DEFAULT);
        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showingCountInput = false;
                currentTimer.fadeOut(); 
            }
        });
        JPanel p = ProgramPresets.createPanel();
        p.setLayout(new GridLayout(1, 0));
        Dimension d = new Dimension(10, 0);
        p.add(prev);
        p.add(Box.createRigidArea(d));
        p.add(b);
        add(p, BorderLayout.SOUTH);
        currentTimer.addComponent(countInput, b, prev, l);
        currentTimer.fadeIn();
        showingCountInput = true; 
    }
    
    private void confirmQuantities() {
        FadingTable table = countInput.getTable();
        float[] counts = new float[table.getRowCount()]; 
        int column = table.getColumnCount() - 1; 
        for (int i = 0; i < counts.length; i++) {
            String val = table.getValueAt(i, column).toString().trim();
            try {
                counts[i] = Float.parseFloat(val);
                if (counts[i] == 0) {
                    int value = ProgramPresets.displayYesNoConfirm("You entered \"0\" for the quanitity in row " + (i + 1) + ".\nIs this accurate?", "Quantity Check", this);
                    if (value == JOptionPane.NO_OPTION || value == JOptionPane.CLOSED_OPTION)
                        return;
                }
                //Enable next button, notify raffle panel these things have completed. 
            } catch (NumberFormatException e) {
                ProgramPresets.displayError("There was an error fetching the quantity for row " + (i + 1) + ".\n" +
                "You entered: " + val + "\nPlease double check your entry. ", "Quantity Error", this);
                return;
            }
        }
        this.showingCountInput = true;
        pcs.firePropertyChange(RaffleControlPanel.NEXT_ENABLE_KEY, null, null);
    }
}
