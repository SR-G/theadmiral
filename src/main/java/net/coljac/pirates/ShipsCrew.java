package net.coljac.pirates;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 14, 2006
 */
public class ShipsCrew implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * Strip faction.
     * 
     * @param rules
     *            the rules
     * @return the string
     */
    private static String stripFaction(final String rules) {
        if (!(rules.indexOf("may use their abilities") > 0)) {
            return null;
        }
        final Matcher m = pattern.matcher(rules);
        if (m.matches()) {
            return m.group(2).trim();
        }

        return null;
    }

    /**
     * Strip faction2.
     * 
     * @param link
     *            the link
     * @return the string
     */
    private static String stripFaction2(final String link) {
        final Matcher m = pattern2.matcher(link);
        if (m.matches()) {
            return m.group(1).trim();
        }
        return null;
    }

    /** The id. */
    private long id;

    /** The crew list. */
    private List<Crew> crewList;

    /** The cargo space used. */
    private int cargoSpaceUsed = 0;

    /** The crew points used. */
    private int crewPointsUsed = 0;

    /** The names. */
    private final List<String> names;

    /** The ship. */
    private final Ship ship; // Keep a reference for links

    /** The faction ok. */
    private boolean factionOK = true;

    /** The string reduce. */
    String stringReduce = "Reduce the cost of all other crew placed on this ship by 1.";

    /** The strng cost no. */
    String strngCostNo = "Crew placed on this ship cost no points, but they always each take up one cargo space";

    /** The regex. */
    static String regex = "(.*[^.]\\. |^)([A-z ]+) crew may use their abilities.*";

    /** The pattern. */
    static Pattern pattern = Pattern.compile(regex);

    /** The regex2. */
    static String regex2 = ".*[Aa]ll (.*) ships.*";

    /** The pattern2. */
    static Pattern pattern2 = Pattern.compile(regex2);

    /**
     * Instantiates a new ships crew.
     * 
     * @param ship
     *            the ship
     */
    public ShipsCrew(final Ship ship) {
        crewList = new ArrayList<Crew>();
        names = new ArrayList<String>();
        this.ship = ship;
    }

    /**
     * Adds the.
     * 
     * @param crew
     *            the crew
     */
    public void add(final Crew crew) {
        crewList.add(crew);
        names.add(crew.getName());
        calculatePoints();
    }

    //
    /**
     * Calculate points.
     */
    public void calculatePoints() {
        cargoSpaceUsed = 0;
        crewPointsUsed = 0;
        boolean reduceTheCost = false;
        boolean costNoPoints = false;
        boolean oarsman = false;

        for (final Crew c : crewList) {
            cargoSpaceUsed += 1;
            if (c.getRules().indexOf(stringReduce) >= 0) {
                reduceTheCost = true;
            } else if (c.getRules().indexOf(strngCostNo) >= 0) {
                costNoPoints = true;
            } else if (!oarsman && c.getName().equals("Oarsman")) {
                oarsman = true;
                cargoSpaceUsed--;
            }
            final String link = c.getLink();
            if ((link != null) && (link.length() > 0)) {
                // is the / a linked card in this group?
                if (link.indexOf(ship.name) >= 0) {
                    cargoSpaceUsed--;
                }
                for (final String name : names) {
                    if (link.indexOf(name) >= 0) {
                        cargoSpaceUsed--;
                    }
                }
                if (link.matches(".*[Aa]ll.*ships.*")) {
                    final String okFaction = stripFaction2(link);
                    if (ship.getFaction().indexOf(okFaction) == 0) {
                        cargoSpaceUsed--;
                    }
                }
            }
        }

        if (costNoPoints) {
            cargoSpaceUsed = crewList.size();
            return;
        }

        for (final Crew c : crewList) {
            int cost = c.getPoints();
            if (reduceTheCost) {
                cost = Math.max(cost - 1, 0);
            }
            crewPointsUsed += cost;
        }
        if (reduceTheCost) {
            crewPointsUsed++; // The card itself doesn't get reduced
        }
        factionOK = checkFactionsOK();
    }

    /**
     * Check factions ok.
     * 
     * @return true, if successful
     */
    boolean checkFactionsOK() {
        final String shipsFaction = ship.getFaction();
        final List<String> okFactions = new ArrayList<String>();
        okFactions.add(shipsFaction);
        if (ship.getRules().indexOf("may use their abilities on this") > 0) {
            if (ship.getRules().indexOf("any nationality may") >= 0) {
                return true;
            } else {
                okFactions.add(stripFaction(ship.getRules()));
            }
        }

        for (final Crew crew : crewList) {
            if (crew.getRules().indexOf("any nationality may") >= 0) {
                return true;
            } else if (crew.getRules().indexOf("may use their abilities") >= 0) {
                okFactions.add(stripFaction(crew.getRules()));
            }

        }
        if (okFactions.contains("Pirate")) {
            okFactions.add("Pirates");
        }
        for (final Crew crew : crewList) {
            if (crew.getRules().toLowerCase().indexOf("ex-patriot") >= 0) {
                continue;
            }
            if (crew.isGeneric()) {
                continue;
            }
            if (!okFactions.contains(crew.getFaction())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the cargo space used.
     * 
     * @return the cargo space used
     */
    public int getCargoSpaceUsed() {
        return cargoSpaceUsed;
    }

    /**
     * Gets the crew list.
     * 
     * @return the crew list
     */
    public List<Crew> getCrewList() {
        return crewList;
    }

    /**
     * Gets the crew points used.
     * 
     * @return the crew points used
     */
    public int getCrewPointsUsed() {
        return crewPointsUsed;
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
     * Checks if is faction ok.
     * 
     * @return true, if is faction ok
     */
    public boolean isFactionOK() {
        return factionOK;
    }

    /**
     * Removes the.
     * 
     * @param crew
     *            the crew
     */
    public void remove(final Crew crew) {
        crewList.remove(crew);
        names.remove(crew.getName());
        calculatePoints();
    }

    /**
     * Sets the cargo space used.
     * 
     * @param cargoSpaceUsed
     *            the new cargo space used
     */
    public void setCargoSpaceUsed(final int cargoSpaceUsed) {
        this.cargoSpaceUsed = cargoSpaceUsed;
    }

    /**
     * Sets the crew list.
     * 
     * @param crewList
     *            the new crew list
     */
    public void setCrewList(final List<Crew> crewList) {
        this.crewList = crewList;
    }

    /**
     * Sets the crew points used.
     * 
     * @param crewPointsUsed
     *            the new crew points used
     */
    public void setCrewPointsUsed(final int crewPointsUsed) {
        this.crewPointsUsed = crewPointsUsed;
    }

    /**
     * Sets the faction ok.
     * 
     * @param factionOK
     *            the new faction ok
     */
    public void setFactionOK(final boolean factionOK) {
        this.factionOK = factionOK;
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
}
