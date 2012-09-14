package net.coljac.pirates.tools.conversion.wizkids;

import java.util.Iterator;
import java.util.List;

import net.coljac.pirates.Card;
import net.coljac.pirates.CardDatabase;
import net.coljac.pirates.Treasure;
import net.coljac.pirates.helper.FileHelper;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 28, 2006
 */
public class ImportTreasure {

    /**
     * The main method.
     * 
     * @param args
     *            the arguments
     */
    public static void main(final String[] args) {
        final List<String> lines = FileHelper.getFileContentsAsList("treasures.txt");
        final CardDatabase db = MakeDB.db;
        // try {
        // db = CardDatabase.init("cards.db");
        // } catch (IOException e) {
        // e.printStackTrace();
        // System.exit(1);
        // }
        for (final Iterator<Card> it = db.getCards().iterator(); it.hasNext();) {
            if (it.next().getCardType().equals("Treasure")) {
                it.remove();
            }
        }
        for (final String line : lines) {
            final String[] tokens = line.split("\t");
            final Card treasure = new Treasure();
            treasure.setExpansion("Pirates of the " + tokens[1]);
            treasure.setName(tokens[3]);
            treasure.setPoints(0);
            treasure.setRarity(tokens[2]);
            treasure.setRules(tokens[4]);
            treasure.setNumber(tokens[0]);
            db.getCards().add(treasure);
        }

        db.save();
    }

}
