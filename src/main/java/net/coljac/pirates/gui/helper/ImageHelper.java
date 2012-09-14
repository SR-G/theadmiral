package net.coljac.pirates.gui.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import net.coljac.pirates.Card;
import net.coljac.pirates.CardDatabase;
import net.coljac.pirates.gui.Constants;

/**
 * The Class ImageHelper.
 */
final public class ImageHelper {

    /**
     * Check image avaibility.
     * 
     * @param db
     *            the db
     * @return the collection
     */
    public static Collection<String> checkImageAvaibility(final CardDatabase db) {
        final Collection<String> notAvailablesImages = new ArrayList<String>();
        for (final Card card : db.getCards()) {
            final String imageFullPath = getExpectedImageFulPath(card);
            if (!new File(imageFullPath).exists()) {
                notAvailablesImages.add(imageFullPath);
            }
        }
        return notAvailablesImages;
    }

    /**
     * Gets the expected image file name.
     * 
     * @param number
     *            the number
     * @return the expected image file name
     */
    public static String getExpectedImageFileName(final String number) {
        if (number.endsWith("-1") || number.endsWith("-2")) {
            return number + Constants.DEFAULT_IMAGE_EXTENSION;
        }

        if (number.toUpperCase().endsWith("A")) {
            return number.toUpperCase().replaceAll("A", "-1") + Constants.DEFAULT_IMAGE_EXTENSION;
        }

        if (number.toUpperCase().endsWith("B")) {
            return number.toUpperCase().replaceAll("B", "-2") + Constants.DEFAULT_IMAGE_EXTENSION;
        }

        if (number.endsWith("_1")) {
            return number.replaceAll("_1", "-1") + Constants.DEFAULT_IMAGE_EXTENSION;
        }

        if (number.endsWith("_2")) {
            return number.replaceAll("_2", "-2") + Constants.DEFAULT_IMAGE_EXTENSION;
        }

        return number + "-1" + Constants.DEFAULT_IMAGE_EXTENSION;
    }

    /**
     * Gets the expected image ful path.
     * 
     * @param card
     *            the card
     * @return the expected image ful path
     */
    public static String getExpectedImageFulPath(final Card card) {
        return new StringBuilder(Constants.DEFAULT_IMAGE_PATH).append(Card.getSetAbbreviation(card.getExpansion())).append(File.separator).append(getExpectedImageFileName(card.getNumber())).toString();
    }

    /**
     * Instantiates a new image helper.
     */
    private ImageHelper() {

    }

}
