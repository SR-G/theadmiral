package net.coljac.pirates;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 11, 2006
 */
public class Fleet implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** The id. */
    private long id;

    /** The ships. */
    private List<Ship> ships = new ArrayList<Ship>();

    /** The crew. */
    private List<Crew> crew = new ArrayList<Crew>();

    /** The extra cards. */
    private List<Card> extraCards = new ArrayList<Card>();

    /** The ship crew. */
    private Map<Ship, ShipsCrew> shipCrew = new HashMap<Ship, ShipsCrew>();

    /** The points. */
    private int points;

    /** The description. */
    private String description;

    /** The name. */
    private String name;

    /** The faction. */
    private String faction;

    /**
     * Instantiates a new fleet.
     */
    public Fleet() {
        points = 0;
    }

    /**
     * Adds the crew.
     * 
     * @param c
     *            the c
     */
    public void addCrew(final Crew c) {
        if (crew.contains(c) && !c.isGeneric()) {
            return;
        }
        crew.add(c);
        points += c.getPoints();
    }

    /**
     * Adds the crew to ship.
     * 
     * @param s
     *            the s
     * @param c
     *            the c
     */
    public void addCrewToShip(final Ship s, final Crew c) {
        if (!ships.contains(s)) {
            return;
        }
        final ShipsCrew list = shipCrew.get(s);
        list.add(c);
        points += c.getPoints();
    }

    /**
     * Adds the extra card.
     * 
     * @param e
     *            the e
     */
    public void addExtraCard(final Card e) {
        extraCards.add(e);
        points += e.getPoints();
    }

    /**
     * Adds the ship.
     * 
     * @param s
     *            the s
     */
    public void addShip(final Ship s) {
        if (!ships.contains(s)) {
            ships.add(s);
            final ShipsCrew list = new ShipsCrew(s);
            shipCrew.put(s, list);
            points += s.getPoints();
        }
    }

    /**
     * Gets the crew.
     * 
     * @return the crew
     */
    public List<Crew> getCrew() {
        return crew;
    }

    /**
     * Gets the description.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the extra cards.
     * 
     * @return the extra cards
     */
    public List<Card> getExtraCards() {
        return extraCards;
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
     * Gets the points.
     * 
     * @return the points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Gets the ship crew.
     * 
     * @return the ship crew
     */
    public Map<Ship, ShipsCrew> getShipCrew() {
        return shipCrew;
    }

    /**
     * Gets the ships.
     * 
     * @return the ships
     */
    public List<Ship> getShips() {
        return ships;
    }

    /**
     * Refresh.
     */
    public void refresh() {
        for (final ShipsCrew crew : shipCrew.values()) {
            crew.calculatePoints();
        }
    }

    /**
     * Removes the crew.
     * 
     * @param c
     *            the c
     */
    public void removeCrew(final Crew c) {
        points -= c.getPoints();
        crew.remove(c);
    }

    /**
     * Removes the crew from ship.
     * 
     * @param c
     *            the c
     * @param s
     *            the s
     */
    public void removeCrewFromShip(final Crew c, final Ship s) {
        if (!ships.contains(s)) {
            return;
        }
        final ShipsCrew list = shipCrew.get(s);
        if (list != null) {
            if (list.getCrewList().contains(c)) {
                list.remove(c);
                points -= c.getPoints();
            }
        }
    }

    /**
     * Removes the extra card.
     * 
     * @param e
     *            the e
     */
    public void removeExtraCard(final Card e) {
        if (extraCards.contains(e)) {
            extraCards.remove(e);
            points -= e.getPoints();
        }
    }

    /**
     * Removes the ship.
     * 
     * @param s
     *            the s
     */
    public void removeShip(final Ship s) {
        if (!ships.contains(s)) {
            return;
        }
        final ShipsCrew list = shipCrew.get(s);
        if (list != null) {
            for (final Crew c : list.getCrewList()) {
                points -= c.getPoints();
            }
        }
        shipCrew.remove(s);
        ships.remove(s);
        points -= s.getPoints();
    }

    /**
     * Sets the crew.
     * 
     * @param crew
     *            the new crew
     */
    public void setCrew(final List<Crew> crew) {
        this.crew = crew;
    }

    /**
     * Sets the description.
     * 
     * @param description
     *            the new description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Sets the extra cards.
     * 
     * @param extraCards
     *            the new extra cards
     */
    public void setExtraCards(final List<Card> extraCards) {
        this.extraCards = extraCards;
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
     * Sets the points.
     * 
     * @param points
     *            the new points
     */
    public void setPoints(final int points) {
        this.points = points;
    }

    /**
     * Sets the ship crew.
     * 
     * @param shipCrew
     *            the ship crew
     */
    public void setShipCrew(final Map<Ship, ShipsCrew> shipCrew) {
        this.shipCrew = shipCrew;
    }

    /**
     * Sets the ships.
     * 
     * @param ships
     *            the new ships
     */
    public void setShips(final List<Ship> ships) {
        this.ships = ships;
    }

}
