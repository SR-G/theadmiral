package net.coljac.pirates.gui.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import net.coljac.pirates.Card;
import net.coljac.pirates.CardDatabase;
import net.coljac.pirates.Crew;
import net.coljac.pirates.Event;
import net.coljac.pirates.Ship;
import net.coljac.pirates.Treasure;
import net.coljac.pirates.gui.ManagerMain;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 27, 2006
 */
public class Importer {

    // public static void importCards(InputStream is) throws IOException {
    // CardDatabase db = ManagerMain.instance.db;
    // if (db == null) return;
    // BufferedReader br = new BufferedReader(new InputStreamReader(is));
    // String line = null;
    // while ((line = br.readLine()) != null) {
    // Card card = parseCard(line);
    // if (card != null) {
    // db.getCards().add(card);
    // if (card instanceof Ship) {
    // db.getShips().add((Ship) card);
    // } else if (card instanceof Crew) {
    // db.getCrew().add((Crew) card);
    // } else if (card instanceof Event) {
    // db.getEvents().add((Event) card);
    // }
    // }
    // }
    // }
    //
    // public static void importCards(String file) throws IOException {
    // FileInputStream fis = new FileInputStream(file);
    // importCards(fis);
    // }
    //
    // // type, number, set, faction, rarity, name, points, masts, move, cannons, cargo, rules, link
    // private static Card parseCard(String line) {
    // String[] tokens = line.trim().split("\t");
    // if (tokens[0].equals("ship")) {
    // Ship ship = new Ship();
    // ship.setCannons(tokens[8]);
    // ship.setCardType("ship");
    // ship.setCargo(Integer.parseInt(tokens[10]));
    // ship.setExpansion(tokens[2]);
    // ship.setExtra("");
    // ship.setFaction(tokens[3]);
    // ship.setFlavor("");
    // ship.setMasts(Integer.parseInt(tokens[7]));
    // ship.setMove(tokens[8]);
    // ship.setName(tokens[5]);
    // ship.setNumber(tokens[1]);
    // ship.setPoints(Integer.parseInt(tokens[6]));
    // ship.setRarity(tokens[4]);
    // ship.setRules(tokens[11]);
    // return ship;
    // }
    // return null;
    // }
    //

    /**
     * Import cards.
     * 
     * @param fileName
     *            the file name
     * @return the string
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static String importCards(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = null;
        CardDatabase db = ManagerMain.instance.db;
        String result = "";
        int cards = 0;
        while ((line = br.readLine()) != null) {
            Card card = Card.build(line);
            if (card == null) {
                System.err.println("NULL CARD: " + line);
                continue;
            }
            if (db.getCards().contains(card)) {
                db.getCards().remove(card);
            }
            db.getCards().add(card);
            cards++;
            if (card instanceof Ship) {
                db.getShips().add((Ship) card);
            } else if (card instanceof Crew) {
                db.getCrew().add((Crew) card);
            } else if (card instanceof Treasure) {
                db.getTreasure().add((Treasure) card);
            } else if (card instanceof Event) {
                db.getEvents().add((Event) card);
            }
        }
        br.close();
        result += "Imported " + cards + " cards.";
        return result;
    }

    /**
     * Import my cards.
     * 
     * @param fileName
     *            the file name
     * @return the string
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static String importMyCards(String fileName) throws IOException {
        StringBuffer result = new StringBuffer();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = null;
        int count = 0;

        CardDatabase db = ManagerMain.instance.db;
        while ((line = br.readLine()) != null) {
            String[] tokens = line.split("\t");

            List<Card> cards = db.getCardsByName(tokens[0]);
            if (cards.size() == 0) {
                result.append("No card found with name: " + tokens[0] + "\n");
            } else if (cards.size() == 1) {
                Card c = cards.get(0);
                c.setOwned(Integer.parseInt(tokens[3]));
                c.setWanted(Integer.parseInt(tokens[4]));
                count++;
            } else {
                Card theOne = null;
                for (Card c : cards) {
                    if (c.getNumber().equals(tokens[1]) && (c.getExpansion().equals(tokens[2]) || Card.getSetAbbreviation(c.getExpansion()).equals(tokens[2]))) {
                        theOne = c;
                        c.setOwned(Integer.parseInt(tokens[3]));
                        c.setWanted(Integer.parseInt(tokens[4]));
                        count++;
                        break;
                    }
                    if (theOne == null) {
                        result.append("Several matches for card " + tokens[0] + " - check number and set are correct.\n");
                    }
                }
            }

            db.save();

        }
        br.close();
        if (result.length() == 0) {
            result.append("Import successful.\n");
        }
        result.append("Cards updated: " + count);

        return result.toString();
    }
}
