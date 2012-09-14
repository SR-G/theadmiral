package net.coljac.pirates.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.coljac.pirates.CardDatabase;
import net.coljac.pirates.Crew;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Apr 1, 2006
 */
public class GenericPanel extends JPanel implements ActionListener {

    /** The cannoneer. */
    private final JButton captain, helmsman, explorer, shipwright, oarsman, musketeer, cannoneer;

    /** The smokepot. */
    private final JButton smokepot;

    /** The firepot. */
    private final JButton firepot;

    /** The stinkpot. */
    private final JButton stinkpot;

    /** The slvreplr. */
    private final JButton slvreplr;

    /** The chainshot. */
    private final JButton chainshot;

    /** The navigator. */
    private final JButton navigator;

    /** The fleet panel. */
    private final FleetsPanel fleetPanel;

    /** The crew. */
    Map<String, Crew> crew = new HashMap<String, Crew>();

    /**
     * Instantiates a new generic panel.
     * 
     * @param fleetPanel
     *            the fleet panel
     */
    public GenericPanel(final FleetsPanel fleetPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.fleetPanel = fleetPanel;
        captain = new JButton("Captain");
        explorer = new JButton("Explorer");
        helmsman = new JButton("Helmsman");
        shipwright = new JButton("Shipwright");
        musketeer = new JButton("Musketeer");
        cannoneer = new JButton("Cannoneer");
        oarsman = new JButton("Oarsman");
        smokepot = new JButton("Smokepot");
        firepot = new JButton("Firepot");
        stinkpot = new JButton("Stinkpot");
        chainshot = new JButton("Chainshot");
        navigator = new JButton("Navigator");
        slvreplr = new JButton("Silver Exp");

        final JLabel label = new JLabel("Add Generic Crew");

        explorer.addActionListener(this);
        captain.addActionListener(this);
        musketeer.addActionListener(this);
        cannoneer.addActionListener(this);
        oarsman.addActionListener(this);
        helmsman.addActionListener(this);
        smokepot.addActionListener(this);
        firepot.addActionListener(this);
        stinkpot.addActionListener(this);
        chainshot.addActionListener(this);
        navigator.addActionListener(this);
        slvreplr.addActionListener(this);

        shipwright.setAlignmentX(Component.CENTER_ALIGNMENT);
        explorer.setAlignmentX(Component.CENTER_ALIGNMENT);
        captain.setAlignmentX(Component.CENTER_ALIGNMENT);
        musketeer.setAlignmentX(Component.CENTER_ALIGNMENT);
        cannoneer.setAlignmentX(Component.CENTER_ALIGNMENT);
        oarsman.setAlignmentX(Component.CENTER_ALIGNMENT);
        helmsman.setAlignmentX(Component.CENTER_ALIGNMENT);

        smokepot.setAlignmentX(Component.CENTER_ALIGNMENT);
        firepot.setAlignmentX(Component.CENTER_ALIGNMENT);
        stinkpot.setAlignmentX(Component.CENTER_ALIGNMENT);
        chainshot.setAlignmentX(Component.CENTER_ALIGNMENT);
        navigator.setAlignmentX(Component.CENTER_ALIGNMENT);
        slvreplr.setAlignmentX(Component.CENTER_ALIGNMENT);

        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        // label.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        label.setBorder(BorderFactory.createCompoundBorder(

        BorderFactory.createLineBorder(Color.black), BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        setBorder(BorderFactory.createLineBorder(Color.black));

        this.add(label);

        add(cannoneer);
        add(captain);
        add(chainshot);
        add(explorer);
        add(firepot);
        add(helmsman);
        add(musketeer);
        add(navigator);
        add(oarsman);
        add(shipwright);
        add(slvreplr);
        add(smokepot);
        add(stinkpot);

        initCrew();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        if (fleetPanel.getCurrentFleet() == null) {
            return;
        }
        if (e.getSource() == captain) {
            fleetPanel.getCurrentFleet().addCrew(crew.get("Captain"));
        } else if (e.getSource() == oarsman) {
            fleetPanel.getCurrentFleet().addCrew(crew.get("Oarsman"));
        } else if (e.getSource() == shipwright) {
            fleetPanel.getCurrentFleet().addCrew(crew.get("Shipwright"));
        } else if (e.getSource() == helmsman) {
            fleetPanel.getCurrentFleet().addCrew(crew.get("Helmsman"));
        } else if (e.getSource() == musketeer) {
            fleetPanel.getCurrentFleet().addCrew(crew.get("Musketeer"));
        } else if (e.getSource() == firepot) {
            fleetPanel.getCurrentFleet().addCrew(crew.get("Firepot Specialist"));
        } else if (e.getSource() == smokepot) {
            fleetPanel.getCurrentFleet().addCrew(crew.get("Smokepot Specialist"));
        } else if (e.getSource() == stinkpot) {
            fleetPanel.getCurrentFleet().addCrew(crew.get("Stinkpot Specialist"));
        } else if (e.getSource() == chainshot) {
            fleetPanel.getCurrentFleet().addCrew(crew.get("Chainshot Specialist"));
        } else if (e.getSource() == cannoneer) {
            fleetPanel.getCurrentFleet().addCrew(crew.get("Cannoneer"));
        } else if (e.getSource() == explorer) {
            fleetPanel.getCurrentFleet().addCrew(crew.get("Explorer"));
        } else if (e.getSource() == navigator) {
            fleetPanel.getCurrentFleet().addCrew(crew.get("Navigator"));
        } else if (e.getSource() == slvreplr) {
            fleetPanel.getCurrentFleet().addCrew(crew.get("Silver Explorer"));
        }
        fleetPanel.fleetChanged();
        ManagerMain.instance.tabPanel.repaint();
    }

    /**
     * Inits the crew.
     */
    private void initCrew() {
        final CardDatabase db = ManagerMain.instance.db;
        for (final Crew c : db.getCrew()) {
            if (c.isGeneric()) {
                crew.put(c.getName(), c);
            }
        }
    }

}
