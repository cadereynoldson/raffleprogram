package fadingComponents;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import logic.ProgramPresets;
import logic.TextStyles;

public class FadingCheckBox extends JCheckBox implements FadingComponent {

    /** Serial ID. */
    private static final long serialVersionUID = -1792173185297742074L;
    
    /** The current alpha value of this label (transparency) */
    private float alpha;
    
    /**
     * 
     * @param title
     * @param style
     */
    public FadingCheckBox(String title, TextStyles style) {
        super();
        alpha = 1f;
        initText(title, style);
    }
    
    /**
     * Initializes the text of the checkbox. 
     * @param text the text to display. 
     * @param style the style to display the text in. 
     */
    private void initText(String text, TextStyles style) {
        setText(text);
        setHorizontalAlignment(SwingConstants.CENTER);
        setForeground(ProgramPresets.COLOR_TEXT);
        if (style == TextStyles.TITLE) 
            setFont(ProgramPresets.TITLE_FONT);
        else if (style == TextStyles.ITALICS)
            setFont(ProgramPresets.DEFAULT_FONT_ITALICS);
        else if (style == TextStyles.DEFAULT)
            setFont(ProgramPresets.DEFAULT_FONT);
        else if (style == TextStyles.LARGE)
            setFont(ProgramPresets.LARGE_FONT);
        else if (style == TextStyles.BOLD)
            setFont(ProgramPresets.DEFAULT_FONT_BOLD);
    }
    
    /**
     * Sets the alpha value of the check box, repaints label to display it. 
     */
    @Override
    public void setAlpha(float value) {
        alpha = value;
        repaint();
    }
    
    /**
     * Override paint such that it allows for a new alpha value. 
     */
    @Override
    public void paint(Graphics g) {
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        super.paint(g2d);
        g2d.dispose();
    }
}
