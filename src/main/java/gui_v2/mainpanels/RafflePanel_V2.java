package gui_v2.mainpanels;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.List;

import javax.swing.JPanel;

import gui_v2.fadingComponents.FadeCapable;
import gui_v2.fadingComponents.FadingComponent;
import gui_v2.fadingComponents.FadingTimer;
import gui_v2.logic.ProgramPresets;
import gui_v2.logic.RaffleLogic;
import gui_v2.subpanels.FilePanel;
import gui_v2.subpanels.FilteringPanel;
import gui_v2.subpanels.ItemsPanel;
import gui_v2.subpanels.RaffleControlPanel;
import gui_v2.subpanels.ReviewEntriesPanel;

public class RafflePanel_V2 extends JPanel implements PropertyChangeListener {
    
    /** Serial ID. */
    private static final long serialVersionUID = 1698243191834703746L;

    private PropertyChangeSupport pcs;
    
    private FadingTimer masterTimer; 
    
    private int currentIndex; 
    
    private JPanel[] panels;
    
    private JPanel centerPanel;
    
    
    private JPanel currentPanel;
    
    private RaffleControlPanel controlPanel;
    
    private FilePanel filePanel;
    
    private FilteringPanel filteringPanel;
    
    private ItemsPanel itemsPanel; 
    
    private ReviewEntriesPanel entriesPanel; 
    
    private RaffleLogic logic; 
    
    /**
     * Creates a new instance of the raffle panel. 
     * @param listener the listener to fire property changes to. 
     * @param logic the gui_v2.logic behind the panel.
     */
    public RafflePanel_V2(PropertyChangeListener listener, RaffleLogic logic) {
        pcs = new PropertyChangeSupport(this);
        pcs.addPropertyChangeListener(listener);
        masterTimer = new FadingTimer(5, this, "MASTER");
        currentIndex = 0; 
        this.logic = logic;
        init();
    }
    
    private void init() {
        setLayout(new BorderLayout());
        setBackground(ProgramPresets.COLOR_BACKGROUND);
        controlPanel = new RaffleControlPanel(this);
        filePanel = new FilePanel(this);
        filteringPanel = new FilteringPanel(this);
        itemsPanel = new ItemsPanel(logic, this);
        entriesPanel = new ReviewEntriesPanel(logic, this);
        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        currentPanel = filePanel;
        panels = new JPanel[4];
        panels[0] = filePanel;
        panels[1] = filteringPanel;
        panels[2] = itemsPanel;
        panels[3] = entriesPanel;
        centerPanel.add(currentPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);
        masterTimer.setComponents(((FadeCapable) currentPanel).getFadingComponents());
        masterTimer.addComponent((FadingComponent) currentPanel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String key = evt.getPropertyName();
        if (evt.getSource() == controlPanel) {
            if (key.equals("PREVIOUS"))
                previousPanel();
            else if (key.equals("NEXT")) 
                nextPanel();
        } else if (evt.getSource() == masterTimer) { //Check fade values if from current panel. 
            float alpha = (Float) evt.getNewValue();
            if (alpha == 0)
                setPanel(false);
        } else if (evt.getSource() == filePanel) {
            filePanelChange(evt);
        } else if (evt.getSource() == filteringPanel) {
            filteringPanelChange(evt);
        } else if (evt.getSource() == itemsPanel) {
            itemsPanelChange(evt);
        }
    }
    
    private void filteringPanelChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(RaffleLogic.FILTER_KEY)) {
            Object filterBy = evt.getNewValue();
            if (filterBy instanceof String) 
                logic.filter((String) filterBy);
            else if (filterBy instanceof List<?>)
                logic.filter((List<?>) filterBy);
            filteringPanel.setRemoved(logic.getOriginalSheet().getNumRows() - logic.getCurrentSheet().getNumRows());
        }
    }
    
    private void filePanelChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(RaffleLogic.LOAD_ENTRIES_KEY) && evt.getNewValue() instanceof File) {
            handleNewEntries((File) evt.getNewValue());
        } else if (evt.getPropertyName().equals(RaffleLogic.LOAD_ITEMS_KEY) && evt.getNewValue() instanceof File) {
            handleNewItems((File) evt.getNewValue());
        } else if (evt.getPropertyName().equals(RaffleLogic.GENERATE_OUTPUT_KEY)) {
            logic.setGenerateOutput((Boolean) evt.getNewValue());
        } else if (evt.getPropertyName().equals(RaffleLogic.AUTO_DETECT_KEY)) {
            logic.setAutoDetect((Boolean) evt.getNewValue());
        }
        if (logic.hasNecessaryFiles()) {
            controlPanel.enableNext();
        } else {
            controlPanel.disableNext();
        }
        controlPanel.updateProgress(logic.getProgress());
    }
    
    private void itemsPanelChange(PropertyChangeEvent evt) {
        if (controlPanel.changeButtonState(evt.getPropertyName())) //Check if button should be enabled or disabled. 
            return; //if control panel was changed, return. 
    }
    
    private void previousPanel() {
        if (currentIndex == panels.length - 1)
            controlPanel.enableNext();
        if (currentIndex - 1 == 0)
            controlPanel.disablePrevious();
        currentIndex--;
        if (currentPanel instanceof FadeCapable && currentPanel instanceof FadingComponent) { //If current panel is capable of fading out. 
            masterTimer.fadeOut();
        } else { //Else, manually switch. 
            JPanel temp = panels[currentIndex];
            if (temp instanceof FadeCapable && temp instanceof FadingComponent) //if the next panel is capable of fading in. 
                setPanel(true);
            else {
                centerPanel.add(temp, BorderLayout.CENTER);
                currentPanel = temp;
            }
            repaint();
        }
    }
    
    private void nextPanel() {
        if (currentIndex == 0)
            controlPanel.enablePrevious();
        if (currentIndex  + 1 == panels.length - 1) 
            controlPanel.disableNext();
        currentIndex++;
        if (currentPanel instanceof FadeCapable && currentPanel instanceof FadingComponent) { //If the current panel is capable of fading out. 
            masterTimer.fadeOut();
        } else {
            JPanel temp = panels[currentIndex];
            if (temp instanceof FadeCapable && temp instanceof FadingComponent) //if the next panel is capable of fading in. 
                setPanel(true);
            else {
                centerPanel.add(temp, BorderLayout.CENTER);
                currentPanel = temp;
            }
            repaint();
        }
    }
    
    /**
     * Sets the current panel according to it's index. 
     * @param hasRemoved indicates if the center panel has removed the current panel. 
     */
    private void setPanel(boolean hasRemoved) {
        if (!hasRemoved)
            centerPanel.remove(currentPanel);
        currentPanel = panels[currentIndex];
        centerPanel.add(currentPanel, BorderLayout.CENTER);
        if (currentPanel == entriesPanel)
            entriesPanel.updateCurrentSheet();
        masterTimer.removeAll();
        if (currentPanel instanceof FadeCapable && currentPanel instanceof FadingComponent) { //If the current panel to display is capable of fading. 
            masterTimer.setComponents(((FadeCapable) currentPanel).getFadingComponents());
            masterTimer.addComponent((FadingComponent) currentPanel);
            masterTimer.fadeIn();
        }
        revalidate();
        repaint();
    }
    
    private void handleNewEntries(File f) {
        try {
            logic.setEntriesSheet(f);
            filteringPanel.setFilters(logic.getOriginalSheet());
            itemsPanel.updateLogic();
            entriesPanel.updateCurrentSheet();
        } catch (IllegalArgumentException e) {
            filePanel.resetEntries();
            ProgramPresets.displayError("An error occurred while loading in the selected entries file:\n" + FilePanel.getShortFileName(f), "Error Loading File", this);
        }
    }
    
    private void handleNewItems(File f) {
        try {
            logic.setItemsSheet(f);
            itemsPanel.updateLogic();
        } catch (IllegalArgumentException e) {
            filePanel.resetItems();
            ProgramPresets.displayError("An error occurred while loading in the selected items file:\n" + FilePanel.getShortFileName(f), "Error Loading File", this);
        }
    }
}
