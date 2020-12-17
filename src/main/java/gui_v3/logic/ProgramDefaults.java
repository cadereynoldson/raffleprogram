package gui_v3.logic;

import javax.swing.*;

public class ProgramDefaults {

    private static final int TEXT_PADDING = 10;

    public static JLabel getCenteredTitle(String title) {
        JLabel label = new JLabel(title);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(ProgramFonts.TITLE_FONT);
        label.setBackground(ProgramColors.FOREGROUND_COLOR);
        label.setForeground(ProgramColors.TEXT_ON_FG_COLOR);
        return label;
    }

    public static JLabel getCenteredTabLabel(String title) {
        JLabel label = new JLabel(title);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(ProgramFonts.DEFAULT_FONT);
        label.setForeground(ProgramColors.TEXT_ON_BG_COLOR);
        label.setBackground(ProgramColors.TAB_COLOR);
        return label;
    }

    public static JTextArea getDescriptionTextArea(String description) {
        JTextArea area = new JTextArea();
        area.setText(description);
        area.setFont(ProgramFonts.DEFAULT_FONT);
        area.setBackground(ProgramColors.FOREGROUND_COLOR);
        area.setForeground(ProgramColors.TEXT_ON_FG_COLOR);
        area.setLineWrap(true);
        area.setBorder(BorderFactory.createEmptyBorder(TEXT_PADDING, TEXT_PADDING, TEXT_PADDING, TEXT_PADDING));
        area.setEditable(false);
        return area;
    }
}
