package net.coljac.pirates.gui.helper;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 15, 2006
 */
public class IconHeaderRenderer extends DefaultTableCellRenderer {

    /**
     * {@inheritDoc}
     * 
     * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Inherit the colors and font from the header component
        if (table != null) {
            JTableHeader header = table.getTableHeader();
            if (header != null) {
                // setForeground(header.getForeground());
                // setBackground(header.getBackground());
                setForeground(Color.YELLOW);
                setBackground(Color.BLACK);
                setFont(header.getFont());
            }
        }

        if (value instanceof ImageIcon) {
            setIcon((ImageIcon) value);
            setText("");

        } else if (value instanceof TextAndIcon) {
            setIcon(((TextAndIcon) value).icon);
            setText(((TextAndIcon) value).text);
        } else {
            setText((value == null) ? "" : value.toString());
            setIcon(null);
        }
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        setHorizontalAlignment(JLabel.CENTER);
        return this;
    }

}
