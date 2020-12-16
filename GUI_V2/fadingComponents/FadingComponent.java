package fadingComponents;


/**
 * Specifies the necessary methods to create a fading component. 
 * @author Cade Reynoldson
 */
public interface FadingComponent {
    
    /**
     * Changes the alpha value of this component. 
     * This method is responsible to repaint the component which implements this interface!
     * @param value the new alpha value. 
     */
    public abstract void setAlpha(float value);
    
}
