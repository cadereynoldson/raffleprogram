package gui_v3.logic;

import java.awt.*;

/**
 * Class for storing various dimensions of panels!
 */
public class ProgramDimensions {

    public static final Insets DEFAULT_INSETS = new Insets(0, 50, 20, 50);

    /** The dimension of the user's screen size */
    public static final Dimension USER_SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    public static final Dimension DEFAULT_SCREEN_SIZE = new Dimension((int) (USER_SCREEN_SIZE.width * 0.8), (int) (USER_SCREEN_SIZE.height * 0.8));

    public static final GridBagConstraints DESCRIPTION_CONSTRAINTS = getDescriptionConstraints();

    public static final GridBagConstraints TOOLBAR_CONSTRAINTS = getToolbarConstraints();

    public static final GridBagConstraints INTERACTION_CONSTRAINTS = getInteractionConstraints();

    public static final GridBagConstraints INFORMATION_CONSTRAINTS = getInformationConstraints();

    public static final GridBagConstraints ITEMS_ADP2_BRIEF_DESC_CONSTRAINTS = getADBriefDescConstraints();

    public static final GridBagConstraints ITEMS_ADP2_TABLE_CONSTRAINTS = getADTableConstraints();

    public static final GridBagConstraints ITEMS_ADP2_BACK_CONSTRAINTS = getADBackConstraints();

    public static final GridBagConstraints ITEMS_ADP2_CONFIRM_QUANTITIES = getADConfirmConstraints();

    public static final GridBagConstraints ITEMS_MP2_BRIEF_DESC_CONSTRAINTS = getManualBriefDescConstraints();

    public static final GridBagConstraints ITEMS_MP2_CHECKBOX_CONSTRAINTS = getManualCheckboxPaneConstraints();

    public static final GridBagConstraints ITEMS_MP2_COMBOBOX_CONSTRAINTS = getManualComboBoxConstraints();

    public static final GridBagConstraints ITEMS_MP2_TABLE_CONSTRAINTS = getManualTableConstraints();

    public static final GridBagConstraints ITEMS_MP2_BACK_CONSTRAINTS = getManualBackConstraints();

    public static final GridBagConstraints ITEMS_MP2_CONFIRM_CONSTRAINTS = getManualConfirmConstraints();

    public static final GridBagConstraints FILTER_BRIEF_DESC_CONSTRAINTS = getFilterBriefDescConstraints();

    public static final GridBagConstraints FILTER_CHECKBOX_CONSTRAINTS = getFilterCheckboxConstraints();

    public static final GridBagConstraints RAFFLE_TABLE_CONSTRAINTS = getRaffleTableConstraints();

    public static final GridBagConstraints RAFFLE_REVIEW_BRIEF_DESC_CONSTRAINTS = getRaffleBriefDescConstraints();

    public static final GridBagConstraints RAFFLE_WINNERS_BRIEF_DESC_CONSTRAINTS = getWinnersBriefDescConstraints();

    public static final GridBagConstraints RAFFLE_WINNERS_TABLE_CONSTRAINTS = getRaffleWinnerTableConstraints();

    public static final GridBagConstraints RAFFLE_WINNERS_BUTTON_PANEL_CONSTRAINTS = getRaffleWinnerButtonPanelConstraints();

    public static final GridBagConstraints RAFFLE_REVIEW_BUTTON_PANEL_CONSTRAINTS = getRaffleButtonConstraints();

    public static final GridBagConstraints RAFFLE_COMBO_BOX_CONSTRAINTS = getRaffleComboBoxPanelConstraints();

    private static GridBagConstraints getGridBagConstraints(int gridx, int gridy, int gridwidth, int gridheight,
            Insets insets, double weightx, double weighty, int anchor, int fill) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = gridx;
        c.gridy = gridy;
        c.gridwidth = gridwidth;
        c.gridheight = gridheight;
        c.insets = insets;
        c.weightx = weightx;
        c.weighty = weighty;
        c.anchor = anchor;
        c.fill = fill;
        return c;
    }

    private static GridBagConstraints getWinnersBriefDescConstraints() {
        return getGridBagConstraints(0, 0, 3, 1, DEFAULT_INSETS, 1, 0.2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private static GridBagConstraints getRaffleComboBoxPanelConstraints() {
        return getGridBagConstraints(0, 1, 3, 1, DEFAULT_INSETS, 1, 0.1, GridBagConstraints.PAGE_END, GridBagConstraints.HORIZONTAL);
    }

    private static GridBagConstraints getRaffleWinnerTableConstraints() {
        return getGridBagConstraints(0, 2, 3, 1, DEFAULT_INSETS, 1, 0.5, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
    }

    private static GridBagConstraints getRaffleWinnerButtonPanelConstraints() {
        return getGridBagConstraints(0, 3, 3, 1, DEFAULT_INSETS, 1, 0.2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private static GridBagConstraints getRaffleBriefDescConstraints() {
        return getGridBagConstraints(0, 0, 3, 1, DEFAULT_INSETS,
                1, 0.3, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private static GridBagConstraints getRaffleTableConstraints() {
        return getGridBagConstraints(0, 1, 3, 1, DEFAULT_INSETS,
                1, 0.9, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
    }

    private static GridBagConstraints getRaffleButtonConstraints() {
        return getGridBagConstraints(0, 2, 3, 1, DEFAULT_INSETS,
                1, 0.3, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private static GridBagConstraints getFilterCheckboxConstraints() {
        return getGridBagConstraints(0, 1, 3, 1, DEFAULT_INSETS,
                1, 0.9, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }


    private static GridBagConstraints getFilterBriefDescConstraints() {
        return getGridBagConstraints(0, 0, 3, 1, DEFAULT_INSETS,
                1, 0.1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH);
    }

    private static GridBagConstraints getManualTableConstraints() {
        return getGridBagConstraints(1, 3, 3, 1, new Insets(0, 50, 20, 50),
                1, 0.5, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
    }

    private static GridBagConstraints getManualBriefDescConstraints() {
        return getGridBagConstraints(1, 1, 3, 1, new Insets(0, 50, 20, 50),
                1, 0.2, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH);
    }

    private static GridBagConstraints getManualCheckboxPaneConstraints() {
        return getGridBagConstraints(1, 2, 1, 1, new Insets(0, 50, 20, 50),
                0.5, 0.2, GridBagConstraints.LINE_START, GridBagConstraints.BOTH);
    }

    private static GridBagConstraints getManualComboBoxConstraints() {
        return getGridBagConstraints(3, 2, 1, 1, new Insets(0, 50, 20, 50),
                0.5, 0.2, GridBagConstraints.LAST_LINE_START, GridBagConstraints.BOTH);
    }

    private static GridBagConstraints getManualBackConstraints() {
        return getGridBagConstraints(1, 4, 1, 1, new Insets(0, 50, 20, 50),
                0.5, 0.2, GridBagConstraints.LAST_LINE_START, GridBagConstraints.BOTH);
    }

    private static GridBagConstraints getManualConfirmConstraints() {
        return getGridBagConstraints(3, 4, 1, 1, new Insets(0, 50, 20, 50),
                0.5, 0.2, GridBagConstraints.LAST_LINE_START, GridBagConstraints.BOTH);
    }

    private static GridBagConstraints getADBackConstraints() {
        return getGridBagConstraints(1, 3, 1, 1, new Insets(0, 50, 20, 50),
                0.5, 0.2, GridBagConstraints.LAST_LINE_START, GridBagConstraints.BOTH);
    }

    private static GridBagConstraints getADConfirmConstraints() {
        return getGridBagConstraints(3, 3, 1, 1, new Insets(0, 50, 20, 50),
                0.5, 0.2, GridBagConstraints.LAST_LINE_START, GridBagConstraints.BOTH);
    }

    private static GridBagConstraints getADTableConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.insets = new Insets(0, 50, 20, 50);
        c.weightx = 1;
        c.weighty = 0.2;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        return c;
    }

    private static GridBagConstraints getADBriefDescConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.insets = new Insets(20, 50, 20, 50);
        c.weightx = 1;
        c.weighty = 0.2;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        return c;
    }

    private static GridBagConstraints getInformationConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.insets = new Insets(2, 2, 4, 4);
        c.weightx = 1;
        c.weighty = 0.2;
        c.anchor = GridBagConstraints.PAGE_END;
        c.fill = GridBagConstraints.BOTH;
        return c;
    }

    private static GridBagConstraints getInteractionConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 2;
        c.gridwidth = 1;
        c.insets = new Insets(4, 2, 2, 4);
        c.weightx = 1;
        c.weighty = 0.8;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.fill = GridBagConstraints.BOTH;
        return c;
    }

    private static GridBagConstraints getDescriptionConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 3;
        c.gridwidth = 1;
        c.insets = new Insets(4, 2, 4, 2);
        c.weightx = 0.1;
        c.weighty = 0.7;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.BOTH;
        return c;
    }

    private static GridBagConstraints getToolbarConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 3;
        c.insets = new Insets(0, 0,0, 2);
        c.weightx = 0.01;
        c.weighty = 1;
        c.ipadx = 50;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.BOTH;
        return c;
    }





}
