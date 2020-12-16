<<<<<<< HEAD:GUI_V2/fadingComponents/FadingLabeledSlider.java
package fadingComponents;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import logic.ProgramPresets;
import logic.TextStyles;

public class FadingLabeledSlider extends JPanel implements FadingComponent {

    /** Serial id. */
    private static final long serialVersionUID = -2087388938949787938L;

    /** The slider. */
    private FadingSlider slider;
    
    /** The label. */
    private FadingLabel label; 
    
    /**
     * Creates a new instance of fading slider. 
     * @param min the minimum value. 
     * @param max the maximum value. 
     * @param currentValue the current value. 
     */
    public FadingLabeledSlider(int min, int max, int currentValue, TextStyles style) {
        setBackground(ProgramPresets.COLOR_BACKGROUND);
        slider = new FadingSlider(min, max, currentValue, style);
        label = new FadingLabel(Integer.toString(currentValue), TextStyles.BOLD);
        label.setForeground(ProgramPresets.COLOR_FOCUS.brighter());
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                label.setText(Integer.toString(slider.getValue()));
            }
        });
        setLayout(new BorderLayout());
        add(slider, BorderLayout.CENTER);
        add(label, BorderLayout.SOUTH);
    }
    
    /**
     * Sets the tick spacing of the slider. 
     * @param minor the minor tick interval. 
     * @param major the major tick interval. 
     */
    public void setTickSpacing(int minor, int major) {
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(minor);
        slider.setMajorTickSpacing(major);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
    }
    
    /**
     * Returns the selected value of the slider. 
     * @return the selected value of the slider. 
     */
    public int getSelectedValue() {
        return slider.getValue();
    }
    
    @Override
    public void setAlpha(float value) {
        slider.setAlpha(value);
        label.setAlpha(value);
        repaint();
    }
}
=======
package fadingComponents;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import logic.ProgramPresets;
import logic.TextStyles;

public class FadingLabeledSlider extends JPanel implements FadingComponent {

    /** Serial id. */
    private static final long serialVersionUID = -2087388938949787938L;

    /** The slider. */
    private FadingSlider slider;
    
    /** The label. */
    private FadingLabel label; 
    
    /**
     * Creates a new instance of fading slider. 
     * @param min the minimum value. 
     * @param max the maximum value. 
     * @param currentValue the current value. 
     */
    public FadingLabeledSlider(int min, int max, int currentValue, TextStyles style) {
        setBackground(ProgramPresets.COLOR_BACKGROUND);
        slider = new FadingSlider(min, max, currentValue, style);
        label = new FadingLabel(Integer.toString(currentValue), TextStyles.BOLD);
        label.setForeground(ProgramPresets.COLOR_FOCUS.brighter());
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                label.setText(Integer.toString(slider.getValue()));
            }
        });
        setLayout(new BorderLayout());
        add(slider, BorderLayout.CENTER);
        add(label, BorderLayout.SOUTH);
    }
    
    /**
     * Sets the tick spacing of the slider. 
     * @param minor the minor tick interval. 
     * @param major the major tick interval. 
     */
    public void setTickSpacing(int minor, int major) {
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(minor);
        slider.setMajorTickSpacing(major);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
    }
    
    /**
     * Returns the selected value of the slider. 
     * @return the selected value of the slider. 
     */
    public int getSelectedValue() {
        return slider.getValue();
    }
    
    @Override
    public void setAlpha(float value) {
        slider.setAlpha(value);
        label.setAlpha(value);
        repaint();
    }
}
>>>>>>> parent of f93266f... Small refactor. Preparing for new GUI development.:src/main/java/gui_v2/fadingComponents/FadingLabeledSlider.java
