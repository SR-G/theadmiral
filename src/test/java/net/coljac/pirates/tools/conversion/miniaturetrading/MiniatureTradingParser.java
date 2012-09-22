package net.coljac.pirates.tools.conversion.miniaturetrading;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import net.coljac.pirates.Card;
import net.coljac.pirates.CardDatabase;
import net.coljac.pirates.Crew;
import net.coljac.pirates.Ship;
import net.coljac.pirates.Treasure;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * The Class MiniatureTradingParser.
 */
public class MiniatureTradingParser {

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
        createDatabase();
        final URL url = new URL("http://www.miniaturetrading.com/im/selectCard/series_id/529/full_format/1/cards_lang/");
        final String expansion = "Pirates of the Spanish Main (unlimited)";
        final Document jsoupDoc = Jsoup.parse(url, 1000);
        final Collection<Card> cards = new ArrayList<Card>();
        for (final Element e : jsoupDoc.getElementsByTag("td")) {
            if (e.hasAttr("valign")) {
                final Elements strong = e.getElementsByTag("strong");
                final Elements ul = e.getElementsByTag("ul");
                if ((strong != null) && (strong.size() > 0) && (ul != null) && (ul.size() > 0)) {
                    final Elements li = ul.get(0).getElementsByTag("li");
                    final String cardName = strong.get(0).text().replaceAll("\"Bones\"", "'Bones'");
                    final String cardDescription = e.getElementsByTag("p").get(0).text();
                    final Map<String, String> datas = extractFields(li);
                    final String cardId = datas.get("Collector's Number");
                    final String cardType = datas.get("Type");
                    if (!cardId.endsWith("-G")) {
                        Card card = null;
                        if ("Ship".equalsIgnoreCase(cardType)) {
                            card = buildShip(cardName, cardId, cardDescription, cardType, datas, expansion);
                        } else if ("Crew".equalsIgnoreCase(cardType)) {
                            card = buildCrew(cardName, cardId, cardDescription, cardType, datas, expansion);
                        } else if ("Unique Treasure".equalsIgnoreCase(cardType)) {
                            card = buildTreasure(cardName, cardId, cardDescription, cardType, datas, expansion);
                        } else {
                            System.err.println("Unknown type [" + cardType + "]");
                        }
                        if (card != null) {
                            cards.add(card);
                        }
                    }
                }
            }
        }
        dumpCards(cards);
    }

}
