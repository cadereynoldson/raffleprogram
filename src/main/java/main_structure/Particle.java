package main_structure;

public class Particle {
   
    /** The value stored in this class. Can take the form of a string, double or integer. */
    private Object value;
    
    /**
     * Creates a new particle. Tries to parse an integer and double. If parsing fails,
     * stores the value as a string. 
     * @param value the value to store. 
     */
    public Particle(String value) {
        setValue(value);
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
                this.value = Double.parseDouble(value);
                double temp = (Double) this.value - Math.floor((Double) this.value); //Cut decimals out to see if it's just a ".0" after the successful parse.
                if (temp == 0) //If after cutting the decimals and the remainder is zero, just make this an integer.
                    this.value = ((Double) this.value).intValue();
            } catch (Exception e2) {
                this.value = value.toLowerCase();
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

    public Particle deepCopy() {
        return new Particle(this.toString());
    }
}
