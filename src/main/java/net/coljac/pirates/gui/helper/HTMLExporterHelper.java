package net.coljac.pirates.gui.helper;

/**
 * The Class HTMLExporterHelper.
 */
public class HTMLExporterHelper {

    /**
     * Builds the html footer.
     * 
     * @return the string
     */
    public String buildHTMLFooter() {
        return "</body></html>";
    }

    /**
     * Builds the html header.
     * 
     * @return the string
     */
    public String buildHTMLHeader() {
        return "<html><head></head><body>";
    }

    /**
     * Builds the html table.
     * 
     * @return the string
     */
    public String buildHTMLTable() {
        return "<table>";
    }

    /**
     * Builds the table footer.
     * 
     * @return the string
     */
    public String buildTableFooter() {
        return "</table>";
    }
}
