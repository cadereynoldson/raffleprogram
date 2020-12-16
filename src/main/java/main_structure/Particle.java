package main_structure;

public class Particle {
   
    /** The value stored in this class. Can take the form of a string or a float. */
    private Object value;
    
    /**
     * Creates a new particle. Tries to parse a float. If parsing fails,
     * stores the value as a string. 
     * @param value the value to store. 
     */
    public Particle(String value) {
        try {
            this.value = Integer.parseInt(value);
        } catch (Exception e){
            try {
                this.value = Float.parseFloat(value);
            } catch (Exception e2) {
                this.value = value;
            }
        }
    }
    
    /**
     * Changes the value of this particle. 
     * @param value
     */
    public void setValue(String value) {
        try {
            this.value = Integer.parseInt(value);
        } catch (Exception e){
            try {
                this.value = Float.parseFloat(value);
            } catch (Exception e2) {
                this.value = value;
            }
        }
    }
    
    /**
     * Returns the value of this particle. 
     * @return
     */
    public Object getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}
