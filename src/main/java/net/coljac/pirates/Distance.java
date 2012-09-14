package net.coljac.pirates;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 13, 2006
 */
public enum Distance {

    /** The s. */
    S("S", 0, 58),

    /** The l. */
    L("L", 1, 84),

    /** The t. */
    T("T", 2, 10),

    /** The d. */
    D("D", 3, 99);

    /** The millis. */
    private int millis;

    /**
     * Instantiates a new distance.
     * 
     * @param millis
     *            the millis
     */
    Distance(final int millis) {
        this.millis = millis;
    }

    /**
     * Instantiates a new distance.
     * 
     * @param s
     *            the s
     * @param i
     *            the i
     * @param millis
     *            the millis
     */
    private Distance(final String s, final int i, final int millis) {
        this.millis = millis;
    }

    /**
     * Gets the millis.
     * 
     * @return the millis
     */
    public int getMillis() {
        return millis;
    }

}
