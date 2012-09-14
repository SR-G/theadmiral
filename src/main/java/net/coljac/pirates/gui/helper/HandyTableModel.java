package net.coljac.pirates.gui.helper;

import javax.swing.table.AbstractTableModel;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 15, 2006
 */
public abstract class HandyTableModel extends AbstractTableModel {

    /** The last clicked row. */
    private int lastClickedRow;

    /**
     * Instantiates a new handy table model.
     */
    protected HandyTableModel() {
    }

    /**
     * Gets the last clicked row.
     * 
     * @return the last clicked row
     */
    public int getLastClickedRow() {
        return lastClickedRow;
    }

    /**
     * Sets the last clicked row.
     * 
     * @param lastClickedRow
     *            the new last clicked row
     */
    public void setLastClickedRow(int lastClickedRow) {
        this.lastClickedRow = lastClickedRow;
    }
}
