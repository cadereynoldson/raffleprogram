package fadingComponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.Timer;

/**
 * Timer for fading all of the FadingComponents contained within this class' instance. 
 * @author Cade Reynoldson
 * @version 1.0
 */
public class FadingTimer extends Timer {
    
    /** Serial ID. */
    private static final long serialVersionUID = -455034882795847849L;

    /** The components this timer is designated to fade. */
    private ArrayList<FadingComponent> componentsToFade;
    
    /** The current alpha value of this timer. */
    private float alpha;
    
    /** The current direction this alpha value is iterating towards. */
    private float direction;
    
    /** Property change support for notifying the progress of the fade. */
    private PropertyChangeSupport pcs;
    
    /** The property of the timer. */
    private String timerProperty;
    
    
    /**
     * Creates a new instance of a fading timer. 
     * @param interval the interval the timer will fade at (ms)
     * @param l the listenener to notify of
     */
    public FadingTimer(int interval, PropertyChangeListener l, String timerProperty) {
        super(interval, null);
        pcs = new PropertyChangeSupport(this);
        pcs.addPropertyChangeListener(l);
        alpha = 1f;
        direction = -0.025f;
        this.timerProperty = timerProperty;
        componentsToFade = new ArrayList<FadingComponent>();
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += direction;
                if (alpha < 0) {
                    alpha = 0;
                    direction = 0.025f;
                    stop();
                } else if (alpha > 1) {
                    alpha = 1; 
                    direction = -0.025f;
                    stop();
                }
                setAlpha(alpha);
            }
        });
    }
    
    /**
     * Creates a soft copy of a current fading timer. 
     * @param t the timer to fade in. 
     */
    public FadingTimer(FadingTimer t) {
        super(t.getDelay(), null);
        componentsToFade = t.componentsToFade;
        alpha = t.alpha;
        direction = t.direction;
        pcs = t.pcs;
        timerProperty = t.timerProperty;
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += direction;
                if (alpha < 0) {
                    alpha = 0;
                    direction = 0.025f;
                    stop();
                } else if (alpha > 1) {
                    alpha = 1; 
                    direction = -0.025f;
                    stop();
                }
                setAlpha(alpha);
            }
        });
    }
    
    public void setProperty(String newProperty) {
        timerProperty = newProperty;
    }
    
    /**
     * Adds a component to fade. Accepts multiple components. 
     * @param c
     */
    public void addComponent(FadingComponent ... components) {
        for (FadingComponent c : components)
            componentsToFade.add(c);
    }
    
    /**
     * Removes a fading compoenent from the timer. Accepts multiple components. 
     * @param c the component to fade. 
     */
    public void removeComponent(FadingComponent ... components) {
        for (FadingComponent c : components)
            componentsToFade.remove(c);
    }
    
    public void setComponents(Collection<FadingComponent> components) {
        componentsToFade.clear();
        for (FadingComponent c : components) {
            componentsToFade.add(c);
        }
    }
    
    /**
     * Removes all of the components this panel is in charge of fading. 
     */
    public void removeAll() {
        componentsToFade.clear();
    }
    
    /**
     * Starts fading out all of the components. 
     */
    public void fadeOut() {
        restart();
        alpha = 1f;
        direction = -0.05f;
        start();
    }
    
    /**
     * Starts fading in all of the components. 
     */
    public void fadeIn() {
        restart();
        alpha = 0f;
        direction = 0.05f;
        start();
    }
    
    /**
     * Sets a new alpha value of the timer. Updates all components this panel fades. 
     * @param value the new alpha value. 
     */
    public void setAlpha(float value) {
        if (alpha != value)
            this.alpha = value;
        for (FadingComponent c : componentsToFade)
            c.setAlpha(alpha);
        pcs.firePropertyChange(timerProperty, null, alpha);
    }
}
