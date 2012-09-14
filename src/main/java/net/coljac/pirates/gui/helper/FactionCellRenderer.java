package net.coljac.pirates.gui.helper;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import net.coljac.pirates.gui.Icons;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 15, 2006
 */
public class FactionCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

    /**
     * {@inheritDoc}
     * 
     * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        DefaultTableCellRenderer comp = (DefaultTableCellRenderer) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        String faction = value.toString();

        Icon icon = Icons.getFactionIcon(faction);
        if (icon == null) {
            comp.setText(faction);
        } else {
            comp.setText("");
        }
        comp.setIcon(icon);
        comp.setHorizontalAlignment(JLabel.CENTER);
        comp.setToolTipText(faction);
        return comp;
    }

    /**
     * {@inheritDoc}
     * 
     * @see javax.swing.table.DefaultTableCellRenderer#validate()
     */
    public void validate() {
    }

    /**
     * {@inheritDoc}
     * 
     * @see javax.swing.table.DefaultTableCellRenderer#revalidate()
     */
    public void revalidate() {
    }

    /**
     * {@inheritDoc}
     * 
     * @see javax.swing.table.DefaultTableCellRenderer#firePropertyChange(java.lang.String, java.lang.Object, java.lang.Object)
     */
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
    }

    /**
     * {@inheritDoc}
     * 
     * @see javax.swing.table.DefaultTableCellRenderer#firePropertyChange(java.lang.String, boolean, boolean)
     */
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
    }
}
