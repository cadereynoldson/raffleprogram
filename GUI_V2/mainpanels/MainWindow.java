/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JTabbedPane;
import javax.swing.Painter;
import javax.swing.UIManager;

import logic.ProgramPresets;
import logic.RaffleLogic;

/**
 *
 * @author Cade
 */
public class MainWindow extends javax.swing.JFrame implements PropertyChangeListener {
    
    private RaffleLogic logic; 
    
    /** The main display item. */
    private JTabbedPane tabs;
    
    /** The panel used for raffling. */
    private RafflePanel_V2 rafflePanel;
    
    /** The panel to display basic information about the program. */
    private AboutPanel_V2 aboutPanel;
    
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        setLookAndFeel();
        logic = new RaffleLogic();
        rafflePanel = new RafflePanel_V2(this, logic);
        aboutPanel = new AboutPanel_V2();
        initComponents();
    }

    /**
     * Initializes the components. 
     */
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1080, 720));
        setLayout(new BorderLayout());
        this.setIconImage(ProgramPresets.RAFFLE_TICKET.getImage());
        this.setTitle("Raffle Program V2");
        this.getContentPane().setBackground(Color.WHITE);
        UIManager.put("TabbedPane.selected", Color.red);
        tabs = new JTabbedPane();
        add(tabs, BorderLayout.CENTER);
        tabs.addTab("RAFFLE", rafflePanel);
        tabs.addTab("ABOUT", aboutPanel);
        pack();
    }
    
    /**
     * Sets the look and feel of the program. 
     */
    private void setLookAndFeel() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    UIManager.put("nimbusBase", ProgramPresets.COLOR_TEXT);
//                    UIManager.put("nimbusFocus", ProgramPresets.COLOR_FOCUS);
//                    UIManager.put("ToolTip[Enabled].backgroundPainter", ProgramPresets.TOOL_TIP_PAINTER);
                    break;
                }
            }
        } catch (Exception e) {

        }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }
}
