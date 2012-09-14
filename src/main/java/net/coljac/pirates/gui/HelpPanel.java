package net.coljac.pirates.gui;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 23, 2006
 */
public class HelpPanel extends JPanel {

    /**
     * Instantiates a new help panel.
     */
    public HelpPanel(final String htmlResource) {
        setLayout(new BorderLayout());
        String text = "";

        try {
            final InputStream is = ManagerMain.class.getResourceAsStream(htmlResource);
            final BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            final StringBuffer help = new StringBuffer();
            while ((line = br.readLine()) != null) {
                help.append(line);
            }
            text = help.toString();
            br.close();
            is.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }

        final JEditorPane helpPane = new JEditorPane("text/html", text);
        helpPane.setEditable(false);

        final JScrollPane scroller = new JScrollPane();
        scroller.setViewportView(helpPane);
        helpPane.setCaretPosition(0);
        scroller.getHorizontalScrollBar().setValue(scroller.getHorizontalScrollBar().getMinimum());
        this.add(scroller, BorderLayout.CENTER);
    }
}
