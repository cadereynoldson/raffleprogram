package gui_v2.fadingComponents;


import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import gui_v2.logic.ProgramPresets;
import gui_v2.logic.TextStyles;

/**
 * Creates a jlabel with the ability to fade itself out 
 * NOTE: CURRENTLY ONLY ACCEPTS THE FADING OF TEXT!
 * @author Cade Reynoldson
 * @version 1.0
 */
public class FadingLabel extends JLabel implements FadingComponent {
    
    /** Serial ID. */
    private static final long serialVersionUID = 5598746262960280390L;
    
    /** The current alpha value of this label (transparency) */
    private float alpha;
    
    /**
     * Creates a new instance of a fading label. 
     * @param text the text of the label. 
     * @param style the style of the label. 
     */
    public FadingLabel(String text, TextStyles style) {
        super();
        alpha = 1f;
        initText(text, style);
    }
    
    /**
     * Initializes the text of the label. 
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
     * Sets the alpha value of the label, repaints panel to display it. 
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
