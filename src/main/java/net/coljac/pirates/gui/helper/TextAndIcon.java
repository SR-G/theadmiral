package net.coljac.pirates.gui.helper;

import javax.swing.Icon;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 15, 2006
 */
public class TextAndIcon {

    /**
     * Instantiates a new text and icon.
     * 
     * @param text
     *            the text
     * @param icon
     *            the icon
     */
    TextAndIcon(String text, Icon icon) {

        this.text = text;
        this.icon = icon;
    }

    /** The text. */
    String text;

    /** The icon. */
    Icon icon;
}
