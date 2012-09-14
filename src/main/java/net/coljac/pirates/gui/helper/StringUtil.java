package net.coljac.pirates.gui.helper;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 27, 2006
 */
public class StringUtil {

    /**
     * Split string.
     * 
     * @param str
     *            the str
     * @param split
     *            the split
     * @return the string
     */
    public static String splitString(String str, int split) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            result.append(str.charAt(i));
            if (i > 0 && i % split == 0) {
                while (i < str.length() - 1 && str.charAt(i) != ' ') {
                    i++;
                    result.append(str.charAt(i));
                }
                result.append("<br>");
            }
        }
        return result.toString();
    }

}
