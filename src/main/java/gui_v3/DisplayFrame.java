package gui_v3;

import gui_v3.components.*;
import gui_v3.logic.*;
import main_structure.Particle;
import main_structure.SpreadSheet;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.HashMap;

/**
 * Display Frame. Acts as the main JFrame for displaying the application.
 */
public class DisplayFrame extends JFrame {

    /** The current location being displayed in the frame. */
    private NavigationLocations currentLocation;

    /** The last ITEM location displayed. */
    private NavigationLocations lastItemLocation;

    /** The las RUN RAFFLE location displayed. */
    private NavigationLocations lastRunRaffleLocation;

    /** The description panel. Displays an in-depth description about the tab the user is on. */
    private DescriptionPanel descriptionPanel;

    /** The interaction panel. Acts as the "main" panel the user will interact with in the program. */
    private InteractionPanel interactionPanel;

    /** The information panel. Acts as a panel for displaying information about the interaction panel (Stats, status, etc.) */
    private InformationPanel informationPanel;

    /** The side tab menu. Acts as the main navigation tool of the program. */
    private VerticalTabs tabs;

    public DisplayFrame() {
        super();
        setIconImage(ProgramDefaults.getRaffleTicketIcon().getImage());
        setTitle("Raffle By Cade");
        initComponents();
        initLayout();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = 0;
                if (RaffleDataStorage.hasWinners() && !RaffleDataStorage.hasWrittenToFile()) //Show warning that they haven't saved winners.
                    confirm = ProgramDefaults.displayYesNoConfirm("You haven't saved your generated raffle winners!\nAre you sure you want to exit?", "Warning", null);
                if (confirm == 0)
                    System.exit(0);
            }
        };
        addWindowListener(exitListener);
        getContentPane().setBackground(ProgramColors.BACKGROUND_COLOR);
        setSize(ProgramDimensions.DEFAULT_SCREEN_SIZE);
        descriptionPanel = new DescriptionPanel();
        interactionPanel = new InteractionPanel(this::handleInteractionEvent);
        informationPanel = new InformationPanel();
        tabs = new VerticalTabs(this::handleTabNavigationEvent);
        currentLocation = NavigationLocations.HOME;
        lastItemLocation = NavigationLocations.ITEMS_AUTO_DETECT_PT1;
        lastRunRaffleLocation = NavigationLocations.RUN_RAFFLE_REVIEW;
    }

    private void initLayout() {
        setLayout(new GridBagLayout());
        add(descriptionPanel, ProgramDimensions.DESCRIPTION_CONSTRAINTS);
        add(tabs, ProgramDimensions.TOOLBAR_CONSTRAINTS);
        add(interactionPanel, ProgramDimensions.INTERACTION_CONSTRAINTS);
        add(informationPanel, ProgramDimensions.INFORMATION_CONSTRAINTS);
        repaint();
    }

    private void handleInteractionEvent(PropertyChangeEvent propertyChangeEvent) {
        String propertyValue = propertyChangeEvent.getPropertyName();
        if (propertyValue.equals(PropertyChangeKeys.RUN_RAFFLE)) { //If the user has decided to run the raffle.
            runRaffle();
        } else if (propertyValue.equals(PropertyChangeKeys.LOAD_ENTRIES)) {
            loadEntries();
        } else if (propertyValue.equals(PropertyChangeKeys.RESET_ENTRIES)) {
            resetEntries();
        } else if (propertyValue.equals(PropertyChangeKeys.ITEMS_NAV_CHANGE)) {
            NavigationLocations placeholder = (NavigationLocations) propertyChangeEvent.getNewValue();
            if (placeholder == NavigationLocations.ITEMS_AUTO_DETECT_PT2 || placeholder == NavigationLocations.ITEMS_AUTO_DETECT_PT1) { //Check for auto detect nav.
                handleItemsADNavigation(placeholder);
            } else { //Handle manual navigation.
                handleItemsManualNavigation(placeholder);
            }
        } else if (propertyValue.equals(PropertyChangeKeys.ITEMS_AD_QUANTITIES_CONFIRMED)) { //If quantities are confirmed, update information panel.
            informationPanel.setLoadItems_autoDetect_pt2();
        } else if (propertyValue.equals(PropertyChangeKeys.RESET_ITEMS)) {
            resetItems();
        } else if (propertyValue.equals(PropertyChangeKeys.LOAD_ITEMS)) {
            loadItems();
        } else if (propertyValue.equals(PropertyChangeKeys.ITEMS_INFO_SET)) {
            validateManualInput((Boolean) propertyChangeEvent.getOldValue(), (String) propertyChangeEvent.getNewValue());
        } else if (propertyValue.equals(PropertyChangeKeys.FILTER_ACTION)) {
            filterAction((String) propertyChangeEvent.getNewValue());
        } else if (propertyValue.equals(PropertyChangeKeys.REMOVE_ENTRY)) {
            raffleRemoveEntry((Integer) propertyChangeEvent.getNewValue());
        } else if (propertyValue.equals(PropertyChangeKeys.SET_AS_WINNER)) {
            raffleSetAsWinner((Integer) propertyChangeEvent.getNewValue());
        } else if (propertyValue.equals(PropertyChangeKeys.UNDO_RAFFLE)) {
            undoRaffle();
        } else if (propertyValue.equals(PropertyChangeKeys.REMOVE_WINNER)) {
            if (propertyChangeEvent.getNewValue() instanceof Integer && propertyChangeEvent.getOldValue() instanceof HashMap)
                    removeWinner((HashMap<String, Particle>) propertyChangeEvent.getOldValue(), (Integer) propertyChangeEvent.getNewValue());
        } else if (propertyValue.equals(PropertyChangeKeys.EXPORT_WINNERS)) {
            exportWinners();
        }
        revalidate();
        repaint();
    }

    private void handleItemsManualNavigation(NavigationLocations navLoc) {
        if (navLoc == NavigationLocations.ITEMS_MANUAL_PT2 && !RaffleDataStorage.hasItemsFile()) //Check that an items spreadsheet exists when going to part one. -
            ProgramDefaults.displayError(ProgramStrings.DIALOGUE_ITEMS_MANUAL_NO_FILE_MESSAGE, ProgramStrings.DIALOGUE_LOAD_ERROR_TITLE, this);
        lastItemLocation = navLoc;
        currentLocation = lastItemLocation;
        updateContents();
    }

    private void handleItemsADNavigation(NavigationLocations navLoc) {
        if (navLoc == NavigationLocations.ITEMS_AUTO_DETECT_PT2
                && !RaffleDataStorage.hasDistributionValues()) { //Display error that you cannot continue without checking a box.
            ProgramDefaults.displayError(ProgramStrings.DIALOGUE_ITEMS_AD_CONTINUE_ERROR, ProgramStrings.DIALOGUE_LOAD_ERROR_TITLE, this);
            return;
        } else if (navLoc == NavigationLocations.ITEMS_AUTO_DETECT_PT1) { //If navigating to this location, ALL PREVIOUS AUTO DETECTION IS WIPED! //TODO: Potentially update???
            RaffleDataStorage.resetItemsData();
        }
        lastItemLocation = navLoc;
        currentLocation = lastItemLocation;
        updateContents();
    }

    /**
     * Handles all navigation events origination from the tab navigation bar.
     * @param propertyChangeEvent the property change event containing data of the new location to be set (If you use this properly :D)
     */
    private void handleTabNavigationEvent(PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getPropertyName().equals(PropertyChangeKeys.NAVIGATION_KEY)
                && propertyChangeEvent.getNewValue() instanceof NavigationLocations) {
            // Check that quantities have been confirmed if navigating away from auto detect part 2.
            if (currentLocation == NavigationLocations.ITEMS_AUTO_DETECT_PT2 && !RaffleDataStorage.hasItemsFile()) {
                if (ProgramDefaults.displayYesNoConfirm(ProgramStrings.DIALOGUE_ITEMS_AD_NAV_AWAY_MESSAGE, ProgramStrings.DIALOGUE_ITEMS_AD_NAV_AWAY_TITLE, this)
                    != JOptionPane.OK_OPTION) //If the user selects anything other than ok, cancel navigation.
                    return;
            }
            currentLocation = (NavigationLocations) propertyChangeEvent.getNewValue();
            tabs.changeNavLocation(currentLocation);
            if (currentLocation == NavigationLocations.ITEMS)  //If using the items tab - navigate using the last items location recorded.
                currentLocation = lastItemLocation;
            else if (currentLocation == NavigationLocations.RUN_RAFFLE) //If using the run raffle tab - navigate using the last items location recorded.
                currentLocation = lastRunRaffleLocation;
            updateContents();
        }
    }

    /**
     * Updates the contents of ALL panels!
     */
    private void updateContents() {
        informationPanel.setContents(currentLocation);
        interactionPanel.setContents(currentLocation);
        descriptionPanel.setContents(currentLocation);
        revalidate();
        repaint();
    }

    private void runRaffle() {
        if (RaffleDataStorage.hasEntriesFile() && RaffleDataStorage.hasItemsFile()) { //Bare bones for running the raffle.
            if (!RaffleDataStorage.hasFiltered()) { //Show information that the user has not filtered their duplicate entries out!
                if (ProgramDefaults.displayYesNoConfirm(ProgramStrings.DIALOGUE_RAFFLE_MISSING_FILTER, ProgramStrings.DIALOGUE_RAFFLE_MISSING_FILTER_TITLE, this)
                        != JOptionPane.OK_OPTION)
                    return;
            }
            if (currentLocation == NavigationLocations.HOME) { // Navigate to the run raffle page if clicking from home. Allows the user to double check their raffle information.
                tabs.changeNavLocation(NavigationLocations.RUN_RAFFLE);
            } else { //Otherwise, we are on run raffle panel, run the raffle.
                RaffleDataStorage.runRaffle();
                lastRunRaffleLocation = NavigationLocations.RUN_RAFFLE_SHOW_WINNERS;
            }
            currentLocation = lastRunRaffleLocation;
            updateContents();
        } else {
            ProgramDefaults.displayError(ProgramStrings.DIALOGUE_RAFFLE_NOT_READY, ProgramStrings.DIALOGUE_RAFFLE_NOT_READY_TITLE, this);
        }
    }

    private void undoRaffle() {
        int action = ProgramDefaults.displayYesNoConfirm(ProgramStrings.RAFFLE_WINNERS_BACK_DIALOGUE, "Warning", this);
        if (action == JOptionPane.YES_OPTION) {
            RaffleDataStorage.undoRunRaffle();
            lastRunRaffleLocation = NavigationLocations.RUN_RAFFLE_REVIEW;
            currentLocation = lastRunRaffleLocation;
            updateContents();
        }
    }

    /**
     * Removes a winner of the dataset and automatically generates a new winner.
     * @param itemIdentifiers a hashmap of column names to particle values of the item to display.
     * @param indexToRemove the index of the contained spreadsheet to remove.
     */
    private void removeWinner(HashMap<String, Particle> itemIdentifiers, int indexToRemove) {
        RaffleDataStorage.removeWinner(itemIdentifiers, indexToRemove, true);
        if (currentLocation == NavigationLocations.RUN_RAFFLE_SHOW_WINNERS) {
            ((InteractionRunRaffleCenter) interactionPanel.getCenterPanel()).updateDisplayedWinnersTable();
            revalidate();
            repaint();
        } else {
            lastRunRaffleLocation = NavigationLocations.RUN_RAFFLE_SHOW_WINNERS;
            currentLocation = lastRunRaffleLocation;
            updateContents();
        }
    }

    /**
     * Handles navigation to the load entries panel.
     */
    private void loadEntries() {
        if (RaffleDataStorage.hasItemsFile() || RaffleDataStorage.hasEntriesFile()) { //Display a warning that this will reset all progress in raffle.
            int promptValue = ProgramDefaults.displayYesNoConfirm(ProgramStrings.DIALOGUE_LOAD_WARNING, ProgramStrings.DIALOGUE_LOAD_WARNING_TITLE, this);
            if (promptValue != JOptionPane.YES_OPTION)
                return;
        }
        JFileChooser fileChooser = ProgramDefaults.getFileChooser(ProgramStrings.ENTRIES_FILE_CHOOSER_TITLE, JFileChooser.FILES_AND_DIRECTORIES);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                RaffleDataStorage.setEntriesSheet(fileChooser.getSelectedFile());
            } catch (IllegalArgumentException e) {
                ProgramDefaults.displayError(ProgramStrings.DIALOGUE_LOAD_ERROR, ProgramStrings.DIALOGUE_LOAD_ERROR_TITLE, this);
            }
        }
        if (interactionPanel.getCenterPanel() instanceof InteractionEntriesCenter) { //Just double check the entries panel is still selected.
            ((InteractionEntriesCenter) interactionPanel.getCenterPanel()).setLoadedFileText(RaffleDataStorage.getEntriesFileString());
            informationPanel.setLoadEntries();
            lastItemLocation = NavigationLocations.ITEMS_AUTO_DETECT_PT1;
        }
    }

    /**
     * Resets the entries file and all other files.
     */
    private void resetEntries() {
        if (RaffleDataStorage.hasItemsFile() || RaffleDataStorage.hasEntriesFile()) { //If there have been files loaded, display error.
            int promptValue = ProgramDefaults.displayYesNoConfirm(ProgramStrings.DIALOGUE_RESET_WARNING, ProgramStrings.DIALOGUE_RESET_WARNING_TITLE, this);
            if (promptValue == JOptionPane.YES_OPTION) {
                RaffleDataStorage.resetData();
                if (interactionPanel.getCenterPanel() instanceof InteractionEntriesCenter) { //Just double check the entries panel is still selected.
                    ((InteractionEntriesCenter) interactionPanel.getCenterPanel()).setLoadedFileText(ProgramStrings.ENTRIES_INFORMATION_FILE_STATUS_NO_FILE_LOADED);
                    informationPanel.setLoadEntries();
                    lastItemLocation = NavigationLocations.ITEMS_AUTO_DETECT_PT1;
                }
            }
        } else { //MAKE BEEP NOISE!
            java.awt.Toolkit.getDefaultToolkit().beep();
        }
    }

    private void resetItems() {
        if (RaffleDataStorage.hasItemsFile()) {
            RaffleDataStorage.resetItemsData();
            if (interactionPanel.getCenterPanel() instanceof InteractionItemsCenter) {
                ((InteractionItemsCenter) interactionPanel.getCenterPanel()).setLoadedFileText(ProgramStrings.ENTRIES_INFORMATION_FILE_STATUS_NO_FILE_LOADED);
                informationPanel.setLoadItems_manual_pt1();
            }
        } else {
            java.awt.Toolkit.getDefaultToolkit().beep();
        }
    }

    private void loadItems() {
        JFileChooser fileChooser = ProgramDefaults.getFileChooser(ProgramStrings.ITEMS_MANUAL_FILE_CHOOSER_TITLE, JFileChooser.FILES_AND_DIRECTORIES);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                RaffleDataStorage.setItemsSheet(fileChooser.getSelectedFile());
            } catch (IllegalArgumentException e) {
                ProgramDefaults.displayError(ProgramStrings.DIALOGUE_LOAD_ERROR, ProgramStrings.DIALOGUE_LOAD_ERROR_TITLE, this);
            }
            if (interactionPanel.getCenterPanel() instanceof  InteractionItemsCenter) {
                ((InteractionItemsCenter) interactionPanel.getCenterPanel()).setLoadedFileText(RaffleDataStorage.getItemsFileString());
                informationPanel.setLoadItems_manual_pt1();
            }
        }
    }

    private void validateManualInput(boolean success, String message) {
        if (success) { //Display success message.
            ProgramDefaults.displayMessage(message, ProgramStrings.DIALOGUE_SUCCESS_TITLE, this);
        } else { //Display error message.
            ProgramDefaults.displayError(message, ProgramStrings.DIALOGUE_ERROR_TITLE, this);
        }
    }

    private void filterAction(String newValue) {
        ((Runnable) () -> {
            RaffleDataStorage.updateFilter(newValue);
            informationPanel.updateDuplicateCount();
        }).run();
    }

    /**
     * Removes an entry manually. This method is CURRENTLY only called when the current view is run raffle.
     * @param index
     */
    private void raffleRemoveEntry(int index) {
        try {
            RaffleDataStorage.removeEntry(index);
            if (interactionPanel.getCenterPanel() instanceof InteractionRunRaffleCenter) {
                ((InteractionRunRaffleCenter) interactionPanel.getCenterPanel()).removeFromTable(index);
                informationPanel.updateRafflePreview();
            }
        }  catch (IllegalArgumentException e) {
            e.printStackTrace();
            ProgramDefaults.displayError(e.getMessage(), ProgramStrings.DIALOGUE_ERROR_TITLE, this);
        }

    }

    private void raffleSetAsWinner(int index) {
        try {
            RaffleDataStorage.manualSetAsWinner(index);
            if (interactionPanel.getCenterPanel() instanceof InteractionRunRaffleCenter) {
                ((InteractionRunRaffleCenter) interactionPanel.getCenterPanel()).removeFromTable(index);
                informationPanel.updateRafflePreview();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            ProgramDefaults.displayError(e.getMessage(), ProgramStrings.DIALOGUE_ERROR_TITLE, this);
        }
    }

    private void exportWinners() {
        String fileName = JOptionPane.showInputDialog(this, "Choose Save File Name (no extension necessary):");
        if (fileName == null)
            return;
        JFileChooser f = ProgramDefaults.getFileChooser("Export \"" + fileName + ".csv\" to Folder:", JFileChooser.SAVE_DIALOG);
        if (f.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            SpreadSheet s = RaffleDataStorage.getCombinedWinnersSheet();
            if (s.writeToFile(fileName, f.getSelectedFile().getAbsolutePath())) {
                RaffleDataStorage.setWrittenToFile(true);
                ProgramDefaults.displayMessage("Successfully saved the winners file.", "Success", this);
            } else {
                RaffleDataStorage.setWrittenToFile(false);
                ProgramDefaults.displayError("There has been an error saving the winners file.", "Error", this);
            }
            informationPanel.setRunRaffleWinners();
            revalidate();
            repaint();
        }
    }

    public static void initLookAndFeel() {
        //Table things
        UIDefaults m = UIManager.getLookAndFeelDefaults();
        m.put("TableHeader.cellBorder", new BorderUIResource.LineBorderUIResource(ProgramColors.FOREGROUND_COLOR)); //Set cell header color.
        m.put("Table.focusCellHighlightBorder", BorderFactory.createLineBorder(ProgramColors.TABLE_HIGHLIGHT_CELL_COLOR, 1));
    }

    public static void main(String[] args) {
        initLookAndFeel();
        java.awt.EventQueue.invokeLater(() ->
                new DisplayFrame().setVisible(true));
    }
}
