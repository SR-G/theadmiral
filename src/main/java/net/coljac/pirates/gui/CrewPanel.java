package net.coljac.pirates.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.coljac.pirates.Card;
import net.coljac.pirates.Crew;
import net.coljac.pirates.Fleet;
import net.coljac.pirates.Ship;
import net.coljac.pirates.gui.helper.FactionCellRenderer;
import net.coljac.pirates.gui.helper.HandyTableModel;
import net.coljac.pirates.gui.helper.IconHeaderRenderer;
import net.coljac.pirates.gui.helper.IncrementMouseListener;
import net.coljac.pirates.gui.helper.RarityRenderer;
import net.coljac.pirates.gui.helper.ToolTipCellRenderer;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 15, 2006
 */
public class CrewPanel extends CardPanel {

    /**
     * The Class CrewTableModel.
     */
    class CrewTableModel extends HandyTableModel {

        /**
         * {@inheritDoc}
         * 
         * @see javax.swing.table.TableModel#getColumnCount()
         */
        @Override
        public int getColumnCount() {
            return cols.length;
        }

        /**
         * {@inheritDoc}
         * 
         * @see javax.swing.table.AbstractTableModel#getColumnName(int)
         */
        @Override
        public String getColumnName(final int columnIndex) {
            return cols[columnIndex];
        }

        /**
         * {@inheritDoc}
         * 
         * @see javax.swing.table.TableModel#getRowCount()
         */
        @Override
        public int getRowCount() {
            return cards.size();
        }

        /**
         * {@inheritDoc}
         * 
         * @see javax.swing.table.TableModel#getValueAt(int, int)
         */
        @Override
        public Object getValueAt(final int rowIndex, final int columnIndex) {
            final Crew crew = cards.get(rowIndex);

            switch (columnIndex) {
            case 0:
                return crew.getOwned();
            case 1:
                return crew.getWanted();
            case 2:
                return crew.getNumber();
            case 3:
                return Card.getSetAbbreviation(crew.getExpansion());
            case 4:
                return crew.getName();
            case 5:
                return crew.getPoints();
            case 6:
                return crew.getFaction();
            case 7:
                return crew.getRules();
            case 8:
                return crew.getLink();
            case 9:
                return crew.getRarity();
            default:
                return null;
            }
        }

        /**
         * {@inheritDoc}
         * 
         * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
         */
        @Override
        public boolean isCellEditable(final int row, final int col) {
            // if (col < 2) return true;
            return false;
        }

        /**
         * {@inheritDoc}
         * 
         * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
         */
        @Override
        public void setValueAt(final Object value, final int row, final int col) {
        }
    }

    /**
     * The Class MouseHandler.
     */
    private class MouseHandler extends MouseAdapter {

        /**
         * {@inheritDoc}
         * 
         * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseClicked(final MouseEvent e) {
            final JTableHeader h = (JTableHeader) e.getSource();
            final TableColumnModel columnModel = h.getColumnModel();
            final int viewColumn = columnModel.getColumnIndexAtX(e.getX());
            final int column = columnModel.getColumn(viewColumn).getModelIndex();
            if (column != -1) {
                sorter.setSort(column + 1);
                sorter.sort(cards);
                model.fireTableDataChanged();
            }
        }
    }

    /** The all cards. */
    public List<Crew> cards, allCards;

    /** The cols. */
    private static String[] cols = new String[] { "O", "W", "Card", "Set", "Name", "Points", "Faction", "Rules", "Link", "Rarity" };

    /** The icons. */
    private static Icon[] icons = new Icon[] { Icons.SYMBOL_HAVE_16, Icons.SYMBOL_WANT_16, null, null, null, null, null, null, null, Icons.SYMBOL_RARITY_16 };

    /** The menu. */
    private final JPopupMenu menu;

    /** The widths. */
    private static int[] widths = new int[] { 16, 16, 30, 30, 150, 20, 50, 400, 150, 10 };

    /** The table. */
    private final JTable table;

    /** The model. */
    private final CrewTableModel model;

    /** The fleets panel. */
    private FleetsPanel fleetsPanel;

    /** The sorter. */
    private final CrewSorter sorter = new CrewSorter();

    /** The filter panel. */
    private final CardFilterPanel filterPanel = new CardFilterPanel(this);

    /** The status bar. */
    private final JLabel statusBar = new JLabel("");

    /** The add to fleet. */
    private final JMenuItem addToFleet;

    /** The add to ship. */
    private final JMenu addToShip;

    /**
     * Instantiates a new crew panel.
     */
    public CrewPanel() {
        setLayout(new BorderLayout());
        allCards = ManagerMain.instance.db.getCrew();
        cards = new ArrayList<Crew>();
        cards.addAll(allCards);

        statusBar.setText(cards.size() + " crew.");

        this.add(filterPanel, BorderLayout.NORTH);

        model = new CrewTableModel();
        table = new JTable(model);
        TableColumn column = null;
        for (int i = 0; i < cols.length; i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(widths[i]);

            column.setHeaderRenderer(new IconHeaderRenderer());
            if (icons[i] != null) {
                column.setHeaderValue(icons[i]);
            } else {
                column.setHeaderValue(cols[i]);
            }
            if (i == 6) {
                column.setCellRenderer(new FactionCellRenderer());
            } else if (i == 9) {
                column.setCellRenderer(new RarityRenderer());
            } else if (i == 7) {
                column.setCellRenderer(new ToolTipCellRenderer());
            }

        }

        menu = new JPopupMenu();
        addToFleet = new JMenuItem("Add to fleet");
        if (fleetsPanel == null) {
            addToFleet.setEnabled(false);
        }
        addToFleet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final Crew c = cards.get(model.getLastClickedRow());
                final Fleet f = fleetsPanel.getCurrentFleet();
                if (f != null) {
                    f.addCrew(c);
                    fleetsPanel.fleetChanged();
                    statusBar.setText(c.getName() + " added to fleet.");
                }
            }
        });
        menu.add(addToFleet);
        addToShip = new JMenu("Add to ship");
        menu.add(addToShip);
        addToShip.add(new JMenuItem("No ships"));
        addToShip.setEnabled(false);

        table.addMouseListener(new IncrementMouseListener(model, this));

        // Delete key
        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent e) {
                final JTable aTable = (JTable) e.getSource();
                final int row = aTable.getSelectedRow();
                final Card card = getCardAtRow(row);
                updatePicture(card);

                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    int first = cards.size();
                    int last = 0;
                    final int[] selected = table.getSelectedRows();
                    final List<Card> toDelete = new ArrayList<Card>();
                    for (int i = 0; i < selected.length; i++) {
                        toDelete.add(cards.get(selected[i]));
                        if (i < first) {
                            first = i;
                        }
                        if (i > last) {
                            last = i;
                        }
                    }
                    cards.removeAll(toDelete);
                    allCards.removeAll(toDelete);
                    ManagerMain.instance.allPanel.cards.removeAll(toDelete);
                    ManagerMain.instance.allPanel.allCards.removeAll(toDelete);
                    ManagerMain.instance.db.getCrew().removeAll(toDelete);

                    new Thread() {
                        @Override
                        public void run() {
                            ManagerMain.instance.db.save();
                        }
                    }.start();
                    model.fireTableRowsDeleted(first, last);
                }
            }
        });

        table.getTableHeader().addMouseListener(new MouseHandler());

        final JScrollPane pane = new JScrollPane();
        pane.setViewportView(table);
        this.add(pane, BorderLayout.CENTER);
        this.add(statusBar, BorderLayout.SOUTH);
    }

    /**
     * {@inheritDoc}
     * 
     * @see net.coljac.pirates.gui.CardPanel#getCardAtRow(int)
     */
    @Override
    public Card getCardAtRow(final int row) {
        return cards.get(row);
    }

    /**
     * Gets the fleets panel.
     * 
     * @return the fleets panel
     */
    public FleetsPanel getFleetsPanel() {
        return fleetsPanel;
    }

    /**
     * {@inheritDoc}
     * 
     * @see net.coljac.pirates.gui.CardPanel#reFilter()
     */
    @Override
    public void reFilter() {
        cards = (List<Crew>) filterPanel.getFilter().filter(allCards);
        sorter.sort(cards);
        model.fireTableDataChanged();
        statusBar.setText(cards.size() + " crew.");
    }

    // ----------------- TABLE MODEL ------------------
    // Wraps the list

    /**
     * Sets the fleets panel.
     * 
     * @param fleetsPanel
     *            the new fleets panel
     */
    public void setFleetsPanel(final FleetsPanel fleetsPanel) {
        this.fleetsPanel = fleetsPanel;
    }

    /**
     * {@inheritDoc}
     * 
     * @see net.coljac.pirates.gui.CardPanel#showMenu(java.awt.Component, int, int)
     */
    @Override
    public void showMenu(final Component comp, final int x, final int y) {
        addToShip.removeAll();
        addToFleet.setEnabled(fleetsPanel.getCurrentFleet() != null);
        addToShip.setEnabled(fleetsPanel.getCurrentFleet() != null);
        if (fleetsPanel.getCurrentFleet() != null) {
            final List<Ship> ships = fleetsPanel.getCurrentFleet().getShips();

            for (final Ship ship : ships) {
                final Ship craft = ship;
                final Crew man = cards.get(model.getLastClickedRow());
                final JMenuItem item = new JMenuItem(ship.getName());
                item.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        fleetsPanel.getCurrentFleet().addCrewToShip(craft, man);
                        fleetsPanel.fleetChanged();
                        statusBar.setText(man.getName() + " added to fleet.");
                    }
                });
                addToShip.add(item);
            }
        }
        menu.show(comp, x, y);
    }

}
