package net.coljac.pirates;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.coljac.pirates.gui.Icons;

/**
 * Created by Q9 Software
 * User: coljac
 * Date: 20/11/2006.
 */
public class Factions {

    /** The factions. */
    private static List<Faction> factions = new ArrayList<Faction>();

    static {
        try {
            final BufferedReader br = new BufferedReader(new InputStreamReader(Icons.class.getResourceAsStream("/data/factions.txt")));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }
                final String[] tokens = line.split(",");
                factions.add(new Faction(tokens[0]));

                Icons.factionIcons.put(tokens[0], Icons.newImageIcon(tokens[1]));
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
        try {
            final BufferedReader br = new BufferedReader(new FileReader("./factions.txt"));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }
                final String[] tokens = line.split(",");
                factions.remove(tokens[0]);
                factions.add(new Faction(tokens[0]));
                Icons.factionIcons.put(tokens[0], Icons.newImageIcon(tokens[1]));
            }
        } catch (final Exception e) {
        }
    }

    /**
     * Gets the factions.
     * 
     * @return the factions
     */
    public static Collection<Faction> getFactions() {
        return factions;
    }

}
