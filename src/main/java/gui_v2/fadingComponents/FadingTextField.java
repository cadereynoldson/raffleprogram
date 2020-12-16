package gui_v2.fadingComponents;


import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JTextField;

import gui_v2.logic.TextStyles;

public class FadingTextField extends JTextField implements FadingComponent {

    /** Serial id. */
    private static final long serialVersionUID = -3008359904546501920L;
    
    private float alpha; 
    
    public FadingTextField(int size, String originalText, TextStyles style) {
        super(originalText, size);
        setFont(TextStyles.getFont(style));
        super.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
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
