package net.coljac.pirates;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 5, 2006
 */
public class Crew extends Card {

    /** The rank. */
    private String rank;

    /** The link. */
    private String link;

    /** The is generic. */
    private boolean isGeneric;

    /**
     * Instantiates a new crew.
     */
    public Crew() {
        isGeneric = false;
        cardType = "Crew";
    }

    /**
     * {@inheritDoc}
     * 
     * @see net.coljac.pirates.Card#buildFromString(java.lang.String)
     */
    @Override
    public void buildFromString(final String fromString) {
        super.buildFromString(fromString);
        super.buildFromString(fromString);
        final String tokens[] = fromString.split("\t");
        link = tokens[12];
        rank = tokens[13];
    }

    /**
     * Gets the link.
     * 
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * Gets the rank.
     * 
     * @return the rank
     */
    public String getRank() {
        return rank;
    }

    /**
     * Checks if is generic.
     * 
     * @return true, if is generic
     */
    public boolean isGeneric() {
        return isGeneric;
    }

    /**
     * Sets the generic.
     * 
     * @param generic
     *            the new generic
     */
    public void setGeneric(final boolean generic) {
        isGeneric = generic;
    }

    /**
     * Sets the link.
     * 
     * @param link
     *            the new link
     */
    public void setLink(final String link) {
        this.link = link;
    }

    /**
     * {@inheritDoc}
     * 
     * @see net.coljac.pirates.Card#setName(java.lang.String)
     */
    @Override
    public void setName(final String name) {
        super.setName(name);
        if (name.equals("Captain") || name.equals("Oarsman") || name.equals("Cannoneer") || name.equals("Musketeer") || name.equals("Explorer") || name.equals("Helmsman") || name.equals("Shipwright") || name.equals("Chainshot Specialist") || name.equals("Firepot Specialist")
                || name.equals("Smokepot Specialist") || name.equals("Stinkpot Specialist") || name.equals("Navigator") || name.equals("Silver Explorer")) {
            isGeneric = true;
        }
    }

    /**
     * Sets the rank.
     * 
     * @param rank
     *            the new rank
     */
    public void setRank(final String rank) {
        this.rank = rank;
    }

    /**
     * {@inheritDoc}
     * 
     * @see net.coljac.pirates.Card#toCSV()
     */
    @Override
    public String toCSV() {
        return (new StringBuilder(String.valueOf(super.toCSV()))).append("\t").append(link).append("\t").append(rank).toString();
    }

    /**
     * {@inheritDoc}
     * 
     * @see net.coljac.pirates.Card#toString()
     */
    @Override
    public String toString() {
        return (new StringBuilder("Crew{number='")).append(number).append('\'').append(", name='").append(name).append('\'').append(", expansion='").append(expansion).append('\'').append(", points=").append(points).append(", faction='").append(faction).append('\'').append(", rank='").append(rank)
                .append('\'').append(", rules='").append(rules).append('\'').append(", flavor='").append(flavor).append('\'').append(", extra='").append(extra).append('\'').append(", rarity='").append(rarity).append('\'').append(", link='").append(link).append('\'').append('}').toString();
    }
}
