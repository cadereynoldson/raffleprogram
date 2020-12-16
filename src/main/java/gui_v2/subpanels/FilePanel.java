package gui_v2.subpanels;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import gui_v2.fadingComponents.FadeCapable;
import gui_v2.fadingComponents.FadingBorderedPanel;
import gui_v2.fadingComponents.FadingButton;
import gui_v2.fadingComponents.FadingCheckBox;
import gui_v2.fadingComponents.FadingComponent;
import gui_v2.fadingComponents.FadingLabel;
import gui_v2.fadingComponents.FadingTimer;
import gui_v2.logic.FileOptions;
import gui_v2.logic.ProgramPresets;
import gui_v2.logic.RaffleLogic;
import gui_v2.logic.TextStyles;

/** 
 * Panel for loading in files. 
 * @author Cade Reynoldson 
 * @version 1.2
 */
public class FilePanel extends JPanel implements PropertyChangeListener, FadeCapable, FadingComponent {
    
    /** Serial ID. */
    private static final long serialVersionUID = -8382896739939952541L;

    /** Default file display. */
    private static final String DEFAULT_FILE = "NO FILE LOADED!";
    
    /** The title of the panel. */
    private FadingLabel title;
    
    /** The interval at which this panel's components will fade at. */
    private int fadeInterval;
    
    /** Property change support for notifying the raffle panel. */
    private PropertyChangeSupport pcs;
    
    /** The description panel. */
    private FadingBorderedPanel descriptionPanel;
    
    /** The panel for selecting items to raffle (OPTIONAL) */
    private FadingBorderedPanel itemPanel;
    
    /** The panel for selecting entries for the raffle */
    private FadingBorderedPanel entriesPanel;
    
    /** The panel for selecting the output folder. (OPTIONAL) */
    private FadingBorderedPanel outputPanel;
    
    /** Timer for fading out the items to raffle panel. */
    private FadingTimer itemsTimer;
    
    /** Timer for fading out the output panel. */
    private FadingTimer outputTimer;
   
    /** Fading checkbox for auto detection of items. */
    private FadingCheckBox autoDetect;
    
    /** Fading checkbox for generating output. */
    private FadingCheckBox generateOutput;
    
    /** The output file label. */
    private FadingLabel outputFile;
    
    /** The item file label. */
    private FadingLabel itemFile;
    
    /** The entries file label. */
    private FadingLabel entriesFile;
    
    /** The button for loading the output folder. */
    private FadingButton outputLoad;
    
    /** The button for loading the items file. */
    private FadingButton itemLoad;
    
    /** The button for loading the entries file. */
    private FadingButton entriesLoad;
    
    /** Allows for choosing of files. */
    private JFileChooser fileChooser;
    
    /** An arraylist of every fading component this contains. */
    private ArrayList<FadingComponent> fadingComponents;
    
    /** The alpha value of this panel .*/
    private float alpha = 1f; 
    
    /**
     * Creates a new instance of the file panel. 
     * @param listener
     */
    public FilePanel(PropertyChangeListener listener) {
        pcs = new PropertyChangeSupport(this);
        pcs.addPropertyChangeListener(listener);
        title = new FadingLabel("STEP 1 - LOAD FILES", TextStyles.TITLE);
        fadeInterval = 5;
        fadingComponents = new ArrayList<FadingComponent>();
        itemsTimer = new FadingTimer(fadeInterval, this, "ITEMS");
        outputTimer = new FadingTimer(fadeInterval, this, "OUTPUT");
        fileChooser = getFileChooser();
        descriptionPanel = descriptionPanel();
        itemPanel = itemsPanel();
        entriesPanel = entriesPanel();
        outputPanel = outputPanel();
        init();
    }
    
    @Override
    public ArrayList<FadingComponent> getFadingComponents() {
        return fadingComponents; 
    }
    
    private void init() {
        setBackground(ProgramPresets.COLOR_BACKGROUND);
        setLayout(new BorderLayout());
        add(title, BorderLayout.NORTH);
        JPanel subPanel = ProgramPresets.createPanel();
        subPanel.setLayout(new GridLayout(0, 1));
        subPanel.add(descriptionPanel);
        subPanel.add(entriesPanel);
        FadingBorderedPanel optionalPanel = new FadingBorderedPanel("OPTIONAL SETTINGS");
        optionalPanel.setLayout(new GridLayout(1, 0));
        optionalPanel.add(itemPanel);
        optionalPanel.add(outputPanel);
        subPanel.add(optionalPanel);
        add(subPanel, BorderLayout.CENTER);
        addFadingComponent(descriptionPanel, entriesPanel, itemPanel, outputPanel, title);
    }
    
    private FadingBorderedPanel descriptionPanel() {
        FadingBorderedPanel p = new FadingBorderedPanel("STEP 1 DESCRIPTION");
        TextStyles style = TextStyles.ITALICS;
        FadingLabel line1 = new FadingLabel("LOAD IN THE DATA FOR YOUR RAFFLE.", TextStyles.LARGE);
        FadingLabel line2 = new FadingLabel("UNCHECK \"AUTO DETECT ITEMS\" TO MANUALLY INPUT RAFFLE ITEMS BASED ON YOUR ENTIRES FILE. THIS IS OPTIONAL.", style);
        FadingLabel line3 = new FadingLabel("IF AUTO DETECT ITEMS IS ON, YOU WILL BE PROMPED TO DOUBLE CHECK THE DETECTED ITEMS ON STEP 3.", style);
        FadingLabel line4 = new FadingLabel("THE OUTPUT FOLDER IS THE FOLDER TO OUTPUT GENERATED WINNERS IN THE FORM OF A \".TXT\" FILE TO. THIS IS OPTIONAL, BUT RECOMMENDED.", style);
        FadingLabel line5 = new FadingLabel("THE CURRENTLY SUPPORTED FILE TYPES FOLLOW \".csv\" FORMATTING!", style);
        p.setLayout(new GridLayout(0, 1));
        p.add(line1);
        p.add(line2);
        p.add(line3);
        p.add(line4);
        p.add(line5);
        addFadingComponent(line1, line2, line3, line4, line5);
        return p;
    }
    
    private FadingBorderedPanel entriesPanel() {
        FadingBorderedPanel p = new FadingBorderedPanel("LOAD ENTIRES FILE");
        p.setLayout(new BorderLayout());
        entriesFile = new FadingLabel(DEFAULT_FILE, TextStyles.LARGE);
        entriesLoad = new FadingButton("LOAD ENTRIES", TextStyles.ITALICS);
        entriesLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileChooser(FileOptions.ENTRIES);
            }
        });
        addFadingComponent(entriesFile, entriesLoad);
        p.add(entriesFile, BorderLayout.CENTER);
        p.add(createSpacedPanel(entriesLoad), BorderLayout.SOUTH);
        return p;
    }
    
    private FadingBorderedPanel itemsPanel() {
        itemFile = new FadingLabel(DEFAULT_FILE, TextStyles.LARGE);
        itemLoad = new FadingButton("LOAD ITEMS", TextStyles.ITALICS);
        itemLoad.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                showFileChooser(FileOptions.ITEMS);
            }
            
        });
        FadingBorderedPanel p = new FadingBorderedPanel("ITEMS TO RAFFLE");
        p.setLayout(new GridLayout(0, 1));
        autoDetect = new FadingCheckBox("AUTO DETECT ITEMS", TextStyles.ITALICS);
        autoDetect.setSelected(true);
        autoDetect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logicChange(autoDetect);
                itemsTimer.fadeOut();
            }
        });
        itemsTimer.addComponent(autoDetect);
        addFadingComponent(itemFile, itemLoad, autoDetect);
        p.add(autoDetect);
        return p;
    }
    
    private FadingBorderedPanel outputPanel() {
        FadingBorderedPanel p = new FadingBorderedPanel("SELECT OUTPUT FOLDER");
        generateOutput = new FadingCheckBox("GENERATE OUTPUT", TextStyles.ITALICS);
        outputFile = new FadingLabel(getFileName(new File(System.getProperty("user.dir"))), TextStyles.LARGE);
        outputLoad = new FadingButton("SELECT OUTPUT FOLDER", TextStyles.ITALICS);
        outputLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileChooser(FileOptions.OUTPUT);
            }
        });
        outputTimer.addComponent(generateOutput);
        generateOutput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logicChange(generateOutput);
                outputTimer.fadeOut();
            }            
        });
        p.setLayout(new GridLayout(0, 1));
        p.add(generateOutput);
        addFadingComponent(generateOutput, outputFile, outputLoad);
        return p;
    }
    
    private void addFadingComponent(FadingComponent ... components) {
        for (FadingComponent c : components) {
            fadingComponents.add(c);
        }
    }
    
    /**
     * Converts the directory of a file to it's shortform path. 
     * @param f the file to convert. 
     * @return the converted file path to display.
     */
    public String getFileName(File f) {
        String[] list = f.toString().split(Pattern.quote("\\"));
        if (list.length == 1)
            return "...\\" + list[0];
        else
            return "...\\" + list[list.length - 2] + "\\" + list[list.length - 1];
    }
    
    private void showFileChooser(FileOptions key) {
        if (key == FileOptions.ENTRIES) {
            fileChooser.setDialogTitle("Select Entries File");
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                entriesFile.setText("ENTRIES FILE: " + getFileName(fileChooser.getSelectedFile()));
                pcs.firePropertyChange(RaffleLogic.LOAD_ENTRIES_KEY, null, fileChooser.getSelectedFile());
            }
        } else if (key == FileOptions.ITEMS) {
            
            fileChooser.setDialogTitle("Select Items File");
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                itemFile.setText("ITEMS FILE: " + getFileName(fileChooser.getSelectedFile()));
                pcs.firePropertyChange(RaffleLogic.LOAD_ITEMS_KEY, null, fileChooser.getSelectedFile());
            }
        } else if (key == FileOptions.OUTPUT) {
            fileChooser.setDialogTitle("Select Output Folder");
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                outputFile.setText("OUTPUT FOLDER: " + getFileName(fileChooser.getSelectedFile()));
                pcs.firePropertyChange(RaffleLogic.LOAD_ITEMS_KEY, null, fileChooser.getSelectedFile());
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == itemsTimer) {
            float currentAlpha = (Float) evt.getNewValue();
            if (currentAlpha == 0) { //current item panel has been faded out. 
                itemPanel.removeAll();
                itemsTimer.removeAll();
                if (autoDetect.isSelected()) {
                    itemsTimer.addComponent(autoDetect);
                    itemPanel.add(autoDetect);
                } else {
                    itemsTimer.addComponent(autoDetect, itemFile, itemLoad);
                    itemPanel.add(autoDetect);
                    itemPanel.add(itemFile);
                    itemPanel.add(createSpacedPanel(itemLoad));
                }
                itemsTimer.fadeIn();
            }
        } else if (evt.getSource() == outputTimer) {
            float currentAlpha = (Float) evt.getNewValue();
            if (currentAlpha == 0) { //current output panel has been faded out. 
                outputPanel.removeAll();
                outputTimer.removeAll();
                if (generateOutput.isSelected()) {
                    outputTimer.addComponent(generateOutput, outputFile, outputLoad);
                    outputPanel.add(generateOutput);
                    outputPanel.add(outputFile);
                    outputPanel.add(createSpacedPanel(outputLoad));
                } else {
                    outputTimer.addComponent(generateOutput);
                    outputPanel.add(generateOutput);
                }
                outputTimer.fadeIn();
            }
        }
    }
    
    /**
     * Method called when a checkbox action occurs. 
     * @param box the box to evaluate. 
     */
    private void logicChange(JCheckBox box) {
        boolean change = box.isSelected();
        String key;
        if (box == autoDetect)
            key = RaffleLogic.AUTO_DETECT_KEY;
        else
            key = RaffleLogic.GENERATE_OUTPUT_KEY;
        pcs.firePropertyChange(key, null, change);
    }
    
    private JFileChooser getFileChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setForeground(ProgramPresets.COLOR_BACKGROUND);
        return chooser; 
    }
    
    /**
     * Creates a spaced panel to avoid oversized buttons. 
     * @param button the button to add in the center of a panel. 
     * @return a spaced panel to avoid oversized buttons.
     */
    private JPanel createSpacedPanel(FadingButton button) {
        JPanel p = ProgramPresets.createPanel();
        p.setLayout(new FlowLayout());
        Dimension d = new Dimension(50, 0);
        p.add(Box.createRigidArea(d));
        p.add(button);
        p.add(Box.createRigidArea(d));
        return p;
    }

    @Override
    public void setAlpha(float value) {
        alpha = value;
        repaint();
        
    }
    
    /**
     * Resets the displayed entries file. 
     */
    public void resetEntries() {
        entriesFile.setText(DEFAULT_FILE);
    }
    
    public void resetItems() {
        itemFile.setText(DEFAULT_FILE);
    }
    
    public static String getShortFileName(File f) {
        String[] list = f.toString().split(Pattern.quote("\\"));
        return list[list.length - 1];
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
