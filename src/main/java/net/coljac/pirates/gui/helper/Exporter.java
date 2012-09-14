package net.coljac.pirates.gui.helper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import net.coljac.pirates.Card;
import net.coljac.pirates.CardDatabase;
import net.coljac.pirates.Crew;
import net.coljac.pirates.Fleet;
import net.coljac.pirates.Ship;
import net.coljac.pirates.ShipsCrew;
import net.coljac.pirates.gui.ManagerMain;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 29, 2006
 */
public class Exporter {

    /**
     * Export card list.
     * 
     * @param fileName
     *            the file name
     * @param mine
     *            the mine
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static void exportCardList(final String fileName, final boolean mine) throws IOException {
        final CardDatabase db = ManagerMain.instance.db;
        final PrintWriter pw = new PrintWriter(new FileWriter(fileName));
        for (final Object element : db.getCards()) {
            final Card card = (Card) element;
            if (!mine || (card.getOwned() > 0) || (card.getWanted() > 0)) {
                pw.println(card.toCSV());
            }
        }

        pw.flush();
        pw.close();
    }

    /**
     * Export checklist.
     * 
     * @param fileName
     *            the file name
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static void exportChecklist(final String fileName) throws IOException {
        final CardDatabase db = ManagerMain.instance.db;
        final PrintWriter pw = new PrintWriter(new FileWriter(fileName));
        for (final Object element : db.getCards()) {
            final Card card = (Card) element;
            if ((card.getOwned() > 0) || (card.getWanted() > 0)) {
                pw.println((new StringBuilder(String.valueOf(card.getName()))).append("\t").append(card.getNumber()).append("\t").append(Card.getSetAbbreviation(card.getExpansion())).append("\t").append(card.getOwned()).append("\t").append(card.getWanted()).toString());
            }
        }

        pw.flush();
        pw.close();
    }

    /**
     * Export fleet.
     * 
     * @param fleet
     *            the fleet
     * @param fileName
     *            the file name
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static void exportFleet(final Fleet fleet, final String fileName) throws IOException {
        final PrintWriter pw = new PrintWriter(new FileWriter(fileName));
        pw.println((new StringBuilder("Fleet: ")).append(fleet.getName()).toString());
        pw.println("--------------------");
        for (final Object element : fleet.getShips()) {
            final Ship s = (Ship) element;
            pw.print("Ship\t");
            pw.println((new StringBuilder(String.valueOf(points(s)))).append("\t").append(s.getName()).append("\t").append(Card.getSetAbbreviation(s.getExpansion())).append("\t").append(s.getNumber()).toString());
            final ShipsCrew cl = fleet.getShipCrew().get(s);
            for (final Object element2 : cl.getCrewList()) {
                final Crew crew = (Crew) element2;
                if (crew.isGeneric()) {
                    pw.print("Crew\t");
                    pw.println((new StringBuilder(String.valueOf(points(crew)))).append("\t    ").append(crew.getName()).append("\tGeneric Crew").toString());
                } else {
                    pw.print("Crew\t");
                    pw.println((new StringBuilder(String.valueOf(points(crew)))).append("\t    ").append(crew.getName()).append("\t").append(Card.getSetAbbreviation(crew.getExpansion())).append("\t").append(crew.getNumber()).toString());
                }
            }

        }

        for (final Object element : fleet.getCrew()) {
            final Crew crew = (Crew) element;
            if (crew.isGeneric()) {
                pw.print("Crew\t");
                pw.println((new StringBuilder(String.valueOf(points(crew)))).append("\t").append(crew.getName()).append("\tGeneric Crew").toString());
            } else {
                pw.print("Crew\t");
                pw.println((new StringBuilder(String.valueOf(points(crew)))).append("\t").append(crew.getName()).append("\t").append(Card.getSetAbbreviation(crew.getExpansion())).append("\t").append(crew.getNumber()).toString());
            }
        }

        for (final Object element : fleet.getExtraCards()) {
            final Card crd = (Card) element;
            if (crd.getName().equals("Captain") || crd.getName().equals("Oarsman") || crd.getName().equals("Cannoneer") || crd.getName().equals("Musketeer") || crd.getName().equals("Explorer") || crd.getName().equals("Helmsman") || crd.getName().equals("Shipwright")
                    || crd.getName().equals("Chainshot Specialist") || crd.getName().equals("Firepot Specialist") || crd.getName().equals("Smokepot Specialist") || crd.getName().equals("Stinkpot Specialist")) {
                pw.print((new StringBuilder(String.valueOf(crd.getCardType()))).append("\t").toString());
                pw.println((new StringBuilder(String.valueOf(points(crd)))).append("\t").append(crd.getName()).append("\tGeneric Crew").toString());
            } else {
                pw.print((new StringBuilder(String.valueOf(crd.getCardType()))).append("\t").toString());
                pw.println((new StringBuilder(String.valueOf(points(crd)))).append("\t").append(crd.getName()).append("\t").append(Card.getSetAbbreviation(crd.getExpansion())).append("\t").append(crd.getNumber()).toString());
            }
        }

        pw.println("--------------------");
        pw.println((new StringBuilder(String.valueOf(fleet.getPoints()))).append(" points.").toString());
        pw.println("--------------------");
        pw.flush();
        pw.close();
    }

    /**
     * Export mt.
     * 
     * @param fileName
     *            the file name
     * @param comment
     *            the comment
     * @param haves
     *            the haves
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static void exportMT(final String fileName, final String comment, final boolean haves) throws IOException {
        final CardDatabase db = ManagerMain.instance.db;
        final PrintWriter pw = new PrintWriter(new FileWriter(fileName));
        for (final Object element : db.getCards()) {
            final Card card = (Card) element;
            if (haves) {
                if (card.getOwned() > 1) {
                    final String expansion = Card.getSetAbbreviation(card.getExpansion());
                    pw.println((new StringBuilder(String.valueOf(Card.getSetAbbreviation(card.getExpansion())))).append("\t").append(getPrefix(expansion)).append(card.getNumber()).append("\t").append(card.getName()).append("\t").append(card.getOwned() - 1).append("\t")
                            .append(comment == null ? "" : comment).toString());
                }
            } else if (card.getWanted() > 0) {
                final String expansion = Card.getSetAbbreviation(card.getExpansion());
                pw.println((new StringBuilder(String.valueOf(expansion))).append("\t").append(getPrefix(expansion)).append(card.getNumber()).append("\t").append(card.getName()).append("\t").append(card.getWanted()).append("\t").append(comment == null ? "" : comment).toString());
            }
        }

        pw.flush();
        pw.close();
    }

    /**
     * Gets the prefix.
     * 
     * @param set
     *            the set
     * @return the prefix
     */
    private static String getPrefix(final String set) {
        if (set.equals("PofSM")) {
            return "PS-";
        }
        if (set.equals("PofCC")) {
            return "C-";
        }
        if (set.equals("PofR")) {
            return "R-";
        }
        if (set.equals("PofBC")) {
            return "BC-";
        }
        if (set.equals("PofSCS")) {
            return "SCS-";
        } else {
            return "";
        }
    }

    /**
     * Points.
     * 
     * @param c
     *            the c
     * @return the string
     */
    private static String points(final Card c) {
        final int points = c.getPoints();
        return points >= 10 ? (new StringBuilder()).append(points).toString() : (new StringBuilder(" ")).append(points).toString();
    }

    /**
     * Instantiates a new exporter.
     */
    public Exporter() {
    }
}
