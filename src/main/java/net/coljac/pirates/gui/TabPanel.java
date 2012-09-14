package net.coljac.pirates.gui;

import javax.swing.JTabbedPane;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 11, 2006
 */
public class TabPanel extends JTabbedPane {

    /**
     * Instantiates a new tab panel.
     */
    public TabPanel() {
    }

    /**
     * Inits the.
     */
    public void init() {
        this.removeAll();
        this.addTab("Ships", null, ManagerMain.instance.shipsPanel);
        this.addTab("Crew", null, ManagerMain.instance.crewPanel);
        this.addTab("Other", null, ManagerMain.instance.otherPanel);
        this.addTab("All", null, ManagerMain.instance.allPanel);
        this.addTab("Fleets", null, ManagerMain.instance.fleetsPanel);
    }

}
