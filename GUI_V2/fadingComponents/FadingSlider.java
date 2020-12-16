package fadingComponents;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JSlider;

import logic.ProgramPresets;
import logic.TextStyles;

/**
 * Implementation of a fading slider. 
 * @author Cade Reynoldson. 
 */
public class FadingSlider extends JSlider implements FadingComponent {
    
    /** Sets the serial id. */
    private static final long serialVersionUID = -4285347140665261121L;
    
    /** The current alpha value of this label (transparency) */
    private float alpha;
    
    /**
     * Creates a new instance of fading slider. 
     * @param min the minimum value. 
     * @param max the maximum value. 
     * @param currentValue the current value. 
     */
    public FadingSlider(int min, int max, int currentValue, TextStyles style) {
        super(min, max, currentValue);
        initText(style);
        alpha = 1f;
    }
    
    public void setTickSpacing(int minor, int major) {
        setMinorTickSpacing(minor);
        setMajorTickSpacing(major);
        this.setPaintLabels(true);
        this.setPaintTicks(true);
    }
    
    /**
     * Initializes the text of the checkbox. 
     * @param text the text to display. 
     * @param style the style to display the text in. 
     */
    private void initText(TextStyles style) {
        setForeground(ProgramPresets.COLOR_TEXT);
        setBackground(ProgramPresets.COLOR_TEXT);
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
