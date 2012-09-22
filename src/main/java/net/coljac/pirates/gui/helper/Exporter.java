package net.coljac.pirates.gui.helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.coljac.pirates.Card;
import net.coljac.pirates.CardDatabase;
import net.coljac.pirates.Crew;
import net.coljac.pirates.Expansion;
import net.coljac.pirates.Expansions;
import net.coljac.pirates.Fleet;
import net.coljac.pirates.Ship;
import net.coljac.pirates.ShipsCrew;
import net.coljac.pirates.gui.Constants;
import net.coljac.pirates.gui.ExporterVelocity;
import net.coljac.pirates.gui.ManagerMain;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 29, 2006
 */
public class Exporter {

    private static final class ExporterComparator implements Comparator<Card> {

        @Override
        public int compare(final Card o1, final Card o2) {
            return new CompareToBuilder().append(o1.getExpansion(), o2.getExpansion()).append(o1.getNumber(), o2.getNumber()).toComparison();
        }
    }

    /**
     * Export card image.
     * 
     * @param destinationPath
     *            the destination path
     * @param card
     *            the card
     * @throws IOException
     */
    private static void exportCardImage(final File destinationPath, final Card card) throws IOException {
        final String sourceImage = ImageHelper.getExpectedImageFulPath(card);
        final File fileSourceImage = new File(sourceImage);
        if (fileSourceImage.exists()) {
            FileUtils.copyFile(fileSourceImage, new File(destinationPath + File.separator + Card.getSetAbbreviation(card.getExpansion()) + "-" + card.getNumber() + Constants.DEFAULT_IMAGE_EXTENSION));
        }
    }

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
        try {
            for (final Object element : db.getCards()) {
                final Card card = (Card) element;
                if (!mine || (card.getOwned() > 0) || (card.getWanted() > 0)) {
                    pw.println(card.toCSV());
                }
            }
        } finally {
            pw.flush();
            pw.close();
        }
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
        try {
            for (final Object element : db.getCards()) {
                final Card card = (Card) element;
                if ((card.getOwned() > 0) || (card.getWanted() > 0)) {
                    pw.println((new StringBuilder(String.valueOf(card.getName()))).append("\t").append(card.getNumber()).append("\t").append(Card.getSetAbbreviation(card.getExpansion())).append("\t").append(card.getOwned()).append("\t").append(card.getWanted()).toString());
                }
            }
        } finally {
            pw.flush();
            pw.close();
        }
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
        try {
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
        } finally {
            pw.flush();
            pw.close();
        }
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
        try {
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
        } finally {
            pw.flush();
            pw.close();
        }
    }

    /**
     * Export mt as html.
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
    public static void exportMTAsHTML(final CardDatabase db, final String fileName, final String comment, final boolean haves) throws IOException {
        db.sort(new ExporterComparator());
        final File destinationPath = new File(fileName).getParentFile();
        final PrintWriter pw = new PrintWriter(new FileWriter(fileName));
        try {
            final ExporterVelocity exporterVelocityCard = new ExporterVelocity("template-export-duplicates-card.velocity");
            final ExporterVelocity exporterVelocityMain = new ExporterVelocity("template-export-duplicates-main.velocity");
            final Map<String, String> duplicates = new HashMap<String, String>();
            int total = 0, count = 0;
            for (final Expansion expansion : Expansions.getSets()) {
                final StringBuilder table = new StringBuilder();
                for (final Object element : db.getCards()) {
                    final Card card = (Card) element;
                    if (haves) {
                        if ((card.getOwned() > 1) && card.getExpansion().equalsIgnoreCase(expansion.getName())) {
                            table.append(renderCardTemplate(exporterVelocityCard, card));
                            exportCardImage(destinationPath, card);
                            count++;
                            total += (card.getOwned() - 1);
                        }
                    } else if ((card.getWanted() > 0) && card.getExpansion().equalsIgnoreCase(expansion.getName())) {
                        table.append(renderCardTemplate(exporterVelocityCard, card));
                        exportCardImage(destinationPath, card);
                        count++;
                        total += (card.getWanted());
                    }
                }
                if (StringUtils.isNotEmpty(table.toString())) {
                    duplicates.put(expansion.getName(), table.toString());
                }
            }
            exporterVelocityMain.addContext("duplicates", duplicates);
            exporterVelocityMain.addContext("comment", comment);
            exporterVelocityMain.addContext("date", new SimpleDateFormat().format(new Date()));
            exporterVelocityMain.addContext("duplicates-total", total);
            exporterVelocityMain.addContext("duplicates-count", count);
            pw.write(exporterVelocityMain.render());
        } finally {
            pw.flush();
            pw.close();
        }
    }

    /**
     * Gets the prefix.
     * 
     * @param set
     *            the set
     * @return the prefix
     */
    private static String getPrefix(final String set) {
        return set;
        /*
         * if (set.equals("PofSM")) {
         * return "PS-";
         * }
         * if (set.equals("PofCC")) {
         * return "C-";
         * }
         * if (set.equals("PofR")) {
         * return "R-";
         * }
         * if (set.equals("PofBC")) {
         * return "BC-";
         * }
         * if (set.equals("PofSCS")) {
         * return "SCS-";
         * } else {
         * return "";
         * }
         */
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
     * Render card template.
     * 
     * @param exporterVelocityCard
     *            the exporter velocity card
     * @param card
     *            the card
     * @return the string
     */
    private static String renderCardTemplate(final ExporterVelocity exporterVelocityCard, final Card card) {
        exporterVelocityCard.clearContext();
        exporterVelocityCard.addContext("card", card);
        exporterVelocityCard.addContext("number", String.valueOf((card.getOwned() - 1)));
        exporterVelocityCard.addContext("image", Card.getSetAbbreviation(card.getExpansion()) + "-" + card.getNumber() + Constants.DEFAULT_IMAGE_EXTENSION);
        return exporterVelocityCard.render();

        // /**
        // * To html.
        // *
        // * @return the string
        // */
        // public String toHTML() {
        // final StringBuilder sb = new StringBuilder();
        // final String imgPath = getSetAbbreviation(getExpansion()) + "-" + getNumber() + Constants.DEFAULT_IMAGE_EXTENSION;
        // sb.append("<tr>");
        // sb.append("<td class=\"count\">" + (getOwned() - 1) + "</td>");
        // sb.append("<td><img width=\"200\" src=\"" + imgPath + "\" /></td>");
        // sb.append("</tr>");
        // return sb.toString();
        // }
        //

    }

    /**
     * Instantiates a new exporter.
     */
    public Exporter() {
    }
}
