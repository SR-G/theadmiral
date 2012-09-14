package net.coljac.pirates;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 21, 2006
 */
public class Fort extends Ship {

    /** The gold cost. */
    private int goldCost;

    /**
     * Gets the gold cost.
     * 
     * @return the gold cost
     */
    public int getGoldCost() {
        return goldCost;
    }

    /**
     * Sets the gold cost.
     * 
     * @param goldCost
     *            the new gold cost
     */
    public void setGoldCost(int goldCost) {
        this.goldCost = goldCost;
    }

    /**
     * Instantiates a new fort.
     */
    public Fort() {
        this.cardType = "Fort";
    }
}
