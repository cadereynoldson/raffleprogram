package main_structure;

public class RaffleSpecs {
    
    /** The entry column name to raffle by. */
    private String entryRaffle;
    
    /** The item column name to raffle by. */
    private String itemRaffle;
    
    /** The count column name in the item spreadsheet. */
    private String countColumn;
    
    /**
     * Creates a new insatance of raffle specs. 
     * @param entryRaffle the column of the entries of the raffle.
     * @param itemRaffle the column of the items in the raffle. 
     * @param countColumn the count column in the items spreadsheet. 
     */
    public RaffleSpecs(String entryRaffle, String itemRaffle, String countColumn) {
        this.entryRaffle = entryRaffle;
        this.itemRaffle = itemRaffle;
        this.countColumn = countColumn;
    }
    
    /**
     * Returns the entry column name of the raffle. 
     * @return the entry column name of the raffle. 
     */
    public String getEntryRaffle() {
        return entryRaffle;
    }

    /**
     * Returns the item column name of the raffle. 
     * @return the item column name of the raffle. 
     */
    public String getItemRaffle() {
        return itemRaffle;
    }

    /**
     * Returns the count column name of the raffle. 
     * @return the count column name of the raffle. 
     */
    public String getCountColumn() {
        return countColumn;
    }
}
