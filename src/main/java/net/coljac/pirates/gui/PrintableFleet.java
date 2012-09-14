package net.coljac.pirates.gui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.coljac.pirates.Card;
import net.coljac.pirates.Crew;
import net.coljac.pirates.Fleet;
import net.coljac.pirates.Ship;
import net.coljac.pirates.ShipsCrew;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 17, 2006
 */
public class PrintableFleet extends JPanel {

    /** The fleet. */
    private final Fleet fleet;

    /** The label. */
    private final JLabel label;

    /**
     * Instantiates a new printable fleet.
     * 
     * @param fleet
     *            the fleet
     */
    public PrintableFleet(final Fleet fleet) {
        this.fleet = fleet;
        setBackground(Color.white);
        setForeground(Color.black);
        final StringBuffer html = new StringBuffer();
        html.append("<html><table width=\"350px\" border=\"1\" cellspacing=\"0\" cellpadding=\"4\">");
        html.append((new StringBuilder("<tr bgcolor=\"#EEEEEE\"><td align=\"center\" colspan=\"4\"> Fleet: ")).append(fleet.getName()).append("</td></tr>").toString());
        html.append("<tr bgcolor=\"#EEEEEE\"><td align=\"center\">Pts</td><td align=\"center\">Name</td><td align=\"center\">Set</td><td align=\"center\">Card</td></tr>");
        for (final Object element : fleet.getShips()) {
            final Ship s = (Ship) element;
            html.append((new StringBuilder("<tr><td>")).append(s.getPoints()).append("</td><td> ").append(s.getName()).append("</td><td> ").append(Card.getSetAbbreviation(s.getExpansion())).append("</td><td> ").append(s.getNumber()).append("</td></tr>").toString());
            final ShipsCrew cl = fleet.getShipCrew().get(s);
            for (final Object element2 : cl.getCrewList()) {
                final Crew crew = (Crew) element2;
                if (crew.isGeneric()) {
                    html.append((new StringBuilder("<tr><td>")).append(crew.getPoints()).append("</td><td>&nbsp;&nbsp;&nbsp<i>").append(crew.getName()).append("</i></td><td align=\"center\" colspan=\"2\">Generic Crew</td></tr>").toString());
                } else {
                    html.append((new StringBuilder("<tr><td>")).append(crew.getPoints()).append("</td><td>&nbsp;&nbsp;&nbsp<i>").append(crew.getName()).append("</i></td><td> ").append(Card.getSetAbbreviation(crew.getExpansion())).append("</td><td> ").append(crew.getNumber()).append("</td></tr>")
                            .toString());
                }
            }

        }

        for (final Object element : fleet.getCrew()) {
            final Crew crew = (Crew) element;
            if (crew.isGeneric()) {
                html.append((new StringBuilder("<tr><td>")).append(crew.getPoints()).append("</td><td><i>").append(crew.getName()).append("</i></td><td align=\"center\" colspan=\"2\">Generic Crew</td></tr>").toString());
            } else {
                html.append((new StringBuilder("<tr><td>")).append(crew.getPoints()).append("</td><td><i>").append(crew.getName()).append("</i></td><td> ").append(Card.getSetAbbreviation(crew.getExpansion())).append("</td><td> ").append(crew.getNumber()).append("</td></tr>").toString());
            }
        }

        for (final Object element : fleet.getExtraCards()) {
            final Card crd = (Card) element;
            if (crd.getName().equals("Captain") || crd.getName().equals("Oarsman") || crd.getName().equals("Cannoneer") || crd.getName().equals("Musketeer") || crd.getName().equals("Explorer") || crd.getName().equals("Helmsman") || crd.getName().equals("Shipwright")
                    || crd.getName().equals("Chainshot Specialist") || crd.getName().equals("Firepot Specialist") || crd.getName().equals("Smokepot Specialist") || crd.getName().equals("Stinkpot Specialist")) {
                html.append((new StringBuilder("<tr><td>")).append(crd.getPoints()).append("</td><td><i>").append(crd.getName()).append("</i></td><td align=\"center\" colspan=\"2\">Generic Crew</td></tr>").toString());
            } else {
                html.append((new StringBuilder("<tr><td>")).append(crd.getPoints()).append("</td><td><i>").append(crd.getName()).append("</i></td><td> ").append(Card.getSetAbbreviation(crd.getExpansion())).append("</td><td> ").append(crd.getNumber()).append("</td></tr>").toString());
            }
        }

        html.append((new StringBuilder("<tr bgcolor=\"#EEEEEE\"><td>")).append(fleet.getPoints()).append("</td><td colspan=\"3\">&nbsp; (Total cost)</td></tr>").toString());
        html.append("</table></html>");
        label = new JLabel(html.toString());
        add(label);
    }

}
