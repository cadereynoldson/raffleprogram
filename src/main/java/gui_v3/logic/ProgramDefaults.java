package gui_v3.logic;

import main_structure.Row;
import main_structure.SpreadSheet;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.*;
import javax.swing.text.JTextComponent;
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

    /** The maximum table rows to display. */
    private static final int MAX_DISPLAYED_TABLE_ROWS = 12;

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

    /**
     * Creates a button themed to this program.
     * @param buttonName the name or text to display for this button.
     * @return a button themed to this program.
     */
    public static JButton getButton(String buttonName) {
        JButton b = new JButton(buttonName);
        b.setBackground(ProgramColors.FOCUS_COLOR);
        b.setForeground(ProgramColors.TEXT_ON_FG_COLOR);
        b.setFont(ProgramFonts.DEFAULT_FONT_LARGE);
        b.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        b.setFocusPainted(false);
        return b;
    }

    /**
     * Creates a JCheckbox themed to this program with a given name.
     * @param checkBoxName the name or text to display with this checkbox.
     * @return a checkbox themed to this program with a given name.
     */
    public static JCheckBox getCheckBox(String checkBoxName) {
        JCheckBox b = new JCheckBox("   " + checkBoxName);
        b.setFont(ProgramFonts.DEFAULT_FONT_LARGE);
        b.setHorizontalAlignment(SwingConstants.CENTER);
        b.setBackground(ProgramColors.FOREGROUND_COLOR);
        b.setFocusPainted(false);
        return b;
    }

    public static JTextField getTextField() {
        JTextField f = new JTextField();
        f.setFont(ProgramFonts.DEFAULT_FONT_LARGE);
        return f;
    }

    /* TABLE METHODS *****************************************************/

    /**
     * Creates a table for a given spreadsheet. By default ALL of the cells in the table WILL be disabled!
     * @param s the spreadsheet to convert to a JTable.
     * @return a JTable consisting of the data of a spreadsheet.
     */
    public static JTable getTable(SpreadSheet s) {
        return getTable(s, -1);
    }

    /**
     * Creates a table for a given spreadsheet. Only one spreadsheet in this table will be enabled!
     * @param s the spreadsheet to convert to a JTable.
     * @param enabledColIndex the index of the column to enable.
     * @return a JTable consisting of the data of a spreadsheet.
     */
    public static JTable getTable(SpreadSheet s, int enabledColIndex) {
        Object[][] data = s.getObjectRepresentation();
        for (int i = 0; i < data[0].length; i++) {
            System.out.println(data[0][i].getClass().toString());
        }
        JTable table = new JTable(data, s.getColumnNames()) {
            /** Only allows for the editing of the count cell. */
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == enabledColIndex)
                    return true;
                return false;
            }

            /** Fetches the column class. */
            @Override
            public Class<?> getColumnClass(int column) {
                Class returnValue;
                if ((column >= 0) && (column < getColumnCount()))
                    returnValue = data[0][column].getClass();
                else
                    returnValue = Object.class;
                return returnValue;
            }
        };
        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        //Sorter and table theme.
        table.setRowSorter(new TableRowSorter<>(table.getModel()));
        table.setBackground(ProgramColors.TABLE_CELL_COLOR);
        table.setForeground(ProgramColors.TEXT_ON_FG_COLOR);
        table.setSelectionBackground(ProgramColors.TABLE_ROW_FOCUS_COLOR);
        table.setFont(ProgramFonts.DEFAULT_FONT_LARGE);
        table.setGridColor(ProgramColors.TABLE_BORDER_COLOR);
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(enabledColIndex).setCellEditor(new TableEditor(getTextField()));
        //Cell height and program color.
        table.setRowHeight(24);
        //Header theme.
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setOpaque(false);
        tableHeader.setBackground(ProgramColors.TABLE_HEADER_COLOR);
        tableHeader.setForeground(ProgramColors.TABLE_HEADER_TEXT_COLOR);
        tableHeader.setFont(ProgramFonts.DEFAULT_FONT_LARGE);
        //Header border color.
        TableCellRenderer headerRenderer = tableHeader.getDefaultRenderer();
        tableHeader.setDefaultRenderer((table1, value, isSelected, hasFocus, row, column) -> {
            JLabel lbl = (JLabel) headerRenderer.getTableCellRendererComponent(table1, value, isSelected, hasFocus, row, column);
            lbl.setForeground(ProgramColors.TABLE_HEADER_TEXT_COLOR);
            lbl.setBorder(BorderFactory.createCompoundBorder(lbl.getBorder(), BorderFactory.createLineBorder(ProgramColors.TEXT_ON_FG_COLOR)));
            lbl.setHorizontalAlignment(SwingConstants.LEADING);
            return lbl;
        });
        return table;
    }

    private static TableRowSorter<TableModel> initRowSorter() {
        TableRowSorter<TableModel> s = new TableRowSorter<>();
        return s;
    }

    /**
     * Table editor, allows for selection of all text
     */
    private static class TableEditor extends DefaultCellEditor {

        public TableEditor(JTextField textField) {
            super(textField);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            Component c = super.getTableCellEditorComponent(table, value, isSelected, row, column);
            if (c instanceof JTextComponent) {
                JTextComponent jtc = (JTextComponent) c;
                jtc.requestFocus();
                SwingUtilities.invokeLater(() -> jtc.selectAll());
            }
            return c;
        }
    }

    public static JScrollPane getTableScrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(ProgramColors.TABLE_CELL_COLOR);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBorder(BorderFactory.createLineBorder(ProgramColors.TEXT_ON_FG_COLOR));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        int height;
        if (table.getRowCount() > MAX_DISPLAYED_TABLE_ROWS)
            height = MAX_DISPLAYED_TABLE_ROWS * table.getRowHeight();
        else
            height = table.getRowCount() * table.getRowHeight();
        table.setPreferredScrollableViewportSize(new Dimension(table.getPreferredSize().width, height));
        return scrollPane;
    }

    /* IMAGE ICON METHODS *****************************************************/

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
        java.awt.Toolkit.getDefaultToolkit().beep();
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
        java.awt.Toolkit.getDefaultToolkit().beep();
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
