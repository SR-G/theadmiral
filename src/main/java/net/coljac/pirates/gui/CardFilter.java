package net.coljac.pirates.gui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.coljac.pirates.Card;
import net.coljac.pirates.Ship;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 11, 2006
 */
public class CardFilter {

    /** The factions. */
    private Set<String> factions = new HashSet<String>();

    /** The sets. */
    private Set<String> sets = new HashSet<String>();

    /** The point ranges. */
    private Set<PointRange> pointRanges = new HashSet<PointRange>();

    /** The search. */
    private String[] search = new String[0];

    /** The have. */
    boolean have = false;

    /** The want. */
    boolean want = false;

    /** The want. */
    boolean duplicates = false;

    /**
     * Filter.
     * 
     * @param cards
     *            the cards
     * @return the list<? extends card>
     */
    public List<? extends Card> filter(final List<? extends Card> cards) {
        if ((factions.size() == 0) && (sets.size() == 0) && (pointRanges.size() == 0) && (search.length == 0) && !have && !want && !duplicates) {
            return cards;
        }

        final List<Card> filtered = new ArrayList<Card>();
        for (final Card card : cards) {
            if (want || have || duplicates) {
                if (want && (card.getWanted() == 0)) {
                    continue;
                }
                if (duplicates && (card.getOwned() <= 1)) {
                    continue;
                }
                if (have && (card.getOwned() == 0)) {
                    continue;
                }
            }
            if ((factions != null) && (factions.size() > 0)) {
                if (!factions.contains(card.getFaction())) {
                    continue;
                }
            }
            if ((sets != null) && (sets.size() > 0)) {
                if (!sets.contains(Ship.getSetAbbreviation(card.getExpansion()))) {
                    continue;
                }
            }
            if ((pointRanges != null) && (pointRanges.size() > 0)) {
                final PointRange pr = PointRange.getPointRange(card.getPoints());
                if (!pointRanges.contains(pr)) {
                    continue;
                }
            }
            if ((search != null) && (search.length > 0)) {
                boolean remove = false;
                for (final String s : search) {
                    final String term = s.toUpperCase();
                    if (!card.getName().toUpperCase().contains(term) && !card.getRules().toUpperCase().contains(term)) {
                        remove = true;
                        break;
                    }
                }
                if (remove) {
                    continue;
                }
            }
            filtered.add(card);
        }
        return filtered;
    }

    /**
     * Gets the factions.
     * 
     * @return the factions
     */
    public Set<String> getFactions() {
        return factions;
    }

    /**
     * Gets the point ranges.
     * 
     * @return the point ranges
     */
    public Set<PointRange> getPointRanges() {
        return pointRanges;
    }

    /**
     * Gets the search.
     * 
     * @return the search
     */
    public String getSearch() {
        return search.toString();
    }

    /**
     * Gets the sets.
     * 
     * @return the sets
     */
    public Set<String> getSets() {
        return sets;
    }

    /**
     * Checks if is duplicates.
     * 
     * @return true, if is duplicates
     */
    public boolean isDuplicates() {
        return duplicates;
    }

    /**
     * Checks if is have.
     * 
     * @return true, if is have
     */
    public boolean isHave() {
        return have;
    }

    /**
     * Checks if is want.
     * 
     * @return true, if is want
     */
    public boolean isWant() {
        return want;
    }

    /**
     * Sets the duplicates.
     * 
     * @param duplicates
     *            the new duplicates
     */
    public void setDuplicates(final boolean duplicates) {
        this.duplicates = duplicates;
    }

    /**
     * Sets the factions.
     * 
     * @param factions
     *            the new factions
     */
    public void setFactions(final Set<String> factions) {
        this.factions = factions;
    }

    /**
     * Sets the have.
     * 
     * @param have
     *            the new have
     */
    public void setHave(final boolean have) {
        this.have = have;
    }

    /**
     * Sets the point ranges.
     * 
     * @param pointRanges
     *            the new point ranges
     */
    public void setPointRanges(final Set<PointRange> pointRanges) {
        this.pointRanges = pointRanges;
    }

    /**
     * Sets the search.
     * 
     * @param searchTerms
     *            the new search
     */
    public void setSearch(final String searchTerms) {
        search = searchTerms.trim().split(" *, *");
    }

    /**
     * Sets the sets.
     * 
     * @param sets
     *            the new sets
     */
    public void setSets(final Set<String> sets) {
        this.sets = sets;
    }

    /**
     * Sets the want.
     * 
     * @param want
     *            the new want
     */
    public void setWant(final boolean want) {
        this.want = want;
    }
}
