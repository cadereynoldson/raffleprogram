package gui_v2.subpanels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.UIManager;

import gui_v2.fadingComponents.FadingBorderedPanel;
import gui_v2.fadingComponents.FadingButton;
import gui_v2.fadingComponents.FadingCheckBox;
import gui_v2.fadingComponents.FadingLabel;
import gui_v2.fadingComponents.FadingLabeledSlider;
import gui_v2.fadingComponents.FadingScrollTable;
import gui_v2.fadingComponents.FadingTable;
import gui_v2.fadingComponents.FadingTimer;
import gui_v2.logic.ProgramPresets;
import main_structure.SpreadSheet;
import gui_v2.mainpanels.MainWindow;

public class TestingFrame extends JFrame {
    
    private JCheckBox fade;
    
    private FadingLabel label;
    
    private FilteringPanel panel;
    
    private FadingCheckBox box;
    
    private FadingButton button; 
    
    private FadingLabeledSlider slider;
    
    private FadingScrollTable table; 
    
    private FadingTimer masterTimer;
    
    public TestingFrame() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1080, 720));
        setLayout(new BorderLayout());
        FadingBorderedPanel p = new FadingBorderedPanel("TESTING");
        masterTimer = new FadingTimer(5, null, "TEST");
//        p.setLayout(new GridLayout(0, 1));
//        slider = new FadingLabeledSlider(0, 100, 20, TextStyles.DEFAULT);
//        slider.setTickSpacing(5, 10);
        table = new FadingScrollTable(new FadingTable(SpreadSheet.readCSV("testFiles/mock_entries.csv")));
        fade = new JCheckBox("Display");
        fade.setSelected(true);
        fade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fade.isSelected()) 
                    masterTimer.fadeIn();
                else
                    masterTimer.fadeOut();
                showError();
            }
        });
       
        
//        masterTimer.addComponent(slider);
//        p.add(slider);
        masterTimer.addComponent(table);
        add(fade, BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
    }
    
    private void showError() {
        ProgramPresets.displayYesNoConfirm("THIS IS A CONFIRM THING! YSES\nNO IDK", "TEST", this);
    }
    
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            UIManager.put("nimbusBase", ProgramPresets.COLOR_TEXT);
            UIManager.put("nimbusFocus", ProgramPresets.COLOR_FOCUS);
            UIManager.put("Slider.tickColor", ProgramPresets.COLOR_TEXT);
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TestingFrame().setVisible(true);
            }
        });
        
    }
}
