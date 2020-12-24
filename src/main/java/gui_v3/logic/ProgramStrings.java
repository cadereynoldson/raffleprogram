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

    public static final String ENTRIES_BRIEF_DESCRIPTION = strToHTML("<center>" +
            "Load in the file containing the information of those who entered your raffle.<center>");

    public static final String ENTRIES_SELECTED_FILE_PROMPT = "Selected Entries File: ";

    public static final String ENTRIES_INFORMATION_FILE_STATUS_PROMPT = "File Status:";

    public static final String ENTRIES_INFORMATION_FILE_STATUS_NO_FILE_LOADED = "No file loaded!";

    public static final String ENTRIES_INFORMATION_FILE_STATUS_FILE_LOADED = "Successfully loaded file!";

    public static final String ENTRIES_INFORMATION_BUTTON_SELECT_FILE = "SELECT FILE";

    public static final String ENTRIES_INFORMATION_BUTTON_RESET_FILE = "RESET FILE";

    /* ITEMS PAGE STRINGS ***********************************************************/

    public static final String ITEMS_AUTO_DETECT_TOGGLE = "   Auto Detect";

    public static final String ITEMS_AD_TITLE_P1 = "Auto Detect Part 1";

    public static final String ITEMS_AD_BRIEF_DESCRIPTION_P1 = strToHTML("<center>Ease your raffle process letting an algorithm detect the items you're raffling." +
                    "<br/>Check the values which would indicate what would uniquely identify a category of raffle item." +
                    "<br/>Common values for this are size, and color. Combine the two for raffling by size and color!</center>");


    public static final String ITEMS_AD_DETECTED_PROMPT = strToHTML("<center>Detected Item Identifiers:</center>");

    public static final String ITEMS_AD_CONTINUE_BUTTON = "CONTINUE";

    public static final String ITEMS_AD_DESCRIPTION_P1_TITLE = "AD Part 1 Description";

    public static final String ITEMS_AD_DESCRIPTION_P1_L1 = strToHTML("Select the checkboxes which would determine a type of item you're raffling.");

    public static final String ITEMS_AD_DESCRIPTION_P1_L2 = strToHTML("Most cases this would only involve checking the \"size\" checkbox," +
                    " as people tend to enter the raffle for a size *blank* shoe!");

    public static final String ITEMS_AD_DESCRIPTION_P1_L3 = strToHTML("Checking multiple boxes would be applicable if you're raffling multiple colorways!" +
                    " An example of this would be to check the checkbox corresponding to size and the checkbox corresponding to color.");

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

    public static final String DIALOGUE_LOAD_ERROR_TITLE = "ERROR";

    public static final String DIALOGUE_LOAD_ERROR = "There has been an error loading your file. Double check it is a compatible file type of the program!";

    /**
     * Converts a string to HTML so lines can wrap within a JLabel.
     * Surrounds the string with "<html> <html>" tags.
     * @param s the string to convert to HTML.
     * @return the string s converted to html (
     */
    public static final String strToHTML(String s) {
        return "<html>" + s + "</html>";
    }

}
