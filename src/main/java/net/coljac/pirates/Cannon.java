package net.coljac.pirates;

import java.io.Serializable;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 13, 2006
 */
public class Cannon implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** The range. */
    private Distance range;

    /** The number. */
    private int number;

    /**
     * Instantiates a new cannon.
     * 
     * @param range
     *            the range
     * @param number
     *            the number
     */
    public Cannon(final Distance range, final int number) {
        this.range = range;
        this.number = number;
    }

    /**
     * Instantiates a new cannon.
     * 
     * @param description
     *            the description
     */
    public Cannon(final String description) {
        try {
            if (description.length() == 1) {
                range = Distance.S;
            }
            range = Distance.valueOf(description.charAt(1) + "");
            number = Integer.parseInt(description.charAt(0) + "");
        } catch (final Exception e) {
        }
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
     * Gets the range.
     * 
     * @return the range
     */
    public Distance getRange() {
        return range;
    }

    /**
     * Sets the number.
     * 
     * @param number
     *            the new number
     */
    public void setNumber(final int number) {
        this.number = number;
    }

    /**
     * Sets the range.
     * 
     * @param range
     *            the new range
     */
    public void setRange(final Distance range) {
        this.range = range;
    }

}
