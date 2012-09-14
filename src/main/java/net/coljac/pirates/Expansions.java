package net.coljac.pirates;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Q9 Software
 * User: coljac
 * Date: 20/11/2006.
 */
public class Expansions {

    /** The Constant sets. */
    private static final Collection<Expansion> sets = new ArrayList<Expansion>();

    static {
        try {
            final BufferedReader br = new BufferedReader(new InputStreamReader(Card.class.getResourceAsStream("/data/sets.txt")));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }
                final String[] tokens = line.split(",");
                sets.add(new Expansion(tokens[0], tokens[1]));
                // abbrevs.put(tokens[0], tokens[1]);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
        try {
            final BufferedReader br = new BufferedReader(new FileReader("./sets.txt"));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }
                final String[] tokens = line.split(",");
                sets.remove(tokens[1]);
                sets.add(new Expansion(tokens[0], tokens[1]));
            }
        } catch (final Exception e) {
        }
    }

    /**
     * Gets the expansion.
     * 
     * @param setName
     *            the set name
     * @return the expansion
     */
    public static Expansion getExpansion(final String setName) {
        if (setName != null) {
            for (final Expansion expansion : sets) {
                if (setName.equalsIgnoreCase(expansion.getName())) {
                    return expansion;
                }
            }
        }
        return null;
    }

    /**
     * Gets the sets.
     * 
     * @return the sets
     */
    public static Collection<Expansion> getSets() {
        return sets;
    }

}
