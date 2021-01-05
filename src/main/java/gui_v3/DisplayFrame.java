package gui_v3;

import gui_v3.components.*;
import gui_v3.logic.*;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.beans.PropertyChangeEvent;

/**
 * Display Frame. Acts as the main JFrame for displaying the application.
 */
public class DisplayFrame extends JFrame {

    /** The current location being displayed in the frame. */
    private NavigationLocations currentLocation;

    /** The description panel. Displays an in-depth description about the tab the user is on. */
    private DescriptionPanel descriptionPanel;

    /** The interaction panel. Acts as the "main" panel the user will interact with in the program. */
    private InteractionPanel interactionPanel;

    /** The information panel. Acts as a panel for displaying information about the interaction panel (Stats, status, etc.) */
    private InformationPanel informationPanel;

    /** The side tab menu. Acts as the main navigation tool of the program. */
    private VerticalTabs tabsAndProgressCircle;

    /** The last ITEM location displayed. */
    private NavigationLocations lastItemLocation;

    /** The count column of the items spreadsheet. */
    private int countColumn;

    public DisplayFrame() {
        super();
        setIconImage(ProgramDefaults.getRaffleTicketIcon().getImage());
        setTitle("Raffle By Cade");
        lastItemLocation = NavigationLocations.ITEMS_AUTO_DETECT_PT1;
        initComponents();
        initLayout();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(ProgramColors.BACKGROUND_COLOR);
        setSize(ProgramDimensions.DEFAULT_SCREEN_SIZE);
        descriptionPanel = new DescriptionPanel();
        interactionPanel = new InteractionPanel(this::handleInteractionEvent);
        informationPanel = new InformationPanel();
        tabsAndProgressCircle = new VerticalTabs(this::handleTabNavigationEvent);
        currentLocation = NavigationLocations.HOME;
    }

    private void initLayout() {
        setLayout(new GridBagLayout());
        add(descriptionPanel, ProgramDimensions.DESCRIPTION_CONSTRAINTS);
        add(tabsAndProgressCircle, ProgramDimensions.TOOLBAR_CONSTRAINTS);
        add(interactionPanel, ProgramDimensions.INTERACTION_CONSTRAINTS);
        add(informationPanel, ProgramDimensions.INFORMATION_CONSTRAINTS);
        repaint();
    }

    private void handleInteractionEvent(PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getPropertyName().equals(PropertyChangeKeys.RUN_RAFFLE)) { //If the user has decided to run the raffle.
            runRaffle();
        } else if (propertyChangeEvent.getPropertyName().equals(PropertyChangeKeys.LOAD_ENTRIES)) {
            loadEntries();
        } else if (propertyChangeEvent.getPropertyName().equals(PropertyChangeKeys.RESET_ENTRIES)) {
            resetEntries();
        } else if (propertyChangeEvent.getPropertyName().equals(PropertyChangeKeys.ITEMS_NAV_CHANGE)) {
            NavigationLocations placeholder = (NavigationLocations) propertyChangeEvent.getNewValue();
            if (placeholder == NavigationLocations.ITEMS_AUTO_DETECT_PT2 || placeholder == NavigationLocations.ITEMS_AUTO_DETECT_PT1) { //Check for auto detect nav.
                handleItemsADNavigation(placeholder);
            } else { //Handle manual navigation.
                handleItemsManualNavigation(placeholder);
            }
        } else if (propertyChangeEvent.getPropertyName().equals(PropertyChangeKeys.ITEMS_AD_QUANTITIES_CONFIRMED)) { //If quantities are confirmed, update information panel.
            informationPanel.setLoadItems_autoDetect_pt2();
        } else if (propertyChangeEvent.getPropertyName().equals(PropertyChangeKeys.RESET_ITEMS)) {
            resetItems();
        } else if (propertyChangeEvent.getPropertyName().equals(PropertyChangeKeys.LOAD_ITEMS)) {
            loadItems();
        } else if (propertyChangeEvent.getPropertyName().equals(PropertyChangeKeys.ITEMS_INFO_SET)) {
            validateManualInput((Boolean) propertyChangeEvent.getOldValue(), (String) propertyChangeEvent.getNewValue());
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
            tabsAndProgressCircle.changeNavLocation(currentLocation);
            System.out.println(currentLocation);
            if (currentLocation == NavigationLocations.ITEMS)  //If navigating to the items tab - navigate using the last items location recorded.
                currentLocation = lastItemLocation;
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
            //TODO: RUN RAFFLE.
        } else {
            ProgramDefaults.displayError(ProgramStrings.DIALOGUE_RAFFLE_NOT_READY, ProgramStrings.DIALOGUE_RAFFLE_NOT_READY_TITLE, this);
        }
    }

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
