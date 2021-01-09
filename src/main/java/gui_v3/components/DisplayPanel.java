package gui_v3.components;

import gui_v3.logic.NavigationLocations;

import javax.swing.*;

public abstract class DisplayPanel extends JPanel{

    public DisplayPanel() {
        super();
    }

    public void setContents(NavigationLocations navLoc) {
        if (navLoc == NavigationLocations.HOME) {
            setHome();
        } else if (navLoc == NavigationLocations.ENTRIES) {
            setLoadEntries();
        } else if (navLoc == NavigationLocations.ITEMS_AUTO_DETECT_PT1) {
            setLoadItems_autoDetect_pt1();
        } else if (navLoc == NavigationLocations.ITEMS_AUTO_DETECT_PT2) {
            setLoadItems_autoDetect_pt2();
        } else if (navLoc == NavigationLocations.ITEMS_MANUAL_PT1) {
            setLoadItems_manual_pt1();
        } else if (navLoc == NavigationLocations.ITEMS_MANUAL_PT2) {
            setLoadItems_manual_pt2();
        } else if (navLoc == NavigationLocations.FILTER) {
            setRemoveDuplicates();
        } else if (navLoc == NavigationLocations.WINNERS) {
            setRaffleWinners();
        }
    }

    abstract void setHome();

    abstract void setLoadEntries();

    abstract void setLoadItems_autoDetect_pt1();

    abstract void setLoadItems_autoDetect_pt2();

    abstract void setLoadItems_manual_pt1();

    abstract void setLoadItems_manual_pt2();

    abstract void setRemoveDuplicates();

    abstract void setRaffleWinners();
}
