package net.coljac.pirates.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.TransferHandler;

import net.coljac.pirates.Card;
import net.coljac.pirates.Crew;
import net.coljac.pirates.Event;
import net.coljac.pirates.Fleet;
import net.coljac.pirates.Ship;
import net.coljac.pirates.ShipsCrew;
import net.coljac.pirates.gui.helper.Exporter;
import net.coljac.pirates.gui.helper.PrintUtilities;
import net.coljac.pirates.gui.helper.StringUtil;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 14, 2006
 */
public class FleetsPanel extends JPanel implements ActionListener {

    /** The delete button. */
    private JButton newButton, openButton, deleteButton;

    /** The print button. */
    private JButton printButton;

    /** The export button. */
    private JButton exportButton;

    /** The fleet combo. */
    private JComboBox fleetCombo;

    /** The new name field. */
    private JTextField newNameField;

    /** The fleets. */
    private List<Fleet> fleets = new ArrayList<Fleet>();

    /** The fleet name map. */
    private Map<String, Fleet> fleetNameMap = new HashMap<String, Fleet>();

    /** The fleet names. */
    private String[] fleetNames;

    /** The current fleet. */
    private Fleet currentFleet = null;

    /** The panel. */
    private FleetPanel panel;

    /**
     * Instantiates a new fleets panel.
     */
    public FleetsPanel() {

        fleets = ManagerMain.instance.db.getFleets();
        fleetNames = new String[fleets.size()];
        int i = 0;
        for (Fleet f : fleets) {
            fleetNames[i++] = f.getName();
            fleetNameMap.put(f.getName(), f);
        }

        printButton = new JButton("Print fleet");
        printButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentFleet != null) {
                    final PrintableFleet pf = new PrintableFleet(currentFleet);
                    pf.setDoubleBuffered(false);
                    JDialog dialog = new JDialog(ManagerMain.instance, "Print fleet", false);
                    dialog.add(pf);
                    dialog.pack();
                    dialog.setLocation((int) ManagerMain.instance.getLocation().getX() + 100, (int) ManagerMain.instance.getLocation().getY() + 100);
                    dialog.setVisible(true);
                    PrintUtilities.printComponent(pf);
                    dialog.setVisible(false);
                }
            }
        });
        exportButton = new JButton("Export fleet");
        exportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentFleet != null) {
                    String file = ManagerMain.instance.getFile("txt", false);
                    if (file != null) {
                        try {
                            Exporter.exportFleet(currentFleet, file);
                        } catch (IOException e1) {
                            ManagerMain.instance.showError("Can't save fleet: " + e1.getMessage());
                        }
                    }
                }
            }
        });
        fleetCombo = new JComboBox(fleetNames);
        newButton = new JButton("New");
        openButton = new JButton("Open");
        deleteButton = new JButton("Delete ");
        newNameField = new JTextField("", 20);

        newButton.addActionListener(this);
        openButton.addActionListener(this);
        deleteButton.addActionListener(this);

        this.setLayout(new BorderLayout());
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Select fleet:"));
        controlPanel.add(fleetCombo);
        controlPanel.add(openButton);
        controlPanel.add(deleteButton);
        controlPanel.add(newButton);
        controlPanel.add(newNameField);
        this.add(controlPanel, BorderLayout.NORTH);
        panel = new FleetPanel(null);

        panel.add(new GenericPanel(this), BorderLayout.WEST);
        this.add(panel, BorderLayout.CENTER);

    }

    /**
     * Adds the fleet.
     * 
     * @param fleet
     *            the fleet
     */
    private void addFleet(Fleet fleet) {
        fleets.add(fleet);
        fleetCombo.addItem(fleet.getName());
        fleetNameMap.put(fleet.getName(), fleet);
    }

    /**
     * Gets the current fleet.
     * 
     * @return the current fleet
     */
    public Fleet getCurrentFleet() {
        return currentFleet;
    }

    /**
     * Fleet changed.
     */
    public void fleetChanged() {
        panel.changed();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openButton) {
            currentFleet = fleetNameMap.get(fleetCombo.getSelectedItem());
            panel.setFleet(currentFleet);
            ManagerMain.instance.shipsPanel.setFleetsPanel(this);
            ManagerMain.instance.crewPanel.setFleetsPanel(this);
            ManagerMain.instance.tabPanel.repaint();
        } else if (e.getSource() == newButton) {
            if (newNameField.getText().length() > 0) {
                Fleet fleet = new Fleet();
                fleet.setName(newNameField.getText());
                addFleet(fleet);
                newNameField.setText("");
                currentFleet = fleet;
                panel.setFleet(currentFleet);
                ManagerMain.instance.shipsPanel.setFleetsPanel(this);
                ManagerMain.instance.crewPanel.setFleetsPanel(this);
            } else {
                ManagerMain.instance.showAlert("Please enter a fleet name.");
            }
            ManagerMain.instance.tabPanel.repaint();
        } else if (e.getSource() == deleteButton) {
            if (ManagerMain.instance.showConfirm("Delete fleet?")) {
                deleteFleet(fleetCombo.getSelectedIndex());
            }
            panel.changed();
            ManagerMain.instance.tabPanel.repaint();
        }
    }

    /**
     * Delete fleet.
     * 
     * @param index
     *            the index
     */
    private void deleteFleet(int index) {
        String fleet = fleetCombo.getItemAt(index).toString();
        fleetCombo.removeItem(fleetCombo.getItemAt(index));
        if (fleetCombo.getItemCount() == 0) {
            fleetCombo.removeAllItems();
        }
        Fleet flt = fleetNameMap.get(fleet);
        if (currentFleet == flt) {
            currentFleet = null;
            panel.setFleet(null);
        }
        ManagerMain.instance.db.getFleets().remove(flt);
        ManagerMain.instance.db.save();
        panel.changed();
    }

    /**
     * The Class FleetPanel.
     */
    public class FleetPanel extends JPanel {

        /** The fleet. */
        private Fleet fleet;

        /** The main panel. */
        public JPanel mainPanel;

        /** The title label. */
        private JLabel titleLabel;

        /**
         * Instantiates a new fleet panel.
         * 
         * @param fleet
         *            the fleet
         */
        public FleetPanel(Fleet fleet) {
            this.fleet = fleet;
            this.setLayout(new BorderLayout());
            titleLabel = new JLabel("Fleet: " + (fleet == null ? "(None)" : fleet.getName()));
            titleLabel.setHorizontalAlignment(JLabel.CENTER);
            titleLabel.setOpaque(true);
            titleLabel.setBackground(Color.BLACK);
            titleLabel.setForeground(Color.YELLOW);
            this.add(titleLabel, BorderLayout.NORTH);

            mainPanel = new JPanel();
            JPanel temp = new JPanel();
            mainPanel.setLayout(new GridBagLayout());
            mainPanel.setBackground(Color.BLACK);
            // mainPanel.setMinimumSize(new Dimension(450, 0));
            // mainPanel.setPreferredSize(new Dimension(450, 200));

            temp.setLayout(new FlowLayout());

            temp.add(mainPanel);
            JScrollPane scroller = new JScrollPane(temp);

            // mainPanel.setBorder(BorderFactory.createLineBorder(Color.yellow, 2));
            JPanel temp2 = new JPanel();
            temp2.add(printButton);
            temp2.add(exportButton);
            this.add(temp2, BorderLayout.SOUTH);

            this.add(scroller, BorderLayout.CENTER);
            changed();
        }

        /**
         * Changed.
         */
        public void changed() {
            mainPanel.removeAll();
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.CENTER;
            c.gridx = 0;
            c.gridy = 0;
            c.gridheight = 1;
            c.gridwidth = 1;
            c.ipadx = 10;
            c.ipady = 10;

            c.gridx = 0;
            c.gridy = 1;
            JLabel delete = new JLabel("     ");
            delete.setOpaque(true);
            delete.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, Color.YELLOW));
            delete.setHorizontalAlignment(JLabel.CENTER);
            delete.setBackground(Color.black);
            delete.setForeground(Color.yellow);
            delete.setPreferredSize(new Dimension(75, 25));
            mainPanel.add(delete, c);

            c.gridx = 1;
            JLabel points = new JLabel("Points");
            points.setOpaque(true);
            points.setPreferredSize(new Dimension(100, 25));
            points.setHorizontalAlignment(JLabel.CENTER);
            points.setBackground(Color.black);
            points.setForeground(Color.yellow);
            points.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, Color.YELLOW));
            mainPanel.add(points, c);

            c.gridx = 2;
            c.gridwidth = 5;
            JLabel ships = new JLabel("Name of Asset");
            ships.setOpaque(true);
            ships.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.YELLOW));
            ships.setPreferredSize(new Dimension(250, 25));
            ships.setHorizontalAlignment(JLabel.CENTER);
            ships.setBackground(Color.black);
            ships.setForeground(Color.yellow);

            mainPanel.add(ships, c);

            int row = 2;
            if (fleet == null) {
                repaint();
                return;
            }
            fleet.refresh();
            for (Ship s : fleet.getShips()) {
                DraggableCard dg = new DraggableCard(s, fleet.getShips());
                c.gridy = row;

                c.gridx = 0;
                c.gridwidth = 1;
                c.ipadx = 0;
                c.ipady = 0;
                mainPanel.add(new DeleteButton(s, null), c);
                c.ipadx = 10;
                c.ipady = 10;
                c.fill = c.HORIZONTAL;

                c.gridx = 1;
                c.gridwidth = 1;
                mainPanel.add(pointsLabel(s.getPoints()), c);

                c.gridx = 2;
                c.gridwidth = 5;
                mainPanel.add(new AssetLabel(s.getName(), s.getRules(), dg), c);
                row += 1;

                List<Crew> crewList = fleet.getShipCrew().get(s).getCrewList();
                for (Crew crew : crewList) {
                    DraggableCard dg2 = new DraggableCard(crew, crewList);
                    c.gridy = row;

                    c.gridx = 0;
                    c.gridwidth = 1;
                    c.ipadx = 0;
                    c.ipady = 0;
                    // c.fill = c.NONE;
                    mainPanel.add(new DeleteButton(crew, s), c);
                    c.ipadx = 10;
                    c.ipady = 10;
                    c.fill = c.HORIZONTAL;

                    c.gridx = 1;
                    c.gridwidth = 1;
                    mainPanel.add(pointsLabel(crew.getPoints()), c);

                    c.gridx = 2;
                    c.gridwidth = 1;
                    JLabel label = new JLabel("   ");
                    label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.yellow));
                    mainPanel.add(label, c);

                    c.gridx = 3;
                    c.gridwidth = 4;
                    mainPanel.add(new AssetLabel(crew.getName(), crew.getRules(), true, dg2), c);
                    row += 1;
                }

            }
            List<Card> otherCards = new ArrayList<Card>();
            otherCards.addAll(fleet.getCrew());
            otherCards.addAll(fleet.getExtraCards());
            for (Card card : otherCards) {
                DraggableCard dg = new DraggableCard(card, (card instanceof Event ? fleet.getExtraCards() : fleet.getCrew()));
                c.gridy = row;

                c.gridx = 0;
                c.gridwidth = 1;
                c.ipadx = 0;
                c.ipady = 0;
                // c.fill = c.NONE;
                mainPanel.add(new DeleteButton(card, null), c);
                c.ipadx = 10;
                c.ipady = 10;
                c.fill = c.HORIZONTAL;

                c.gridx = 1;
                c.gridwidth = 1;
                mainPanel.add(pointsLabel(card.getPoints()), c);

                c.gridx = 2;
                c.gridy = row;
                row += 1;
                c.gridwidth = 5;
                c.gridheight = 1;
                mainPanel.add(new AssetLabel(card.getName(), card.getRules(), dg), c);
            }

            c.gridx = 1;
            c.gridy = row;
            c.gridheight = 1;
            c.gridwidth = 1;

            JLabel pointsTotal = pointsLabel(fleet.getPoints());
            pointsTotal.setForeground(Color.red);
            mainPanel.add(pointsTotal, c);
            c.gridwidth = 5;
            c.gridx = 2;
            JLabel total = new JLabel("   Total cost");
            total.setForeground(Color.red);

            mainPanel.add(total, c);
            mainPanel.repaint();

        }

        /** The pt font. */
        Font ptFont = null;

        /**
         * Points label.
         * 
         * @param points
         *            the points
         * @return the j label
         */
        private JLabel pointsLabel(int points) {
            JLabel label = new JLabel(points + "");
            label.setForeground(Color.YELLOW);
            label.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.YELLOW));
            label.setHorizontalAlignment(JLabel.CENTER);
            // label.setFont(ptFont);
            return label;
        }

        /** The asset font. */
        Font assetFont = null;

        /**
         * Gets the fleet.
         * 
         * @return the fleet
         */
        public Fleet getFleet() {
            return fleet;
        }

        /**
         * Sets the fleet.
         * 
         * @param fleet
         *            the new fleet
         */
        public void setFleet(Fleet fleet) {
            this.fleet = fleet;
            titleLabel.setText("Fleet: " + (fleet == null ? "" : fleet.getName()));
            changed();
        }

    }

    /**
     * The Class AssetLabel.
     */
    public class AssetLabel extends JLabel {

        /** The card. */
        private DraggableCard card;

        /**
         * Instantiates a new asset label.
         * 
         * @param asset
         *            the asset
         * @param toolTipText
         *            the tool tip text
         * @param card
         *            the card
         */
        public AssetLabel(String asset, String toolTipText, DraggableCard card) {
            this(asset, toolTipText, false, card);
        }

        /**
         * Instantiates a new asset label.
         * 
         * @param asset
         *            the asset
         * @param toolTipText
         *            the tool tip text
         * @param inset
         *            the inset
         * @param card
         *            the card
         */
        private AssetLabel(String asset, String toolTipText, boolean inset, DraggableCard card) {
            String text = "    " + asset;
            setForeground(Color.YELLOW);

            if (card.card instanceof Ship) {
                Ship ship = (Ship) card.card;
                ShipsCrew screw = currentFleet.getShipCrew().get(ship);

                text += "     [C: " + screw.getCargoSpaceUsed() + "/" + ship.getCargo() + "] / [P: " + screw.getCrewPointsUsed() + "/" + ship.getPoints() + "]";
                if (screw.getCrewPointsUsed() > ship.getPoints() || screw.getCargoSpaceUsed() > ship.getCargo()) {
                    setForeground(Color.RED);
                } else if (!screw.isFactionOK()) {
                    setForeground(Color.BLUE);
                }
            }

            setText(text);
            this.card = card;
            if (inset) {
                setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.YELLOW));
            } else {
                setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.YELLOW));
            }
            setHorizontalAlignment(JLabel.LEFT);
            setToolTipText("<html>" + StringUtil.splitString(toolTipText, 60) + "</html>");

            this.setTransferHandler(new TransferHandler("card"));
            MouseListener listener = new DragMouseAdapter();
            this.addMouseListener(listener);

            // label.setFont(ptFont);
        }

        /**
         * Gets the card.
         * 
         * @return the card
         */
        public DraggableCard getCard() {
            return card;
        }

        // Accept a dropped card
        /**
         * Sets the card.
         * 
         * @param newCard
         *            the new card
         */
        public void setCard(DraggableCard newCard) {
            if (card.sourceList == newCard.sourceList) {
                int newIndex = card.sourceList.indexOf((Ship) newCard.card);
                int oldIndex = card.sourceList.indexOf((Ship) card.card);
                if (newIndex > oldIndex) {
                    newCard.sourceList.remove((Ship) newCard.card);
                    card.sourceList.add(oldIndex, newCard.card);
                }
            } else if (newCard.card instanceof Crew && card.card instanceof Ship) {
                // boolean generalCrew = currentFleet.getCrew().contains((Crew) newCard.card);
                // remove from old place
                newCard.sourceList.remove(newCard.card);
                // put in new place
                currentFleet.getShipCrew().get((Ship) card.card).add((Crew) newCard.card);
            }
            panel.changed();
            ManagerMain.instance.tabPanel.repaint();
        }

        /**
         * The Class DragMouseAdapter.
         */
        private class DragMouseAdapter extends MouseAdapter {

            /**
             * {@inheritDoc}
             * 
             * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
             */
            public void mousePressed(MouseEvent e) {
                JComponent c = (JComponent) e.getSource();
                TransferHandler handler = c.getTransferHandler();
                handler.exportAsDrag(c, e, TransferHandler.COPY);
            }
        }
    }

    /**
     * The Class DraggableCard.
     */
    public class DraggableCard {

        /** The card. */
        public Card card;

        /** The source list. */
        public List sourceList;

        /**
         * Instantiates a new draggable card.
         * 
         * @param card
         *            the card
         * @param sourceList
         *            the source list
         */
        public DraggableCard(Card card, List sourceList) {
            this.card = card;
            this.sourceList = sourceList;
        }

    }

    /**
     * The Class DeleteButton.
     */
    public class DeleteButton extends JButton implements ActionListener {

        /** The card. */
        Card card = null;

        /** The parent. */
        Ship parent = null;

        /**
         * Instantiates a new delete button.
         * 
         * @param card
         *            the card
         * @param parent
         *            the parent
         */
        public DeleteButton(Card card, Ship parent) {
            this.card = card;
            this.parent = parent;
            this.setText("");
            this.setIcon(Icons.SYMBOL_RARITY_16);
            this.addActionListener(this);
            this.setBackground(Color.black);
            this.setForeground(new Color(0, 30, 30));
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            if (card == null) {
                return;
            }
            if (card instanceof Ship) {
                currentFleet.removeShip((Ship) card);
            } else if (parent != null) {
                currentFleet.removeCrewFromShip((Crew) card, parent);
            } else if (card instanceof Crew) {
                currentFleet.removeCrew((Crew) card);
            } else {
                currentFleet.removeExtraCard(card);
            }
            ManagerMain.instance.db.save();
            panel.changed();
            // Todo: remove this
            ManagerMain.instance.tabPanel.repaint();
        }

    }

}
