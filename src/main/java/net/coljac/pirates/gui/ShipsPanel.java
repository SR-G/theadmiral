package net.coljac.pirates.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.coljac.pirates.Card;
import net.coljac.pirates.Fleet;
import net.coljac.pirates.Fort;
import net.coljac.pirates.Ship;
import net.coljac.pirates.gui.helper.FactionCellRenderer;
import net.coljac.pirates.gui.helper.HandyTableModel;
import net.coljac.pirates.gui.helper.IconHeaderRenderer;
import net.coljac.pirates.gui.helper.IncrementMouseListener;
import net.coljac.pirates.gui.helper.RarityRenderer;
import net.coljac.pirates.gui.helper.ToolTipCellRenderer;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 11, 2006
 */
public class ShipsPanel extends CardPanel {

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

    /**
     * The Class ShipTableModel.
     */
    class ShipTableModel extends HandyTableModel {

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
            final Ship ship = cards.get(rowIndex);

            switch (columnIndex) {
            case 0:
                return ship.getOwned();
            case 1:
                return ship.getWanted();
            case 2:
                return ship.getNumber();
            case 3:
                return Card.getSetAbbreviation(ship.getExpansion());
            case 4:
                return ship.getName();
            case 5:
                return ship.getPoints();
            case 6:
                return ship.getFaction();
            case 7:
                return ship.getMasts();
            case 8:
                return ship.getCargo();
            case 9:
                if (ship instanceof Fort) {
                    return "Cost: " + ((Fort) ship).getGoldCost();
                }
                return ship.getMove();
            case 10:
                return ship.getCannons();
            case 11:
                if (ship instanceof Fort) {
                    return "(FORT) " + ship.getRules();
                }
                return ship.getRules();
            case 12:
                return ship.getRarity();
            case 13:
                return ship.getFlavor();
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
            cards.get(row).setName(value.toString());
            fireTableCellUpdated(row, col);
        }
    }

    /** The cards. */
    public List<Ship> cards;

    /** The all cards. */
    public List<Ship> allCards;

    /** The cols. */
    private static String[] cols = new String[] { "O", "W", "Card", "Set", "Name", "Points", "Faction", "Masts", "Cargo", "Move", "Guns", "Rules", "Rarity" };

    /** The menu. */
    private final JPopupMenu menu;

    /** The icons. */
    private static Icon[] icons = new Icon[] { Icons.SYMBOL_HAVE_16, Icons.SYMBOL_WANT_16, null, null, null, null, null, Icons.SYMBOL_MASTS_16, Icons.SYMBOL_CARGO_16, Icons.SYMBOL_MOVE_16, Icons.SYMBOL_GUNS_16, null, Icons.SYMBOL_RARITY_16 };

    /** The widths. */
    private static int[] widths = new int[] { 16, 16, 30, 30, 100, 20, 50, 16, 16, 40, 50, 550, 10 // 850
    };

    /** The table. */
    private final JTable table;

    /** The model. */
    private final ShipTableModel model;

    /** The sorter. */
    private final ShipSorter sorter = new ShipSorter();

    /** The filter panel. */
    private final CardFilterPanel filterPanel;

    /** The status bar. */
    private final JLabel statusBar = new JLabel("");

    /** The fleets panel. */
    private FleetsPanel fleetsPanel;

    /** The add to fleet. */
    JMenuItem addToFleet;

    /**
     * Instantiates a new ships panel.
     */
    public ShipsPanel() {
        cards = new ArrayList<Ship>();
        filterPanel = new CardFilterPanel(this);
        setLayout(new BorderLayout());

        // JScrollPane scroller = new JScrollPane(filterPanel);
        this.add(filterPanel, BorderLayout.NORTH);

        allCards = ManagerMain.instance.db.getShips();
        cards = new ArrayList<Ship>();
        cards.addAll(allCards);
        statusBar.setText(cards.size() + " cards.");

        model = new ShipTableModel();
        // TableSorter sorter = new TableSorter(model);
        // table = new JTable(sorter);
        table = new JTable(model);
        // sorter.setTableHeader(table.getTableHeader());

        table.addMouseListener(new IncrementMouseListener(model, this));
        table.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(final KeyEvent e) {
            }

            @Override
            public void keyReleased(final KeyEvent e) {
                final JTable aTable = (JTable) e.getSource();
                final int row = aTable.getSelectedRow();
                final Card card = getCardAtRow(row);
                updatePicture(card);
            }

            @Override
            public void keyTyped(final KeyEvent e) {

            }

        });

        menu = new JPopupMenu();
        addToFleet = new JMenuItem("Add to fleet");
        if (fleetsPanel == null) {
            addToFleet.setEnabled(false);
        }
        addToFleet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final Ship s = cards.get(model.getLastClickedRow());
                final Fleet f = fleetsPanel.getCurrentFleet();
                if (f != null) {
                    f.addShip(s);
                    fleetsPanel.fleetChanged();
                    statusBar.setText(s.getName() + " added to fleet.");
                }
            }
        });
        menu.add(addToFleet);

        TableColumn column = null;
        for (int i = 0; i < cols.length; i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(widths[i]);

            column.setHeaderRenderer(new IconHeaderRenderer());
            if (icons[i] != null) {
                column.setHeaderValue(icons[i]);
                // new TextAndIcon("", new ImageIcon("./src/move_32.png")));
            } else {
                column.setHeaderValue(cols[i]);
            }
            if (i == 6) {
                column.setCellRenderer(new FactionCellRenderer());
            } else if (i == 12) {
                column.setCellRenderer(new RarityRenderer());
            } else if (i == 11) {
                column.setCellRenderer(new ToolTipCellRenderer());
            }

        }

        table.getTableHeader().addMouseListener(new MouseHandler());

        // TODO - abstract this out along with a generic cardpanel
        // Delete key
        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent e) {
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
                    ManagerMain.instance.db.getShips().removeAll(toDelete);

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

        final JScrollPane pane = new JScrollPane();
        // JTable table = new JTable(new ShipTableModel());
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
        cards = (List<Ship>) filterPanel.getFilter().filter(allCards);
        sorter.sort(cards);
        model.fireTableDataChanged();
        statusBar.setText(cards.size() + " cards.");
    }

    /**
     * Sets the fleets panel.
     * 
     * @param fleetsPanel
     *            the new fleets panel
     */
    public void setFleetsPanel(final FleetsPanel fleetsPanel) {
        this.fleetsPanel = fleetsPanel;
        addToFleet.setEnabled((fleetsPanel != null) && (fleetsPanel.getCurrentFleet() != null));
    }

    /**
     * {@inheritDoc}
     * 
     * @see net.coljac.pirates.gui.CardPanel#showMenu(java.awt.Component, int, int)
     */
    @Override
    public void showMenu(final Component comp, final int x, final int y) {
        addToFleet.setEnabled(fleetsPanel.getCurrentFleet() != null);
        menu.show(comp, x, y);
    }

}
