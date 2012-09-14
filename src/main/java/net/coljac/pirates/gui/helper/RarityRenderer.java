package net.coljac.pirates.gui.helper;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import net.coljac.pirates.Card;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 15, 2006
 */
public class RarityRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

    /** The colors. */
    private Map<String, Color> colors = new HashMap<String, Color>();

    /** The font. */
    private Font font;

    /**
     * Instantiates a new rarity renderer.
     */
    public RarityRenderer() {
        colors = new HashMap<String, Color>();
        colors.put("R", Color.YELLOW);
        colors.put("C", Color.RED);
        colors.put("U", Color.LIGHT_GRAY);
        colors.put("V", Color.GREEN);
        colors.put("M", Color.GREEN);
        colors.put("P", Color.GREEN);
        colors.put("L", Color.ORANGE);
    }

    /**
     * {@inheritDoc}
     * 
     * @see javax.swing.table.DefaultTableCellRenderer#firePropertyChange(java.lang.String, boolean, boolean)
     */
    @Override
    public void firePropertyChange(final String s, final boolean flag, final boolean flag1) {
    }

    /**
     * {@inheritDoc}
     * 
     * @see javax.swing.table.DefaultTableCellRenderer#firePropertyChange(java.lang.String, java.lang.Object, java.lang.Object)
     */
    @Override
    protected void firePropertyChange(final String s, final Object obj, final Object obj1) {
    }

    /**
     * {@inheritDoc}
     * 
     * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    @Override
    public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int rowIndex, final int vColIndex) {
        final DefaultTableCellRenderer comp = (DefaultTableCellRenderer) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, rowIndex, vColIndex);
        final String abbrev = Card.getRarityAbbreviation(value.toString());
        comp.setForeground(colors.get(abbrev));
        comp.setHorizontalAlignment(0);
        if (font == null) {
            font = new Font(comp.getFont().getFontName(), 1, comp.getFont().getSize());
        }
        comp.setFont(font);
        comp.setText(abbrev);
        comp.setToolTipText(value.toString());
        return comp;
    }

    /**
     * {@inheritDoc}
     * 
     * @see javax.swing.table.DefaultTableCellRenderer#revalidate()
     */
    @Override
    public void revalidate() {
    }

    /**
     * {@inheritDoc}
     * 
     * @see javax.swing.table.DefaultTableCellRenderer#validate()
     */
    @Override
    public void validate() {
    }
}
