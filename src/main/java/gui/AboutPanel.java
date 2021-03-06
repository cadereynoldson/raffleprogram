/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Toolkit;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
/**
 * Displays information about the program. 
 * @author Cade Reynoldson. 
 */
public class AboutPanel extends javax.swing.JPanel {

    /** Serial ID. */
    private static final long serialVersionUID = -2611719123705888370L;
    
    /** Link to description about the program. */
    public static final String aboutLink = "http://docs.google.com/document/d/198a5-hnS8HMOw5veUp64-v-1c8tMRJDCqJXl4N8v0bg/edit?usp=sharing";
    
    /**
     * Creates new form AboutPanel
     */
    public AboutPanel() {
        initComponents();
        initializeLink();
    }

    /**
     * Initializes the link. 
     */
    private void initializeLink() {
        linkLabel.setText(aboutLink);
        linkLabel.setForeground(Color.BLUE.darker());
        linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        linkLabel.addMouseListener(new MouseAdapter() {
 
            @Override
            public void mouseClicked(MouseEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI(aboutLink));
                    } catch (IOException | URISyntaxException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
            }
 
            @Override
            public void mouseExited(MouseEvent e) {
                linkLabel.setText(aboutLink);
            }
 
            @Override
            public void mouseEntered(MouseEvent e) {
                linkLabel.setText("<html><a href=''>" + aboutLink + "</a></html>");
                
            }
 
        });
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

        title = new javax.swing.JLabel();
        description = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        linkLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(0, 204, 204));
        setFont(MasterDisplay.miscFont);
        setMaximumSize(new java.awt.Dimension(128000, 128000));
        setMinimumSize(new java.awt.Dimension(641, 481));
        setName("About"); // NOI18N
        setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLayout(new java.awt.BorderLayout());
        title.setForeground(new java.awt.Color(153, 153, 153));
        try {
            title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            URL url = getClass().getResource("/UPDATED_ABOUT_TITLEV2_00000.png");
            title.setIcon(new javax.swing.ImageIcon(url)); // NOI18N
            add(title, java.awt.BorderLayout.PAGE_START);
            description.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            description.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FINAL_DESCRIPTION_00000.png"))); // NOI18N
            add(description, java.awt.BorderLayout.CENTER);
        } catch (Exception e) {
            remove(title);
            remove(description);
            title = new javax.swing.JLabel();
            description = new javax.swing.JLabel();
            title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            title.setText("ABOUT FAILED TO LOAD");
            title.setFont(MasterDisplay.titleFont);
            description.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            description.setText("SEE THE LINK BELOW FOR A FULL DESCRIPTION ON HOW TO USE THIS PROGRAM!");
            description.setFont(MasterDisplay.tabAndButtonFont);
            add(title, java.awt.BorderLayout.NORTH);
            add(description, java.awt.BorderLayout.CENTER);
        }
        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridLayout(3, 0));

        linkLabel.setBackground(new java.awt.Color(204, 204, 204));
        linkLabel.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        linkLabel.setForeground(new java.awt.Color(0, 0, 0));
        linkLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        linkLabel.setText("uninitialized");
        jPanel1.add(linkLabel);

        add(jPanel1, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel description;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel linkLabel;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
