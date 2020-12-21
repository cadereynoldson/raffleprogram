package gui_v3;

import gui_v3.BaseComponents.DescriptionPanel;
import gui_v3.BaseComponents.InformationPanel;
import gui_v3.BaseComponents.InteractionPanel;
import gui_v3.BaseComponents.VerticalTabs;
import gui_v3.logic.*;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

/**
 * Display Frame. Acts as the main JFrame for displaying the application.
 */
public class DisplayFrame extends JFrame {

    /** The description panel. Displays an in-depth description about the tab the user is on. */
    private DescriptionPanel descriptionPanel;

    /** The interaction panel. Acts as the "main" panel the user will interact with in the program. */
    private InteractionPanel interactionPanel;

    /** The information panel. Acts as a panel for displaying information about the interaction panel (Stats, status, etc.) */
    private InformationPanel informationPanel;

    /** The side tab menu. Acts as the main navigation tool of the program. */
    private VerticalTabs tabsAndProgressCircle;


    public DisplayFrame() {
        super();
        setIconImage(ProgramDefaults.getRaffleTicketIcon().getImage());
        setTitle("Raffle By Cade");
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
        tabsAndProgressCircle = new VerticalTabs(this::handleNavigationEvent);
    }

    private void handleInteractionEvent(PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getPropertyName().equals(PropertyChangeKeys.RUN_RAFFLE)) { //If the user has decided to run the raffle.
            runRaffle();
        }
    }

    private void handleNavigationEvent(PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getPropertyName().equals(PropertyChangeKeys.NAVIGATION_KEY)) {
            System.out.println("Navigation Change");
            repaint();
        }
    }

    private void initLayout() {
        setLayout(new GridBagLayout());
        add(descriptionPanel, ProgramDimensions.DESCRIPTION_CONSTRAINTS);
        add(tabsAndProgressCircle, ProgramDimensions.TOOLBAR_CONSTRAINTS);
        add(interactionPanel, ProgramDimensions.INTERACTION_CONSTRAINTS);
        add(informationPanel, ProgramDimensions.INFORMATION_CONSTRAINTS);
        repaint();
    }

    private void runRaffle() {
        if (RaffleDataStorage.hasEntriesFile() && RaffleDataStorage.hasItemsFile()) { //Bare bones for running the raffle.
            if (!RaffleDataStorage.hasFiltered()) { //Show information that the user has not filtered their duplicate entries out!
                if (ProgramDefaults.displayYesCancelConfirm(ProgramStrings.DIALOGUE_RAFFLE_MISSING_FILTER, ProgramStrings.DIALOGUE_RAFFLE_MISSING_FILTER_TITLE, this)
                        != JOptionPane.OK_OPTION)
                    return;
            }
            //TODO: RUN RAFFLE.
        } else {
            ProgramDefaults.displayError(ProgramStrings.DIALOGUE_RAFFLE_NOT_READY, ProgramStrings.DIALOGUE_RAFFLE_NOT_READY_TITLE, this);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() ->
                new DisplayFrame().setVisible(true));
    }
}
