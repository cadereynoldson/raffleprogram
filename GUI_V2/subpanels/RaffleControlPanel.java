package subpanels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import logic.ProgramPresets;

public class RaffleControlPanel extends JPanel {
    
    /** Serial ID. */
    private static final long serialVersionUID = -3491659941800531484L;
    
    public static final String NEXT_DISABLE_KEY = "DISABLE_NEXT";
    
    public static final String NEXT_ENABLE_KEY = "ENABLE_NEXT";
    
    public static final String PREVIOUS_DISABLE_KEY = "DISABLE_PREVIOUS";
    
    public static final String PREVIOUS_ENABLE_KEY = "ENABLE_NEXT";
    
    /** 
     * Property change support.
     * This panel fires the changes:
     * - "NEXT": Continue to the next raffle page. 
     * - "PREVIOUS": Go back to the previous page. 
     */
    private PropertyChangeSupport pcs;
    
    private JButton nextButton;
    
    private JButton previousButton;
    
    private JPanel progressPanel; 
    
    private ProgressBar progressBar;
    
    private final Timer progressTimer; 
    
    /** The progress ultimately being displayed. */
    private int progress;
    
    /** The current progress being displayed. */
    private int currentProgress;
    
    private JLabel progressLabel; 
    
    private ControlButtonListener buttonListener;
    
    public RaffleControlPanel(PropertyChangeListener listener) {
        pcs = new PropertyChangeSupport(this);
        pcs.addPropertyChangeListener(listener);
        buttonListener = new ControlButtonListener();
        init();
        progressTimer = new Timer(5, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentProgress < progress) {
                    progress--;
                } else if (currentProgress > progress) {
                    progress++;
                } else {
                    progressTimer.stop();
                }
                progressLabel.setText(progress + "%");
                progressBar.updateProgress(progress);
            }
        });
    }
    
    
    
    private void init() {
        setBorder(ProgramPresets.getCenterTitledBorder("CONTROLS"));
        setBackground(ProgramPresets.COLOR_BACKGROUND);
        setLayout(new FlowLayout());
        Dimension buttonSize = new Dimension(200, 30);
        nextButton = ProgramPresets.createButton("NEXT STEP");
        nextButton.setPreferredSize(buttonSize);
        previousButton = ProgramPresets.createButton("PREVIOUS STEP");
        previousButton.setPreferredSize(buttonSize);
        previousButton.setEnabled(false);
        nextButton.setEnabled(false);
        progressBar = new ProgressBar(ProgramPresets.COLOR_FOCUS, ProgramPresets.COLOR_TEXT,
                ProgramPresets.COLOR_BACKGROUND, 500, 20);
        progressBar.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        progressLabel = ProgramPresets.createCenteredLabelBold("0%");
        progressPanel = ProgramPresets.createPanel();
        progressPanel.setBorder(ProgramPresets.getCenterTitledBorder("PROGRESS TOWARDS RAFFLE"));
        progressPanel.setToolTipText("<html><p width=\"200\">" 
                + "AN ESTIMATION OF<br>YOUR PROGRESS TOWARDS<br>RUNNING A RAFFLE.<br>" 
                + "</p></html>");
        progressPanel.setLayout(new GridLayout(0, 1));
        progressPanel.add(progressLabel);
        progressPanel.add(progressBar);
        Dimension spacer = new Dimension(100, 0);
        add(previousButton);
        add(Box.createRigidArea(spacer));
        add(progressPanel);
        add(Box.createRigidArea(spacer));
        add(nextButton);
        previousButton.addActionListener(buttonListener);
        nextButton.addActionListener(buttonListener);
    }
    

    public boolean changeButtonState(String key) {
        if (key.equals(NEXT_DISABLE_KEY)) {
            disableNext();
            return true;
        } else if (key.equals(NEXT_ENABLE_KEY)) {
            enableNext();
            return true;
        } else if (key.equals(PREVIOUS_DISABLE_KEY)) {
            disablePrevious();
            return true;
        } else if (key.equals(PREVIOUS_ENABLE_KEY)) {
            enablePrevious();
            return true;
        } else {
            return false; 
        }
            
    }
    
    public void enablePrevious() {
        previousButton.setEnabled(true);
    }
    
    public void disablePrevious() {
        previousButton.setEnabled(false);
    }
    
    public void enableNext() {
        nextButton.setEnabled(true);
    }
    
    public void disableNext() {
        nextButton.setEnabled(false);
    }
    
    public int getProgress() {
        return progress;
    }
    
    /**
     * Updates the progress of the bar. 
     * @param value the new value to display. 
     */
    public void updateProgress(int value) {
        currentProgress = value;
        progressTimer.restart();
        progressTimer.start();
    }
 
    private class ControlButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == previousButton) {
                previousAction();
            } else if (e.getSource() == nextButton) {
                nextAction();
            }
        }
        
        private void previousAction() {
            pcs.firePropertyChange("PREVIOUS", null, null);
        }
        
        private void nextAction() {
            pcs.firePropertyChange("NEXT", null, null);
        }
    }
}
