package net.coljac.pirates;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Feb 28, 2006
 */
public class Ship extends Card {

    /** serialVersionUID */
    private static final long serialVersionUID = 3467331773657192539L;

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
     */
    public Ship() {
        cardType = "Ship";
    }

    /**
     * Instantiates a new ship.
     * 
     * @param name
     *            the name
     */
    public Ship(final String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     * 
     * @see net.coljac.pirates.Card#buildFromString(java.lang.String)
     */
    @Override
    public void buildFromString(final String fromString) {
        super.buildFromString(fromString);
        final String[] tokens = fromString.split("\t");
        masts = Integer.parseInt(tokens[12]);
        cargo = Integer.parseInt(tokens[13]);
        setMove(tokens[14]);
        setCannons(tokens[15]);
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
     * Gets the cargo.
     * 
     * @return the cargo
     */
    public int getCargo() {
        return cargo;
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
     * Gets the masts.
     * 
     * @return the masts
     */
    public int getMasts() {
        return masts;
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
     * Gets the move distance.
     * 
     * @return the move distance
     */
    public int getMoveDistance() {
        int d = 0;
        for (int i = 0; i < getMoves().length; i++) {
            final Distance distance = getMoves()[i];
            d += distance.getMillis();
        }
        return d;
    }

    /**
     * Gets the moves.
     * 
     * @return the moves
     */
    public Distance[] getMoves() {
        if ((moves == null) && (move != null)) {
            setMove(getMove());
        }
        return moves;
    }

    /**
     * Sets the cannons.
     * 
     * @param cannons
     *            the new cannons
     */
    public void setCannons(final String cannons) {
        this.cannons = cannons;
        final String[] guns = cannons.split(" *, *");
        final Cannon[] cns = new Cannon[guns.length];
        for (int i = 0; i < guns.length; i++) {
            final String gun = guns[i];
            cns[i] = new Cannon(gun);
        }
        this.guns = cns;
    }

    /**
     * Sets the cargo.
     * 
     * @param cargo
     *            the new cargo
     */
    public void setCargo(final int cargo) {
        this.cargo = cargo;
    }

    /**
     * Sets the guns.
     * 
     * @param guns
     *            the new guns
     */
    public void setGuns(final Cannon[] guns) {
        this.guns = guns;
    }

    /**
     * Sets the masts.
     * 
     * @param masts
     *            the new masts
     */
    public void setMasts(final int masts) {
        this.masts = masts;
    }

    /**
     * Sets the move.
     * 
     * @param move
     *            the new move
     */
    public void setMove(final String move) {
        this.move = move;
        if (move.length() > 0) {
            final String[] segments = move.split(" *, *");
            final Distance[] ds = new Distance[segments.length];
            for (int i = 0; i < segments.length; i++) {
                final String segment = segments[i];
                ds[i] = Distance.valueOf(segment);
            }
            moves = ds;
        } else {
            moves = new Distance[0];
        }
    }

    /**
     * Sets the moves.
     * 
     * @param moves
     *            the new moves
     */
    public void setMoves(final Distance[] moves) {
        this.moves = moves;
    }

    // ID Card Name Points Faction Masts Cargo Move Cannons Ability Rarity Set Flavor

    /**
     * {@inheritDoc}
     * 
     * @see net.coljac.pirates.Card#toCSV()
     */
    @Override
    public String toCSV() {
        return super.toCSV() + "\t" + masts + "\t" + cargo + "\t" + move + "\t" + cannons + "\t";
    }

    /**
     * {@inheritDoc}
     * 
     * @see net.coljac.pirates.Card#toString()
     */
    @Override
    public String toString() {
        return "Ship{" + "id=" + id + ", number='" + number + '\'' + ", name='" + name + '\'' + ", expansion='" + expansion + '\'' + ", points=" + points + ", faction='" + faction + '\'' + ", masts=" + masts + ", cargo=" + cargo + ", move='" + move + '\'' + ", rules='" + rules + '\'' + ", flavor='"
                + flavor + '\'' + ", cannons='" + cannons + '\'' + ", extra='" + extra + '\'' + ", rarity='" + rarity + '\'' + '}';
    }

}
