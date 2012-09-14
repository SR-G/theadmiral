package net.coljac.pirates.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class FileHelper.
 */
public class FileHelper {

    /**
     * Gets the file contents as array.
     * 
     * @param fileName
     *            the file name
     * @return the file contents as array
     */
    public static String[] getFileContentsAsArray(final String fileName) {
        final ArrayList lines = new ArrayList();
        try {
            final BufferedReader br = new BufferedReader(new FileReader(fileName));
            for (String line = null; (line = br.readLine()) != null;) {
                lines.add(line);
            }

            br.close();
        } catch (final Exception e) {
        }
        return (String[]) lines.toArray(new String[0]);
    }

    /**
     * Gets the file contents as list.
     * 
     * @param fileName
     *            the file name
     * @return the file contents as list
     */
    public static List getFileContentsAsList(final String fileName) {
        final ArrayList lines = new ArrayList();
        try {
            final BufferedReader br = new BufferedReader(new FileReader(fileName));
            for (String line = null; (line = br.readLine()) != null;) {
                lines.add(line);
            }

            br.close();
        } catch (final Exception e) {
        }
        return lines;
    }

    /**
     * Gets the file contents as string.
     * 
     * @param fileName
     *            the file name
     * @return the file contents as string
     */
    public static String getFileContentsAsString(final String fileName) {
        final StringBuffer lines = new StringBuffer();
        try {
            final BufferedReader br = new BufferedReader(new FileReader(fileName));
            for (String line = null; (line = br.readLine()) != null;) {
                lines.append(line + "\n");
            }

        } catch (final FileNotFoundException fnf) {
            return null;
        } catch (final Exception e) {
        }
        return lines.toString();
    }

    /**
     * Gets the file contents as string.
     * 
     * @param fileName
     *            the file name
     * @param encoding
     *            the encoding
     * @return the file contents as string
     */
    public static String getFileContentsAsString(final String fileName, final String encoding) {
        final StringBuffer lines = new StringBuffer();
        try {
            final BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), encoding));
            for (String line = null; (line = br.readLine()) != null;) {
                lines.append(line + "\n");
            }

        } catch (final FileNotFoundException fnf) {
            return null;
        } catch (final Exception e) {
        }
        return lines.toString();
    }

    /**
     * Write file.
     * 
     * @param file
     *            the file
     * @param contents
     *            the contents
     */
    public static void writeFile(final String file, final String contents) {
        try {
            final PrintWriter bw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            bw.print(contents);
            bw.flush();
            bw.close();
        } catch (final IOException e) {
        }
    }

    /**
     * Write file ut f8.
     * 
     * @param file
     *            the file
     * @param contents
     *            the contents
     */
    public static void writeFileUTF8(final String file, final String contents) {
        try {
            final PrintWriter bw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8")));
            bw.print(contents);
            bw.flush();
            bw.close();
        } catch (final IOException e) {
        }
    }
}
