package net.coljac.pirates.gui.helper;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import net.coljac.pirates.Card;
import net.coljac.pirates.gui.CardPanel;
import net.coljac.pirates.gui.ManagerMain;
import net.coljac.pirates.gui.OtherPanel;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 15, 2006
 * 
 * @see IncrementMouseEvent
 */
public class IncrementMouseListener extends MouseAdapter {

    /** The model. */
    private final HandyTableModel model;

    /** The callback. */
    private final CardPanel callback;

    /**
     * Instantiates a new increment mouse listener.
     * 
     * @param model
     *            the model
     * @param callback
     *            the callback
     */
    public IncrementMouseListener(final HandyTableModel model, final CardPanel callback) {
        this.model = model;
        this.callback = callback;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseClicked(final MouseEvent e) {
        final JTable aTable = (JTable) e.getSource();
        final int row = aTable.rowAtPoint(e.getPoint());
        final int col = aTable.columnAtPoint(e.getPoint());
        if (col > 1) {
            return;
        }
        final boolean right = e.getButton() == MouseEvent.BUTTON3;
        final Card card = callback.getCardAtRow(row);
        if (col == 0) {
            card.setOwned(card.getOwned() + (right ? -1 : 1));
            if (card.getOwned() < 0) {
                card.setOwned(0);
            }
        } else if (col == 1) {
            card.setWanted(card.getWanted() + (right ? -1 : 1));
            if (card.getWanted() < 0) {
                card.setWanted(0);
            }
        }
        new Thread() {
            @Override
            public void run() {
                ManagerMain.instance.db.save();
            }
        }.start();
        model.fireTableRowsUpdated(row, row);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(final MouseEvent e) {
        final JTable aTable = (JTable) e.getSource();
        final int col = aTable.columnAtPoint(e.getPoint());
        final int menuRow = aTable.rowAtPoint(e.getPoint());
        model.setLastClickedRow(menuRow);
        int target = 4;
        if (callback instanceof OtherPanel) {
            target = 5;
        }
        if ((col == target) && e.isPopupTrigger()) {
            callback.showMenu(e.getComponent(), e.getX(), e.getY());
            // menu.show(e.getComponent(), e.getX(), e.getY());
        }

        final int row = aTable.rowAtPoint(e.getPoint());
        final Card card = callback.getCardAtRow(row);
        callback.updatePicture(card);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(final MouseEvent e) {
        final JTable aTable = (JTable) e.getSource();
        final int col = aTable.columnAtPoint(e.getPoint());
        final int menuRow = aTable.rowAtPoint(e.getPoint());
        model.setLastClickedRow(menuRow);
        int target = 4;
        if (callback instanceof OtherPanel) {
            target = 5;
        }
        if ((col == target) && e.isPopupTrigger()) {
            callback.showMenu(e.getComponent(), e.getX(), e.getY());
            // menu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

}
