package net.coljac.pirates.tools.conversion.wizkids;

import java.util.ArrayList;
import java.util.List;

import net.coljac.pirates.Card;
import net.coljac.pirates.CardDatabase;
import net.coljac.pirates.Ship;
import net.coljac.pirates.helper.FileHelper;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Feb 28, 2006
 */
public class MakeDB {

    /** The db. */
    public static CardDatabase db;

    /**
     * Import my ships.
     * 
     * @param fileName
     *            the file name
     */
    public static void importMyShips(final String fileName) {
        final List<String> lines = FileHelper.getFileContentsAsList(fileName);
        for (final String s : lines) {
            final String[] tokens = s.split("\t");
            int wanted = 0;
            int owned = 0;
            try {
                owned = Integer.parseInt(tokens[1]);
            } catch (final NumberFormatException e) {
            }
            try {
                wanted = Integer.parseInt(tokens[0]);
            } catch (final NumberFormatException e) {

            }
            final String name = tokens[3];
            String number = tokens[2];
            while (number.length() < 3) {
                number = "0" + number;
            }
            final List<Card> ships = db.getCardsByName(name);
            if (ships.size() == 1) {
                final Ship theShip = (Ship) ships.get(0);
                theShip.setOwned(owned);
                theShip.setWanted(wanted);
            } else if (ships.size() == 0) {
                System.out.println("Can't find ship " + name);
            } else {
                final List numbered = new ArrayList<Ship>();
                for (final Card c : ships) {
                    if (c.getNumber().equals(number)) {
                        numbered.add(c);
                    }
                }
                if (numbered.size() == 1) {
                    final Ship theShip = (Ship) numbered.get(0);
                    theShip.setOwned(owned);
                    theShip.setWanted(wanted);
                    db.save();
                } else {
                    System.out.println("Too many ships: " + name + "  " + numbered.size());
                }
            }
            db.save();
        }
    }

    /**
     * The main method.
     * 
     * @param args
     *            the arguments
     * @throws Exception
     *             the exception
     */
    public static void main(final String[] args) throws Exception {

        db = CardDatabase.init("cards.db");
        // makeDB();
        importMyShips("./my_ships.txt");

    }

    /**
     * Make db.
     */
    public static void makeDB() {
        // final String[] sets = new String[] { "PSM", "CC", "PR", "BC", "SCS" };
        // final GetData getter = new GetData();
        // for (final String set : sets) {
        // try {
        // getter.getShips(set);
        // getter.getCrew(set);
        // } catch (final Exception e) {
        // e.printStackTrace();
        // }
        // }
        // try {
        // getter.getForts("PSM");
        // getter.getEvents("PSM");
        // } catch (final Exception e) {
        // e.printStackTrace();
        // }

        // importMyShips("./my_ships.txt");

        // CardDatabase db = null;
        // try {
        // db = CardDatabase.init("cards.db");
        // } catch (IOException e) {
        // e.printStackTrace();
        // }

        //
        // List l = HibernateUtil.createQuery("from Ship as ship").list();
        // System.out.println("Ships in DB: " + l.size());
        // db.getShips().addAll(l);
        //
        // l = HibernateUtil.createQuery("from Crew as crew").list();
        // System.out.println("Crew in DB: " + l.size());
        // db.getCrew().addAll(l);
        //
        // l = HibernateUtil.createQuery("from Event as event").list();
        // System.out.println("Events in DB: " + l.size());
        // db.getEvents().addAll(l);
        //
        // l = HibernateUtil.createQuery("from Fort as fort").list();
        // System.out.println("Forts in DB: " + l.size());
        // db.getShips().addAll(l);
        //
        // l = HibernateUtil.createQuery("from Card as card").list();
        // System.out.println("Cards in DB: " + l.size());
        // db.getCards().addAll(l);
        ImportTreasure.main(null);

        db.save();

        // try {
        // HibernateUtil.currentSession().connection().createStatement().execute("SHUTDOWN");
        // } catch (SQLException e) {
        // e.printStackTrace();
        // }
    }

}
