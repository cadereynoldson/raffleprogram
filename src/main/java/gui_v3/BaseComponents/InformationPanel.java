package gui_v3.BaseComponents;

import gui_v3.logic.*;

import javax.swing.*;
import java.awt.*;

public class InformationPanel extends DisplayPanel {

    public InformationPanel() {
        super();
        initTheme();
        setHome();
    }

    private void initTheme() {
        setBackground(ProgramColors.FOREGROUND_COLOR);
        setBorder(BorderFactory.createLineBorder(ProgramColors.TEXT_ON_FG_COLOR));
    }

    /**
     * Updates the description based on the labels provided.
     * @param labels the labels to set as this information.
     */
    private void updateInformation(JLabel ... labels) {
        removeAll();
        setLayout(new GridLayout(0, 1));
        for (JLabel label : labels) {
            add(label);
        }
    }

    @Override
    public void setHome() {
        updateInformation(
            ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.HOME_INFORMATION_CONTACT_TITLE),
            ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.HOME_INFORMATION_CONTACT_EMAIL),
            ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.HOME_INFORMATION_CONTACT_INSTA)
        );
    }

    @Override
    public void setLoadEntries() {
        JLabel status;
        if (RaffleDataStorage.hasEntriesFile())
            status = ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.getUnderlinedHTMLString(ProgramStrings.ENTRIES_INFORMATION_FILE_STATUS_FILE_LOADED));
        else
            status = ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.getUnderlinedHTMLString(ProgramStrings.ENTRIES_INFORMATION_FILE_STATUS_NO_FILE_LOADED));
        updateInformation(
            ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.ENTRIES_INFORMATION_FILE_STATUS_PROMPT),
            status
        );
    }

    @Override
    public void setLoadItems_autoDetect_pt1() {
        JLabel status;
        if (RaffleDataStorage.hasEntriesFile())
            status = ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.getUnderlinedHTMLString(RaffleDataStorage.getEntriesFileString()));
        else
            status = ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.getUnderlinedHTMLString(ProgramStrings.ITEMS_AD_INFO_P1_NO_ENTRIES));
        updateInformation(
                ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.ITEMS_AD_INFO_P1_PROMPT),
                status
        );
    }

    @Override
    public void setLoadItems_autoDetect_pt2() {
        JLabel status;
        if (RaffleDataStorage.hasItemsFile())
            status = ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.getUnderlinedHTMLString(ProgramStrings.ITEMS_AD_INFO_P2_SAVED));
        else
            status = ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.getUnderlinedHTMLString(ProgramStrings.ITEMS_AD_INFO_P2_NOT_SAVED));
        updateInformation(
                ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.ITEMS_AD_INFO_P2_PROMPT),
                status
        );
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
