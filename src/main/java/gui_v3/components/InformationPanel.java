package gui_v3.components;

import gui_v3.logic.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class InformationPanel extends DisplayPanel {

    /** The timer to display filtering changes with. Mostly serves the purpose of looking pretty. */
    private Timer filteringTimer;

    /** The information of the filtering timer. */
    private FilteringInfo filteringInfo;

    public InformationPanel() {
        super();
        initFilteringTimer();
        initTheme();
        setHome();
    }

    /**
     * Initializing the filtering timer. Makes the program look fancy.
     */
    private void initFilteringTimer() {
        filteringTimer = new Timer(1, e -> {
            if (RaffleDataStorage.getCurrentEntriesSheet().getNumRows() < RaffleDataStorage.getOriginalEntriesSheet().getNumRows() - filteringInfo.currentRemovedValue) {
                filteringInfo.currentRemovedValue++;
            } else if (RaffleDataStorage.getCurrentEntriesSheet().getNumRows() > RaffleDataStorage.getOriginalEntriesSheet().getNumRows() - filteringInfo.currentRemovedValue) {
                filteringInfo.currentRemovedValue--;
            } else {
                filteringTimer.stop();
            }
            filteringInfo.currentCountLabel.setText(Integer.toString(filteringInfo.originalEntriesCount - filteringInfo.currentRemovedValue));
            filteringInfo.removedCountLabel.setText(Integer.toString(filteringInfo.currentRemovedValue));
        });
    }

    private void initTheme() {
        setBackground(ProgramColors.FOREGROUND_COLOR);
        setBorder(BorderFactory.createLineBorder(ProgramColors.TEXT_ON_FG_COLOR));
    }

    /**
     * Updates the description based on the labels provided. WARNING: Will set the filtering info reference equal to null.
     * @param labels the labels to set as this information.
     */
    private void updateInformation(JLabel ... labels) {
        removeAll();
        setLayout(new GridLayout(0, 1));
        filteringInfo = null;
        for (JLabel label : labels)
            add(label);
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
        if (RaffleDataStorage.hasEntriesFile()) {
            JLabel status, entryCount, columnCount;
            status = ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.getUnderlinedHTMLString(ProgramStrings.ENTRIES_INFORMATION_FILE_STATUS_FILE_LOADED));
            String entryCountStr = ProgramStrings.ENTRIES_INFORMATION_ROW_COUNT_PROMPT + RaffleDataStorage.getOriginalEntriesSheet().getNumRows();
            StringBuilder columnCountStr = new StringBuilder(ProgramStrings.ENTRIES_INFORMATION_COL_COUNT_PROMPT + RaffleDataStorage.getOriginalEntriesSheet().getNumColumns() + " (");
            String[] columnNames = RaffleDataStorage.getOriginalEntriesSheet().getColumnNames();
            for (int i = 0; i < columnNames.length - 1; i++) {
                columnCountStr.append(columnNames[i]);
                columnCountStr.append(", ");
            }
            columnCountStr.append(columnNames[columnNames.length - 1]);
            columnCountStr.append(")");
            entryCount = ProgramDefaults.getCenterAlignedInteractionLabel(entryCountStr);
            columnCount = ProgramDefaults.getCenterAlignedInteractionLabel(columnCountStr.toString());
            updateInformation(status, entryCount, columnCount);
        } else {
            updateInformation(
                    ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.ENTRIES_INFORMATION_FILE_STATUS_PROMPT),
                    ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.getUnderlinedHTMLString(ProgramStrings.ENTRIES_INFORMATION_FILE_STATUS_NO_FILE_LOADED))
            );
        }
    }

    @Override
    public void setLoadItems_autoDetect_pt1() {
        if (RaffleDataStorage.hasEntriesFile()) {
            updateInformation(
                    ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.ITEMS_AD_INFO_P1_PROMPT),
                    ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.getUnderlinedHTMLString(RaffleDataStorage.getEntriesFileString()))
            );
        }
        else
            updateInformation(ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.ITEMS_NO_ENTRIES_BRIEF_DESC_L2));

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
        JLabel status;
        if (RaffleDataStorage.hasItemsFile())
            status = ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.getUnderlinedHTMLString(ProgramStrings.ITEMS_MANUAL_FILE_LOADED));
        else
            status = ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.getUnderlinedHTMLString(ProgramStrings.ITEMS_MANUAL_FILE_NO_FILE));
        updateInformation(
                ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.ITEMS_MANUAL_FILE_PROMPT),
                status
            );
    }

    @Override
    public void setLoadItems_manual_pt2() {
        JLabel status;
        if (RaffleDataStorage.hasItems()) {
            status = ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.getUnderlinedHTMLString(ProgramStrings.ITEMS_MANUAL_SAVED));
        } else {
            status = ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.getUnderlinedHTMLString(ProgramStrings.ITEMS_MANUAL_NOT_SAVED));
        }
        updateInformation(
                ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.ITEMS_MANUAL_INFO_PROMPT),
                status
        );
    }

    public void updateDuplicateCount() {
        if (RaffleDataStorage.hasEntriesFile() && filteringInfo != null) {
            filteringTimer.restart();
            filteringTimer.start();
        }
    }

    @Override
    public void setRemoveDuplicates() {
        if (RaffleDataStorage.hasEntriesFile()) {
            filteringInfo = new FilteringInfo();
            JLabel originalCount = ProgramDefaults.getCenterAlignedInteractionLabel("" + filteringInfo.originalEntriesCount);
            JPanel ccp = ProgramDefaults.getBlankPanel(new BorderLayout());
            ccp.setBorder(getFilteringCountBorder(ProgramStrings.FILTER_INFO_CURRENT_COUNT));
            ccp.add(filteringInfo.currentCountLabel, BorderLayout.CENTER);
            JPanel rcp = ProgramDefaults.getBlankPanel(new BorderLayout());
            rcp.setBorder(getFilteringCountBorder(ProgramStrings.FILTER_INFO_DUPLICATES_REMOVED));
            rcp.add(filteringInfo.removedCountLabel, BorderLayout.CENTER);
            JPanel ocp = ProgramDefaults.getBlankPanel(new BorderLayout());
            ocp.setBorder(getFilteringCountBorder(ProgramStrings.FILTER_INFO_ORIGINAL_COUNT));
            ocp.add(originalCount, BorderLayout.CENTER);
            //Add all these borders.
            removeAll();
            setLayout(new GridLayout(1, 0));
            add(ocp);
            add(ccp);
            add(rcp);
        } else {
            updateInformation(ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.FILTER_NO_ENTRIES_L1));
        }
    }

    @Override
    public void setRunRaffleReview() {
        if (RaffleDataStorage.hasEntriesFile() && RaffleDataStorage.hasItemsFile()) { //If raffle is ready to run :
            removeAll();
            setLayout(new GridLayout(0, 2));
            filteringInfo = new FilteringInfo();
            if (!RaffleDataStorage.hasFiltered()) { //Display no filter warning.

            }
            JPanel entriesDisplay = ProgramDefaults.getBlankPanel(new GridLayout(0, 2));
            entriesDisplay.setBorder(getFilteringCountBorder(ProgramStrings.RAFFLE_REVIEW_ENTRIES_TITLE));
            entriesDisplay.add(ProgramDefaults.getLeftAlignedInteractionLabel(ProgramStrings.RAFFLE_REVIEW_INFO_FILE));
            entriesDisplay.add(ProgramDefaults.getLeftAlignedInteractionLabel(ProgramStrings.getFileName(RaffleDataStorage.getEntriesFileString())));
            entriesDisplay.add(ProgramDefaults.getLeftAlignedInteractionLabel(ProgramStrings.RAFFLE_REVIEW_ENTRIES_COUNT));
            entriesDisplay.add(filteringInfo.currentCountLabel);
            JPanel itemsDisplay = ProgramDefaults.getBlankPanel(new GridLayout(0, 2));
            itemsDisplay.setBorder(getFilteringCountBorder(ProgramStrings.RAFFLE_REVIEW_ITEMS_TITLE));
            itemsDisplay.add(ProgramDefaults.getLeftAlignedInteractionLabel(ProgramStrings.RAFFLE_REVIEW_INFO_FILE));
            if (RaffleDataStorage.usingAutodetect())
                itemsDisplay.add(ProgramDefaults.getLeftAlignedInteractionLabel(RaffleDataStorage.getItemsFileString()));
            else
                itemsDisplay.add(ProgramDefaults.getLeftAlignedInteractionLabel(ProgramStrings.getFileName(RaffleDataStorage.getItemsFileString())));
            itemsDisplay.add(ProgramDefaults.getLeftAlignedInteractionLabel(ProgramStrings.RAFFLE_REVIEW_ITEMS_COUNT));
            itemsDisplay.add(ProgramDefaults.getCenterAlignedInteractionLabel(String.valueOf(RaffleDataStorage.getTotalNumItems())));
            add(entriesDisplay);
            add(itemsDisplay);
        } else {
            updateInformation(ProgramDefaults.getCenterAlignedInteractionLabel(ProgramStrings.RAFFLE_REVIEW_NOT_READY));
        }
    }


    @Override
    public void setRunRaffleWinners() {

    }

    private Border getFilteringCountBorder(String title) {
        return BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), title, TitledBorder.CENTER, TitledBorder.BELOW_TOP, ProgramFonts.DEFAULT_FONT_LARGE);
    }

    /**
     * Filtering info class. Helps with the animation of entries data information changes.
     */
    private class FilteringInfo {

        /** The original count of entries in this panel. */
        private int originalEntriesCount;

        /** The ultimate current removed value to display. Subtract this from the original entries count for the current entries. */
        private int currentRemovedValue;

        /** The current count of information in the raffle. */
        private JLabel currentCountLabel;

        /** The count of entrants removed from the raffle. */
        private JLabel removedCountLabel;

        /**
         * Creates a new instance of timer info. This class must have a valid raffle entries file loaded for it to be properly instantiated.
         */
        public FilteringInfo() {
            if (RaffleDataStorage.hasEntriesFile()) {
                originalEntriesCount = RaffleDataStorage.getOriginalEntriesSheet().getNumRows();
                currentRemovedValue = originalEntriesCount - RaffleDataStorage.getCurrentEntriesSheet().getNumRows();
                currentCountLabel = ProgramDefaults.getCenterAlignedInteractionLabel("" + RaffleDataStorage.getCurrentEntriesSheet().getNumRows());
                removedCountLabel = ProgramDefaults.getCenterAlignedInteractionLabel("" + currentRemovedValue);
            } else {
                throw new IllegalArgumentException("No filtering info can be captured as there is nolpopo entries file loaded.");
            }
        }
    }
}
