package net.coljac.pirates.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.coljac.pirates.gui.helper.StringUtil;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 30, 2006
 */
public class AboutPanel extends JPanel {

    /** The jokes. */
    private List<String> jokes;

    /**
     * Instantiates a new about panel.
     */
    public AboutPanel() {
        initJokes();
        setLayout(new BorderLayout());
        final JLabel jokeLabel = new JLabel("Arrrrrr!");
        if (jokes.size() > 0) {
            int index = new Random().nextInt(jokes.size());
            if ((index % 2) == 1) {
                index--;
            }
            jokeLabel.setText("<html>" + StringUtil.splitString(jokes.get(index), 50) + "<br>" + StringUtil.splitString(jokes.get(index + 1), 50) + "</html>");
        }

        jokeLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        final JLabel logoLabel = new JLabel(Icons.ICON_ADMIRAL_LARGE);

        final JLabel aboutLabel1 = new JLabel("The Admiral");
        final Font defaultFont = aboutLabel1.getFont();

        aboutLabel1.setForeground(new Color(0, 0, 50));
        aboutLabel1.setFont(new Font("Arial", Font.BOLD, 20));

        final JLabel aboutLabel2 = new JLabel("A free fleet management application for all ye");
        final JLabel aboutLabel2a = new JLabel("Pirates of the Spanish Main.");
        aboutLabel2.setFont(new Font(defaultFont.getFontName(), Font.PLAIN, defaultFont.getSize() - 1));
        final JLabel aboutLabel3 = new JLabel("Version: " + ManagerMain.VERSION);
        final JLabel aboutLabel4 = new JLabel("Software (C) 2007 Colin Jacobs (coljac@coljac.net)");
        final JLabel aboutLabel4a = new JLabel("Portions (C) 2008 Jonathan Borowski (SuperHomer)");
        final JLabel aboutLabel5 = new JLabel("<html>Pirates of the Spanish Main and all card descriptions <br>are (C) and remain the property of WizKids Inc.</html>");
        aboutLabel5.setFont(new Font(defaultFont.getFontName(), Font.PLAIN, defaultFont.getSize() - 2));

        final JPanel left = new JPanel();
        left.setLayout(new BorderLayout());
        jokeLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        left.add(jokeLabel, BorderLayout.SOUTH);
        left.add(logoLabel, BorderLayout.CENTER);
        left.setBorder(BorderFactory.createEtchedBorder());
        this.add(left, BorderLayout.WEST);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 15));

        final JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.add(new JLabel("         "));
        aboutLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutLabel2a.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutLabel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutLabel4.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutLabel4a.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutLabel5.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutLabel1.setHorizontalAlignment(JLabel.CENTER);
        aboutLabel2.setHorizontalAlignment(JLabel.CENTER);
        aboutLabel2a.setHorizontalAlignment(JLabel.CENTER);
        aboutLabel3.setHorizontalAlignment(JLabel.CENTER);
        aboutLabel4.setHorizontalAlignment(JLabel.CENTER);
        aboutLabel4a.setHorizontalAlignment(JLabel.CENTER);
        aboutLabel5.setHorizontalAlignment(JLabel.CENTER);
        right.add(aboutLabel1);
        right.add(aboutLabel2);
        right.add(aboutLabel2a);
        right.add(aboutLabel3);
        right.add(aboutLabel4);
        right.add(aboutLabel4a);
        right.add(aboutLabel5);
        right.setBorder(BorderFactory.createEtchedBorder());

        this.add(right, BorderLayout.CENTER);
    }

    /**
     * Inits the jokes.
     */
    private void initJokes() {
        jokes = new ArrayList<String>();
        final InputStream is = AboutPanel.class.getResourceAsStream("/data/jokes.txt");
        if (is != null) {
            final BufferedReader br = new BufferedReader(new InputStreamReader(is));
            try {
                String line = null;
                while ((line = br.readLine()) != null) {
                    jokes.add(line);
                }
                br.close();
            } catch (final IOException e) {
            }
        }
    }

}
