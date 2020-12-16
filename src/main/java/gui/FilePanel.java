/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Allows for the user of the program to load in files. 
 * @author Cade Reynoldson
 */
public class FilePanel extends javax.swing.JPanel {

    /** Serial ID. */
    private static final long serialVersionUID = -7718951308822326163L;

    /** Code for referring to the item values. */
    public static int ITEM_CODE = 0;
    
    /** Code for referring to the entry values. */
    public static int ENTRY_CODE = 1;
    
    /** Code for referring to the output values. */
    public static int OUTPUT_CODE = 2;
    
    /** The defualt text for no file selected. */
    public static String DEFAULT_TEXT = "NO FILE SELECTED!";
    
    /** The item file. */
    private File itemFile;
    
    /** The entry file. */
    private File entryFile;
    
    /** The output file directory. */
    private File outputFile;
    
    /** JTextField allows for user selection of the file outputName. */
    private JTextField outputFileName; 
    
    /** 
     * Property change support for notifying raffle panel. 
     * Property Changes this class sends: 
     * - ITEM: New item file has been selected. 
     * - ENTRY: New entry file has been selected. 
     * NOTE: New value will be in the new value of the property change. 
     */
    private PropertyChangeSupport pcs; 
    
    /**
     * Creates new form of the file panel. Handles selection of all files. 
     */
    public FilePanel(PropertyChangeListener listener) {
        pcs = new PropertyChangeSupport(this);
        pcs.addPropertyChangeListener(listener);
        initComponents();
        setFonts();
        init();
    }
    
    /**
     * Initializes the panel. 
     */
    private void init() {
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new GridLayout(1, 4));
        subPanel.add(itemFilePanel);
        subPanel.add(entryFilePanel);
        subPanel.add(outputFilePanel);
        JPanel textPanel = new JPanel(new BorderLayout());
        outputFileName = new JTextField("ItemName");
        outputFileName.setFont(MasterDisplay.miscFont);
        textPanel.setBackground(Color.WHITE);
        textPanel.setBorder(MasterDisplay.getTitledBorder("OUTPUT FILE NAME"));
        textPanel.add(outputFileName);
        subPanel.setBackground(Color.WHITE);
        subPanel.add(textPanel);
        add(subPanel);
        this.setName("Raffle");
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBackground(Color.WHITE);
        itemFilePanel.setBackground(Color.WHITE);
        entryFilePanel.setBackground(Color.WHITE);
        outputFilePanel.setBackground(Color.WHITE);
    }
    
    /**
     * Returns the output file path to generate text files to. 
     */
    public File getOutputFilePath() {
        return outputFile;
    }
    
    
    /** 
     * Returns the output file name.
     * @return the output file name. 
     */
    public String getOutputFileName() {
        return outputFileName.getText();
    }
    
    /**
     * Resets the values of a specific display according to the value code.
     * - NOTE: Use the value code fields!
     * @param valueCode the value code of the display to set to default. 
     */
    public void resetValues(int valueCode) {
        if (valueCode == ITEM_CODE) {
            itemFileLabel.setText(DEFAULT_TEXT);
            itemFile = null;
        } else if (valueCode == ENTRY_CODE) {
            entryFileLabel.setText(DEFAULT_TEXT);
            entryFile = null;
        } else if (valueCode == OUTPUT_CODE) {
            outputFolderLabel.setText("NO FOLDER SELECTED!");
            outputFile = null;
        }
    }
    
    /** 
     * Sets the fonts of all components. 
     */
    private void setFonts() {
        fileTitle.setFont(MasterDisplay.titleFont);
        entryFileButton.setFont(MasterDisplay.tabAndButtonFont);
        entryFileLabel.setFont(MasterDisplay.tabAndButtonFont);
        itemFileButton.setFont(MasterDisplay.tabAndButtonFont);
        itemFileLabel.setFont(MasterDisplay.tabAndButtonFont);
        outputFolderButton.setFont(MasterDisplay.tabAndButtonFont);
        outputFolderLabel.setFont(MasterDisplay.tabAndButtonFont);
    }
    
    
    
    /**
     * Converts the directory of a file to it's shortform path. 
     * @param f the file to convert. 
     * @return the converted file path to display.
     */
    public String getFileName(File f) {
        String[] list = f.toString().split(Pattern.quote("\\"));
        return "...\\" + list[list.length - 1];
    }
    
    /**
     * Output folder button handler. 
     * @param evt the event. 
     */
    private void outputFolderButtonActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select Output Folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        File workingDirectory = new File(System.getProperty("user.dir"));
        chooser.setCurrentDirectory(workingDirectory);
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            outputFile = chooser.getSelectedFile();
            outputFolderLabel.setText(getFileName(chooser.getSelectedFile()));
        }
    }

    /**
     * The item file button handler. 
     * @param evt the event. 
     */
    private void itemFileButtonActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select Raffle Items File");
        File workingDirectory = new File(System.getProperty("user.dir"));
        chooser.setCurrentDirectory(workingDirectory);
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            itemFile = chooser.getSelectedFile();
            itemFileLabel.setText(getFileName(itemFile));
            pcs.firePropertyChange("ITEM" , null, itemFile);
        }
    }

    /**
     * The entry file button handler. 
     * @param evt the event/
     */
    private void entryFileButtonActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select Entries File");
        File workingDirectory = new File(System.getProperty("user.dir"));
        chooser.setCurrentDirectory(workingDirectory);
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            entryFile = chooser.getSelectedFile();
            entryFileLabel.setText(getFileName(entryFile));
            pcs.firePropertyChange("ENTRY", null, entryFile);
        }
    }
    
    /*********************************************************************
     *                  AUTOMATICALLY GENERATED CODE:                    *
     *********************************************************************/
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        itemFilePanel = new javax.swing.JPanel();
        itemFileLabel = new javax.swing.JLabel();
        itemFileButton = new javax.swing.JButton();
        entryFilePanel = new javax.swing.JPanel();
        entryFileLabel = new javax.swing.JLabel();
        entryFileButton = new javax.swing.JButton();
        outputFilePanel = new javax.swing.JPanel();
        outputFolderLabel = new javax.swing.JLabel();
        outputFolderButton = new javax.swing.JButton();
        fileTitle = new javax.swing.JLabel();

        itemFilePanel.setMinimumSize(new java.awt.Dimension(200, 200));
        itemFilePanel.setLayout(new java.awt.BorderLayout());

        itemFilePanel.setBorder(MasterDisplay.getTitledBorder("ITEMS FILE"));

        itemFileLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        itemFileLabel.setText("NO FILE SELECTED!");
        itemFilePanel.add(itemFileLabel, java.awt.BorderLayout.CENTER);

        itemFileButton.setText("CHOOSE OR CHANGE FILE");
        itemFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemFileButtonActionPerformed(evt);
            }
        });
        itemFilePanel.add(itemFileButton, java.awt.BorderLayout.PAGE_END);

        entryFilePanel.setMinimumSize(new java.awt.Dimension(200, 200));
        entryFilePanel.setLayout(new java.awt.BorderLayout());
        
        entryFilePanel.setBorder(MasterDisplay.getTitledBorder("ENTRIES FILE"));

        entryFileLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        entryFileLabel.setText("NO FILE SELECTED!");
        entryFilePanel.add(entryFileLabel, java.awt.BorderLayout.CENTER);

        entryFileButton.setText("CHOOSE OR CHANGE FILE");
        entryFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entryFileButtonActionPerformed(evt);
            }
        });
        entryFilePanel.add(entryFileButton, java.awt.BorderLayout.PAGE_END);

        outputFilePanel.setMinimumSize(new java.awt.Dimension(200, 200));
        outputFilePanel.setLayout(new java.awt.BorderLayout());

        outputFilePanel.setBorder(MasterDisplay.getTitledBorder("OUTPUT FOLDER"));

        outputFolderLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        outputFolderLabel.setText("NO FOLDER SELECTED!");
        outputFilePanel.add(outputFolderLabel, java.awt.BorderLayout.CENTER);

        outputFolderButton.setText("CHOOSE OR CHANGE FOLDER");
        outputFolderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outputFolderButtonActionPerformed(evt);
            }
        });
        outputFilePanel.add(outputFolderButton, java.awt.BorderLayout.PAGE_END);

        setLayout(new java.awt.BorderLayout());

        fileTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fileTitle.setText("FILES");
        add(fileTitle, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton entryFileButton;
    private javax.swing.JLabel entryFileLabel;
    private javax.swing.JPanel entryFilePanel;
    private javax.swing.JLabel fileTitle;
    private javax.swing.JButton itemFileButton;
    private javax.swing.JLabel itemFileLabel;
    private javax.swing.JPanel itemFilePanel;
    private javax.swing.JPanel outputFilePanel;
    private javax.swing.JButton outputFolderButton;
    private javax.swing.JLabel outputFolderLabel;
    // End of variables declaration//GEN-END:variables
}
