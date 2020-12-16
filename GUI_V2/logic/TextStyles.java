package logic;

import java.awt.Font;

public enum TextStyles {
    TITLE, ITALICS, DEFAULT, LARGE, BOLD;
    
    public static Font getFont(TextStyles style) {
        if (style == TextStyles.TITLE) 
            return (ProgramPresets.TITLE_FONT);
        else if (style == TextStyles.ITALICS)
            return (ProgramPresets.DEFAULT_FONT_ITALICS);
        else if (style == TextStyles.DEFAULT)
            return (ProgramPresets.DEFAULT_FONT);
        else if (style == TextStyles.LARGE)
            return (ProgramPresets.LARGE_FONT);
        else if (style == TextStyles.BOLD)
            return (ProgramPresets.DEFAULT_FONT_BOLD);
        else
            return null;
    }
    
}
