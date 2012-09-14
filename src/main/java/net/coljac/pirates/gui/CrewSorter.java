package net.coljac.pirates.gui;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.coljac.pirates.Crew;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 12, 2006
 */
public class CrewSorter implements Comparator {

    /** The sort order. */
    int[] sortOrder;

    /**
     * Instantiates a new crew sorter.
     */
    public CrewSorter() {
        sortOrder = new int[] { 3, 4, 5, 1, 2, 6, 7, 8, 9, 10 };
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(final Object o1, final Object o2) {
        final Crew crew1 = (Crew) o1;
        final Crew crew2 = (Crew) o2;

        int criterion = 0;
        for (criterion = 0; criterion < sortOrder.length; criterion++) {
            final int result = compareCrew(crew1, crew2, sortOrder[criterion]);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    /**
     * Compare crew.
     * 
     * @param crew1
     *            the crew1
     * @param crew2
     *            the crew2
     * @param criterion
     *            the criterion
     * @return the int
     */
    public int compareCrew(Crew crew1, Crew crew2, final int criterion) {
        int c = criterion;
        if (c < 0) {
            c = Math.abs(c);
            final Crew temp = crew1;
            crew1 = crew2;
            crew2 = temp;
        }

        final int result = 0;
        switch (c) {
        case 1: // '\001'
            return crew1.getOwned() - crew2.getOwned();

        case 2: // '\002'
            return crew1.getWanted() - crew2.getWanted();

        case 3: // '\003'
            return compareNums(crew1.getNumber(), crew2.getNumber());

        case 4: // '\004'
            return crew1.getExpansion().compareTo(crew2.getExpansion());

        case 5: // '\005'
            return crew1.getName().compareTo(crew2.getName());

        case 6: // '\006'
            return crew1.getPoints() - crew2.getPoints();

        case 7: // '\007'
            return crew1.getFaction().compareTo(crew2.getFaction());

        case 8: // '\b'
            return crew1.getRules().compareTo(crew2.getRules());

        case 9: // '\t'
            return compareLink(crew1.getLink(), crew2.getLink());

        case 10: // '\n'
            return compareRarity(crew1.getRarity(), crew2.getRarity());
        }
        return 0;
    }

    // "O", "W", "Card", "Set", "Name", "Points", "Faction", "Rules", "Link", "Rarity"

    /**
     * Compare link.
     * 
     * @param l1
     *            the l1
     * @param l2
     *            the l2
     * @return the int
     */
    private int compareLink(final String l1, final String l2) {
        if ((l1 == null) && (l2 == null)) {
            return 0;
        }
        if ((l1 == null) && (l2 != null)) {
            return 1;
        }
        if ((l1 != null) && (l2 == null)) {
            return -1;
        }
        return l1.compareTo(l2);
    }

    /**
     * Compare nums.
     * 
     * @param s1
     *            the s1
     * @param s2
     *            the s2
     * @return the int
     */
    private int compareNums(final String s1, final String s2) {
        try {
            final int num1 = Integer.parseInt(s1);
            final int num2 = Integer.parseInt(s2);
            return num1 - num2;
        } catch (final NumberFormatException e) {
            return s1.compareTo(s2);
        }
    }

    /**
     * Compare rarity.
     * 
     * @param r1
     *            the r1
     * @param r2
     *            the r2
     * @return the int
     */
    private int compareRarity(String r1, String r2) {
        r1 = r1.toUpperCase();
        r2 = r2.toUpperCase();

        return getRarityIndex(r1) - getRarityIndex(r2);
    }

    /**
     * Gets the rarity index.
     * 
     * @param rarity
     *            the rarity
     * @return the rarity index
     */
    private int getRarityIndex(final String rarity) {
        if (rarity.startsWith("C")) {
            return 1;
        }
        if (rarity.startsWith("U")) {
            return 2;
        }
        if (rarity.startsWith("R")) {
            return 3;
        }
        if (rarity.startsWith("S")) {
            return 4;
        }
        if (rarity.startsWith("T")) {
            return 5;
        }
        if (rarity.startsWith("L")) {
            return 6;
        }
        if (rarity.startsWith("V")) {
            return 7;
        }
        if (rarity.startsWith("M")) {
            return 8;
        }
        return !rarity.startsWith("P") ? 10 : 9;
    }

    /**
     * Gets the sort order.
     * 
     * @return the sort order
     */
    public int[] getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the sort.
     * 
     * @param column
     *            the new sort
     */
    public void setSort(final int column) {
        if (Math.abs(sortOrder[0]) == column) {
            // Second click - reverse sort order
            sortOrder[0] = -1 * sortOrder[0];
            return;
        }
        final int[] newSort = new int[sortOrder.length];
        newSort[0] = column;
        int j = 1;
        for (final int element : sortOrder) {
            if (element != column) {
                newSort[j++] = element;
                if (j >= newSort.length) {
                    break;
                }
            }
        }
        sortOrder = newSort;
    }

    /**
     * Sets the sort order.
     * 
     * @param sortOrder
     *            the new sort order
     */
    public void setSortOrder(final int[] sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Sort.
     * 
     * @param crew
     *            the crew
     */
    public void sort(final List<Crew> crew) {
        Collections.sort(crew, this);
    }

}
