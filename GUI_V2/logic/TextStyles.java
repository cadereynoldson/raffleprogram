<<<<<<< HEAD:GUI_V2/logic/TextStyles.java
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
=======
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
>>>>>>> parent of f93266f... Small refactor. Preparing for new GUI development.:src/main/java/gui_v2/logic/TextStyles.java
