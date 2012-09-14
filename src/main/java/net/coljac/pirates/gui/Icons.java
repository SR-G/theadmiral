package net.coljac.pirates.gui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 13, 2006
 */
public class Icons {

    /** The Constant path. */
    private static final String path = "/icons/";

    /** The faction icons. */
    public static Map<String, Icon> factionIcons = new HashMap<String, Icon>();

    /** The Constant ICON_ADMIRAL_32. */
    public static final ImageIcon ICON_ADMIRAL_32 = newImageIcon(path + "admiral.png");

    /** The Constant ICON_ADMIRAL_LARGE. */
    public static final ImageIcon ICON_ADMIRAL_LARGE = newImageIcon(path + "admirals.png");

    /** The Constant SYMBOL_MASTS_32. */
    public static final Icon SYMBOL_MASTS_32 = newImageIcon(path + "masts_32.png");

    /** The Constant SYMBOL_GUNS_32. */
    public static final Icon SYMBOL_GUNS_32 = newImageIcon(path + "guns_32.png");

    /** The Constant SYMBOL_MOVE_32. */
    public static final Icon SYMBOL_MOVE_32 = newImageIcon(path + "move_32.png");

    /** The Constant SYMBOL_RARITY_32. */
    public static final Icon SYMBOL_RARITY_32 = newImageIcon(path + "rarity_32.png");

    /** The Constant SYMBOL_HAVE_32. */
    public static final Icon SYMBOL_HAVE_32 = newImageIcon(path + "have_32.png");

    /** The Constant SYMBOL_WANT_32. */
    public static final Icon SYMBOL_WANT_32 = newImageIcon(path + "want_32.png");

    /** The Constant SYMBOL_CARGO_32. */
    public static final Icon SYMBOL_CARGO_32 = newImageIcon(path + "cargo_32.png");

    /** The Constant SYMBOL_MASTS_16. */
    public static final Icon SYMBOL_MASTS_16 = newImageIcon(path + "masts_16.png");

    /** The Constant SYMBOL_GUNS_16. */
    public static final Icon SYMBOL_GUNS_16 = newImageIcon(path + "guns_16.png");

    /** The Constant SYMBOL_MOVE_16. */
    public static final Icon SYMBOL_MOVE_16 = newImageIcon(path + "move_16.png");

    /** The Constant SYMBOL_RARITY_16. */
    public static final Icon SYMBOL_RARITY_16 = newImageIcon(path + "rarity_16.png");

    /** The Constant SYMBOL_HAVE_16. */
    public static final Icon SYMBOL_HAVE_16 = newImageIcon(path + "have_16.png");

    /** The Constant SYMBOL_WANT_16. */
    public static final Icon SYMBOL_WANT_16 = newImageIcon(path + "want_16.png");

    /** The Constant SYMBOL_CARGO_16. */
    public static final Icon SYMBOL_CARGO_16 = newImageIcon(path + "cargo_16.png");

    /**
     * Gets the faction icon.
     * 
     * @param faction
     *            the faction
     * @return the faction icon
     */
    public static Icon getFactionIcon(final String faction) {
        return factionIcons.get(faction);
    }

    /**
     * New image icon.
     * 
     * @param url
     *            the url
     * @return the image icon
     */
    public static ImageIcon newImageIcon(final String url) {
        if (new File(url).exists()) {
            return new ImageIcon(url);
        } else {
            return new ImageIcon(Icons.class.getResource(url));
        }
    }

}
