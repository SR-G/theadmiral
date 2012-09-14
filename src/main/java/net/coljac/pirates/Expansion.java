package net.coljac.pirates;

import java.io.Serializable;

/**
 * The Class Expansion.
 */
public class Expansion implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** The name. */
    private String name;

    /** The abbreviation. */
    private String abbreviation;

    /** The release date. */
    private String releaseDate;

    /**
     * Instantiates a new expansion.
     * 
     * @param name
     *            the name
     * @param abbreviation
     *            the abbreviation
     */
    public Expansion(final String name, final String abbreviation) {
        super();
        this.name = name;
        this.abbreviation = abbreviation;
    }

    /**
     * Gets the abbreviation.
     * 
     * @return the abbreviation
     */
    public String getAbbreviation() {
        return abbreviation;
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
     * Gets the release date.
     * 
     * @return the release date
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the abbreviation.
     * 
     * @param abbreviation
     *            the new abbreviation
     */
    public void setAbbreviation(final String abbreviation) {
        this.abbreviation = abbreviation;
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
     * Sets the release date.
     * 
     * @param releaseDate
     *            the new release date
     */
    public void setReleaseDate(final String releaseDate) {
        this.releaseDate = releaseDate;
    }

}
