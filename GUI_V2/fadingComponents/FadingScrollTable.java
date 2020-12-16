package fadingComponents;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JScrollPane;

import logic.ProgramPresets;

public class FadingScrollTable extends JScrollPane implements FadingComponent {
    
    private FadingTable table;
    
    private float alpha;
    
    public FadingScrollTable(FadingTable table) {
        super(table);
        this.table = table;
        
        alpha = 1; 
        table.setFillsViewportHeight(true);
        init();
    }

    public FadingTable getTable() {
        return table;
    }
    
    private void init() {
        setBackground(ProgramPresets.COLOR_BACKGROUND);
    }
    
    @Override
    public void setAlpha(float value) {
        table.setAlpha(value);
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
