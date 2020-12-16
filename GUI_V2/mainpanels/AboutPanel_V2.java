package mainpanels;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import logic.ProgramPresets;

/**
 * Second iteration of the AboutPanel. Narrowed out bugs and no longer uses images for displaying.
 * @author Cade Reynoldson
 * @version 2.0
 */
public class AboutPanel_V2 extends JPanel {
    
    /** Serial ID. */
    private static final long serialVersionUID = 17340983140983L;

    /** Title of the panel. */
    private static final String TITLE = "RAFFLE AUTOMATION";
    
    /** Sub title of the panel. */
    private static final String SUB_TITLE = "ORIGINALLY DEVELOPED FOR 35TH AVE SKATE SHOP";
    
    /** Link to the description. */
    private static final String LINK = "http://docs.google.com/document/d/198a5-hnS8HMOw5veUp64-v-1c8tMRJDCqJXl4N8v0bg/edit?usp=sharing";
    
    /** The label which stores the link. */
    private JLabel linkLabel;
    
    /** The label which stores the main title. */
    private JLabel mainTitle;
    
    /** The sub title of the program. */
    private JLabel subTitle;
    
    /**
     * Creates a new about panel. 
     */
    public AboutPanel_V2() {
        init();
    }
    
    /**
     * Initializes the about panel. 
     */
    private void init() {
        setLayout(new BorderLayout());
        setBackground(ProgramPresets.COLOR_BACKGROUND);
        add(getTitlePanel(), BorderLayout.NORTH);
        add(getTextPanel(), BorderLayout.CENTER);
        add(getLinkPanel(), BorderLayout.SOUTH);
    }
    
    /**
     * Creates the text panel. 
     * @return the text panel. 
     */
    private JPanel getTextPanel() {
        JPanel p = ProgramPresets.createPanel();
        p.setLayout(new GridLayout(0, 1));
        p.add(ProgramPresets.createCenteredLabelBold(
                "THIS PROGRAM WAS CREATED TO HELP AUTOMATE THE PROCESS OF SKATESHOP RAFFLES"));
        p.add(ProgramPresets.createCenteredLabelBold(
                "LET THE GOOGLE SERVERS HANDLE THE STRESS CREATED BY HOARDS OF DUNK FANS/RESELLERS"));
        p.add(ProgramPresets.createCenteredLabelBold(
                "RUN THIS PROGRAM ON YOUR COMPUTER WITH THE DATA FROM YOUR GOOGLE FORMS BASED RAFFLE"));
        p.add(ProgramPresets.createCenteredLabelBold(
                "DO NOT HESITATE TO CONTANCT ME WITH ANY PROBLEMS YOU ENCOUNTER WHILE USING THIS PROGRAM"));
        JPanel contact = ProgramPresets.createTitledPanel("CONTACT INFORMATION");
        contact.setLayout(new GridLayout(0, 1));
        contact.add(ProgramPresets.createCenteredLabelBold("CADE REYNOLDSON"));
        contact.add(ProgramPresets.createCenteredLabelBold("CADER444@GMAIL.COM"));
        contact.add(ProgramPresets.createCenteredLabelBold("INSTAGRAM: @OLLIEHOLE"));
        JPanel subContact = ProgramPresets.createPanel();
        subContact.setLayout(new BorderLayout());
        subContact.add(Box.createRigidArea(new Dimension(300, 0)), BorderLayout.EAST);
        subContact.add(contact);
        subContact.add(Box.createRigidArea(new Dimension(300, 0)), BorderLayout.WEST);
        p.add(subContact);
        p.add(ProgramPresets.createCenteredTitle(
                "SEE THE LINK BELOW FOR A FULL DESCRIPTION ON HOW TO USE THIS PROGRAM"));
        JPanel finalPanel = ProgramPresets.createPanel();
        finalPanel.setLayout(new BorderLayout());
        finalPanel.add(Box.createRigidArea(new Dimension(75, 0)), BorderLayout.WEST);
        finalPanel.add(Box.createRigidArea(new Dimension(75, 0)), BorderLayout.EAST);
        finalPanel.add(p, BorderLayout.CENTER);
        return finalPanel;
    }
    
    /**
     * Creates the link panel. 
     * @return the link panel. 
     */
    private JPanel getLinkPanel() {
        JPanel p = ProgramPresets.createTitledPanel("LINK");
        p.setLayout(new BorderLayout());
        linkLabel = new JLabel(LINK);
        linkLabel.setForeground(ProgramPresets.COLOR_BUTTONS);
        linkLabel.setFont(ProgramPresets.DEFAULT_FONT_ITALICS);
        linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        linkLabel.addMouseListener(new MouseAdapter() {
 
            @Override
            public void mouseClicked(MouseEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI(LINK));
                    } catch (IOException | URISyntaxException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
            }
 
            @Override
            public void mouseExited(MouseEvent e) {
                linkLabel.setText(LINK);
            }
 
            @Override
            public void mouseEntered(MouseEvent e) {
                linkLabel.setText("<html><a href=''>" + LINK + "</a></html>");
                
            }
 
        });
        linkLabel.setHorizontalAlignment(SwingConstants.CENTER);
        linkLabel.setVerticalAlignment(SwingConstants.CENTER);
        p.add(linkLabel, BorderLayout.CENTER);
        JPanel sub = ProgramPresets.createPanel();
        sub.setLayout(new BorderLayout());
        sub.add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.EAST);
        sub.add(p, BorderLayout.CENTER);
        sub.add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.WEST);
        sub.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.SOUTH);
        return sub;
    }
    
    /**
     * Creates the title panel. 
     * @return the title panel. 
     */
    private JPanel getTitlePanel() {
        JPanel main = ProgramPresets.createPanel();
        main.setLayout(new FlowLayout());
        JLabel tron1 = new JLabel(ProgramPresets.TRON);
        JLabel tron2 = new JLabel(ProgramPresets.TRON);
        mainTitle = ProgramPresets.createCenteredTitle(TITLE);
        subTitle = ProgramPresets.createCenteredTitle(SUB_TITLE);
        JPanel mainSub = ProgramPresets.createPanel();
        mainSub.setLayout(new GridLayout(2, 1));
        mainSub.add(mainTitle);
        mainSub.add(subTitle);
        main.add(tron1);
        main.add(Box.createRigidArea(new Dimension(50, 0)));
        main.add(mainSub);
        main.add(Box.createRigidArea(new Dimension(50, 0)));
        main.add(tron2);
        return main;
    }
}
