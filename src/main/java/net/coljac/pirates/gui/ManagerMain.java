package net.coljac.pirates.gui;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.ToolTipManager;
import javax.swing.filechooser.FileFilter;

import net.coljac.pirates.CardDatabase;
import net.coljac.pirates.gui.helper.Exporter;
import net.coljac.pirates.gui.helper.ImageHelper;
import net.coljac.pirates.gui.helper.Importer;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 11, 2006
 */
public class ManagerMain extends JFrame {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** The instance. */
    public static ManagerMain instance;

    /**
     * Creates the instance.
     */
    public static synchronized void createInstance() {
        instance = new ManagerMain();
    }

    /**
     * The main method.
     * 
     * @param args
     *            the arguments
     */
    public static void main(final String[] args) {
        // try {
        // UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        // } catch (Exception e) {
        // }
        // JFrame.setDefaultLookAndFeelDecorated(true);
        createInstance();
    }

    /** The ships panel. */
    public ShipsPanel shipsPanel;

    /** The fleets panel. */
    public FleetsPanel fleetsPanel;

    /** The other panel. */
    public OtherPanel otherPanel;

    /** The all panel. */
    public OtherPanel allPanel;

    /** The crew panel. */
    public CrewPanel crewPanel;

    /** The db. */
    public CardDatabase db;

    /** The tab panel. */
    public TabPanel tabPanel;

    /** The file menu. */
    private final JMenu fileMenu = new JMenu("File");

    /** The help menu. */
    private final JMenu helpMenu = new JMenu("Help");

    /** The about panel. */
    private final AboutPanel aboutPanel;

    /**
     * Instantiates a new manager main.
     * 
     * @throws HeadlessException
     *             the headless exception
     */
    public ManagerMain() throws HeadlessException {
        super("The Admiral");
        String cardDB = "cards.db";
        fileMenu.setMnemonic(KeyEvent.VK_F);
        helpMenu.setMnemonic(KeyEvent.VK_H);

        if (System.getProperty("card.db") != null) {
            cardDB = System.getProperty("card.db");
        }
        if (new File(cardDB).exists()) {
            try {
                db = CardDatabase.init(cardDB);
            } catch (final IOException e) {
                e.printStackTrace();
                db = new CardDatabase(cardDB);
            }
        } else {
            try {
                final InputStream is = ManagerMain.class.getResourceAsStream("/data/cards.db");
                if (is != null) {
                    db = CardDatabase.init(is, cardDB);
                    db.clearOwnedCards();
                } else {
                    db = new CardDatabase(cardDB);
                }
            } catch (final Exception e) {
                e.printStackTrace();
                db = new CardDatabase(cardDB);
            }
        }
        checkImagesAvailabily();
        init();
        setIconImage(Icons.ICON_ADMIRAL_32.getImage());

        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) (screen.getWidth() * .7), (int) (screen.getHeight() * 0.8));
        setLocation((int) (screen.getWidth() * .025), (int) (screen.getHeight() * 0.05));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                shutdown();
                System.exit(0);
            }
        });

        setVisible(true);
        aboutPanel = new AboutPanel();
    }

    /**
     * Check images availabily.
     * 
     * @param db2
     *            the db2
     */
    private void checkImagesAvailabily() {
        final List<String> notAvailablesImages = ImageHelper.checkImageAvaibility(db);
        if (notAvailablesImages.size() > 0) {
            Collections.sort(notAvailablesImages);
            System.out.println("Warning, " + notAvailablesImages.size() + " images aren't available");
            for (final String notAvailableImage : notAvailablesImages) {
                System.out.println(" - " + notAvailableImage);
            }
        }
    }

    /**
     * Db changed.
     */
    public void dbChanged() {
        init();
        // repaint();
    }

    /**
     * Gets the comment string.
     * 
     * @return the comment string
     */
    private String getCommentString() {
        final String inputValue = JOptionPane.showInputDialog(this, "Enter comment:", "Comment?", JOptionPane.QUESTION_MESSAGE);
        return inputValue;
    }

    /**
     * Gets the file.
     * 
     * @param extension
     *            the extension
     * @param open
     *            the open
     * @return the file
     */
    public String getFile(final String extension, final boolean open) {
        final String ext = extension;
        final JFileChooser chooser = new JFileChooser(new File("."));
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return pathname.isDirectory() || pathname.getAbsolutePath().toUpperCase().endsWith(ext.toUpperCase());
            }

            @Override
            public String getDescription() {
                return "Tab-delimited text";
            }
        });
        final int returnVal = open ? chooser.showOpenDialog(this) : chooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    /**
     * Inits the.
     */
    private void init() {

        if (getJMenuBar() == null) {
            final JMenuBar mb = new JMenuBar();
            initializeFileMenu();
            initializeHelpMenu();
            mb.add(fileMenu);
            mb.add(helpMenu);
            setJMenuBar(mb);
        }

        ToolTipManager.sharedInstance().setInitialDelay(0);

        instance = this;
        shipsPanel = new ShipsPanel();
        otherPanel = new OtherPanel();
        allPanel = new OtherPanel(true);
        fleetsPanel = new FleetsPanel();
        crewPanel = new CrewPanel();

        shipsPanel.setFleetsPanel(fleetsPanel);
        crewPanel.setFleetsPanel(fleetsPanel);
        otherPanel.setFleetsPanel(fleetsPanel);
        allPanel.setFleetsPanel(fleetsPanel);

        if (tabPanel == null) {
            tabPanel = new TabPanel();
            this.add(tabPanel);
        }
        tabPanel.init();

        repaint();

    }

    /**
     * Initialize file menu.
     */
    private void initializeFileMenu() {

        final JMenu exportSubMenu = new JMenu("Export");
        fileMenu.add(exportSubMenu);

        final JMenu importSubMenu = new JMenu("Import");
        fileMenu.add(importSubMenu);

        final JMenuItem exportMy = new JMenuItem("Export my cards");
        exportMy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final String file = getFile("txt", false);
                if (file != null) {
                    try {
                        Exporter.exportCardList(file, true);
                    } catch (final IOException e1) {
                        showError(e1.getMessage());
                    }
                }
            }
        });
        exportSubMenu.add(exportMy);

        final JMenuItem exportAll = new JMenuItem("Export all cards");
        exportAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final String file = getFile("txt", false);
                if (file != null) {
                    try {
                        Exporter.exportCardList(file, false);
                    } catch (final IOException e1) {
                        showError(e1.getMessage());
                    }
                }
            }
        });

        exportSubMenu.add(exportAll);

        final JMenuItem exportChecklist = new JMenuItem("Export checklist");
        exportChecklist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final String file = getFile("txt", false);
                if (file != null) {
                    try {
                        Exporter.exportChecklist(file);
                    } catch (final IOException e1) {
                        showError(e1.getMessage());
                    }
                }
            }
        });

        exportSubMenu.add(exportChecklist);

        // Miniatures trading

        final JMenu minis = new JMenu("Trading");
        exportSubMenu.add(minis);

        final JMenuItem haves = new JMenuItem("Haves");
        haves.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final String file = getFile("txt", true);
                try {
                    Exporter.exportMT(file, getCommentString(), true);
                } catch (final IOException ioe) {
                    showError("Problem with the export: " + ioe.getMessage());
                }
            }
        });
        minis.add(haves);

        final JMenuItem wants = new JMenuItem("Wants");
        wants.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final String file = getFile("txt", true);
                try {
                    Exporter.exportMT(file, getCommentString(), false);
                } catch (final IOException ioe) {
                    showError("Problem with the export: " + ioe.getMessage());
                }
            }
        });
        minis.add(wants);

        final JMenuItem importCards = new JMenuItem("Import cards definitions");
        importCards.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final String file = getFile("txt", true);
                if (file != null) {
                    final JProgressBar progress = new JProgressBar(0, 100);
                    progress.setIndeterminate(true);
                    final JDialog progressDialog = new JDialog(ManagerMain.instance, "Importing...", false);
                    progressDialog.setUndecorated(true);
                    // progressDialog.setSize(200, 100);
                    progressDialog.setLocation((int) (ManagerMain.instance.getLocation().getX() + instance.getWidth()) / 2, (int) (ManagerMain.instance.getLocation().getY() + instance.getHeight()) / 2);
                    final JPanel panel = new JPanel();
                    panel.setBorder(BorderFactory.createTitledBorder("Importing..."));
                    panel.add(progress);
                    progressDialog.add(panel);

                    progressDialog.pack();

                    // new Thread() {
                    // public void run() {
                    progressDialog.setVisible(true);
                    // }
                    // }.start();
                    new Thread() {
                        @Override
                        public void run() {
                            String result = null;
                            try {
                                result = Importer.importCards(file);
                            } catch (final IOException e1) {
                                showError(e1.getMessage());
                            } finally {
                                progressDialog.setVisible(false);
                                progressDialog.dispose();
                                dbChanged();
                                if (result != null) {
                                    showMessage(result);
                                }
                            }
                        }
                    }.start();

                }
            }
        });

        importSubMenu.add(importCards);

        // / CHECKLIST
        final JMenuItem importMine = new JMenuItem("Import checklist");
        importMine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {

                final String file = getFile("txt", true);
                if (file != null) {
                    final JProgressBar progress = new JProgressBar(0, 100);
                    final JDialog progressDialog = new JDialog(ManagerMain.instance, "Importing...", true);
                    progressDialog.setLocation((int) ManagerMain.instance.getLocation().getX() / 2, (int) ManagerMain.instance.getLocation().getY() / 2);
                    progressDialog.add(progress);
                    progressDialog.pack();
                    progress.setVisible(true);

                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                final String result = Importer.importMyCards(file);
                                if (result != null) {
                                    showError(result);
                                }
                                // progress.setVisible(false);
                                showMessage(result);
                                dbChanged();
                            } catch (final IOException e1) {
                                showError(e1.getMessage());
                            }
                        }
                    }; // .start();

                }
            }
        });

        importSubMenu.add(importMine);

        fileMenu.addSeparator();

        final JMenuItem clear = new JMenuItem("Clear Cards");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int option = JOptionPane.showConfirmDialog(ManagerMain.instance, "This will remove all cards! Are you sure?", "Remove all cards?", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    db.clear();
                    db.save();
                    dbChanged();
                }

            }
        });
        fileMenu.add(clear);

        final JMenuItem clearOwned = new JMenuItem("Clear Have and Wants");
        clearOwned.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int option = JOptionPane.showConfirmDialog(ManagerMain.instance, "This will remove all \"Have\" and \"Wants\" counters ! Are you sure?", "Clear have and wants counters ?", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    db.clearOwnedCards();
                    db.save();
                    dbChanged();
                }

            }
        });
        fileMenu.add(clearOwned);

        fileMenu.addSeparator();

        final JMenuItem stats = new JMenuItem("Statistics");
        stats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JDialog dialog = new JDialog(instance, "Statistics", false);
                dialog.add(new StatisticsPanel());
                dialog.setSize(720, 900);
                dialog.setLocationRelativeTo(ManagerMain.instance);
                dialog.setVisible(true);
            }
        });
        fileMenu.add(stats);

        fileMenu.addSeparator();

        final JMenuItem exit = new JMenuItem("Exit", KeyEvent.VK_X);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                shutdown();
                System.exit(0);
            }
        });

        fileMenu.add(exit);
    }

    /**
     * Initialize help menu.
     */
    private void initializeHelpMenu() {
        final JMenuItem help = new JMenuItem("Help");
        help.setMnemonic(KeyEvent.VK_F1);
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JDialog dialog = new JDialog(instance, "Admiral Help", false);
                dialog.add(new HelpPanel("/help/help.html"));
                dialog.setSize(700, 500);
                dialog.setLocationRelativeTo(ManagerMain.instance);
                dialog.setVisible(true);
            }
        });

        final JMenuItem versions = new JMenuItem("Versions");
        versions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JDialog dialog = new JDialog(instance, "Admiral Versions", false);
                dialog.add(new HelpPanel("/help/versions.html"));
                dialog.setSize(700, 500);
                dialog.setLocationRelativeTo(ManagerMain.instance);
                dialog.setVisible(true);
            }
        });

        final JMenuItem about = new JMenuItem("About");
        about.setMnemonic(KeyEvent.VK_A);
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JDialog aboutDialog = new JDialog(ManagerMain.instance, "About", false);
                aboutDialog.add(aboutPanel);
                aboutDialog.pack();
                aboutDialog.setLocationRelativeTo(ManagerMain.instance);
                aboutDialog.setVisible(true);
            }
        });
        helpMenu.add(help);
        helpMenu.add(versions);
        helpMenu.addSeparator();
        helpMenu.add(about);
    }

    /**
     * Show alert.
     * 
     * @param message
     *            the message
     */
    public void showAlert(final String message) {
        JOptionPane.showMessageDialog(this, message, "Are you sure?", JOptionPane.YES_NO_OPTION);
    }

    /**
     * Show confirm.
     * 
     * @param message
     *            the message
     * @return true, if successful
     */
    public boolean showConfirm(final String message) {
        final int i = JOptionPane.showConfirmDialog(this, message, "Are you sure?", JOptionPane.YES_NO_OPTION);
        return i == JOptionPane.YES_OPTION;
    }

    /**
     * Show error.
     * 
     * @param message
     *            the message
     */
    public void showError(final String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Show message.
     * 
     * @param message
     *            the message
     */
    public void showMessage(final String message) {
        JOptionPane.showMessageDialog(this, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shutdown.
     */
    private void shutdown() {
        try {
            ManagerMain.instance.db.save();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
