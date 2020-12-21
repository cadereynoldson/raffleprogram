package gui_v3.BaseComponents;

import gui_v3.logic.ProgramColors;
import gui_v3.logic.ProgramDefaults;
import gui_v3.logic.ProgramStrings;
import gui_v3.logic.RaffleDataStorage;

import javax.swing.*;
import java.awt.*;

public class InformationPanel extends JPanel implements GUINavigationLocations {

    public InformationPanel() {
        super();
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
        setLayout(new GridLayout(0, 1));
        add(ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.HOME_INFORMATION_CONTACT_TITLE));
        add(ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.HOME_INFORMATION_CONTACT_EMAIL));
        add(ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.HOME_INFORMATION_CONTACT_INSTA));
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
