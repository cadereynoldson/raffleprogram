package gui_v2.logic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.Painter;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class ProgramPresets {
    
    public static final Font TITLE_FONT = new java.awt.Font("Century Gothic", 3, 22);
    
    public static final Font LARGE_FONT = new java.awt.Font("Century Gothic", 3, 18);
    
    public static final Font DEFAULT_FONT_ITALICS = new java.awt.Font("Century Gothic", 2, 14);
    
    public static final Font DEFAULT_FONT_BOLD = new java.awt.Font("Century Gothic", 1, 14);
    
    public static final Font DEFAULT_FONT = new java.awt.Font("Century Gothic", 0, 14);
    
    /** Black Olive - Background color. Dark theme.  */
    public static final Color COLOR_BACKGROUND = new Color(53, 53, 49);
    
    /** Dark Cyan - Color for buttons */
    public static final Color COLOR_BUTTONS = new Color(73, 140, 138);
    
    /** Platinum - Color for text and borders. */
    public static final Color COLOR_TEXT = new Color(229, 229, 229);
    
    /** The color to use when something has focus. */
    public static final Color COLOR_FOCUS = new Color(0, 174, 174);
    
    /** Image icon for the 35th tron. */
    public static final ImageIcon TRON = getTron();
    
    /** Raffle ticket for the icon of the program. */
    public static final ImageIcon RAFFLE_TICKET = getRaffleTicket();
    
    public static final ImageIcon INFORMATION_ICON = getInformationIcon();
    
    public static final ImageIcon ERROR_ICON = getErrorIcon();
    
    public static final ImageIcon CONFIRM_ICON = getConfirmIcon();
    
    public static final Painter<JToolTip> TOOL_TIP_PAINTER = getToolTipPainter();
    
    public static JButton createButton(String buttonName) {
        JButton b = new JButton(buttonName);
        b.setBackground(COLOR_FOCUS);
        b.setFont(DEFAULT_FONT_ITALICS);
        b.setForeground(COLOR_BACKGROUND);
        return b;
    }
    
    /**
     * Creates titled & bordered panel. 
     * @param title the title of the bordered panel. 
     * @return a blank titled and bordered panel. 
     */
    public static JPanel createTitledPanel(String title) {
        JPanel p = new JPanel();
        p.setBackground(COLOR_BACKGROUND);
        p.setBorder(getTitledBorder(title));
        return p;
    }
    
    /**
     * Creates a blank bordered panel.
     * @return a blank bordered panel. 
     */
    public static JPanel createBorderedPanel() {
        JPanel p = new JPanel();
        p.setBackground(COLOR_BACKGROUND);
        p.setBorder(getBorder());
        return p;
    }
    
    /**
     * Creates a blank panel. 
     * @return a blank panel. 
     */
    public static JPanel createPanel() {
        JPanel p = new JPanel();
        p.setBackground(COLOR_BACKGROUND);
        return p;
    }
    
    /**
     * Creates a centered JLabel with the specified text in this programs title format. 
     * @param text the text to put in the JLabel. 
     * @return a JLabel with the specified text as a title. 
     */
    public static JLabel createCenteredTitle(String text) {
        JLabel l = new JLabel(text);
        l.setFont(TITLE_FONT);
        l.setForeground(COLOR_TEXT);
        l.setHorizontalAlignment(SwingConstants.CENTER);
        return l;
    }
    
    /**
     * Creates a centered JLabel in this programs default text format. 
     * @param the text of the JLabel. 
     * @return a centered JLabel in this programs default text format. 
     */
    public static JLabel createCenteredLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(DEFAULT_FONT);
        l.setForeground(COLOR_TEXT);
        l.setHorizontalAlignment(SwingConstants.CENTER);
        return l;
    }
    
    /**
     * Creates a centered bolded JLabel in this programs format. 
     * @param text the text of the JLabel. 
     * @return a centered bolded JLabel in this programs format. 
     */
    public static JLabel createCenteredLabelBold(String text) {
        JLabel l = new JLabel(text);
        l.setFont(DEFAULT_FONT_ITALICS);
        l.setForeground(COLOR_TEXT);
        l.setHorizontalAlignment(SwingConstants.CENTER);
        return l;
    }
    
    /**
     * Creates a titled border with a parameterized title. 
     * @param title the title to enscribe in the border. 
     * @return a titled border with the parameterized title. 
     */
    public static Border getTitledBorder(String title) {
        TitledBorder b = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(COLOR_TEXT), title, TitledBorder.LEFT, TitledBorder.TOP, DEFAULT_FONT_ITALICS);
        b.setTitleColor(COLOR_TEXT);
        return b;
    }
    
    /**
     * Creates a center titled border with a parameterized title. 
     * @param title the title to enscribe in the border. 
     * @return a titled border with the parameterized title. 
     */
    public static Border getCenterTitledBorder(String title) {
        TitledBorder b = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(COLOR_TEXT), title, TitledBorder.CENTER, TitledBorder.TOP, DEFAULT_FONT_ITALICS);
        b.setTitleColor(COLOR_TEXT);
        return b;
    }
    
    /**
     * Returns a border in this programs format. 
     * @return a border in this programs format. 
     */
    public static Border getBorder() {
        return BorderFactory.createLineBorder(COLOR_TEXT);
    }
    
    /**
     * Returns an image icon containing the 35th Tron. 
     * @return an image icon containing the 35th Tron. 
     */
    public static ImageIcon getTron() {
        return new ImageIcon(ProgramPresets.class.getResource("/WHITE_TRON.png"));
    }
    
    /**
     * Returns an image icon containing the raffle ticket. 
     * @return an imago icon containing the raffle ticket. 
     */
    public static ImageIcon getRaffleTicket() {
        return new ImageIcon(ProgramPresets.class.getResource("/RaffleTicket.jpg"));
    }
    
    public static ImageIcon getInformationIcon() {
        return new ImageIcon(ProgramPresets.class.getResource("/InformationIcon.png"));
    }
    
    public static ImageIcon getConfirmIcon() {
        return new ImageIcon(ProgramPresets.class.getResource("/ConfirmIcon.png"));
    }
    
    public static ImageIcon getErrorIcon() {
        return new ImageIcon(ProgramPresets.class.getResource("/ErrorIcon.png"));
    }
    
    /**
     * Displays an error in the form of an option pane. 
     * @param message the message to display. 
     * @param title the title of the message. 
     * @param displayIn the container to display the message in. 
     */
    public static void displayError(String message, String title, Container displayIn) {
        JOptionPane error = new JOptionPane(message, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, ERROR_ICON);
        colorDialog(error);
        error.setBackground(COLOR_BACKGROUND);
        error.setFont(DEFAULT_FONT);
        JDialog jd = error.createDialog(displayIn, title);
        jd.setVisible(true);
    }
    
    /**
     * Displays a message in the form of an option pane. 
     * @param message the message to display. 
     * @param title the title of the message. 
     * @param displayIn the container to display the message in. 
     */
    public static void displayMessage(String message, String title, Container displayIn) {
        JOptionPane info = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, INFORMATION_ICON);
        colorDialog(info);
        info.setBackground(COLOR_BACKGROUND);
        info.setFont(DEFAULT_FONT);
        JDialog jd = info.createDialog(displayIn, title);
        jd.setVisible(true);
    }
    
    /**
     * Displays a message in the form of an option pane. 
     * @param message the message to display. 
     * @param title the title of the message. 
     * @param displayIn the container to display the message in. 
     * @return the selected value by the user. 
     */
    public static int displayYesNoConfirm(String message, String title, Container displayIn) {
        JOptionPane confirm = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_NO_OPTION, CONFIRM_ICON);
        colorDialog(confirm);
        confirm.setBackground(COLOR_BACKGROUND);
        confirm.setFont(DEFAULT_FONT);
        JDialog jd = confirm.createDialog(displayIn, title);
        jd.setVisible(true);
        int value = ((Integer) confirm.getValue()).intValue();
        System.out.println(value == JOptionPane.OK_OPTION);
        return value; 
    }
    
    /**
     * Colors a container according the the values in this program. 
     * @param c the container to color. 
     */
    private static void colorDialog(Container c) {
        Component[] m = c.getComponents();
        for (int i = 0; i < m.length; i++) {
            String className = m[i].getClass().getName();
            if (className.equals("javax.swing.JPanel"))
                m[i].setBackground(COLOR_BACKGROUND);
            else if (className.equals("javax.swing.JLabel")) {
                m[i].setForeground(COLOR_TEXT);
                m[i].setFont(DEFAULT_FONT);
            }
            if (m[i] instanceof Container) //Recursively color all components in a dialog. 
                colorDialog((Container) m[i]);
        }
    }
    
    /** 
     * Creates a custom tool tip painter. 
     * @return a custom tool tip painter. 
     */
    public static Painter<JToolTip> getToolTipPainter() {
        final Painter<JToolTip> painter = new Painter<JToolTip>() {
            @Override
            public void paint(Graphics2D g, JToolTip object, int width, int height) {
                object.setBackground(COLOR_BACKGROUND);
                object.setForeground(COLOR_TEXT);
                object.setFont(DEFAULT_FONT);
                object.setBorder(BorderFactory.createLineBorder(COLOR_TEXT));
            }
        };
        return painter; 
    }
    
}
