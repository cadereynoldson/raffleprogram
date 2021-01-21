package gui_v3.components;

import gui_v3.logic.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DescriptionPanel extends DisplayPanel {

    private JLabel descriptionTitle;

    public DescriptionPanel() {
        super();
        descriptionTitle = ProgramDefaults.getCenteredTitle(ProgramStrings.INTERACTION_HOME_TITLE);
        descriptionTitle.setFont(ProgramFonts.DESCRIPTION_TITLE_FONT);
        descriptionTitle.setBorder(new EmptyBorder(10, 10, 10, 10));
        initComponents();
        setHome();
        setPreferredSize(new Dimension(400, 0));
    }

    /**
     * Refreshes the display based on the new title, and a list of components to set in the center.
     * @param title
     * @param components
     */
    private void refreshDisplay(String title, Component ... components) {
        removeAll();
        setLayout(new BorderLayout());
        descriptionTitle.setText(title);
        add(descriptionTitle, BorderLayout.NORTH);
        JPanel holder = ProgramDefaults.getBlankPanel();
        holder.setLayout(new GridLayout(0, 1));
        for (Component c : components)
            holder.add(c);
        add(holder, BorderLayout.CENTER);
        repaint();
    }

    /**
     * Initializes the components of the panel.
     */
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(ProgramColors.FOREGROUND_COLOR);
        setBorder(BorderFactory.createLineBorder(ProgramColors.TEXT_ON_FG_COLOR));
    }

    @Override
    public void setHome() {
        JPanel holder = ProgramDefaults.getBlankPanel();
        holder.setLayout(new GridLayout(0, 1));
        holder.add(ProgramDefaults.getDescriptionLabel(ProgramStrings.HOME_DESCRIPTION_LINK_HEADER));
        holder.add(ProgramDefaults.convertToLink(ProgramStrings.HOME_DESCRIPTION_ABOUT_TEXT, ProgramStrings.HOME_DESCRIPTION_ABOUT_LINK));
        holder.add(ProgramDefaults.convertToLink(ProgramStrings.HOME_DESCRIPTION_GITHUB_TEXT, ProgramStrings.HOME_DESCRIPTION_GITHUB_LINK));
        refreshDisplay(
                ProgramStrings.HOME_DESCRIPTION_TITLE,
                ProgramDefaults.getDescriptionLabel(ProgramStrings.HOME_DESCRIPTION_L1),
                ProgramDefaults.getDescriptionLabel(ProgramStrings.HOME_DESCRIPTION_L2),
                ProgramDefaults.getDescriptionLabel(ProgramStrings.HOME_DESCRIPTION_L3),
                holder
        );
    }

    @Override
    public void setLoadEntries() {
        refreshDisplay(
                ProgramStrings.ENTRIES_DESCRIPTION_TITLE,
                ProgramDefaults.getDescriptionLabel(ProgramStrings.ENTRIES_DESCRIPTION_L1),
                ProgramDefaults.getDescriptionLabel(ProgramStrings.ENTRIES_DESCRIPTION_L2),
                ProgramDefaults.getDescriptionLabel(ProgramStrings.ENTRIES_DESCRIPTION_L3)
        );
    }

    @Override
    public void setLoadItems_autoDetect_pt1() {
        if (RaffleDataStorage.hasEntriesFile()) {
            refreshDisplay(
                    ProgramStrings.ITEMS_AD_DESCRIPTION_P1_TITLE,
                    ProgramDefaults.getDescriptionLabel(ProgramStrings.ITEMS_AD_DESCRIPTION_P1_L1),
                    ProgramDefaults.getDescriptionLabel(ProgramStrings.ITEMS_AD_DESCRIPTION_P1_L2),
                    ProgramDefaults.getDescriptionLabel(ProgramStrings.ITEMS_AD_DESCRIPTION_P1_L3),
                    ProgramDefaults.getDescriptionLabel(ProgramStrings.ITEMS_AD_DESCRIPTION_P1_L4)
            );
        } else {
            refreshDisplay(
                    ProgramStrings.ITEMS_NO_ENTRIES_DESCRIPTION_TITLE,
                    ProgramDefaults.getDescriptionLabel(ProgramStrings.ITEMS_NO_ENTRIES_DESCRIPTION_L1),
                    ProgramDefaults.getDescriptionLabel(ProgramStrings.ITEMS_NO_ENTRIES_DESCRIPTION_L2),
                    ProgramDefaults.getDescriptionLabel(ProgramStrings.ITEMS_NO_ENTRIES_DESCRIPTION_L3)
            );
        }
    }

    @Override
    public void setLoadItems_autoDetect_pt2() {
        refreshDisplay(
                ProgramStrings.ITEMS_AD_DESCRIPTION_P2_TITLE,
                ProgramDefaults.getDescriptionLabel(ProgramStrings.ITEMS_AD_DESCRIPTION_P2_L1),
                ProgramDefaults.getDescriptionLabel(ProgramStrings.ITEMS_AD_DESCRIPTION_P2_L2),
                ProgramDefaults.getDescriptionLabel(ProgramStrings.ITEMS_AD_DESCRIPTION_P2_L3)
        );
    }

    @Override
    public void setLoadItems_manual_pt1() {
        refreshDisplay(
                ProgramStrings.ITEMS_MANUAL_DESCRIPTION_P1_TITLE,
                ProgramDefaults.getDescriptionLabel(ProgramStrings.ITEMS_MANUAL_DESCRIPTION_P1_L1),
                ProgramDefaults.getDescriptionLabel(ProgramStrings.ITEMS_MANUAL_DESCRIPTION_P1_L2),
                ProgramDefaults.getDescriptionLabel(ProgramStrings.ITEMS_MANUAL_DESCRIPTION_P1_L3)
        );
    }

    @Override
    public void setLoadItems_manual_pt2() {
        refreshDisplay(
                ProgramStrings.ITEMS_MANUAL_DESCRIPTION_P2_TITLE,
                ProgramDefaults.getDescriptionLabel(ProgramStrings.ITEMS_MANUAL_DESCRIPTION_P2_L1),
                ProgramDefaults.getDescriptionLabel(ProgramStrings.ITEMS_MANUAL_DESCRIPTION_P2_L2)
        );
    }

    @Override
    public void setRemoveDuplicates() {
        refreshDisplay(
                ProgramStrings.FILTER_DESCRIPTION_TITLE,
                ProgramDefaults.getDescriptionLabel(ProgramStrings.FILTER_DESCRIPTION_L1),
                ProgramDefaults.getDescriptionLabel(ProgramStrings.FILTER_DESCRIPTION_L2),
                ProgramDefaults.getDescriptionLabel(ProgramStrings.FILTER_DESCRIPTION_L3)
        );
    }

    @Override
    void setRunRaffleReview() {
        refreshDisplay(ProgramStrings.RAFFLE_REVIEW_DESC_TITLE,
                ProgramDefaults.getDescriptionLabel(ProgramStrings.RAFFLE_REVIEW_DESC_L1),
                ProgramDefaults.getDescriptionLabel(ProgramStrings.RAFFLE_REVIEW_DESC_L2));
    }

    @Override
    void setRunRaffleWinners() {

    }

}
