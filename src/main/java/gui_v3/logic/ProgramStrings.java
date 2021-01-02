package gui_v3.logic;

public class ProgramStrings {

    /* TAB STRINGS *************************************************************/

    public static final String TAB_HOME = "Home";
    public static final String TAB_LOAD_ENTRIES = "<html><center>Load<br/>Entries</center><html>";
    public static final String TAB_LOAD_ITEMS = "<html><center>Load<br/>Items</center><html>";
    public static final String TAB_REMOVE_DUPLICATES = "<html><center>Remove<br/>Duplicates</center><html>";
    public static final String TAB_RAFFLE_WINNERS = "<html><center>Raffle<br/>Winners</center><html>";

    /* HOME PAGE STRINGS *************************************************************/

    /** Title of the home page description. */
    public static final String HOME_DESCRIPTION_TITLE = "Welcome!";

    /** The title of the interaction panel for the home tab. */
    public static final String INTERACTION_HOME_TITLE = "Home";

    /** A brief description to display in the interaction panel. */
    public static final String INTERACTION_HOME_BRIEF_DESCRIPTION = strToHTML("<center>Raffle off items with full control of the entry data.<br/>" +
            "Return to this page once you have everything loaded to run the raffle!</center>");

    /** Line one of the home page description. */
    public static final String HOME_DESCRIPTION_L1 = strToHTML("1. Setup a Google Forms page for your entrants to apply for the raffle in.");

    /** Line two of the home page description. */
    public static final String HOME_DESCRIPTION_L2 = strToHTML("2. Save these entries as a .csv file.");

    /** Line three of the home page description */
    public static final String HOME_DESCRIPTION_L3 = strToHTML("3. Load them into this application!");

    public static final String HOME_DESCRIPTION_LINK_HEADER = strToHTML("See the links for more information:");

    public static final String HOME_DESCRIPTION_ABOUT_LINK = "https://docs.google.com/document/d/1SkGNVDmBWyi0XSRDR0ehsspmcI42kAjV6DOqVYySZe8/edit?usp=sharing";

    public static final String HOME_DESCRIPTION_ABOUT_TEXT = strToHTML("About");

    public static final String HOME_DESCRIPTION_GITHUB_LINK = "https://github.com/cadereynoldson/raffleprogram";

    public static final String HOME_DESCRIPTION_GITHUB_TEXT = strToHTML("GitHub");

    public static final String HOME_CHECKLIST_TITLE = "<html>Raffle Checklist:<html>";

    public static final String HOME_CHECKLIST_ENTRIES = "Set Raffle Entries";

    public static final String HOME_CHECKLIST_ITEMS = "Set Raffle Items";

    public static final String HOME_CHECKLIST_FILTER = "Removed Duplicate Raffle Entries";

    public static final String HOME_RAFFLE_BUTTON = "RUN RAFFLE";

    public static final String HOME_INFORMATION_CONTACT_TITLE = strToHTML("<center>Creator Contact Info:<center>");

    public static final String HOME_INFORMATION_CONTACT_EMAIL = strToHTML("<center>cadereynoldson@gmail.com<center>");

    public static final String HOME_INFORMATION_CONTACT_INSTA = strToHTML("<center>Instagram: @olliehole<center>");


    /* LOAD ENTRIES PAGE STRINGS ****************************************************/

    public  static final String ENTRIES_DESCRIPTION_TITLE = "Load Entries File Description";

    public static final String ENTRIES_DESCRIPTION_L1 = strToHTML("Load the entries of your raffle to the program.");

    public static final String ENTRIES_DESCRIPTION_L2 = strToHTML("This file should be in the form of a .csv or .xls, or .xlsx.");

    public static final String ENTRIES_DESCRIPTION_L3 = strToHTML("Download this file from your google forms-based raffle.");

    public static final String ENTRIES_TITLE = "Load Raffle Entries";

    public static final String ENTRIES_FILE_CHOOSER_TITLE = "Select Entries File";

    public static final String ENTRIES_BRIEF_DESCRIPTION = strToHTML("<center>" +
            "Load in the file containing the information of those who entered your raffle.<center>");

    public static final String ENTRIES_SELECTED_FILE_PROMPT = "Selected Entries File: ";

    public static final String ENTRIES_INFORMATION_FILE_STATUS_PROMPT = "File Status:";

    public static final String ENTRIES_INFORMATION_FILE_STATUS_NO_FILE_LOADED = "No file loaded!";

    public static final String ENTRIES_INFORMATION_FILE_STATUS_FILE_LOADED = "Successfully loaded file!";

    public static final String ENTRIES_INFORMATION_BUTTON_SELECT_FILE = "SELECT FILE";

    public static final String ENTRIES_INFORMATION_BUTTON_RESET_FILE = "RESET FILE";

    /* ITEMS PAGE STRINGS ***********************************************************/

    public static final String ITEMS_AUTO_DETECT_TOGGLE = "Auto Detect";

    public static final String ITEMS_AD_TITLE_P1 = "Load Items (Auto Detect) PT. 1";

    public static final String ITEMS_AD_BRIEF_DESCRIPTION_P1 = strToHTML("<center>Ease your raffle process letting an algorithm detect the items you're raffling." +
                    "<br/>Check the values which would indicate what would uniquely identify a category of raffle item.</center>");


    public static final String ITEMS_AD_DETECTED_PROMPT = strToHTML("<center>Detected Item Identifiers:</center>");

    public static final String ITEMS_AD_NO_ENTRIES_FILE = strToHTML("<center><b>There is no entries file to base auto-detection off of!</b>" +
                    "<br/>Auto detection is least error prone way of setting your raffle items, and is highly encouraged!" +
                    "<br/>Or you can manually input the items by un-checking the auto detect checkbox.</center>");

    public static final String ITEMS_AD_CONTINUE_BUTTON = "CONTINUE";

    public static final String ITEMS_AD_DESCRIPTION_P1_TITLE = "Load Items (AD PT. 1) Description";

    public static final String ITEMS_AD_DESCRIPTION_P1_L1 = strToHTML("Select the checkboxes which would determine a type of item you're raffling.");

    public static final String ITEMS_AD_DESCRIPTION_P1_L2 = strToHTML("Most cases this would only involve checking the \"size\" checkbox," +
                    " as people tend to enter the raffle for a size *blank* shoe!");

    public static final String ITEMS_AD_DESCRIPTION_P1_L3 = strToHTML("Checking multiple boxes would be applicable if you're raffling multiple colorways!" +
                    " An example of this would be to check the checkbox corresponding to size and the checkbox corresponding to color.");

    public static final String ITEMS_AD_TITLE_P2 = "Load Items (Auto Detect) PT. 2";

    public static final String ITEMS_AD_BRIEF_DESCRIPTION_P2 = strToHTML("<center>Fill in the following table with the number of items you have to raffle for the individual items!</center>");

    public static final String ITEMS_AD_DESCRIPTION_P2_TITLE = "Load Items (AD PT. 2) Description";

    public static final String ITEMS_AD_DESCRIPTION_P2_L1 = strToHTML("Fill out the table with the quantities of items you have to raffle.");

    public static final String ITEMS_AD_DESCRIPTION_P2_L2 = strToHTML("Click \"confirm quantities\" to confirm the quantities of each item you have.");

    public static final String ITEMS_AD_DESCRIPTION_P2_L3 = strToHTML("Once quantities are confirmed, you have the bare minimum needed to raffle your items!");

    public static final String ITEMS_AD_BACK = "BACK";

    public static final String ITEMS_AD_CONFIRM_QUANTITIES = "CONFIRM QUANTITIES";

    public static final String ITEMS_AD_INFO_P1_PROMPT = "Auto detect based off of file: ";

    public static final String ITEMS_AD_INFO_P1_NO_ENTRIES = "No entries file to run auto detect on!";

    public static final String ITEMS_AD_INFO_P2_PROMPT = "Items Quantities Status:";

    public static final String ITEMS_AD_INFO_P2_SAVED = "Quantities Confirmed!";

    public static final String ITEMS_AD_INFO_P2_NOT_SAVED = "Quantities are not confirmed!";

    public static final String ITEMS_MANUAL_P1_TITLE = "Load Items (Manual) PT. 1";

    public static final String ITEMS_MANUAL_P1_BRIEF_DESCRIPTION = strToHTML("<center>Manually load in the items of your raffle." +
            "<br/>This is useful if you already have a spreadsheet with counts of your items to raffle.</center>");

    public static final String ITEMS_MANUAL_DESCRIPTION_P1_L1 = strToHTML("Manually load in the items of your raffle.");

    public static final String ITEMS_MANUAL_DESCRIPTION_P1_L2 = strToHTML("This file should at minimum contain columns of:" +
            "<br/> - Identifiers of the items you'll be raffling (Such as size, color)" +
            "<br/> - Quantities you have of each item.");

    public static final String ITEMS_MANUAL_DESCRIPTION_P1_L3 = strToHTML("Click continue to organize the data for the program.");


    public static final String ITEMS_MANUAL_FILE_PROMPT = "Selected Items File: ";

    public static final String ITEMS_MANUAL_FILE_NO_FILE = "No file loaded!";

    public static final String ITEMS_MANUAL_LOAD_FILE = "LOAD FILE";

    public static final String ITEMS_MANUAL_RESET_FILE = "RESET FILE";

    public static final String ITEMS_MANUAL_CONTINUE = "CONTINUE";

    public static final String ITEMS_MANUAL_CONFIRM = "CONFIRM";

    public static final String ITEMS_MANUAL_BACK = "BACK";

    public static final String ITEMS_MANUAL_P2_TITLE = "Load Items (Manual) PT. 2";

    public static final String ITEMS_MANUAL_P2_BRIEF_DESCRIPTION = strToHTML("<center>Format the data to run through the program." +
            "<br/>See the description for the requirements of this step.</center>");

    public static final String ITEMS_MANUAL_P2_WINNERS_BY = strToHTML("<center>Choose Your Winners By:</center>");

    public static final String ITEMS_MANUAL_P2_SHOE_COUNTS = strToHTML("<center>Column of data with item counts</center>");

    public static final String ITEMS_MANUAL_P2_TABLE_TITLE = "Reference Table";

    public static final String ITEMS_MANUAL_FILE_CHOOSER_TITLE = "Select Items File";

    /* DIALOGUE STRINGS *************************************************************/

    public static final String DIALOGUE_RAFFLE_NOT_READY_TITLE = "Missing raffle steps";

    public static final String DIALOGUE_RAFFLE_NOT_READY = "You haven't completed all of the steps needed to raffle! " +
            "\nCheck the list on the home page to see what you're missing.";

    public static final String DIALOGUE_RAFFLE_MISSING_FILTER_TITLE = "No duplicates removed";

    public static final String DIALOGUE_RAFFLE_MISSING_FILTER = "You haven't removed duplicates from your raffle!" +
            "\nThis could result in a biased raffle towards people who entered more than once." +
            "\nAre you sure you want to continue?";

    public static final String DIALOGUE_LOAD_WARNING_TITLE = "Warning";

    public static final String DIALOGUE_LOAD_WARNING = "Warning! Loading a new file will reset all of your progress.\nAre you sure you want to continue?";

    public static final String DIALOGUE_RESET_WARNING_TITLE = "Warning";

    public static final String DIALOGUE_RESET_WARNING = "Resetting your entries file will reset all of your progress!\nAre you sure you want to continue?";

    public static final String DIALOGUE_LOAD_ERROR_TITLE = "Error";

    public static final String DIALOGUE_LOAD_ERROR = "There has been an error loading your file. Double check it is a compatible file type of the program!";

    public static final String DIALOGUE_ITEMS_AD_CONTINUE_ERROR = "You haven't selected any values which would identify your raffle items!";

    public static final String DIALOGUE_ITEMS_AD_QUANTITIES_WARNING = "You have one or more items with a quantity of zero." +
            "\nIs this intended? " +
            "\nSelecting yes will finish the confirm quantities process.";

    public static final String DIALOGUE_ITEMS_AD_QUANTITIES_ERROR = "There are one or more quantity values which are not a number!\n" +
            "All quantities MUST be in the form of a non decimal number, such as \"1\" or \"44\"";

    public static final String DIALOGUE_ITEMS_AD_QUANTITIES_SUCCESS = "Successfully Confirmed Quantities!";

    public static final String DIALOGUE_ITEMS_AD_NAV_AWAY_TITLE = "Warning";

    public static final String DIALOGUE_ITEMS_AD_NAV_AWAY_MESSAGE = "You haven't saved your item quantities!" +
            "\nAre you sure you want to navigate away from this page?";

    public static final String DIALOGUE_SUCCESS_TITLE = "Success";

    public static final String DIALOGUE_ITEMS_MANUAL_NO_FILE_MESSAGE = "You haven't loaded in an items file!";

    public static final String QUANTITY_COLUMN_NAME = "Quantity";

    /**
     * Converts a string to HTML so lines can wrap within a JLabel.
     * Surrounds the string with "<html> <html>" tags.
     * @param s the string to convert to HTML.
     * @return the string s converted to html (
     */
    public static final String strToHTML(String s) {
        return "<html>" + s + "</html>";
    }

    public static final String getUnderlinedHTMLString(String s) {
        return strToHTML("<u>" + s + "</u>");
    }

}
