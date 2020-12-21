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

    public static final String HOME_RAFFLE_BUTTON = "Run Raffle!";

    public static final String HOME_INFORMATION_CONTACT_TITLE = strToHTML("<center>Creator Contact Info:<center>");

    public static final String HOME_INFORMATION_CONTACT_EMAIL = strToHTML("<center>cadereynoldson@gmail.com<center>");

    public static final String HOME_INFORMATION_CONTACT_INSTA = strToHTML("<center>Instagram: @olliehole<center>");

    /* DIALOGUE PAGE STRINGS *************************************************************/

    public static final String DIALOGUE_RAFFLE_NOT_READY_TITLE = "Missing raffle steps";

    public static final String DIALOGUE_RAFFLE_NOT_READY = "You haven't completed all of the steps to raffle! " +
            "Check the checklist on the home page to see what you're missing.";

    public static final String DIALOGUE_RAFFLE_MISSING_FILTER_TITLE = "No duplicates removed";

    public static final String DIALOGUE_RAFFLE_MISSING_FILTER = "You haven't removed duplicates from your raffle!" +
            "This could result in a biased raffle towards people who entered more than once." +
            "\nAre you sure you want to continue?";


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
