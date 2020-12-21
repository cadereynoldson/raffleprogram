package gui_v3.BaseComponents;

import gui_v3.logic.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DescriptionPanel extends JPanel implements GUINavigationLocations {

    private JLabel descriptionTitle;
    private ArrayList<Component> descriptionLabels;

    public DescriptionPanel() {
        super();
        descriptionTitle = ProgramDefaults.getCenteredTitle(ProgramStrings.INTERACTION_HOME_TITLE);
        descriptionLabels = new ArrayList<>();
        initComponents();
        setHome();
    }

    public void changeDescription(NavigationLocations navLoc) {
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
        } else if (navLoc == NavigationLocations.ITEMS_MANUAL_PT3) {
            setLoadItems_manual_pt3();
        } else if (navLoc == NavigationLocations.FILTER) {
            setRemoveDuplicates();
        } else if (navLoc == NavigationLocations.WINNERS) {
            setRaffleWinners();
        }
    }

    private void refreshDisplay() {
        removeAll();
        setLayout(new BorderLayout());
        add(descriptionTitle, BorderLayout.NORTH);
        JPanel holder = ProgramDefaults.getBlankPanel();
        holder.setLayout(new GridLayout(0, 1));
        for (Component c : descriptionLabels)
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
        descriptionLabels.clear();
        descriptionTitle.setText(ProgramStrings.HOME_DESCRIPTION_TITLE);
        descriptionLabels.add(ProgramDefaults.getDescriptionLabel(ProgramStrings.HOME_DESCRIPTION_L1));
        descriptionLabels.add(ProgramDefaults.getDescriptionLabel(ProgramStrings.HOME_DESCRIPTION_L2));
        descriptionLabels.add(ProgramDefaults.getDescriptionLabel(ProgramStrings.HOME_DESCRIPTION_L3));
        JPanel holder = ProgramDefaults.getBlankPanel();
        holder.setLayout(new GridLayout(0, 1));
        holder.add(ProgramDefaults.getDescriptionLabel(ProgramStrings.HOME_DESCRIPTION_LINK_HEADER));
        holder.add(ProgramDefaults.convertToLink(ProgramStrings.HOME_DESCRIPTION_ABOUT_TEXT, ProgramStrings.HOME_DESCRIPTION_ABOUT_LINK));
        holder.add(ProgramDefaults.convertToLink(ProgramStrings.HOME_DESCRIPTION_GITHUB_TEXT, ProgramStrings.HOME_DESCRIPTION_GITHUB_LINK));
        descriptionLabels.add(holder);
        refreshDisplay();
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
