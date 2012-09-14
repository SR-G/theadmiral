package net.coljac.pirates.gui;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 11, 2006
 */
public enum PointRange {

    /** The one to five. */
    oneToFive, /** The six to ten. */
    sixToTen, /** The eleven to fifteen. */
    elevenToFifteen, /** The sixteen plus. */
    sixteenPlus;

    /**
     * Gets the point range.
     * 
     * @param points
     *            the points
     * @return the point range
     */
    public static PointRange getPointRange(int points) {
        if (points < 6) {
            return oneToFive;
        } else if (points < 11) {
            return sixToTen;
        } else if (points < 16) {
            return elevenToFifteen;
        }
        return sixteenPlus;
    }

}
