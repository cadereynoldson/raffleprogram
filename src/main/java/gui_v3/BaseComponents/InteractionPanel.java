package gui_v3.BaseComponents;

import gui_v3.logic.ProgramColors;
import gui_v3.logic.ProgramDefaults;
import gui_v3.logic.ProgramStrings;
import gui_v3.logic.RaffleDataStorage;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Serves as the main interaction panel. Given a navigation key, panel will
 * dynamically switch to the key.
 */
public class InteractionPanel extends JPanel implements GUINavigationLocations{

    private JLabel title;

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

    @Override
    public void setHome() {
        removeAll();
        setLayout(new BorderLayout());
        title = ProgramDefaults.getCenteredTitle(ProgramStrings.INTERACTION_HOME_TITLE);
        add(title, BorderLayout.NORTH);
        add(new InteractionHomeCenter(pcs), BorderLayout.CENTER);
    }

    @Override
    public void setLoadEntries() {

    }

    @Override
    public void setLoadItems_autoDetect_pt1() {

    }

    @Override
    public void setLoadItems_autoDetect_pt2() {

    }

    @Override
    public void setLoadItems_manual_pt1() {

    }

    @Override
    public void setLoadItems_manual_pt2() {

    }

    @Override
    public void setLoadItems_manual_pt3() {

    }

    @Override
    public void setRemoveDuplicates() {

    }

    @Override
    public void setRaffleWinners() {

    }
}
