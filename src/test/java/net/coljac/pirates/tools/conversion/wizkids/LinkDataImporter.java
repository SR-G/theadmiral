package net.coljac.pirates.tools.conversion.wizkids;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.coljac.pirates.Card;
import net.coljac.pirates.CardDatabase;
import net.coljac.pirates.Crew;
import net.coljac.pirates.helper.FileHelper;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Apr 2, 2006
 */
public class LinkDataImporter {

    /** The links. */
    private static Map<String, String> links = new HashMap<String, String>();

    // static String regex = "(Crew Link: [^<]*)<br:";
    /** The regex. */
    static String regex = "<SPAN class=TwelvepxUnderlineLink><b>[^<]*</b>.*?Crew Link: ([^<]*)";

    /** The pattern. */
    static Pattern pattern = Pattern.compile(regex);

    /** The regex2. */
    static String regex2 = ".*<SPAN class=TwelvepxUnderlineLink><b>([^<]*)</b>.*?Crew Link: ([^<]*)";

    /** The pattern2. */
    static Pattern pattern2 = Pattern.compile(regex2);

    /**
     * The main method.
     * 
     * @param args
     *            the arguments
     */
    public static void main(final String[] args) {
        process("linkdata.txt");
        process("linkdata2.txt");
        process("linkdata3.txt");
        process("linkdata4.txt");
        process("linkdata5.txt");
        try {
            final CardDatabase db = CardDatabase.init("cards.db");
            for (final String name : links.keySet()) {
                final List<Card> cards = db.getCardsByName(name);
                if (cards.size() == 0) {
                    System.out.println("Can't find " + name);
                } else if (cards.size() == 1) {
                    ((Crew) cards.get(0)).setLink(links.get(name));
                } else {
                    System.out.println("Several results " + name);
                }

            }
            db.save();

        } catch (final IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Main2.
     * 
     * @param args
     *            the args
     */
    public static void main2(final String[] args) {
        final String regex = ".*?([A-z]+) -> ([A-z]+)";
        final Pattern p = Pattern.compile(regex);
        final String input = "some stuff Col -> dave, Simon -> mike";
        final Matcher m = p.matcher(input);
        while (m.find()) {
            System.out.println(m.group(1) + ": " + m.group(2));
        }

    }

    /**
     * Process.
     * 
     * @param file
     *            the file
     */
    static void process(final String file) {
        final String in = FileHelper.getFileContentsAsString(file);
        final Matcher m = pattern.matcher(in);
        while (m.find()) {
            processLine(m.group());
        }
    }

    /**
     * Process line.
     * 
     * @param in
     *            the in
     */
    private static void processLine(final String in) {
        final Matcher m = pattern2.matcher(in);
        if (m.find()) {
            links.put(m.group(1), m.group(2));
            System.out.println(m.group(1) + " -> " + m.group(2));
        }
    }

}
