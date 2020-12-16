<<<<<<< HEAD:GUI_V2/subpanels/FilteringPanel.java
package subpanels;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import fadingComponents.FadeCapable;
import fadingComponents.FadingBorderedPanel;
import fadingComponents.FadingCheckBox;
import fadingComponents.FadingComponent;
import fadingComponents.FadingLabel;
import logic.ProgramPresets;
import logic.RaffleLogic;
import logic.TextStyles;
import main_structure.SpreadSheet;

public class FilteringPanel extends JPanel implements FadeCapable, FadingComponent {

    /** Serial ID. */
    private static final long serialVersionUID = 4867442415121023126L;
    
    /** Property change support for notifying the raffle panel parent. */
    private PropertyChangeSupport pcs;
    
    /** Panel which holds a description of the panel. */
    private FadingBorderedPanel descriptionPanel; 
    
    /** The panel which contians the options for filtering. */
    private FadingBorderedPanel filterPanel; 
    
    /** The container for holding all of the check boxes. */
    private JPanel checkboxContainer; 
    
    /** A fading label for the title of the program. */
    private FadingLabel title;
    
    /** The current alpha of this panel. */
    private float alpha = 1f;
    
    /** The original count of entries in this panel. */
    private int originalCount; 
    
    /** The ultimate current removed value to display. */
    private int currentRemoved;
    
    /** Placeholder value for changing the displayed current removed value. */
    private int removed;
    
    /** Timer for graphical updates of the values being displayed. */
    private Timer updateTimer;
    
    /** The original count label. */
    private FadingLabel originalCountLabel; 
    
    /** The current count label. */
    private FadingLabel currentCountLabel;
    
    /** The number filtered label. */
    private FadingLabel numberFilteredLabel;
    
    /** TODO: Checkbox which when selected will kick anyone who submitted anyone who entered more than once. */
    private FadingCheckBox kickMultipleEntries; 
    
    /** List of all of the filter options created. */
    private ArrayList<FadingCheckBox> filters;
    
    /** List of all of the components of this panel which are capable of fading. */
    private ArrayList<FadingComponent> fadingComponents;
    
    /**
     * Instantiates a new instance of the filtering panel. 
     * @param listener listener to send property changes to. 
     */
    public FilteringPanel(PropertyChangeListener listener) {
        pcs = new PropertyChangeSupport(this);
        pcs.addPropertyChangeListener(listener);
        fadingComponents = new ArrayList<FadingComponent>();
        filters = new ArrayList<FadingCheckBox>();
        title = new FadingLabel("STEP 2 - FILTERING", TextStyles.TITLE);
        setBackground(ProgramPresets.COLOR_BACKGROUND);
        setLayout(new BorderLayout());
        add(title, BorderLayout.NORTH);
        fadingComponents.add(title);
        updateTimer = new Timer(2, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentRemoved < removed) {
                    removed--;
                } else if (currentRemoved > removed) {
                    removed++;
                } else {
                    updateTimer.stop();
                }
                currentCountLabel.setText(Integer.toString(originalCount - removed));
                numberFilteredLabel.setText(Integer.toString(removed));
            }
        });
        init();
    }
    
    /**
     * Initializes main panels. 
     */
    private void init() {
        descriptionPanel = initDescription();
        filterPanel = initFilteringPanel(); 
        checkboxContainer = ProgramPresets.createPanel();
        JPanel subPanel = ProgramPresets.createPanel();
        subPanel.setLayout(new BorderLayout());
        subPanel.add(descriptionPanel, BorderLayout.NORTH);
        subPanel.add(filterPanel, BorderLayout.CENTER);
        add(subPanel, BorderLayout.CENTER);
        addFadingComponent(descriptionPanel, filterPanel);
    }
    
    /**
     * Initializes a description panel. 
     * @return the description panel. 
     */
    private FadingBorderedPanel initDescription() {
        FadingBorderedPanel p = new FadingBorderedPanel("STEP 2 DESCRIPTION");
        TextStyles style = TextStyles.ITALICS;
        FadingLabel line1 = new FadingLabel("FILTER THE ENTRIES OF YOUR RAFFLE.", TextStyles.LARGE);
        FadingLabel line2 = new FadingLabel("THIS STEP ALLOWS FOR THE REMOVAL OF DUPLICATE VALUES IN A COLUMN.", style);
        FadingLabel line3 = new FadingLabel("THIS CREATES FOR AN EQUAL OPPORTUNITY FOR EACH RAFFLE ENTRY TO WIN.", style);
        FadingLabel line4 = new FadingLabel("OR - YOU CAN CHOOSE TO KICK OUT PEOPLE WHO PUT IN MULTIPLE ENTRIES ALL TOGETHER.", style);
        FadingLabel line5 = new FadingLabel("SEE THE LINK IN THE ABOUT TAB FOR RECOMMENDED FILTERING COLUMNS.", style);
        p.setLayout(new GridLayout(0, 1));
        p.add(line1);
        p.add(line2);
        p.add(line3);
        p.add(line4);
        p.add(line5);
        addFadingComponent(line1, line2, line3, line4, line5);
        return p;
    }
    
    /**
     * Initializes the filtering panel. 
     * @return
     */
    private FadingBorderedPanel initFilteringPanel() {
        FadingBorderedPanel p = new FadingBorderedPanel("FILTERING");
        FadingLabel description = new FadingLabel("CHECK THE BOXES TO ELIMINATE DUPLICATES OF THAT COLUMN.", TextStyles.BOLD);
        JPanel info = initFilteringInfo();
        p.setLayout(new BorderLayout());
        p.add(description, BorderLayout.NORTH);
        p.add(info, BorderLayout.SOUTH);
        addFadingComponent(description);
        return p;
    }
    
    /**
     * Initializes the panel for filtering info. 
     * @return the panel for filtering info. 
     */
    public JPanel initFilteringInfo() {
        originalCountLabel = new FadingLabel("0", TextStyles.BOLD);
        currentCountLabel = new FadingLabel("0", TextStyles.BOLD);
        numberFilteredLabel = new FadingLabel("0", TextStyles.BOLD);
        Color c = ProgramPresets.COLOR_FOCUS.brighter();
        originalCountLabel.setForeground(c);
        currentCountLabel.setForeground(c);
        numberFilteredLabel.setForeground(c);
        kickMultipleEntries = new FadingCheckBox("REMOVE ANYONE WHO ENTERS MORE THAN ONCE", TextStyles.BOLD);
        JPanel master = ProgramPresets.createPanel();
        master.setLayout(new GridLayout(0, 1));
        JPanel p = ProgramPresets.createPanel();
        p.setLayout(new GridLayout(1, 0));
        FadingBorderedPanel originalCountPanel = new FadingBorderedPanel("TOTAL ORIGINAL ENTRIES");
        originalCountPanel.setLayout(new BorderLayout());
        originalCountPanel.add(originalCountLabel, BorderLayout.CENTER);
        FadingBorderedPanel currentCountPanel = new FadingBorderedPanel("CURRENT ENTRIES");
        currentCountPanel.setLayout(new BorderLayout());
        currentCountPanel.add(currentCountLabel, BorderLayout.CENTER);
        FadingBorderedPanel numberFilteredPanel = new FadingBorderedPanel("DUPLICATE ENTRIES REMOVED");
        numberFilteredPanel.setLayout(new BorderLayout());
        numberFilteredPanel.add(numberFilteredLabel, BorderLayout.CENTER);
        FadingBorderedPanel filteringOptionsPanel = new FadingBorderedPanel("FILTERING OPTIONS");
        filteringOptionsPanel.setLayout(new BorderLayout());
        filteringOptionsPanel.add(kickMultipleEntries, BorderLayout.CENTER);
        p.add(originalCountPanel);
        p.add(currentCountPanel);
        p.add(numberFilteredPanel);
        master.add(filteringOptionsPanel);
        master.add(p);
        addFadingComponent(originalCountLabel, currentCountLabel, 
                numberFilteredLabel, originalCountPanel, currentCountPanel, numberFilteredPanel, filteringOptionsPanel);
        return master;
    }
    
    /**
     * Sets the filters of the spreadsheet being displayed. 
     * @param s the spreadsheet to display. 
     */
    public void setFilters(SpreadSheet s) {
        String[] columnNames = s.getColumnNames();
        filters.clear();
        checkboxContainer.removeAll();
        checkboxContainer.setLayout(new GridLayout(columnNames.length, 1));
        for (int i = 0; i < columnNames.length; i++) {
            FadingCheckBox current = new FadingCheckBox(columnNames[i], TextStyles.BOLD);
            current.setHorizontalAlignment(SwingConstants.CENTER);
            checkboxContainer.add(current);
            filters.add(current);
            current.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        pcs.firePropertyChange(RaffleLogic.FILTER_KEY, null, ((FadingCheckBox) e.getSource()).getText());
                    } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                        pcs.firePropertyChange(RaffleLogic.FILTER_KEY, null, getSelectedBoxes());
                    }
                }
            });
        }
        originalCountLabel.setText(Integer.toString(s.getNumRows()));
        currentCountLabel.setText(Integer.toString(s.getNumRows()));
        numberFilteredLabel.setText(Integer.toString(0));
        originalCount = s.getNumRows();
        removed = 0;
        currentRemoved = 0;
        filterPanel.add(checkboxContainer, BorderLayout.CENTER);
    }
    
    /**
     * Sets the new removed value. 
     * @param value the new removed value. 
     */
    public void setRemoved(int value) {
        updateTimer.restart();
        currentRemoved = value;
        updateTimer.start();
    }
    
    /**
     * Returns an array list of values which are selected for filtering. 
     * @return an array list of values selected for filtering. 
     */
    public ArrayList<String> getSelectedBoxes() {
        ArrayList<String> selected = new ArrayList<String>();
        for (FadingCheckBox b : filters)
            if (b.isSelected())
                selected.add(b.getText());
        return selected; 
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
     * Sets the alpha value of this panel. 
     * @param value
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
=======
package subpanels;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import fadingComponents.FadeCapable;
import fadingComponents.FadingBorderedPanel;
import fadingComponents.FadingCheckBox;
import fadingComponents.FadingComponent;
import fadingComponents.FadingLabel;
import logic.ProgramPresets;
import logic.RaffleLogic;
import logic.TextStyles;
import main_structure.SpreadSheet;

public class FilteringPanel extends JPanel implements FadeCapable, FadingComponent {

    /** Serial ID. */
    private static final long serialVersionUID = 4867442415121023126L;
    
    /** Property change support for notifying the raffle panel parent. */
    private PropertyChangeSupport pcs;
    
    /** Panel which holds a description of the panel. */
    private FadingBorderedPanel descriptionPanel; 
    
    /** The panel which contians the options for filtering. */
    private FadingBorderedPanel filterPanel; 
    
    /** The container for holding all of the check boxes. */
    private JPanel checkboxContainer; 
    
    /** A fading label for the title of the program. */
    private FadingLabel title;
    
    /** The current alpha of this panel. */
    private float alpha = 1f;
    
    /** The original count of entries in this panel. */
    private int originalCount; 
    
    /** The ultimate current removed value to display. */
    private int currentRemoved;
    
    /** Placeholder value for changing the displayed current removed value. */
    private int removed;
    
    /** Timer for graphical updates of the values being displayed. */
    private Timer updateTimer;
    
    /** The original count label. */
    private FadingLabel originalCountLabel; 
    
    /** The current count label. */
    private FadingLabel currentCountLabel;
    
    /** The number filtered label. */
    private FadingLabel numberFilteredLabel;
    
    /** TODO: Checkbox which when selected will kick anyone who submitted anyone who entered more than once. */
    private FadingCheckBox kickMultipleEntries; 
    
    /** List of all of the filter options created. */
    private ArrayList<FadingCheckBox> filters;
    
    /** List of all of the components of this panel which are capable of fading. */
    private ArrayList<FadingComponent> fadingComponents;
    
    /**
     * Instantiates a new instance of the filtering panel. 
     * @param listener listener to send property changes to. 
     */
    public FilteringPanel(PropertyChangeListener listener) {
        pcs = new PropertyChangeSupport(this);
        pcs.addPropertyChangeListener(listener);
        fadingComponents = new ArrayList<FadingComponent>();
        filters = new ArrayList<FadingCheckBox>();
        title = new FadingLabel("STEP 2 - FILTERING", TextStyles.TITLE);
        setBackground(ProgramPresets.COLOR_BACKGROUND);
        setLayout(new BorderLayout());
        add(title, BorderLayout.NORTH);
        fadingComponents.add(title);
        updateTimer = new Timer(2, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentRemoved < removed) {
                    removed--;
                } else if (currentRemoved > removed) {
                    removed++;
                } else {
                    updateTimer.stop();
                }
                currentCountLabel.setText(Integer.toString(originalCount - removed));
                numberFilteredLabel.setText(Integer.toString(removed));
            }
        });
        init();
    }
    
    /**
     * Initializes main panels. 
     */
    private void init() {
        descriptionPanel = initDescription();
        filterPanel = initFilteringPanel(); 
        checkboxContainer = ProgramPresets.createPanel();
        JPanel subPanel = ProgramPresets.createPanel();
        subPanel.setLayout(new BorderLayout());
        subPanel.add(descriptionPanel, BorderLayout.NORTH);
        subPanel.add(filterPanel, BorderLayout.CENTER);
        add(subPanel, BorderLayout.CENTER);
        addFadingComponent(descriptionPanel, filterPanel);
    }
    
    /**
     * Initializes a description panel. 
     * @return the description panel. 
     */
    private FadingBorderedPanel initDescription() {
        FadingBorderedPanel p = new FadingBorderedPanel("STEP 2 DESCRIPTION");
        TextStyles style = TextStyles.ITALICS;
        FadingLabel line1 = new FadingLabel("FILTER THE ENTRIES OF YOUR RAFFLE.", TextStyles.LARGE);
        FadingLabel line2 = new FadingLabel("THIS STEP ALLOWS FOR THE REMOVAL OF DUPLICATE VALUES IN A COLUMN.", style);
        FadingLabel line3 = new FadingLabel("THIS CREATES FOR AN EQUAL OPPORTUNITY FOR EACH RAFFLE ENTRY TO WIN.", style);
        FadingLabel line4 = new FadingLabel("OR - YOU CAN CHOOSE TO KICK OUT PEOPLE WHO PUT IN MULTIPLE ENTRIES ALL TOGETHER.", style);
        FadingLabel line5 = new FadingLabel("SEE THE LINK IN THE ABOUT TAB FOR RECOMMENDED FILTERING COLUMNS.", style);
        p.setLayout(new GridLayout(0, 1));
        p.add(line1);
        p.add(line2);
        p.add(line3);
        p.add(line4);
        p.add(line5);
        addFadingComponent(line1, line2, line3, line4, line5);
        return p;
    }
    
    /**
     * Initializes the filtering panel. 
     * @return
     */
    private FadingBorderedPanel initFilteringPanel() {
        FadingBorderedPanel p = new FadingBorderedPanel("FILTERING");
        FadingLabel description = new FadingLabel("CHECK THE BOXES TO ELIMINATE DUPLICATES OF THAT COLUMN.", TextStyles.BOLD);
        JPanel info = initFilteringInfo();
        p.setLayout(new BorderLayout());
        p.add(description, BorderLayout.NORTH);
        p.add(info, BorderLayout.SOUTH);
        addFadingComponent(description);
        return p;
    }
    
    /**
     * Initializes the panel for filtering info. 
     * @return the panel for filtering info. 
     */
    public JPanel initFilteringInfo() {
        originalCountLabel = new FadingLabel("0", TextStyles.BOLD);
        currentCountLabel = new FadingLabel("0", TextStyles.BOLD);
        numberFilteredLabel = new FadingLabel("0", TextStyles.BOLD);
        Color c = ProgramPresets.COLOR_FOCUS.brighter();
        originalCountLabel.setForeground(c);
        currentCountLabel.setForeground(c);
        numberFilteredLabel.setForeground(c);
        kickMultipleEntries = new FadingCheckBox("REMOVE ANYONE WHO ENTERS MORE THAN ONCE", TextStyles.BOLD);
        JPanel master = ProgramPresets.createPanel();
        master.setLayout(new GridLayout(0, 1));
        JPanel p = ProgramPresets.createPanel();
        p.setLayout(new GridLayout(1, 0));
        FadingBorderedPanel originalCountPanel = new FadingBorderedPanel("TOTAL ORIGINAL ENTRIES");
        originalCountPanel.setLayout(new BorderLayout());
        originalCountPanel.add(originalCountLabel, BorderLayout.CENTER);
        FadingBorderedPanel currentCountPanel = new FadingBorderedPanel("CURRENT ENTRIES");
        currentCountPanel.setLayout(new BorderLayout());
        currentCountPanel.add(currentCountLabel, BorderLayout.CENTER);
        FadingBorderedPanel numberFilteredPanel = new FadingBorderedPanel("DUPLICATE ENTRIES REMOVED");
        numberFilteredPanel.setLayout(new BorderLayout());
        numberFilteredPanel.add(numberFilteredLabel, BorderLayout.CENTER);
        FadingBorderedPanel filteringOptionsPanel = new FadingBorderedPanel("FILTERING OPTIONS");
        filteringOptionsPanel.setLayout(new BorderLayout());
        filteringOptionsPanel.add(kickMultipleEntries, BorderLayout.CENTER);
        p.add(originalCountPanel);
        p.add(currentCountPanel);
        p.add(numberFilteredPanel);
        master.add(filteringOptionsPanel);
        master.add(p);
        addFadingComponent(originalCountLabel, currentCountLabel, 
                numberFilteredLabel, originalCountPanel, currentCountPanel, numberFilteredPanel, filteringOptionsPanel);
        return master;
    }
    
    /**
     * Sets the filters of the spreadsheet being displayed. 
     * @param s the spreadsheet to display. 
     */
    public void setFilters(SpreadSheet s) {
        String[] columnNames = s.getColumnNames();
        filters.clear();
        checkboxContainer.removeAll();
        checkboxContainer.setLayout(new GridLayout(columnNames.length, 1));
        for (int i = 0; i < columnNames.length; i++) {
            FadingCheckBox current = new FadingCheckBox(columnNames[i], TextStyles.BOLD);
            current.setHorizontalAlignment(SwingConstants.CENTER);
            checkboxContainer.add(current);
            filters.add(current);
            current.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        pcs.firePropertyChange(RaffleLogic.FILTER_KEY, null, ((FadingCheckBox) e.getSource()).getText());
                    } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                        pcs.firePropertyChange(RaffleLogic.FILTER_KEY, null, getSelectedBoxes());
                    }
                }
            });
        }
        originalCountLabel.setText(Integer.toString(s.getNumRows()));
        currentCountLabel.setText(Integer.toString(s.getNumRows()));
        numberFilteredLabel.setText(Integer.toString(0));
        originalCount = s.getNumRows();
        removed = 0;
        currentRemoved = 0;
        filterPanel.add(checkboxContainer, BorderLayout.CENTER);
    }
    
    /**
     * Sets the new removed value. 
     * @param value the new removed value. 
     */
    public void setRemoved(int value) {
        updateTimer.restart();
        currentRemoved = value;
        updateTimer.start();
    }
    
    /**
     * Returns an array list of values which are selected for filtering. 
     * @return an array list of values selected for filtering. 
     */
    public ArrayList<String> getSelectedBoxes() {
        ArrayList<String> selected = new ArrayList<String>();
        for (FadingCheckBox b : filters)
            if (b.isSelected())
                selected.add(b.getText());
        return selected; 
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
     * Sets the alpha value of this panel. 
     * @param value
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
>>>>>>> parent of f93266f... Small refactor. Preparing for new GUI development.:src/main/java/gui_v2/subpanels/FilteringPanel.java
