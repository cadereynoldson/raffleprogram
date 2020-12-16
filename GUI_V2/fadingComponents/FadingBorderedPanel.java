package fadingComponents;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import logic.ProgramPresets;

/**
 * Panel which supports the fading of it's border.
 * - NOTE: This panel does not fade the components contained within it! 
 * @author Cade Reynoldson 
 */
public class FadingBorderedPanel extends JPanel implements FadingComponent {
    
    /** Serial ID. */
    private static final long serialVersionUID = -7706530594515958123L;

    /** The r color of the border. */
    private static final float r = ((float) ProgramPresets.COLOR_TEXT.getRed()) / 255f;
    
    /** The g color of the border. */
    private static final float g = ((float) ProgramPresets.COLOR_TEXT.getGreen()) / 255f;
    
    /** The b color of the border. */
    private static final float b = ((float) ProgramPresets.COLOR_TEXT.getBlue()) / 255f;
    
    /** The alpha value of the border. */
    float alpha;
    
    /** The title of the border. May be null. */
    String title;
    
    /**
     * Creates a new instance of the fading bordered panel. 
     */
    public FadingBorderedPanel() {
        super();
        title = null;
        alpha = 1f;
        setBorder(getNextBorder());
        setBackground(ProgramPresets.COLOR_BACKGROUND);
    }
    
    /**
     * Creates a new instance of the fading bordered panel with a title. 
     * @param title the title of the panel/border. 
     */
    public FadingBorderedPanel(String title) {
        super();
        this.title = title;
        alpha = 1f;
        setBorder(getNextBorder());
        setBackground(ProgramPresets.COLOR_BACKGROUND);
    }
    
    /**
     * Changes the alpha value of the border of the panel. 
     */
    public void setAlpha(float value) {
        alpha = value;
        setBorder(getNextBorder());
        repaint();
    }
    
    /**
     * Returns the next border with the updated alpha value. Always update alpha before calling this method. 
     * @return the next border with the updated alpha value. 
     */
    private Border getNextBorder() {
        if (title == null) {
            return BorderFactory.createLineBorder(new Color(r, g, b, alpha));
        } else {
            Color c = new Color(r, g, b, alpha);
            TitledBorder b = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(
                    c), title, TitledBorder.CENTER, TitledBorder.TOP, ProgramPresets.DEFAULT_FONT_ITALICS);
            b.setTitleColor(c);
            return b;
        }
    }
    
    public void changeBorderTitle(String newTitle) {
        title = newTitle;
        setBorder(getNextBorder());
        repaint();
    }
}


