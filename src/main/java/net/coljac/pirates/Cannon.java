package net.coljac.pirates;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 13, 2006
 */
public class Cannon {

    /** The range. */
    private Distance range;

    /** The number. */
    private int number;

    /**
     * Instantiates a new cannon.
     * 
     * @param description
     *            the description
     */
    public Cannon(String description) {
        try {
            if (description.length() == 1) {
                this.range = Distance.S;
            }
            this.range = Distance.valueOf(description.charAt(1) + "");
            this.number = Integer.parseInt(description.charAt(0) + "");
        } catch (Exception e) {
        }
    }

    /**
     * Instantiates a new cannon.
     * 
     * @param range
     *            the range
     * @param number
     *            the number
     */
    public Cannon(Distance range, int number) {
        this.range = range;
        this.number = number;
    }

    /**
     * Gets the range.
     * 
     * @return the range
     */
    public Distance getRange() {
        return range;
    }

    /**
     * Sets the range.
     * 
     * @param range
     *            the new range
     */
    public void setRange(Distance range) {
        this.range = range;
    }

    /**
     * Gets the number.
     * 
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the number.
     * 
     * @param number
     *            the new number
     */
    public void setNumber(int number) {
        this.number = number;
    }

}
