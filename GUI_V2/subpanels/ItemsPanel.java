<<<<<<< HEAD:GUI_V2/subpanels/ItemsPanel.java
package subpanels;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import fadingComponents.FadeCapable;
import fadingComponents.FadingBorderedPanel;
import fadingComponents.FadingButton;
import fadingComponents.FadingCheckBox;
import fadingComponents.FadingComponent;
import fadingComponents.FadingLabel;
import fadingComponents.FadingLabeledSlider;
import fadingComponents.FadingScrollTable;
import fadingComponents.FadingTable;
import fadingComponents.FadingTextField;
import fadingComponents.FadingTimer;
import logic.ProgramPresets;
import logic.RaffleLogic;
import logic.TextStyles;
import main_structure.SpreadSheet;

public class ItemsPanel extends JPanel implements FadeCapable, FadingComponent, PropertyChangeListener {
    
    /** Serial ID. */
    private static final long serialVersionUID = 1755425953974187181L;

    private FadingLabel title; 
    
    private FadingBorderedPanel description;
    
    private AutoDetectPanel autoDetectPanel; 
    
    private PropertyChangeSupport pcs; 
    
    private RaffleLogic logic; 
    
    private float alpha; 
    
    /** Center panel. Used for displaying description and the current item selection mode panel. */
    private JPanel centerPanel; 
   
    private ArrayList<FadingComponent> fadingComponents;
    
    /** The timer contianing the components which are currently in focus. */
    private FadingTimer currentTimer;
    
    public ItemsPanel(RaffleLogic logic, PropertyChangeListener listener) {
        this.logic = logic; 
        pcs = new PropertyChangeSupport(this);
        pcs.addPropertyChangeListener(listener);
        alpha = 1f; 
        title = new FadingLabel("STEP 3 - ITEMS", TextStyles.TITLE);
        fadingComponents = new ArrayList<FadingComponent>();
        currentTimer = new FadingTimer(5, this, "ITEMS"); 
        centerPanel = ProgramPresets.createPanel();
        centerPanel.setLayout(new BorderLayout()); 
        setBackground(ProgramPresets.COLOR_BACKGROUND);
        setLayout(new BorderLayout());
        add(title, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        init();
    }

    private void init() {
        description = new FadingBorderedPanel("DESCRIPTION");
        centerPanel.add(description, BorderLayout.NORTH);
        setAutoDetectDescription();
        autoDetectPanel = new AutoDetectPanel(logic, this);
        centerPanel.add(autoDetectPanel, BorderLayout.CENTER);
    }
    
    /**
     * Sets the description for auto detection. 
     */
    private void setAutoDetectDescription() {
        description.removeAll();
        FadingLabel line1 = new FadingLabel("AUTO DETECT MODE - SELECT THE IDENTIFIERS OF THE ITEMS YOU\'RE RAFFLING.", TextStyles.LARGE);
        FadingLabel line2 = new FadingLabel("AN IDENTIFIER IS SOMETHING A RAFFLE WOULD BE RAN BY, FOR EXAMPLE \"SIZE\" OR \"SIZE\" AND \"COLOR\"", TextStyles.ITALICS);
        FadingLabel line3 = new FadingLabel("CHECK THE BOXES INDICATING WHICH IDENTIFIERS YOU'RE RAFFLING BY", TextStyles.BOLD);
        FadingLabel line4 = new FadingLabel("COMMON IDENTIFIERS INCLUDE \"SIZE\" AND \"COLOR\".", TextStyles.ITALICS);
        FadingLabel line5 = new FadingLabel("HIT \"CONFIRM IDENTIFIERS\" TO PROCEED TO THE COUNT INPUT.", TextStyles.ITALICS);
        description.setLayout(new GridLayout(0, 1));
        description.add(line1);
        description.add(line2);
        description.add(line3);
        description.add(line4);
        description.add(line5);
        addFadingComponent(line1, line2, line3, line4, line5);
    }
    
    /**
     * Sets the description for manual mode. 
     */
    private void setManualDescription() {
        description.removeAll();
        FadingLabel line1 = new FadingLabel("SELECT THE RAFFLE ITEMS", TextStyles.LARGE);
        FadingLabel line2 = new FadingLabel("MATCH THE RAFFLE PARAMETERS OF THE ENTRIES TO THE ITEMS.", TextStyles.ITALICS);
        FadingLabel line3 = new FadingLabel("E.G. ENTRIES SIZES TO ITEMS SIZES.", TextStyles.ITALICS);
        FadingLabel line4 = new FadingLabel("- NOTE: THIS METHOD IS MUCH MORE PRONE TO ERROR THAN AUTO DETECT", TextStyles.ITALICS);
        FadingLabel line5 = new FadingLabel("SEE THE LINK IN THE ABOUT TAB FOR A FULL DESCRIPTION.", TextStyles.ITALICS);
        description.setLayout(new GridLayout(0, 1));
        description.add(line1);
        description.add(line2);
        description.add(line3);
        description.add(line4);
        description.add(line5);
        addFadingComponent(line1, line2, line3, line4, line5);
    }
    
    /**
     * Sets the current items mode according to the values in the raffle logic of this panel.
     * @param logic the logic to set the current mode according to. 
     */
    public void updateLogic() {
        if (logic.autoDetectEnabled()) {
            centerPanel.removeAll();
            centerPanel.add(description, BorderLayout.NORTH);
            setAutoDetectDescription();
            autoDetectPanel.autoDetect(); //Auto detect the new items.
            centerPanel.add(autoDetectPanel, BorderLayout.CENTER);
        } else {
            setManualDescription();
            if (logic.getItems() != null) { //TODO: Set all parameters for manual choosing. 
                
            }
        }
    }
    
    @Override
    public void setAlpha(float value) {
        alpha = value;
        repaint();
    }

    /**
     * Adds a fading component to the panel. 
     * @param components the components to add to the panel. 
     */
    private void addFadingComponent(FadingComponent ... components) {
        for (FadingComponent c : components) {
            fadingComponents.add(c);
        }
    }
    
    @Override
    public ArrayList<FadingComponent> getFadingComponents() {
        return fadingComponents;
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == currentTimer) {
            
        } else if (evt.getSource() == autoDetectPanel) { //If the event came from the auto detect panel. 
            pcs.firePropertyChange(evt.getPropertyName(), null, null); //TODO: Implement tool tips?
        }
    }
}
=======
package subpanels;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import fadingComponents.FadeCapable;
import fadingComponents.FadingBorderedPanel;
import fadingComponents.FadingButton;
import fadingComponents.FadingCheckBox;
import fadingComponents.FadingComponent;
import fadingComponents.FadingLabel;
import fadingComponents.FadingLabeledSlider;
import fadingComponents.FadingScrollTable;
import fadingComponents.FadingTable;
import fadingComponents.FadingTextField;
import fadingComponents.FadingTimer;
import logic.ProgramPresets;
import logic.RaffleLogic;
import logic.TextStyles;
import main_structure.SpreadSheet;

public class ItemsPanel extends JPanel implements FadeCapable, FadingComponent, PropertyChangeListener {
    
    /** Serial ID. */
    private static final long serialVersionUID = 1755425953974187181L;

    private FadingLabel title; 
    
    private FadingBorderedPanel description;
    
    private AutoDetectPanel autoDetectPanel; 
    
    private PropertyChangeSupport pcs; 
    
    private RaffleLogic logic; 
    
    private float alpha; 
    
    /** Center panel. Used for displaying description and the current item selection mode panel. */
    private JPanel centerPanel; 
   
    private ArrayList<FadingComponent> fadingComponents;
    
    /** The timer contianing the components which are currently in focus. */
    private FadingTimer currentTimer;
    
    public ItemsPanel(RaffleLogic logic, PropertyChangeListener listener) {
        this.logic = logic; 
        pcs = new PropertyChangeSupport(this);
        pcs.addPropertyChangeListener(listener);
        alpha = 1f; 
        title = new FadingLabel("STEP 3 - ITEMS", TextStyles.TITLE);
        fadingComponents = new ArrayList<FadingComponent>();
        currentTimer = new FadingTimer(5, this, "ITEMS"); 
        centerPanel = ProgramPresets.createPanel();
        centerPanel.setLayout(new BorderLayout()); 
        setBackground(ProgramPresets.COLOR_BACKGROUND);
        setLayout(new BorderLayout());
        add(title, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        init();
    }

    private void init() {
        description = new FadingBorderedPanel("DESCRIPTION");
        centerPanel.add(description, BorderLayout.NORTH);
        setAutoDetectDescription();
        autoDetectPanel = new AutoDetectPanel(logic, this);
        centerPanel.add(autoDetectPanel, BorderLayout.CENTER);
    }
    
    /**
     * Sets the description for auto detection. 
     */
    private void setAutoDetectDescription() {
        description.removeAll();
        FadingLabel line1 = new FadingLabel("AUTO DETECT MODE - SELECT THE IDENTIFIERS OF THE ITEMS YOU\'RE RAFFLING.", TextStyles.LARGE);
        FadingLabel line2 = new FadingLabel("AN IDENTIFIER IS SOMETHING A RAFFLE WOULD BE RAN BY, FOR EXAMPLE \"SIZE\" OR \"SIZE\" AND \"COLOR\"", TextStyles.ITALICS);
        FadingLabel line3 = new FadingLabel("CHECK THE BOXES INDICATING WHICH IDENTIFIERS YOU'RE RAFFLING BY", TextStyles.BOLD);
        FadingLabel line4 = new FadingLabel("COMMON IDENTIFIERS INCLUDE \"SIZE\" AND \"COLOR\".", TextStyles.ITALICS);
        FadingLabel line5 = new FadingLabel("HIT \"CONFIRM IDENTIFIERS\" TO PROCEED TO THE COUNT INPUT.", TextStyles.ITALICS);
        description.setLayout(new GridLayout(0, 1));
        description.add(line1);
        description.add(line2);
        description.add(line3);
        description.add(line4);
        description.add(line5);
        addFadingComponent(line1, line2, line3, line4, line5);
    }
    
    /**
     * Sets the description for manual mode. 
     */
    private void setManualDescription() {
        description.removeAll();
        FadingLabel line1 = new FadingLabel("SELECT THE RAFFLE ITEMS", TextStyles.LARGE);
        FadingLabel line2 = new FadingLabel("MATCH THE RAFFLE PARAMETERS OF THE ENTRIES TO THE ITEMS.", TextStyles.ITALICS);
        FadingLabel line3 = new FadingLabel("E.G. ENTRIES SIZES TO ITEMS SIZES.", TextStyles.ITALICS);
        FadingLabel line4 = new FadingLabel("- NOTE: THIS METHOD IS MUCH MORE PRONE TO ERROR THAN AUTO DETECT", TextStyles.ITALICS);
        FadingLabel line5 = new FadingLabel("SEE THE LINK IN THE ABOUT TAB FOR A FULL DESCRIPTION.", TextStyles.ITALICS);
        description.setLayout(new GridLayout(0, 1));
        description.add(line1);
        description.add(line2);
        description.add(line3);
        description.add(line4);
        description.add(line5);
        addFadingComponent(line1, line2, line3, line4, line5);
    }
    
    /**
     * Sets the current items mode according to the values in the raffle logic of this panel.
     * @param logic the logic to set the current mode according to. 
     */
    public void updateLogic() {
        if (logic.autoDetectEnabled()) {
            centerPanel.removeAll();
            centerPanel.add(description, BorderLayout.NORTH);
            setAutoDetectDescription();
            autoDetectPanel.autoDetect(); //Auto detect the new items.
            centerPanel.add(autoDetectPanel, BorderLayout.CENTER);
        } else {
            setManualDescription();
            if (logic.getItems() != null) { //TODO: Set all parameters for manual choosing. 
                
            }
        }
    }
    
    @Override
    public void setAlpha(float value) {
        alpha = value;
        repaint();
    }

    /**
     * Adds a fading component to the panel. 
     * @param components the components to add to the panel. 
     */
    private void addFadingComponent(FadingComponent ... components) {
        for (FadingComponent c : components) {
            fadingComponents.add(c);
        }
    }
    
    @Override
    public ArrayList<FadingComponent> getFadingComponents() {
        return fadingComponents;
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == currentTimer) {
            
        } else if (evt.getSource() == autoDetectPanel) { //If the event came from the auto detect panel. 
            pcs.firePropertyChange(evt.getPropertyName(), null, null); //TODO: Implement tool tips?
        }
    }
}
>>>>>>> parent of f93266f... Small refactor. Preparing for new GUI development.:src/main/java/gui_v2/subpanels/ItemsPanel.java
