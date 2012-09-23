package net.coljac.pirates.tools.conversion.potsmfrance;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import net.coljac.pirates.Card;
import net.coljac.pirates.CardDatabase;
import net.coljac.pirates.Crew;
import net.coljac.pirates.Ship;
import net.coljac.pirates.Treasure;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * The Class MiniatureTradingParser.
 */
public class POTSMFranceParser {

    /** The db. */
    private static CardDatabase db;

    /**
     * Builds the crew.
     * 
     * @param cardName
     *            the card name
     * @param cardId
     *            the card id
     * @param cardDescription
     *            the card description
     * @param cardType
     *            the card type
     * @param datas
     *            the datas
     * @param expansion
     *            the expansion
     * @return the card
     */
    private static Card buildCrew(final String cardName, final String cardId, final String cardDescription, final String cardType, final Map<String, String> datas, final String expansion) {
        final Crew crew = new Crew();
        crew.setName(cardName);
        // crew.setId(Long.valueOf(cardId).longValue());
        crew.setNumber(cardId);
        crew.setExtra(cardId);
        crew.setRarity(buildRarity(datas.get("Rarity")));
        crew.setRules(cardDescription);
        crew.setExpansion(expansion);
        crew.setFaction(buildFaction(datas.get("Faction Affiliation")));
        crew.setPoints(Integer.valueOf(datas.get("Point Value")));
        final Crew previous = (Crew) findCard(cardName);
        if (previous != null) {
            crew.setFlavor(previous.getFlavor());
            crew.setRules(previous.getRules());
            crew.setGeneric(previous.isGeneric());
            crew.setRank(previous.getRank());
            crew.setLink(previous.getLink());
        }

        return crew;
    }

    /**
     * Builds the faction.
     * 
     * @param faction
     *            the faction
     * @return the string
     */
    private static String buildFaction(final String faction) {
        final Map<String, String> conversions = new HashMap<String, String>();
        conversions.put("England", "English");
        conversions.put("Pirate", "Pirates");
        conversions.put("Spain", "Spanish");
        // French, The Cursed, American, Barbary Corsair
        if (conversions.containsKey(faction)) {
            return conversions.get(faction);
        } else {
            return faction;
        }
    }

    /**
     * Builds the rarity.
     * 
     * @param rarity
     *            the rarity
     * @return the string
     */
    private static String buildRarity(final String rarity) {
        if (rarity.equals("C")) {
            return "Common";
        } else if (rarity.equals("R")) {
            return "Rare";
        } else if (rarity.equals("U")) {
            return "Uncommon";
        } else {
            throw new RuntimeException("Unknown rariry [" + rarity + "]");
        }
    }

    /**
     * Builds the ship.
     * 
     * @param cardName
     *            the card name
     * @param cardId
     *            the card id
     * @param cardDescription
     *            the card description
     * @param cardType
     *            the card type
     * @param datas
     *            the datas
     * @param expansion
     *            the expansion
     * @return the card
     */
    private static Card buildShip(final String cardName, final String cardId, final String cardDescription, final String cardType, final Map<String, String> datas, final String expansion) {
        final Ship ship = new Ship();
        ship.setName(cardName);
        // ship.setId(Long.valueOf(cardId).longValue());
        ship.setNumber(cardId);
        ship.setExtra(cardId);
        ship.setRules(cardDescription);
        ship.setExpansion(expansion);
        ship.setRarity(buildRarity(datas.get("Rarity")));
        ship.setCargo(Integer.valueOf(datas.get("Cargo Space")));
        ship.setFaction(buildFaction(datas.get("Faction Affiliation")));
        ship.setMove(datas.get("Base Move").replaceAll("\\+", ","));
        ship.setMasts(Integer.valueOf(datas.get("Number of Masts")));
        ship.setPoints(Integer.valueOf(datas.get("Point Value")));
        final String cannons = datas.get("Cannons").replaceAll("-", ",");
        ship.setCannons(cannons);
        final Ship previous = (Ship) findCard(cardName);
        if (previous != null) {
            // System.out.println(cardName + " | " + previous.getNumber() + " | " + previous.getId() + " | " + previous.getExtra());
            ship.setFlavor(previous.getFlavor());
            ship.setRules(previous.getRules());
        }
        return ship;
    }

    /**
     * Builds the treasure.
     * 
     * @param cardName
     *            the card name
     * @param cardId
     *            the card id
     * @param cardDescription
     *            the card description
     * @param cardType
     *            the card type
     * @param datas
     *            the datas
     * @param expansion
     *            the expansion
     * @return the card
     */
    private static Card buildTreasure(final String cardName, final String cardId, final String cardDescription, final String cardType, final Map<String, String> datas, final String expansion) {
        final Treasure treasure = new Treasure();
        treasure.setName(cardName);
        // treasure.setId(Long.valueOf(cardId).longValue());
        treasure.setNumber(cardId);
        treasure.setExtra(cardId);
        treasure.setRules(cardDescription);
        treasure.setRarity(buildRarity(datas.get("Rarity")));
        treasure.setExpansion(expansion);
        treasure.setPoints(Integer.valueOf(datas.get("Point Value")));
        treasure.setFaction(buildFaction(datas.get("Faction Affiliation")));
        final Treasure previous = (Treasure) findCard(cardName);
        if (previous != null) {
            treasure.setFlavor(previous.getFlavor());
            treasure.setRules(previous.getRules());
        }
        return treasure;
    }

    /**
     * Creates the database.
     * 
     * @return the card database
     */
    private static void createDatabase() {
        final String cardDB = "cards.db";
        try {
            db = CardDatabase.init(cardDB);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Dump cards.
     * 
     * @param cards
     *            the cards
     */
    private static void dumpCards(final Collection<Card> cards) {
        for (final Card card : cards) {
            System.out.println(card.toCSV());
        }
    }

    /**
     * Extract fields.
     * 
     * @param li
     *            the li
     * @return the map
     */
    private static Map<String, String> extractFields(final Elements li) {
        final Map<String, String> result = new HashMap<String, String>();
        for (final Element e : li) {
            final StringTokenizer st = new StringTokenizer(e.text(), ":");
            final String key = st.nextToken().trim();
            String value = st.nextToken();
            value = value.replaceAll("<strong>", "");
            value = value.replaceAll("</strong>", "");
            result.put(key, value.trim());
        }
        return result;
    }

    /**
     * Find card.
     * 
     * @param cardName
     *            the card name
     * @return the card
     */
    private static Card findCard(final String cardName) {
        for (final Card c : db.getCardsByName(cardName)) {
            if (c.getExpansion().equalsIgnoreCase("Pirates of the Spanish Main")) {
                return c;
            }
        }
        System.err.println("can't find card [" + cardName + "]");
        return null;
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
        final Map<String, String> result = new HashMap<String, String>();
        final String baseUrl = "http://www.potsm-france.com/modules/pirates_liste/carte.php?id=";

        for (int i = 1; i <= 13; i++) {
            final String extension = StringUtils.leftPad(String.valueOf(i), 2, "0");
            for (int j = 1; j <= 500; j++) {
                for (int k = 1; k <= 2; k++) {
                    final String card = extension + "-" + StringUtils.leftPad(String.valueOf(j), 3, "0") + "-" + k;
                    final URL url = new URL(baseUrl + card);
                    //
                    // final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                    final InputStream in = url.openStream();
                    if (in != null) {
                        final Document jsoupDoc = Jsoup.parse(in, "ISO-8859-1", baseUrl + card);
                        final Elements divs = jsoupDoc.getElementsByAttributeValueContaining("style", "text-align");
                        if ((divs != null) && (divs.size() >= 2)) {
                            final Element eId = divs.get(2);
                            final String realId = eId.text().replace('(', '\0').replace(')', '\0').trim();
                            if (realId.equalsIgnoreCase(card)) {
                                final Elements texts = jsoupDoc.getElementsByAttributeValueContaining("style", "text-indent");
                                if (texts.size() == 2) {
                                    System.out.println(realId + "=" + texts.get(1).text());
                                } else if (texts.size() == 3) {
                                    System.out.println(realId + "=" + texts.get(2).text());
                                } else if (texts.size() == 4) {
                                    if (divs.size() >= 6) {
                                        // 2 cards
                                        final Element eId2 = divs.get(6);
                                        final String realId2 = eId2.text().replace('(', '\0').replace(')', '\0').trim();
                                        System.out.println(realId + "=" + texts.get(1).text());
                                        System.out.println(realId2 + "=" + texts.get(3).text());
                                    } else {
                                        // one card with rules + extra
                                        System.out.println(realId + "=" + texts.get(2).text() + "\t" + texts.get(3).text());
                                    }
                                } else if (texts.size() == 5) {
                                    final Element eId2 = divs.get(6);
                                    final String realId2 = eId2.text().replace('(', '\0').replace(')', '\0').trim();
                                    System.out.println(realId + "=" + texts.get(2).text());
                                    System.out.println(realId2 + "=" + texts.get(4).text());
                                } else if (texts.size() == 6) {
                                    final Element eId2 = divs.get(6);
                                    final String realId2 = eId2.text().replace('(', '\0').replace(')', '\0').trim();
                                    System.out.println(realId + "=" + texts.get(2).text());
                                    System.out.println(realId2 + "=" + texts.get(5).text());
                                } else {
                                    System.err.println(card + "!!!!" + texts.size());
                                }
                            }
                        }
                        Thread.sleep(3000);
                    }
                }
            }
        }
        System.exit(0);
    }
}
