package net.coljac.pirates.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.coljac.pirates.Expansion;
import net.coljac.pirates.Expansions;
import net.coljac.pirates.Faction;
import net.coljac.pirates.Factions;
import net.miginfocom.swing.MigLayout;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 11, 2006
 */
public class CardFilterPanel extends JPanel implements ActionListener {

    /** The filter. */
    private CardFilter filter = new CardFilter();

    /** The keyword label. */
    private JLabel factionLabel, expansionsLabel, pointsLabel, keywordLabel, ownedLabel;

    /** The factions list. */
    private final List<JCheckBox> factionsList = new ArrayList<JCheckBox>();

    /** The expansions list. */
    private final List<JCheckBox> expansionsList = new ArrayList<JCheckBox>();

    /** The pt_16. */
    private JCheckBox pt1_5, pt6_10, pt11_15, pt_16;

    /** The want. */
    private JCheckBox have, donthave, want, duplicates;

    /** The search field. */
    private JTextField searchField;

    /** The clear button. */
    private JButton searchButton, clearButton;

    /** The listener. */
    private final CardPanel listener;

    /** The factions map. */
    private final Map<Component, Faction> factionsMap = new HashMap<Component, Faction>();

    /** The preferred. */
    private final Dimension preferred = new Dimension(50, 55);

    /** The faction check boxes links. */
    private final Map<JLabel, JCheckBox> factionCheckBoxesLinks = new HashMap<JLabel, JCheckBox>();

    /**
     * Instantiates a new card filter panel.
     * 
     * @param listener
     *            the listener
     */
    public CardFilterPanel(final CardPanel listener) {
        this.listener = listener;

        setLayout(new BorderLayout());

        final JPanel west = new JPanel();
        add(west, BorderLayout.WEST);
        west.setLayout(new MigLayout("insets 1"));

        initOwnedFilters(west);
        initFactions(west);
        initPoints(west);
        initExpansions(west);
        initSearch(west);

        initPicture(this);

    }

    /**
     * {@inheritDoc}
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        executeActionOnObject(e.getSource());
    }

    /**
     * Execute action on object.
     * 
     * @param src
     *            the src
     */
    public void executeActionOnObject(final Object src) {
        if (src == pt1_5) {
            if (pt1_5.isSelected()) {
                filter.getPointRanges().add(PointRange.oneToFive);
            } else {
                filter.getPointRanges().remove(PointRange.oneToFive);
            }
        } else if (src == pt6_10) {
            if (pt6_10.isSelected()) {
                filter.getPointRanges().add(PointRange.sixToTen);
            } else {
                filter.getPointRanges().remove(PointRange.sixToTen);
            }
        } else if (src == pt11_15) {
            if (pt11_15.isSelected()) {
                filter.getPointRanges().add(PointRange.elevenToFifteen);
            } else {
                filter.getPointRanges().remove(PointRange.elevenToFifteen);
            }
        } else if (src == pt_16) {
            if (pt_16.isSelected()) {
                filter.getPointRanges().add(PointRange.sixteenPlus);
            } else {
                filter.getPointRanges().remove(PointRange.sixteenPlus);
            }
        } else if (src == clearButton) {
            searchField.setText("");
            filter.setSearch("");
        } else if ((src == searchField) || (src == searchButton)) {
            filter.setSearch(searchField.getText());
        } else if (expansionsList.contains(src)) { // src == setSM || src == setSCS || src == setR || src == setCC || src == setBC) {
            final JCheckBox check = (JCheckBox) src;
            final String faction = check.getText();
            if (check.isSelected()) {
                filter.getSets().add(faction);
            } else {
                filter.getSets().remove(faction);
            }
        } else if (factionsList.contains(src)) {
            // src == facA || src == facB || src == facE ||src == facC || src == facF || src == facJ || src == facP || src == facS
            final JCheckBox check = (JCheckBox) src;
            final String faction = factionsMap.get(check).getKey();
            if (check.isSelected()) {
                filter.getFactions().add(faction);
            } else {
                filter.getFactions().remove(faction);
            }
        } else if (src == have) {
            filter.setHave(have.isSelected());
        } else if (src == donthave) {
            filter.setDonthave(donthave.isSelected());
        } else if (src == want) {
            filter.setWant(want.isSelected());
        } else if (src == duplicates) {
            filter.setDuplicates(duplicates.isSelected());
        }

        if (listener != null) {
            listener.reFilter();
        }
    }

    /**
     * Gets the filter.
     * 
     * @return the filter
     */
    public CardFilter getFilter() {
        return filter;
    }

    /**
     * Gets the panel.
     * 
     * @return the panel
     */
    private JPanel getPanel() {
        final JPanel panel = new JPanel();
        // panel.setLayout(new FlowLayout());
        panel.setLayout(new MigLayout("insets 0 0 0 100", "[]", "[bottom]"));
        panel.setForeground(Color.BLUE);
        panel.setFont(new Font("Arial", Font.PLAIN, 8));
        return panel;
    }

    /**
     * Inits the expansions.
     * 
     * @param p
     *            the p
     */
    private void initExpansions(final JPanel p) {
        expansionsLabel = new JLabel("Sets :");
        for (final Expansion expansion : Expansions.getSets()) {
            final JCheckBox check = new JCheckBox(expansion.getAbbreviation());
            check.addActionListener(this);
            check.setToolTipText(expansion.getName());
            expansionsList.add(check);
        }
        final JPanel temp = getPanel();
        int cnt = 0;
        final int nbExpansionsByRows = Math.round(expansionsList.size() / 3);
        System.out.println(">>>>>>>>>>>>>>" + nbExpansionsByRows);
        for (final JCheckBox check : expansionsList) {
            temp.add(check);
            System.out.println("  " + check.getText());
            cnt++;
            if ((cnt > 0) && ((cnt % nbExpansionsByRows) == 0)) {
                temp.add(check, "h 14!, wrap");
            } else {
                temp.add(check, "h 14!");
            }

        }
        temp.setPreferredSize(preferred);
        p.add(expansionsLabel);
        p.add(temp, "wrap");

    }

    /**
     * Inits the factions.
     * 
     * @param p
     *            the p
     */
    private void initFactions(final JPanel p) {
        factionLabel = new JLabel("Faction :");
        for (final Faction f : Factions.getFactions()) {
            final JCheckBox check = new JCheckBox();
            factionsList.add(check);
            factionsMap.put(check, f);
        }

        final JPanel temp = getPanel();
        temp.setPreferredSize(preferred);
        int cnt = 0;
        final int nbFactionsByRows = Math.round(factionsList.size() / 2);
        for (final JCheckBox check : factionsList) {
            check.addActionListener(this);
            check.setToolTipText(factionsMap.get(check).getKey());
            temp.add(check);
            final JLabel l = new JLabel(Icons.factionIcons.get(factionsMap.get(check).getKey()));
            factionCheckBoxesLinks.put(l, check);
            l.setToolTipText(factionsMap.get(check).getKey());
            l.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(final MouseEvent e) {
                    final Object src = e.getSource();
                    if (factionCheckBoxesLinks.containsKey(src)) {
                        final JCheckBox currentCheck = factionCheckBoxesLinks.get(src);
                        if (currentCheck != null) {
                            currentCheck.setSelected(!currentCheck.isSelected());
                            executeActionOnObject(factionCheckBoxesLinks.get(src));
                        }
                    }
                }

                @Override
                public void mouseEntered(final MouseEvent e) {
                }

                @Override
                public void mouseExited(final MouseEvent e) {
                }

                @Override
                public void mousePressed(final MouseEvent e) {
                }

                @Override
                public void mouseReleased(final MouseEvent e) {
                }

            });
            if ((cnt > 0) && ((cnt % nbFactionsByRows) == 0)) {
                temp.add(l, "h 15!, wrap");
            } else {
                temp.add(l, "h 15!");
            }
            cnt++;
        }
        p.add(factionLabel);
        p.add(temp, "wrap");
    }

    /**
     * Inits the owned filters.
     * 
     * @param p
     *            the p
     */
    private void initOwnedFilters(final JPanel p) {
        ownedLabel = new JLabel("Owned :");
        have = new JCheckBox("Have");
        have.setToolTipText("Cards owned");
        donthave = new JCheckBox("Don't have");
        duplicates = new JCheckBox("Duplicates");
        duplicates.setToolTipText("Cards owned more than 1 times");
        want = new JCheckBox("Want");
        have.addActionListener(this);
        donthave.addActionListener(this);
        donthave.setToolTipText("Cards not owned");
        duplicates.addActionListener(this);
        want.addActionListener(this);

        final JPanel temp = getPanel();
        temp.add(have, "h 15!");
        temp.add(donthave, "h 15!, wrap");
        temp.add(want, "h 15!");
        temp.add(duplicates, "h 15!, wrap");
        p.add(ownedLabel);
        p.add(temp, "");
    }

    /**
     * Inits the picture.
     * 
     * @param p
     *            the p
     */
    private void initPicture(final JPanel p) {
        listener.addPicture(p);
    }

    /**
     * Inits the points.
     * 
     * @param p
     *            the p
     */
    private void initPoints(final JPanel p) {
        pointsLabel = new JLabel("Point Range :");
        pt1_5 = new JCheckBox("1-5");
        pt6_10 = new JCheckBox("6-10");
        pt11_15 = new JCheckBox("11-15");
        pt_16 = new JCheckBox("16+");
        pt1_5.addActionListener(this);
        pt6_10.addActionListener(this);
        pt11_15.addActionListener(this);
        pt_16.addActionListener(this);

        final JPanel temp = getPanel();
        temp.add(pt1_5, "h 15!, wrap");
        temp.add(pt6_10, "h 15!, wrap");
        temp.add(pt11_15, "h 15!, wrap");
        temp.add(pt_16, "h 15!");
        p.add(pointsLabel);
        p.add(temp, "");
    }

    /**
     * Inits the search.
     * 
     * @param p
     *            the p
     */
    private void initSearch(final JPanel p) {
        keywordLabel = new JLabel("Search :");

        searchField = new JTextField("", 30);
        searchField.setToolTipText("Separate terms by commas.");

        final URL imgSearchURL = getClass().getResource("/icons/gui_search.png");
        final Icon iconSearch = new ImageIcon(imgSearchURL);

        final URL imgClearURL = getClass().getResource("/icons/gui_clear.png");
        final Icon iconClear = new ImageIcon(imgClearURL);

        searchButton = new JButton("Search", iconSearch);
        searchButton.setToolTipText("Search");
        clearButton = new JButton(null, iconClear);
        clearButton.setToolTipText("Clear");
        searchField.addActionListener(this);
        searchButton.addActionListener(this);
        clearButton.addActionListener(this);

        final JPanel temp = getPanel();
        temp.add(searchField, "grow");
        temp.add(searchButton);
        temp.add(clearButton, "w 40!");
        p.add(keywordLabel, "");
        p.add(temp, "span");
    }

    /**
     * Sets the filter.
     * 
     * @param filter
     *            the new filter
     */
    public void setFilter(final CardFilter filter) {
        this.filter = filter;
    }

}
