package gui_v3.BaseComponents;

import gui_v3.logic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class VerticalTabsAndProgressCircle extends JPanel implements MouseListener {

    private TabItem homeTab;

    private TabItem loadEntries;

    private TabItem loadItems;

    private TabItem removeDuplicates;

    private TabItem raffleWinners;

    private TabItem selectedTab;

    private PropertyChangeSupport listener;

    public VerticalTabsAndProgressCircle(PropertyChangeListener listener) {
        super();
        this.listener = new PropertyChangeSupport(this);
        this.listener.addPropertyChangeListener(listener);
        setBackground(ProgramColors.TEXT_ON_FG_COLOR);
        homeTab = new TabItem(ProgramStrings.TAB_HOME, NavigationLocations.HOME, this);
        loadEntries = new TabItem(ProgramStrings.TAB_LOAD_ENTRIES, NavigationLocations.ENTRIES, this);
        loadItems = new TabItem(ProgramStrings.TAB_LOAD_ITEMS, NavigationLocations.ITEMS, this);
        removeDuplicates = new TabItem(ProgramStrings.TAB_REMOVE_DUPLICATES, NavigationLocations.FILTER, this);
        raffleWinners = new TabItem(ProgramStrings.TAB_RAFFLE_WINNERS, NavigationLocations.WINNERS, this);
        selectedTab = homeTab;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(0, 1));
        homeTab.setSelected(true);
        add(homeTab);
        add(loadEntries);
        add(loadItems);
        add(removeDuplicates);
        add(raffleWinners);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof TabItem) {
            TabItem source = (TabItem) e.getSource();
            if (source != selectedTab) {
                selectedTab.setSelected(false);
                selectedTab = source;
                selectedTab.setSelected(true);
                listener.firePropertyChange("NAV", null, selectedTab.navKey);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        return;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        return;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        return;
    }

    /**
     * Tab item class. Serves as a clickable button for navigating between different panels.
     */
    public class TabItem extends JPanel {

        /** Indicates whether this tab is selected or not. */
        public boolean selected;

        /** The title (displayed text) in the form of a JLabel. */
        public JLabel title;

        /** The navigation key of this tab item. */
        public NavigationLocations navKey;

        /**
         * Creates a new instance of this tab panel.
         * @param title the title (display text) of the tab.
         * @param navKey the navigation key of the tab (See NavigationLocations enum)
         */
        public TabItem(String title, NavigationLocations navKey, MouseListener listener) {
            super();
            this.title = ProgramDefaults.getCenteredTabLabel(title);
            this.navKey = navKey;
            addMouseListener(listener);
            selected = false;
            initTabItem();
        }

        /**
         * Initializes the tab item.
         */
        private void initTabItem() {
            this.setLayout(new BorderLayout());
            this.setBackground(ProgramColors.TAB_COLOR);
            this.add(title, BorderLayout.CENTER);
        }

        /**
         * Changes whether this tab has been selected or not.
         * @param val the value to set selected.
         */
        public void setSelected(boolean val) {
            boolean holder = selected;
            selected = val;
            if (holder != selected) { //New value set. Update display.
                if (selected) {
                    setBackground(ProgramColors.BACKGROUND_COLOR);
                    title.setFont(ProgramFonts.DEFAULT_FONT_ITALICS);
                } else {
                    setBackground(ProgramColors.TAB_COLOR);
                    title.setFont(ProgramFonts.DEFAULT_FONT);
                }
            }
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension panelSize = getSize();
            int highlightWidth = (int) (panelSize.width * 0.10); // Width of the highlight is 10% the size of the tab.
            if (selected) { //If this tab is selected paint rectangle on left side.
                //Fill Highlight
                g.setColor(ProgramColors.FOCUS_COLOR);
                g.fillRect(0, 0, highlightWidth, panelSize.height);
                g.setColor(ProgramColors.TEXT_ON_FG_COLOR);
                g.drawRect(0, 0, highlightWidth, panelSize.height);
                //Draw bottom line border!
                g.drawLine(0, 0, panelSize.width, 0);
            } else { //Otherwise, paint it on the right side.
                g.setColor(ProgramColors.FOCUS_COLOR);
                g.fillRect(panelSize.width - highlightWidth - 1, 0, highlightWidth, panelSize.height);
                g.setColor(ProgramColors.TEXT_ON_FG_COLOR);
                g.drawRect(panelSize.width - highlightWidth - 1, 0, highlightWidth, panelSize.height);
                g.setColor(ProgramColors.TEXT_ON_FG_COLOR);
                g.drawRect(0, 0, panelSize.width - 1, panelSize.height);
            }
        }
    }
}
