<<<<<<< HEAD:GUI_V2/subpanels/ProgressBar.java
package subpanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

import logic.ProgramPresets;

/**
 * Custom progress bar implementation. Note: Doesn't display text. 
 * @author Cade Reynoldson 
 * @version 1.0
 */
public class ProgressBar extends JPanel {

    /** Serial ID. */
    private static final long serialVersionUID = -8925942614573267847L;
    
    /** The current progress of the bar. */
    private int progress = 0;
    
    /** The width of the bar to display. */
    private final int width;
    
    /** The height of the bar to display. */
    private final int height;
	
    /** The color to outline the bars with. */
    private Color outlineColor;
    
    /** The gradient to display on the bar. */
	private LinearGradientPaint barPaint;
	
	/** The gradient to display below the bar. */
	private LinearGradientPaint backgroundPaint;
	
	/**
	 * Creates a new instance of the progress bar. 
	 * @param barColor the color of the bar to display. 
	 * @param backgroundColor the color of the area behind the bar. 
	 * @param outlineColor the color of the ouline. 
	 * @param width the width of the bar. 
	 * @param height the height of the bar. 
	 */
	public ProgressBar(Color barColor, Color backgroundColor, Color outlineColor, int width, int height) {
		this.width = width;
		this.height = height;
		this.outlineColor = outlineColor;
		init();
		initGradients(barColor, backgroundColor);
	}
	
	/**
	 * Initializes the progress bar. 
	 */
	private void init() {
	    setBackground(ProgramPresets.COLOR_BACKGROUND);
	    setPreferredSize(new Dimension(width, height + 2));
	}
	
	/**
	 * Initializes the gradient paints. 
	 * @param barColor the bar color to create a gradient with.
	 * @param backgroundColor the background color to create the gradient with.
	 */
	private void initGradients(Color barColor, Color backgroundColor) {
	    Point2D start = new Point2D.Float((float) width / 2f,  0f);
	    Point2D end = new Point2D.Float((float) width / 2f, height);
	    float[] dist = {0.0f, 0.2f, 1.0f};
	    Color[] barColors = {barColor, barColor.darker(), barColor};
	    Color[] backgroundColors = {backgroundColor.darker(), backgroundColor, backgroundColor.darker()};
	    barPaint = new LinearGradientPaint(start, end, dist, barColors);
	    backgroundPaint = new LinearGradientPaint(start, end, dist, backgroundColors);
	}
	
	/**
	 * Updates the progress of the bar. NOTE: CURRENTLY ONLY SUPPORTS 0-100
	 * @param value the new value to display.
	 */
	public void updateProgress(int value) {
		this.progress = value;
		repaint();
	}
	
	/**
	 * Paints the component. 
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		float currentWidth = progress * ((float) width / 100f);
		float rounding = (float) height / 2;
		RoundRectangle2D.Float barface = 
		        new RoundRectangle2D.Float(0, 0, width, height + 2, rounding, rounding);
		RoundRectangle2D.Float bar = 
		        new RoundRectangle2D.Float(0, 1, currentWidth, height, rounding, rounding);
		g2d.setPaint(backgroundPaint);
        g2d.fill(barface);
        g2d.setColor(outlineColor);
        g2d.draw(barface);
		g2d.setPaint(barPaint);
		g2d.draw(bar);
		g2d.fill(bar);
		g2d.setColor(outlineColor);
		g2d.draw(bar);
	}
=======
package subpanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

import logic.ProgramPresets;

/**
 * Custom progress bar implementation. Note: Doesn't display text. 
 * @author Cade Reynoldson 
 * @version 1.0
 */
public class ProgressBar extends JPanel {

    /** Serial ID. */
    private static final long serialVersionUID = -8925942614573267847L;
    
    /** The current progress of the bar. */
    private int progress = 0;
    
    /** The width of the bar to display. */
    private final int width;
    
    /** The height of the bar to display. */
    private final int height;
	
    /** The color to outline the bars with. */
    private Color outlineColor;
    
    /** The gradient to display on the bar. */
	private LinearGradientPaint barPaint;
	
	/** The gradient to display below the bar. */
	private LinearGradientPaint backgroundPaint;
	
	/**
	 * Creates a new instance of the progress bar. 
	 * @param barColor the color of the bar to display. 
	 * @param backgroundColor the color of the area behind the bar. 
	 * @param outlineColor the color of the ouline. 
	 * @param width the width of the bar. 
	 * @param height the height of the bar. 
	 */
	public ProgressBar(Color barColor, Color backgroundColor, Color outlineColor, int width, int height) {
		this.width = width;
		this.height = height;
		this.outlineColor = outlineColor;
		init();
		initGradients(barColor, backgroundColor);
	}
	
	/**
	 * Initializes the progress bar. 
	 */
	private void init() {
	    setBackground(ProgramPresets.COLOR_BACKGROUND);
	    setPreferredSize(new Dimension(width, height + 2));
	}
	
	/**
	 * Initializes the gradient paints. 
	 * @param barColor the bar color to create a gradient with.
	 * @param backgroundColor the background color to create the gradient with.
	 */
	private void initGradients(Color barColor, Color backgroundColor) {
	    Point2D start = new Point2D.Float((float) width / 2f,  0f);
	    Point2D end = new Point2D.Float((float) width / 2f, height);
	    float[] dist = {0.0f, 0.2f, 1.0f};
	    Color[] barColors = {barColor, barColor.darker(), barColor};
	    Color[] backgroundColors = {backgroundColor.darker(), backgroundColor, backgroundColor.darker()};
	    barPaint = new LinearGradientPaint(start, end, dist, barColors);
	    backgroundPaint = new LinearGradientPaint(start, end, dist, backgroundColors);
	}
	
	/**
	 * Updates the progress of the bar. NOTE: CURRENTLY ONLY SUPPORTS 0-100
	 * @param value the new value to display.
	 */
	public void updateProgress(int value) {
		this.progress = value;
		repaint();
	}
	
	/**
	 * Paints the component. 
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		float currentWidth = progress * ((float) width / 100f);
		float rounding = (float) height / 2;
		RoundRectangle2D.Float barface = 
		        new RoundRectangle2D.Float(0, 0, width, height + 2, rounding, rounding);
		RoundRectangle2D.Float bar = 
		        new RoundRectangle2D.Float(0, 1, currentWidth, height, rounding, rounding);
		g2d.setPaint(backgroundPaint);
        g2d.fill(barface);
        g2d.setColor(outlineColor);
        g2d.draw(barface);
		g2d.setPaint(barPaint);
		g2d.draw(bar);
		g2d.fill(bar);
		g2d.setColor(outlineColor);
		g2d.draw(bar);
	}
>>>>>>> parent of f93266f... Small refactor. Preparing for new GUI development.:src/main/java/gui_v2/subpanels/ProgressBar.java
}