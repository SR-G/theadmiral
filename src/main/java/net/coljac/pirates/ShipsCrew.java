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

    /** The id. */
    private long id;

    /** The crew list. */
    private List<Crew> crewList;

    /** The cargo space used. */
    private int cargoSpaceUsed = 0;

    /** The crew points used. */
    private int crewPointsUsed = 0;

    /** The names. */
    private List<String> names;

    /** The ship. */
    private Ship ship; // Keep a reference for links

    /** The faction ok. */
    private boolean factionOK = true;

    /**
     * Instantiates a new ships crew.
     * 
     * @param ship
     *            the ship
     */
    public ShipsCrew(Ship ship) {
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
    public void add(Crew crew) {
        crewList.add(crew);
        names.add(crew.getName());
        calculatePoints();
    }

    /**
     * Removes the.
     * 
     * @param crew
     *            the crew
     */
    public void remove(Crew crew) {
        crewList.remove(crew);
        names.remove(crew.getName());
        calculatePoints();
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

    /**
     * Sets the crew list.
     * 
     * @param crewList
     *            the new crew list
     */
    public void setCrewList(List<Crew> crewList) {
        this.crewList = crewList;
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
     * Sets the cargo space used.
     * 
     * @param cargoSpaceUsed
     *            the new cargo space used
     */
    public void setCargoSpaceUsed(int cargoSpaceUsed) {
        this.cargoSpaceUsed = cargoSpaceUsed;
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
     * Sets the crew points used.
     * 
     * @param crewPointsUsed
     *            the new crew points used
     */
    public void setCrewPointsUsed(int crewPointsUsed) {
        this.crewPointsUsed = crewPointsUsed;
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
     * Sets the faction ok.
     * 
     * @param factionOK
     *            the new faction ok
     */
    public void setFactionOK(boolean factionOK) {
        this.factionOK = factionOK;
    }

    /** The string reduce. */
    String stringReduce = "Reduce the cost of all other crew placed on this ship by 1.";

    /** The strng cost no. */
    String strngCostNo = "Crew placed on this ship cost no points, but they always each take up one cargo space";

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

        for (Crew c : crewList) {
            cargoSpaceUsed += 1;
            if (c.getRules().indexOf(stringReduce) >= 0) {
                reduceTheCost = true;
            } else if (c.getRules().indexOf(strngCostNo) >= 0) {
                costNoPoints = true;
            } else if (!oarsman && c.getName().equals("Oarsman")) {
                oarsman = true;
                cargoSpaceUsed--;
            }
            String link = c.getLink();
            if (link != null && link.length() > 0) {
                // is the / a linked card in this group?
                if (link.indexOf(ship.name) >= 0) {
                    cargoSpaceUsed--;
                }
                for (String name : names) {
                    if (link.indexOf(name) >= 0) {
                        cargoSpaceUsed--;
                    }
                }
                if (link.matches(".*[Aa]ll.*ships.*")) {
                    String okFaction = stripFaction2(link);
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

        for (Crew c : crewList) {
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
        String shipsFaction = ship.getFaction();
        List<String> okFactions = new ArrayList<String>();
        okFactions.add(shipsFaction);
        if (ship.getRules().indexOf("may use their abilities on this") > 0) {
            if (ship.getRules().indexOf("any nationality may") >= 0) {
                return true;
            } else {
                okFactions.add(stripFaction(ship.getRules()));
            }
        }

        for (Crew crew : crewList) {
            if (crew.getRules().indexOf("any nationality may") >= 0) {
                return true;
            } else if (crew.getRules().indexOf("may use their abilities") >= 0) {
                okFactions.add(stripFaction(crew.getRules()));
            }

        }
        if (okFactions.contains("Pirate")) {
            okFactions.add("Pirates");
        }
        for (Crew crew : crewList) {
            if (crew.getRules().toLowerCase().indexOf("ex-patriot") >= 0)
                continue;
            if (crew.isGeneric())
                continue;
            if (!okFactions.contains(crew.getFaction())) {
                return false;
            }
        }
        return true;
    }

    /** The regex. */
    static String regex = "(.*[^.]\\. |^)([A-z ]+) crew may use their abilities.*";

    /** The pattern. */
    static Pattern pattern = Pattern.compile(regex);

    /**
     * Strip faction.
     * 
     * @param rules
     *            the rules
     * @return the string
     */
    private static String stripFaction(String rules) {
        if (!(rules.indexOf("may use their abilities") > 0))
            return null;
        Matcher m = pattern.matcher(rules);
        if (m.matches()) {
            return m.group(2).trim();
        }

        return null;
    }

    /** The regex2. */
    static String regex2 = ".*[Aa]ll (.*) ships.*";

    /** The pattern2. */
    static Pattern pattern2 = Pattern.compile(regex2);

    /**
     * Strip faction2.
     * 
     * @param link
     *            the link
     * @return the string
     */
    private static String stripFaction2(String link) {
        Matcher m = pattern2.matcher(link);
        if (m.matches()) {
            return m.group(1).trim();
        }
        return null;
    }
}
