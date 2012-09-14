package net.coljac.pirates.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.coljac.pirates.Card;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 15, 2006
 */
public class CardPanel extends JPanel {

    // protected List<Card> cards = new ArrayList<Card>();
    // protected List<Card> allCards = new ArrayList<Card>();

    /** The blank image. */
    protected ImageIcon blankImage;

    /** The picture. */
    protected JLabel picture;

    /**
     * Adds the picture.
     * 
     * @param panel
     *            the panel
     */
    public void addPicture(final JPanel panel) {

        final JPanel tempPanel = new JPanel(new BorderLayout());
        panel.add(tempPanel, BorderLayout.CENTER);

        picture = new JLabel("Image", null, JLabel.RIGHT);
        picture.setText(null);
        final URL imgURL = getClass().getResource("/icons/blank.png");
        final ImageIcon temp = new ImageIcon(imgURL);
        final int expectedHeight = 200;
        final int currentWidth = temp.getIconWidth();
        final int currentHeight = temp.getIconHeight();
        final double expectedWidth = (currentHeight / expectedHeight) * currentWidth;
        blankImage = new ImageIcon(temp.getImage().getScaledInstance((int) Math.round(expectedWidth), expectedHeight, Image.SCALE_SMOOTH));
        picture.setIcon(blankImage);
        picture.setPreferredSize(new Dimension(300, 200));
        picture.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
        final JLabel spacer = new JLabel();
        tempPanel.add(spacer, BorderLayout.CENTER);
        tempPanel.add(picture, BorderLayout.EAST);
    }

    private String buildCardFileName(final String number) {
        final String result;
        final String extension = ".png";

        if (number.toUpperCase().endsWith("A")) {
            return number.toUpperCase().replaceAll("A", "-1") + extension;
        }

        if (number.toUpperCase().endsWith("B")) {
            return number.toUpperCase().replaceAll("B", "-2") + extension;
        }

        if (number.toUpperCase().endsWith("_1")) {
            return number.toUpperCase().replaceAll("_1", "-1") + extension;
        }

        if (number.toUpperCase().endsWith("_2")) {
            return number.toUpperCase().replaceAll("_2", "-2") + extension;
        }

        return number + "-1" + extension;
    }

    //
    // public List<Card> getCards() {
    // return cards;
    // }
    //
    // public List<Card> getAllCards() {
    // return allCards;
    // }

    /**
     * Gets the card at row.
     * 
     * @param row
     *            the row
     * @return the card at row
     */
    public Card getCardAtRow(final int row) {
        // return cards.get(row);
        return null;
    }

    /**
     * Re filter.
     */
    public void reFilter() {
    }

    /**
     * Show menu.
     * 
     * @param comp
     *            the comp
     * @param x
     *            the x
     * @param y
     *            the y
     */
    public void showMenu(final Component comp, final int x, final int y) {

    }

    /**
     * Update picture.
     * 
     * @param card
     *            the card
     */
    public void updatePicture(final Card card) {
        final String imagesPath = "img" + File.separator + "expansions" + File.separator;
        final String expectedFileCardName = imagesPath + card.getSetAbbreviation(card.getExpansion()) + File.separator + buildCardFileName(card.getNumber());
        if (new File(expectedFileCardName).exists()) {
            final ImageIcon newImage = new ImageIcon(expectedFileCardName, card.getName());
            final int currentHeight = newImage.getIconHeight();
            final int currentWidth = newImage.getIconWidth();
            final int expectedHeight = 200;
            final double ratio = (double) expectedHeight / (double) currentHeight;
            final int expectedWidth = (int) (ratio * currentWidth);
            picture.setIcon(new ImageIcon(newImage.getImage().getScaledInstance(expectedWidth, expectedHeight, Image.SCALE_SMOOTH)));
            picture.setToolTipText(expectedFileCardName);
            picture.repaint();
        } else {
            System.err.println("Image not found [" + expectedFileCardName + "]");
            picture.setToolTipText(expectedFileCardName);
            picture.setIcon(blankImage);
            picture.repaint();
        }
    }

}
