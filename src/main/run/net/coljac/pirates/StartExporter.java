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
        final StringBuilder comment = new StringBuilder();
        comment.append("Contact : Killing Joke sur <a href=\"http://trictrac.net/jeux/forum/privmsg.php?mode=post&u=4870\" target=\"_blank\">Tric Trac</a><br />");
        Exporter.exportMTAsHTML(db, "target/test.html", comment.toString(), true);
    }

}
