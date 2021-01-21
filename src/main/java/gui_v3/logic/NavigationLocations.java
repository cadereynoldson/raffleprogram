package gui_v3.logic;

/**
 * Enum for MAIN navigation locations. Each value represents a tab.
 */
public enum NavigationLocations {
    HOME, ENTRIES, ITEMS, ITEMS_AUTO_DETECT_PT1, ITEMS_AUTO_DETECT_PT2, ITEMS_MANUAL_PT1, ITEMS_MANUAL_PT2, FILTER, RUN_RAFFLE, RUN_RAFFLE_REVIEW, RUN_RAFFLE_SHOW_WINNERS;

    /**
     * Indicates if the location to travel to is an item location.
     * @return true/false based on if a location is an item location.
     */
    public static boolean isItemLocation(NavigationLocations location) {
        return (location == ITEMS) || (location == ITEMS_AUTO_DETECT_PT1) || (location == ITEMS_AUTO_DETECT_PT2)
                || (location ==  ITEMS_MANUAL_PT1) || (location == ITEMS_MANUAL_PT2);
    }
}
