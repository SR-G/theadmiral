package net.coljac.pirates;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 23, 2006
 */
public class CardDatabase implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 100000L;

    /**
     * Inits the.
     * 
     * @param is
     *            the is
     * @param fileName
     *            the file name
     * @return the card database
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static CardDatabase init(final InputStream is, final String fileName) throws IOException {
        final File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        final ObjectInputStream ois = new ObjectInputStream(is);
        CardDatabase db = null;
        try {
            db = (CardDatabase) ois.readObject();
        } catch (final ClassNotFoundException e) {
            return null;
        }
        db.setFileName(fileName);
        return db;
    }

    /**
     * Inits the.
     * 
     * @param fileName
     *            the file name
     * @return the card database
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static CardDatabase init(final String fileName) throws IOException {
        final File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
            final CardDatabase db = new CardDatabase(fileName);
            return db;
        } else {
            final ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            try {
                final CardDatabase db = (CardDatabase) ois.readObject();
                return db;
            } catch (final ClassNotFoundException e) {
                // Can't happen - it's THIS class!
                e.printStackTrace();
            }
        }
        return null;

    }

    /**
     * The main method.
     * 
     * @param args
     *            the arguments
     */
    public static void main(final String[] args) {
        try {
            final CardDatabase db = CardDatabase.init("blah.dat");
            db.save();
        } catch (final IOException e) {
            e.printStackTrace();
        }

    }

    /** The file name. */
    private String fileName;

    /** The cards. */
    private List<Card> cards;

    /** The ships. */
    private List<Ship> ships;

    /** The events. */
    private List<Event> events;

    /** The crew. */
    private List<Crew> crew;

    /** The treasure. */
    private List<Card> treasure;

    /** The fleets. */
    private List<Fleet> fleets;

    /**
     * Instantiates a new card database.
     * 
     * @param fileName
     *            the file name
     */
    public CardDatabase(final String fileName) {
        this.fileName = fileName;
        cards = new ArrayList<Card>();
        ships = new ArrayList<Ship>();
        crew = new ArrayList<Crew>();
        events = new ArrayList<Event>();
        treasure = new ArrayList<Card>();
        fleets = new ArrayList<Fleet>();
    }

    /**
     * Clear.
     */
    public void clear() {
        cards.clear();
        ships.clear();
        fleets.clear();
        crew.clear();
        events.clear();
        treasure.clear();
    }

    /**
     * Gets the card.
     * 
     * @param name
     *            the name
     * @return the card
     */
    public Card getCard(final String name) {
        for (final Card s : cards) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Gets the cards.
     * 
     * @return the cards
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * Gets the cards by name.
     * 
     * @param name
     *            the name
     * @return the cards by name
     */
    public List<Card> getCardsByName(final String name) {
        final List<Card> result = new ArrayList<Card>();
        for (final Card s : cards) {
            if (s.getName().equals(name)) {
                result.add(s);
            }
        }
        return result;
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
     * Gets the events.
     * 
     * @return the events
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Gets the file name.
     * 
     * @return the file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Gets the fleets.
     * 
     * @return the fleets
     */
    public List<Fleet> getFleets() {
        return fleets;
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
     * Gets the treasure.
     * 
     * @return the treasure
     */
    public List<Card> getTreasure() {
        return treasure;
    }

    /**
     * Save.
     */
    public void save() {
        try {
            final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(this);
            oos.flush();
            oos.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the cards.
     * 
     * @param cards
     *            the new cards
     */
    public void setCards(final List<Card> cards) {
        this.cards = cards;
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
     * Sets the events.
     * 
     * @param events
     *            the new events
     */
    public void setEvents(final List<Event> events) {
        this.events = events;
    }

    /**
     * Sets the file name.
     * 
     * @param fileName
     *            the new file name
     */
    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    /**
     * Sets the fleets.
     * 
     * @param fleets
     *            the new fleets
     */
    public void setFleets(final List<Fleet> fleets) {
        this.fleets = fleets;
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

    /**
     * Sets the treasure.
     * 
     * @param treasure
     *            the new treasure
     */
    public void setTreasure(final List<Card> treasure) {
        this.treasure = treasure;
    }

}
