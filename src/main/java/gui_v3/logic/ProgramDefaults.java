package gui_v3.logic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

public class ProgramDefaults {

    /** Confirm icon to be displayed in a dialogue box. */
    private static final ImageIcon CONFIRM_ICON = new ImageIcon(ProgramDefaults.class.getResource("/ConfirmIcon.png"));

    /** Information icon to be displayed in a dialogue box. */
    private static final ImageIcon INFORMATION_ICON = new ImageIcon(ProgramDefaults.class.getResource("/InformationIcon.png"));

    /** Error icon to be displayed in a dialogue box. */
    private static final ImageIcon ERROR_ICON = new ImageIcon(ProgramDefaults.class.getResource("/ErrorIcon.png"));

    /**
     * Creates a foreground label.
     * @param text the text to display in the JLabel.
     * @param horizAlignment the horizontal alignment (SWING CONSTANTS).
     * @param vertAlignment the vertical alignment (SWING CONSTANTS).
     * @param font the font of the label.
     * @return a formatted label for the foreground of a
     */
    public static JLabel getForegroundLabel(String text, int horizAlignment, int vertAlignment, Font font) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(horizAlignment);
        label.setVerticalAlignment(vertAlignment);
        label.setFont(font);
        label.setBackground(ProgramColors.FOREGROUND_COLOR);
        label.setForeground(ProgramColors.TEXT_ON_FG_COLOR);
        return label;
    }

    /**
     * Returns a foreground formatted JLabel used for titles.
     * @param title the title to display.
     * @return a foreground formatted JLabel used for titles.
     */
    public static JLabel getCenteredTitle(String title) {
        return getForegroundLabel(title, SwingConstants.CENTER, SwingConstants.CENTER, ProgramFonts.TITLE_FONT);
    }

    /**
     * Returns a left aligned interaction label formatted for the interaction panel.
     * Main difference is larger text.
     * @param text the text to display.
     * @return a left aligned interaction label formatted for the interaction panel.
     */
    public static JLabel getLeftAlignedInteractionLabel(String text) {
        return getForegroundLabel(text, SwingConstants.LEADING, SwingConstants.CENTER, ProgramFonts.DEFAULT_FONT_LARGE);
    }

    public static JLabel getCenterAlignedInteractionLabel(String text) {
        return getForegroundLabel(text, SwingConstants.CENTER, SwingConstants.CENTER, ProgramFonts.DEFAULT_FONT_LARGE);
    }

    public static JLabel getRightAlignedInteractionLabel(String text) {
        return getForegroundLabel(text, SwingConstants.TRAILING, SwingConstants.CENTER, ProgramFonts.DEFAULT_FONT_LARGE);
    }

    public static JLabel getFileDisplayLabel(String text) {
        return getForegroundLabel(ProgramStrings.strToHTML("<u>" + text + "</u>"), SwingConstants.LEADING, SwingConstants.CENTER, ProgramFonts.DEFAULT_FONT_LARGE_ITALICS);
    }

    /**
     * Creates a label to be displayed on the tab list.
     * @param title the title of the tab.
     * @return a label to be displayed on the tab list.
     */
    public static JLabel getCenteredTabLabel(String title) {
        JLabel label = getForegroundLabel(title, SwingConstants.CENTER, SwingConstants.CENTER, ProgramFonts.DEFAULT_FONT_SMALL);
        label.setForeground(ProgramColors.TEXT_ON_BG_COLOR);
        label.setBackground(ProgramColors.TAB_COLOR);
        return label;
    }

    /**
     * Creates a label to be used in the description panel. This is center aligned, and font size of 14.
     * @param text the text to display.
     * @return a label to be used in the description panel.
     */
    public static JLabel getDescriptionLabel(String text) {
        JLabel l = getForegroundLabel(text, SwingConstants.CENTER, SwingConstants.CENTER, ProgramFonts.DEFAULT_FONT_SMALL);
        l.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        return l;
    }

    /**
     * Creates a blank panel with the programs foreground color.
     * @return a blank panel with the programs foreground color.
     */
    public static JPanel getBlankPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(ProgramColors.FOREGROUND_COLOR);
        return panel;
    }

    /**
     * Allows a JLabel to function as a web link.
     * @param text the text to display.
     * @param link the link to route to.
     * @return a JLabel which functions as a link.
     */
    public static final JLabel convertToLink(final String text, final String link) {
        JLabel label = getDescriptionLabel("<html><u>" + text + "</u></html>");
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.setForeground(ProgramColors.LINK_COLOR);
        label.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(link));
                } catch (IOException | URISyntaxException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setText("<html><u>" + text + "</u></html>");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setText("<html><a href=''>" + text + "</a></html>");
            }
        });
        return label;
    }

    /**
     * Creates a spaced panel to avoid over-sized buttons. This will contain the default BUTTON padding of y = 20, and x = 40
     * @param button the button to contain in the panel.
     * @return a spaced panel to avoid over sized buttons.
     */
    public static JPanel createSpacedPanel(JButton button) {
        return createSpacedPanel(button, 40, 20);
    }

    /**
     * Creates a spaced panel to avoid over-sized button.
     * @param button the button to place at the center of the panel.
     * @param xPadding the number of pixels to pad the text in the button on the x axis (left and right of the text of the button).
     * @param yPadding the number of pixels to pad the text in the button on the y axis (above and below the text of the button).
     * @return a spaced panel to avoid over sized buttons.
     */
    public static JPanel createSpacedPanel(JButton button, int xPadding, int yPadding) {
        JPanel p = ProgramDefaults.getBlankPanel();
        p.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.ipady = yPadding;
        c.ipadx = xPadding;
        c.anchor = GridBagConstraints.CENTER;
        p.setAlignmentY(Component.CENTER_ALIGNMENT);
        p.add(button, c);
        return p;
    }

    public static JButton getButton(String buttonName) {
        JButton b = new JButton(buttonName);
        b.setBackground(ProgramColors.FOCUS_COLOR);
        b.setForeground(ProgramColors.TEXT_ON_FG_COLOR);
        b.setFont(ProgramFonts.DEFAULT_FONT_LARGE);
        b.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        b.setFocusPainted(false);
        return b;
    }

    public static JCheckBox getCheckBox(String checkBoxName) {
        JCheckBox b = new JCheckBox(checkBoxName);
        b.setFont(ProgramFonts.DEFAULT_FONT_LARGE);
        b.setHorizontalAlignment(SwingConstants.CENTER);
        b.setBackground(ProgramColors.FOREGROUND_COLOR);
        b.setFocusPainted(false);
        return b;
    }

    /**
     * Creates an returns an image icon containing the x.png image.
     * @return an ImageIcon containing the x.png image.
     */
    public static ImageIcon getXIcon() {
        return new ImageIcon(ProgramDefaults.class.getResource("/x_small.png"));
    }

    /**
     * Creates and returns an image icon containing the check.png image.
     * @return an ImageIcon containing the check.png image.
     */
    public static ImageIcon getCheckIcon() {
        return new ImageIcon(ProgramDefaults.class.getResource("/check_small.png"));
    }

    /**
     * Creates and returns an image icon containing the RaffleTicket.jpg image.
     * @return an ImageIcon containing the RaffleTicket.jpg image.
     */
    public static ImageIcon getRaffleTicketIcon() {
        return new ImageIcon(ProgramDefaults.class.getResource("/RaffleTicket.jpg"));
    }

    /**
     * Converts the directory of a file to it's shortform path.
     * @param f the file to convert.
     * @return the converted file path to display.
     */
    public static String getFileName(File f) {
        String[] list = f.toString().split(Pattern.quote("\\"));
        if (list.length == 1)
            return "...\\" + list[0];
        else
            return "...\\" + list[list.length - 2] + "\\" + list[list.length - 1];
    }

    public static int displayYesNoConfirm(String message, String title, Container displayIn) {
        JOptionPane confirm = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_NO_OPTION, CONFIRM_ICON);
        colorDialog(confirm);
        confirm.setBackground(ProgramColors.BACKGROUND_COLOR);
        confirm.setFont(ProgramFonts.DEFAULT_FONT_SMALL);
        JDialog jd = confirm.createDialog(displayIn, title);
        jd.setVisible(true);
        try {
            int value = ((Integer) confirm.getValue()).intValue();
            return value;
        } catch (NullPointerException e) {
            return JOptionPane.NO_OPTION;
        }
    }

    public static void displayError(String message, String title, Container displayIn) {
        JOptionPane error = new JOptionPane(message, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, ERROR_ICON);
        colorDialog(error);
        error.setBackground(ProgramColors.BACKGROUND_COLOR);
        error.setFont(ProgramFonts.DEFAULT_FONT_SMALL);
        JDialog jd = error.createDialog(displayIn, title);
        jd.setVisible(true);
    }

    public static void displayMessage(String message, String title, Container displayIn) {
        JOptionPane info = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, INFORMATION_ICON);
        colorDialog(info);
        info.setBackground(ProgramColors.BACKGROUND_COLOR);
        info.setFont(ProgramFonts.DEFAULT_FONT_SMALL);
        JDialog jd = info.createDialog(displayIn, title);
        jd.setVisible(true);
    }

    /**
     * Colors a container according the the values in this program.
     * @param c the container to color.
     */
    private static void colorDialog(Container c) {
        Component[] m = c.getComponents();
        for (int i = 0; i < m.length; i++) {
            if (m[i] instanceof JPanel)
                m[i].setBackground(ProgramColors.BACKGROUND_COLOR);
            else if (m[i] instanceof JLabel) {
                m[i].setForeground(ProgramColors.FOREGROUND_COLOR);
                m[i].setFont(ProgramFonts.DEFAULT_FONT_SMALL);
            } else if (m[i] instanceof JButton) {
                m[i].setBackground(ProgramColors.FOCUS_COLOR);
                m[i].setForeground(ProgramColors.TEXT_ON_FG_COLOR);
                m[i].setFont(ProgramFonts.DEFAULT_FONT_SMALL);
                ((JButton) m[i]).setFocusPainted(false);
                ((JButton) m[i]).setBorder(BorderFactory.createLineBorder(ProgramColors.TEXT_ON_FG_COLOR));
                m[i].setPreferredSize(new Dimension(80, 40));
            }
            if (m[i] instanceof Container) //Recursively color all components in a dialog.
                colorDialog((Container) m[i]);
        }
    }

    private static final class JGradientButton extends JButton{
        private JGradientButton(String text){
            super(text);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setPaint(new GradientPaint(
                    new Point(0, 0),
                    getBackground(),
                    new Point(0, getHeight()/3),
                    ProgramColors.FOCUS_COLOR_HIGHLIGHT));
            g2.fillRect(0, 0, getWidth(), getHeight()/3);
            g2.setPaint(new GradientPaint(
                    new Point(0, getHeight()/3),
                    ProgramColors.FOCUS_COLOR_HIGHLIGHT,
                    new Point(0, getHeight()),
                    getBackground()));
            g2.fillRect(0, getHeight()/3, getWidth(), getHeight());
            g2.dispose();

            super.paintComponent(g);
        }
    }
}
