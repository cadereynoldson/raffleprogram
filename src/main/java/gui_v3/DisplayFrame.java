package gui_v3;

import gui_v3.BaseComponents.*;
import gui_v3.logic.*;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.io.File;

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
            lastItemLocation = (NavigationLocations) propertyChangeEvent.getNewValue();
        }
    }

    /**
     * Handles all navigation events origination from the tab navigation bar.
     * @param propertyChangeEvent the property change event containing data of the new location to be set (If you use this properly :D)
     */
    private void handleTabNavigationEvent(PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getPropertyName().equals(PropertyChangeKeys.NAVIGATION_KEY)
                && propertyChangeEvent.getNewValue() instanceof NavigationLocations) {
            currentLocation = (NavigationLocations) propertyChangeEvent.getNewValue();
            if (currentLocation == NavigationLocations.ITEMS)  //If navigating to the items tab - navigate using the last items location recorded.
                currentLocation = lastItemLocation;
            informationPanel.setContents(currentLocation);
            interactionPanel.setContents(currentLocation);
            descriptionPanel.setContents(currentLocation);
            repaint();
        }
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
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Entries File");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                RaffleDataStorage.setEntriesSheet(fileChooser.getSelectedFile());
                if (interactionPanel.getCenterPanel() instanceof InteractionEntriesCenter) { //Just double check the entries panel is still selected.
                    ((InteractionEntriesCenter) interactionPanel.getCenterPanel()).setLoadedFileText(RaffleDataStorage.getEntriesFileString());
                    informationPanel.setLoadEntries();
                }
            } catch (IllegalArgumentException e) {
                ProgramDefaults.displayError(ProgramStrings.DIALOGUE_LOAD_ERROR, ProgramStrings.DIALOGUE_LOAD_ERROR_TITLE, this);
            }
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
                }
            }
        } else { //MAKE BEEP NOISE!
            java.awt.Toolkit.getDefaultToolkit().beep();
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() ->
                new DisplayFrame().setVisible(true));
    }
}
