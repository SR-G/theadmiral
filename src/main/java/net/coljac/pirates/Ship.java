package net.coljac.pirates;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Feb 28, 2006
 */
public class Ship extends Card {

    /** The masts. */
    private int masts;

    /** The cargo. */
    private int cargo;

    /** The move. */
    private String move = "";

    /** The cannons. */
    private String cannons;

    /** The moves. */
    private transient Distance[] moves;

    /** The guns. */
    private transient Cannon[] guns;

    /**
     * Instantiates a new ship.
     * 
     * @param name
     *            the name
     */
    public Ship(String name) {
        this.name = name;
    }

    /**
     * Instantiates a new ship.
     */
    public Ship() {
        this.cardType = "Ship";
    }

    /**
     * Gets the masts.
     * 
     * @return the masts
     */
    public int getMasts() {
        return masts;
    }

    /**
     * Sets the masts.
     * 
     * @param masts
     *            the new masts
     */
    public void setMasts(int masts) {
        this.masts = masts;
    }

    /**
     * Gets the cargo.
     * 
     * @return the cargo
     */
    public int getCargo() {
        return cargo;
    }

    /**
     * Sets the cargo.
     * 
     * @param cargo
     *            the new cargo
     */
    public void setCargo(int cargo) {
        this.cargo = cargo;
    }

    /**
     * Gets the move.
     * 
     * @return the move
     */
    public String getMove() {
        return move;
    }

    /**
     * Sets the move.
     * 
     * @param move
     *            the new move
     */
    public void setMove(String move) {
        this.move = move;
        if (move.length() > 0) {
            String[] segments = move.split(" *, *");
            Distance[] ds = new Distance[segments.length];
            for (int i = 0; i < segments.length; i++) {
                String segment = segments[i];
                ds[i] = Distance.valueOf(segment);
            }
            this.moves = ds;
        } else {
            this.moves = new Distance[0];
        }
    }

    /**
     * Gets the cannons.
     * 
     * @return the cannons
     */
    public String getCannons() {
        return cannons;
    }

    /**
     * Sets the cannons.
     * 
     * @param cannons
     *            the new cannons
     */
    public void setCannons(String cannons) {
        this.cannons = cannons;
        String[] guns = cannons.split(" *, *");
        Cannon[] cns = new Cannon[guns.length];
        for (int i = 0; i < guns.length; i++) {
            String gun = guns[i];
            cns[i] = new Cannon(gun);
        }
        this.guns = cns;
    }

    /**
     * Gets the move distance.
     * 
     * @return the move distance
     */
    public int getMoveDistance() {
        int d = 0;
        for (int i = 0; i < getMoves().length; i++) {
            Distance distance = getMoves()[i];
            d += distance.getMillis();
        }
        return d;
    }

    /**
     * Gets the guns.
     * 
     * @return the guns
     */
    public Cannon[] getGuns() {
        return guns;
    }

    /**
     * Sets the guns.
     * 
     * @param guns
     *            the new guns
     */
    public void setGuns(Cannon[] guns) {
        this.guns = guns;
    }

    /**
     * Gets the moves.
     * 
     * @return the moves
     */
    public Distance[] getMoves() {
        if (moves == null && move != null) {
            setMove(getMove());
        }
        return moves;
    }

    /**
     * Sets the moves.
     * 
     * @param moves
     *            the new moves
     */
    public void setMoves(Distance[] moves) {
        this.moves = moves;
    }

    /**
     * {@inheritDoc}
     * 
     * @see net.coljac.pirates.Card#toString()
     */
    public String toString() {
        return "Ship{" + "id=" + id + ", number='" + number + '\'' + ", name='" + name + '\'' + ", expansion='" + expansion + '\'' + ", points=" + points + ", faction='" + faction + '\'' + ", masts=" + masts + ", cargo=" + cargo + ", move='" + move + '\'' + ", rules='" + rules + '\'' + ", flavor='"
                + flavor + '\'' + ", cannons='" + cannons + '\'' + ", extra='" + extra + '\'' + ", rarity='" + rarity + '\'' + '}';
    }

    // ID Card Name Points Faction Masts Cargo Move Cannons Ability Rarity Set Flavor

    /**
     * {@inheritDoc}
     * 
     * @see net.coljac.pirates.Card#buildFromString(java.lang.String)
     */
    public void buildFromString(String fromString) {
        super.buildFromString(fromString);
        String[] tokens = fromString.split("\t");
        this.masts = Integer.parseInt(tokens[12]);
        this.cargo = Integer.parseInt(tokens[13]);
        this.setMove(tokens[14]);
        this.setCannons(tokens[15]);
    }

    /**
     * {@inheritDoc}
     * 
     * @see net.coljac.pirates.Card#toCSV()
     */
    public String toCSV() {
        return super.toCSV() + "\t" + masts + "\t" + cargo + "\t" + move + "\t" + cannons + "\t";
    }

}
