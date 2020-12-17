package gui_v3;

import gui_v3.BaseComponents.DescriptionPanel;
import gui_v3.BaseComponents.VerticalTabsAndProgressCircle;
import gui_v3.logic.ProgramColors;
import gui_v3.logic.ProgramDimensions;
import gui_v3.logic.PropertyChangeKeys;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

public class DisplayFrame extends JFrame {

    private DescriptionPanel descriptionPanel;

    private VerticalTabsAndProgressCircle tabsAndProgressCircle;


    public DisplayFrame() {
        super();
        initComponents();
        initLayout();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(ProgramColors.BACKGROUND_COLOR);
        setSize(ProgramDimensions.DEFAULT_SCREEN_SIZE);
        descriptionPanel = new DescriptionPanel("LONG STRING ahhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhahhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        tabsAndProgressCircle = new VerticalTabsAndProgressCircle(this::handleNavigationEvent);
    }

    private void handleNavigationEvent(PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getPropertyName().equals(PropertyChangeKeys.NAVIGATION_KEY)) {
            //TODO: Add navigation handling.
            System.out.println("Navigation Change");
            repaint();
        }
    }

    private void initLayout() {
        setLayout(new GridBagLayout());
        add(descriptionPanel, ProgramDimensions.DESCRIPTION_CONSTRAINTS);
        add(tabsAndProgressCircle, ProgramDimensions.TOOLBAR_CONSTRAINTS);
    }


    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() ->
                new DisplayFrame().setVisible(true));
    }
}
