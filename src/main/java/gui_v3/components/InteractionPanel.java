package gui_v3.components;

import gui_v3.logic.*;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Serves as the main interaction panel. Given a navigation key, panel will
 * dynamically switch to the key.
 */
public class InteractionPanel extends DisplayPanel {

    private JLabel title;

    private JPanel centerPanel;

    private PropertyChangeSupport pcs;

    public InteractionPanel(PropertyChangeListener listener) {
        super();
        pcs = new PropertyChangeSupport(this);
        pcs.addPropertyChangeListener(listener);
        initTheme();
        setHome();
    }

    private void initTheme() {
        setBackground(ProgramColors.FOREGROUND_COLOR);
        setBorder(BorderFactory.createLineBorder(ProgramColors.TEXT_ON_FG_COLOR));
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    private void resetComponents(String titleStr, JPanel centerPanel) {
        removeAll();
        setLayout(new BorderLayout());
        title = ProgramDefaults.getCenteredTitle(titleStr);
        this.centerPanel = centerPanel;
        add(title, BorderLayout.NORTH);
        add(this.centerPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * Updates the components based on navigation to an item location.
     * @param newTitle the new title of the panel.
     * @param newLocation the new location to display.
     */
    private void updateItemsComponents(String newTitle, NavigationLocations newLocation) {
        if (centerPanel instanceof InteractionItemsCenter) {
            ((InteractionItemsCenter) centerPanel).setLocationDisplayed(newLocation);
            title.setText(newTitle);
            revalidate();
            repaint();
        } else {
            resetComponents(newTitle, new InteractionItemsCenter(pcs, newLocation));
        }
    }

    @Override
    public void setHome() {
        resetComponents(ProgramStrings.INTERACTION_HOME_TITLE, new InteractionHomeCenter(pcs));
    }

    @Override
    public void setLoadEntries() {
        resetComponents(ProgramStrings.ENTRIES_TITLE, new InteractionEntriesCenter(pcs));
    }

    @Override
    public void setLoadItems_autoDetect_pt1() {
        updateItemsComponents(ProgramStrings.ITEMS_AD_TITLE_P1, NavigationLocations.ITEMS_AUTO_DETECT_PT1);
    }

    @Override
    public void setLoadItems_autoDetect_pt2() {
        updateItemsComponents(ProgramStrings.ITEMS_AD_TITLE_P2, NavigationLocations.ITEMS_AUTO_DETECT_PT2);
    }

    @Override
    public void setLoadItems_manual_pt1() {
        updateItemsComponents(ProgramStrings.ITEMS_MANUAL_P1_TITLE, NavigationLocations.ITEMS_MANUAL_PT1);
    }

    @Override
    public void setLoadItems_manual_pt2() {
        updateItemsComponents(ProgramStrings.ITEMS_MANUAL_P2_TITLE, NavigationLocations.ITEMS_MANUAL_PT2);
    }

    @Override
    public void setRemoveDuplicates() {
        resetComponents(ProgramStrings.FILTER_TITLE, new InteractionFilteringCenter(pcs));
    }

    @Override
    public void setRaffleWinners() {

    }
}
