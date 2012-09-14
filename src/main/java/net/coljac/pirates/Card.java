package net.coljac.pirates;

import java.io.Serializable;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 11, 2006
 */
public abstract class Card implements Serializable {

    /**
     * Builds the.
     * 
     * @param fromString
     *            the from string
     * @return the card
     */
    public static Card build(final String fromString) {
        final String tokens[] = fromString.split("\t");
        Card card;
        if (tokens[0].equals("Fort")) {
            card = new Fort();
        } else if (tokens[0].equals("Ship")) {
            card = new Ship();
        } else if (tokens[0].equals("Crew")) {
            card = new Crew();
        } else if (tokens[0].equals("Event")) {
            card = new Event();
        } else if (tokens[0].equals("Equipment")) {
            card = new Equipment();
        } else if (tokens[0].equals("Treasure")) {
            card = new Treasure();
        } else {
            return null;
        }
        card.buildFromString(fromString);
        return card;
    }

    /**
     * Gets the rarity abbreviation.
     * 
     * @param rarity
     *            the rarity
     * @return the rarity abbreviation
     */
    public static String getRarityAbbreviation(final String rarity) {
        if ((rarity != null) && (rarity.length() > 0)) {
            return (new StringBuilder(String.valueOf(rarity.charAt(0)))).toString();
        } else {
            return rarity;
        }
    }

    /**
     * Gets the sets the abbreviation.
     * 
     * @param setName
     *            the set name
     * @return the sets the abbreviation
     */
    public static String getSetAbbreviation(final String setName) {
        final Expansion set = Expansions.getExpansion(setName);
        if (set != null) {
            return set.getAbbreviation();
        } else {
            return setName;
        }
    }

    /** The id. */
    protected long id;

    /** The number. */
    protected String number;

    /** The name. */
    protected String name;

    /** The expansion. */
    protected String expansion;

    /** The points. */
    protected int points;

    /** The extra. */
    protected String extra;

    /** The rarity. */
    protected String rarity;

    /** The rules. */
    protected String rules;

    /** The flavor. */
    protected String flavor;

    /** The faction. */
    protected String faction;

    /** The owned. */
    protected int owned;

    /** The wanted. */
    protected int wanted;

    /** The card type. */
    protected String cardType;

    /**
     * Instantiates a new card.
     */
    public Card() {
        number = "";
        name = "";
        expansion = "";
        rules = "";
    }

    /**
     * Builds the from string.
     * 
     * @param fromString
     *            the from string
     */
    public void buildFromString(final String fromString) {
        final String tokens[] = fromString.split("\t");
        setNumber(tokens[1]);
        setName(tokens[2]);
        setOwned(Integer.parseInt(tokens[3]));
        setWanted(Integer.parseInt(tokens[4]));
        setFaction(tokens[5]);
        setExpansion(tokens[6]);
        setPoints(Integer.parseInt(tokens[7]));
        setRarity(tokens[8]);
        setRules(tokens[9]);
        setFlavor(tokens[10]);
        setExtra(tokens[11]);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        final Card that = (Card) obj;
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        return number.equals(that.number) && name.equals(that.name) && expansion.equals(that.expansion) && rules.equals(that.rules);
    }

    /**
     * Gets the card type.
     * 
     * @return the card type
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * Gets the expansion.
     * 
     * @return the expansion
     */
    public String getExpansion() {
        return expansion;
    }

    /**
     * Gets the extra.
     * 
     * @return the extra
     */
    public String getExtra() {
        return extra;
    }

    /**
     * Gets the faction.
     * 
     * @return the faction
     */
    public String getFaction() {
        return faction;
    }

    /**
     * Gets the flavor.
     * 
     * @return the flavor
     */
    public String getFlavor() {
        return flavor;
    }

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the number.
     * 
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Gets the owned.
     * 
     * @return the owned
     */
    public int getOwned() {
        return owned;
    }

    /**
     * Gets the points.
     * 
     * @return the points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Gets the rarity.
     * 
     * @return the rarity
     */
    public String getRarity() {
        return rarity;
    }

    /**
     * Gets the rules.
     * 
     * @return the rules
     */
    public String getRules() {
        return rules;
    }

    /**
     * Gets the wanted.
     * 
     * @return the wanted
     */
    public int getWanted() {
        return wanted;
    }

    /**
     * Sets the card type.
     * 
     * @param cardType
     *            the new card type
     */
    public void setCardType(final String cardType) {
        this.cardType = cardType;
    }

    /**
     * Sets the expansion.
     * 
     * @param expansion
     *            the new expansion
     */
    public void setExpansion(final String expansion) {
        this.expansion = expansion;
    }

    /**
     * Sets the extra.
     * 
     * @param extra
     *            the new extra
     */
    public void setExtra(final String extra) {
        this.extra = extra;
    }

    /**
     * Sets the faction.
     * 
     * @param faction
     *            the new faction
     */
    public void setFaction(final String faction) {
        this.faction = faction;
    }

    /**
     * Sets the flavor.
     * 
     * @param flavor
     *            the new flavor
     */
    public void setFlavor(final String flavor) {
        this.flavor = flavor;
    }

    /**
     * Sets the id.
     * 
     * @param id
     *            the new id
     */
    public void setId(final long id) {
        this.id = id;
    }

    /**
     * Sets the name.
     * 
     * @param name
     *            the new name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Sets the number.
     * 
     * @param number
     *            the new number
     */
    public void setNumber(final String number) {
        this.number = number;
    }

    /**
     * Sets the owned.
     * 
     * @param owned
     *            the new owned
     */
    public void setOwned(final int owned) {
        this.owned = owned;
    }

    /**
     * Sets the points.
     * 
     * @param points
     *            the new points
     */
    public void setPoints(final int points) {
        this.points = points;
    }

    /**
     * Sets the rarity.
     * 
     * @param rarity
     *            the new rarity
     */
    public void setRarity(final String rarity) {
        this.rarity = rarity;
    }

    /**
     * Sets the rules.
     * 
     * @param rules
     *            the new rules
     */
    public void setRules(final String rules) {
        this.rules = rules;
    }

    /**
     * Sets the wanted.
     * 
     * @param wanted
     *            the new wanted
     */
    public void setWanted(final int wanted) {
        this.wanted = wanted;
    }

    /**
     * To csv.
     * 
     * @return the string
     */
    public String toCSV() {
        return (new StringBuilder(String.valueOf(cardType))).append("\t").append(number).append("\t").append(name).append("\t").append(owned).append("\t").append(wanted).append("\t").append(faction).append("\t").append(expansion).append("\t").append(points).append("\t").append(rarity).append("\t")
                .append(rules).append("\t").append(flavor).append("\t").append(extra).toString();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return (new StringBuilder("Card{id=")).append(id).append(", number='").append(number).append('\'').append(", name='").append(name).append('\'').append(", expansion='").append(expansion).append('\'').append(", points=").append(points).append(", extra='").append(extra).append('\'')
                .append(", rarity='").append(rarity).append('\'').append(", rules='").append(rules).append('\'').append(", flavor='").append(flavor).append('\'').append(", faction='").append(faction).append('\'').append(", owned=").append(owned).append(", wanted=").append(wanted)
                .append(", cardType='").append(cardType).append('\'').append('}').toString();
    }
}
