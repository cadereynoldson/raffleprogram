package gui_v3.BaseComponents;

import gui_v3.logic.*;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
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
        resetComponents(ProgramStrings.ITEMS_AD_TITLE_P1, new InteractionItemsCenter(pcs, NavigationLocations.ITEMS_AUTO_DETECT_PT1));
    }

    @Override
    public void setLoadItems_autoDetect_pt2() {
        if (centerPanel instanceof InteractionItemsCenter) {//If the items panel is being displayed, notify the displayed center panel to update to a new display instance.
            ((InteractionItemsCenter) centerPanel).setLocationDisplayed(NavigationLocations.ITEMS_AUTO_DETECT_PT2);
            title.setText(ProgramStrings.ITEMS_AD_TITLE_P2);
            repaint();
        } else
            resetComponents(ProgramStrings.ITEMS_AD_TITLE_P2, new InteractionItemsCenter(pcs, NavigationLocations.ITEMS_AUTO_DETECT_PT2));
    }

    @Override
    public void setLoadItems_manual_pt1() {
        if (centerPanel instanceof InteractionItemsCenter) { //If the items panel is being displayed, notify the displayed center panel to update to a new display instance.

        }
    }

    @Override
    public void setLoadItems_manual_pt2() {
        if (centerPanel instanceof InteractionItemsCenter) { //If the items panel is being displayed, notify the displayed center panel to update to a new display instance.

        }
    }

    @Override
    public void setLoadItems_manual_pt3() {
        if (centerPanel instanceof InteractionItemsCenter) { //If the items panel is being displayed, notify the displayed center panel to update to a new display instance.

        }
    }

    private void itemsNavigation(PropertyChangeEvent propertyChangeEvent) {
    }

    @Override
    public void setRemoveDuplicates() {

    }

    @Override
    public void setRaffleWinners() {

    }
}
