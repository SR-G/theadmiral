package net.coljac.pirates.gui;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.coljac.pirates.Card;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 12, 2006
 */
public class CardSorter implements Comparator {

    /** The sort order. */
    int[] sortOrder;

    /**
     * Instantiates a new card sorter.
     */
    public CardSorter() {
        sortOrder = new int[] { 3, 5, 6, 1, 2, 4, 7, 8 };
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(final Object o1, final Object o2) {
        final Card card1 = (Card) o1;
        final Card card2 = (Card) o2;

        int criterion = 0;
        for (criterion = 0; criterion < sortOrder.length; criterion++) {
            final int result = compareCards(card1, card2, sortOrder[criterion]);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    /**
     * Compare cards.
     * 
     * @param card1
     *            the card1
     * @param card2
     *            the card2
     * @param criterion
     *            the criterion
     * @return the int
     */
    public int compareCards(Card card1, Card card2, final int criterion) {
        int c = criterion;
        if (c < 0) {
            c = Math.abs(c);
            final Card temp = card1;
            card1 = card2;
            card2 = temp;
        }

        final int result = 0;
        switch (c) {
        case 1: // '\001'
            return card1.getOwned() - card2.getOwned();

        case 2: // '\002'
            return card1.getWanted() - card2.getWanted();

        case 3: // '\003'
            return card1.getCardType().compareTo(card2.getCardType());

        case 4: // '\004'
            return compareNums(card1.getNumber(), card2.getNumber());

        case 5: // '\005'
            return card1.getExpansion().compareTo(card2.getExpansion());

        case 6: // '\006'
            return card1.getName().compareTo(card2.getName());

        case 7: // '\007'
            return card1.getPoints() - card2.getPoints();

        case 8: // '\b'
            return card1.getRules().compareTo(card2.getRules());

        case 9: // '\t'
            return compareRarity(card1.getRarity(), card2.getRarity());
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
     * @param cards
     *            the cards
     */
    public void sort(final List<Card> cards) {
        Collections.sort(cards, this);
    }

}
