package net.coljac.pirates;

import net.coljac.pirates.gui.helper.Exporter;

/**
 * The Class StartExporter.
 */
public class StartExporter {

    /**
     * The main method.
     * 
     * @param args
     *            the arguments
     * @throws Exception
     *             the exception
     */
    public static void main(final String[] args) throws Exception {
        final CardDatabase db = CardDatabase.init("cards.db");
        Exporter.exportMTAsHTML(db, "target/test.html", "ZZZZZZZZZZZZZZZZ", true);
    }

}
