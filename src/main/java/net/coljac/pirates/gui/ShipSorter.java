package net.coljac.pirates.gui;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.coljac.pirates.Ship;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 12, 2006
 */
public class ShipSorter implements Comparator {

    /** The sort order. */
    int[] sortOrder;

    // "Owned", "Wanted", "Card", "Set", "Name", "Points", "Faction", "Masts", "Cargo",
    // "Move", "Guns", "Rules", "Rarity"

    /**
     * Instantiates a new ship sorter.
     */
    public ShipSorter() {
        sortOrder = new int[] { 3, 4, 5, 1, 2, 6, 7, 8, 9, 10, 11, 12, 13 };
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(final Object o1, final Object o2) {
        final Ship ship1 = (Ship) o1;
        final Ship ship2 = (Ship) o2;

        int criterion = 0;
        for (criterion = 0; criterion < sortOrder.length; criterion++) {
            final int result = compareShips(ship1, ship2, sortOrder[criterion]);
            if (result != 0) {
                return result;
            }
        }
        return 0;
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
     * Compare ships.
     * 
     * @param ship1
     *            the ship1
     * @param ship2
     *            the ship2
     * @param criterion
     *            the criterion
     * @return the int
     */
    public int compareShips(Ship ship1, Ship ship2, final int criterion) {
        int c = criterion;
        if (c < 0) {
            c = Math.abs(c);
            final Ship temp = ship1;
            ship1 = ship2;
            ship2 = temp;
        }

        final int result = 0;
        switch (c) {
        case 1: // '\001'
            return ship1.getOwned() - ship2.getOwned();

        case 2: // '\002'
            return ship1.getWanted() - ship2.getWanted();

        case 3: // '\003'
            return compareNums(ship1.getNumber(), ship2.getNumber());

        case 4: // '\004'
            return ship1.getExpansion().compareTo(ship2.getExpansion());

        case 5: // '\005'
            return ship1.getName().compareTo(ship2.getName());

        case 6: // '\006'
            return ship1.getPoints() - ship2.getPoints();

        case 7: // '\007'
            return ship1.getFaction().compareTo(ship2.getFaction());

        case 8: // '\b'
            return ship1.getMasts() - ship2.getMasts();

        case 9: // '\t'
            return ship1.getCargo() - ship2.getCargo();

        case 10: // '\n'
            return ship1.getMoveDistance() - ship2.getMoveDistance();

        case 11: // '\013'
            return ship1.getCannons().compareTo(ship2.getCannons());

        case 12: // '\f'
            return ship1.getRules().compareTo(ship2.getRules());

        case 13: // '\r'
            return compareRarity(ship1.getRarity(), ship2.getRarity());
        }
        return 0;
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

    // "Owned", "Wanted", "Card", "Set", "Name",
    // "Points", "Faction", "Masts", "Cargo", "Move",
    // "Guns", "Rules", "Rarity"

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
     * @param ships
     *            the ships
     */
    public void sort(final List<Ship> ships) {
        Collections.sort(ships, this);
    }

}
