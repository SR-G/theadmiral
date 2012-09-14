package net.coljac.pirates;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 21, 2006
 */
public class Fort extends Ship {

    /** serialVersionUID */
    private static final long serialVersionUID = 2873115845625827943L;

    /** The gold cost. */
    private int goldCost;

    /**
     * Instantiates a new fort.
     */
    public Fort() {
        cardType = "Fort";
    }

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
    public void setGoldCost(final int goldCost) {
        this.goldCost = goldCost;
    }
}
