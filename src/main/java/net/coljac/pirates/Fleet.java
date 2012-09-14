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
     * Adds the ship.
     * 
     * @param s
     *            the s
     */
    public void addShip(Ship s) {
        if (!ships.contains(s)) {
            ships.add(s);
            ShipsCrew list = new ShipsCrew(s);
            shipCrew.put(s, list);
            points += s.getPoints();
        }
    }

    /**
     * Adds the crew.
     * 
     * @param c
     *            the c
     */
    public void addCrew(Crew c) {
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
    public void addCrewToShip(Ship s, Crew c) {
        if (!ships.contains(s))
            return;
        ShipsCrew list = shipCrew.get(s);
        list.add(c);
        points += c.getPoints();
    }

    /**
     * Removes the ship.
     * 
     * @param s
     *            the s
     */
    public void removeShip(Ship s) {
        if (!ships.contains(s))
            return;
        ShipsCrew list = shipCrew.get(s);
        if (list != null) {
            for (Crew c : list.getCrewList()) {
                points -= c.getPoints();
            }
        }
        shipCrew.remove(s);
        ships.remove(s);
        points -= s.getPoints();
    }

    /**
     * Removes the crew.
     * 
     * @param c
     *            the c
     */
    public void removeCrew(Crew c) {
        points -= c.getPoints();
        crew.remove(c);
    }

    /**
     * Refresh.
     */
    public void refresh() {
        for (ShipsCrew crew : shipCrew.values()) {
            crew.calculatePoints();
        }
    }

    /**
     * Removes the crew from ship.
     * 
     * @param c
     *            the c
     * @param s
     *            the s
     */
    public void removeCrewFromShip(Crew c, Ship s) {
        if (!ships.contains(s))
            return;
        ShipsCrew list = shipCrew.get(s);
        if (list != null) {
            if (list.getCrewList().contains(c)) {
                list.remove(c);
                points -= c.getPoints();
            }
        }
    }

    /**
     * Adds the extra card.
     * 
     * @param e
     *            the e
     */
    public void addExtraCard(Card e) {
        extraCards.add(e);
        this.points += e.getPoints();
    }

    /**
     * Removes the extra card.
     * 
     * @param e
     *            the e
     */
    public void removeExtraCard(Card e) {
        if (extraCards.contains(e)) {
            extraCards.remove(e);
            points -= e.getPoints();
        }
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
     * Sets the ships.
     * 
     * @param ships
     *            the new ships
     */
    public void setShips(List<Ship> ships) {
        this.ships = ships;
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
     * Sets the crew.
     * 
     * @param crew
     *            the new crew
     */
    public void setCrew(List<Crew> crew) {
        this.crew = crew;
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
     * Sets the extra cards.
     * 
     * @param extraCards
     *            the new extra cards
     */
    public void setExtraCards(List<Card> extraCards) {
        this.extraCards = extraCards;
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
     * Sets the points.
     * 
     * @param points
     *            the new points
     */
    public void setPoints(int points) {
        this.points = points;
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
     * Sets the description.
     * 
     * @param description
     *            the new description
     */
    public void setDescription(String description) {
        this.description = description;
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
     * Sets the name.
     * 
     * @param name
     *            the new name
     */
    public void setName(String name) {
        this.name = name;
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
     * Sets the faction.
     * 
     * @param faction
     *            the new faction
     */
    public void setFaction(String faction) {
        this.faction = faction;
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
     * Sets the ship crew.
     * 
     * @param shipCrew
     *            the ship crew
     */
    public void setShipCrew(Map<Ship, ShipsCrew> shipCrew) {
        this.shipCrew = shipCrew;
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
     * Sets the id.
     * 
     * @param id
     *            the new id
     */
    public void setId(long id) {
        this.id = id;
    }

}
