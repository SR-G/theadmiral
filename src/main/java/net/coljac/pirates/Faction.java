package net.coljac.pirates;

/**
 * The Class Faction.
 */
public class Faction {

    /** The label. */
    private String label;

    /** The key. */
    private String key;

    /**
     * Instantiates a new faction.
     * 
     * @param key
     *            the key
     */
    public Faction(final String key) {
        super();
        this.key = key;
    }

    /**
     * Instantiates a new faction.
     * 
     * @param key
     *            the key
     * @param label
     *            the label
     */
    public Faction(final String key, final String label) {
        super();
        this.key = key;
        this.label = label;
    }

    /**
     * Gets the key.
     * 
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets the label.
     * 
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the key.
     * 
     * @param key
     *            the new key
     */
    public void setKey(final String key) {
        this.key = key;
    }

    /**
     * Sets the label.
     * 
     * @param label
     *            the new label
     */
    public void setLabel(final String label) {
        this.label = label;
    }

}
