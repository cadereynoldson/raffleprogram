<<<<<<< HEAD:GUI_V2/fadingComponents/FadingTable.java
package fadingComponents;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Comparator;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import logic.ProgramPresets;
import main_structure.SpreadSheet;

public class FadingTable extends JTable implements FadingComponent {
    
    private SpreadSheet displayedSheet; 
    
    private TableRowSorter<TableModel> sorter;
    
    private float alpha;
    
    public FadingTable(SpreadSheet s) {
        super(new DefaultTableModel(s.getObjectRepresentation(), s.getColumnNames()));
        this.setFont(ProgramPresets.DEFAULT_FONT);
        this.setAutoCreateRowSorter(true);
        displayedSheet = s;
        alpha = 1f; 
        initSorter();
        this.setRowSorter(sorter);
    }
    
    /**
     * Creates a new fading table with an integer indicating the allignment of the cells. 
     * @param s the new spreadsheet. 
     * @param centerAllign JLabel based constant for cell allignment.  
     */
    public FadingTable(SpreadSheet s, int cellAllignment) {
        this(s);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(cellAllignment);
        for (int i = 0; i < displayedSheet.getNumColumns(); i++)
            getColumnModel().getColumn(i).setCellRenderer(renderer);
    }

    /**
     * Initializes the table sorter for sorting by a row. 
     */
    private void initSorter() {
        sorter = new TableRowSorter<TableModel>(this.getModel());
        Comparator<Integer> intCompare = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };
        Comparator<Float> floatCompare = new Comparator<Float>() {
            @Override
            public int compare(Float o1, Float o2) {
                return Float.compare(o1, o2);
            }
        };
        Comparator<String> stringCompare = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        };
        for (int i = 0; i < displayedSheet.getNumColumns(); i++) {
            Object value = displayedSheet.getColumn(i).get(0).getValue();
            if (value instanceof Integer)
                sorter.setComparator(i, intCompare);
            else if (value instanceof Float)
                sorter.setComparator(i, floatCompare);
            else if (value instanceof String)
                sorter.setComparator(i, stringCompare);
        }
    }
    
    /**
     * Adds a row from the parameterized table at the given index to this table. 
     * @param table the table to add the row from.
     * @param index the index of the row to add to this one.
     */
    public void addRow(FadingTable table, int index) {
        Object[] vals = table.getRowAsArray(index);
        ((DefaultTableModel) getModel()).addRow(vals);
    }
    
    public void removeRow(int index) {
        ((DefaultTableModel) this.getModel()).removeRow(index);
    }
    
    /**
     * Returns a string representation of the row at the index IN THE TABLE MODEL!
     * @param index the string representation
     * @return
     */
    public String getRowAsString(int index) {
        String confirmString = "";
        for (int i = 0; i < getColumnCount(); i++) {
            if (i == getColumnCount() - 1) {
                confirmString += getValueAt(index, i);
            } else {
                confirmString += getValueAt(index, i) ;
            }
        }
        return confirmString;
    }
    
    public Object[] getRowAsArray(int index) {
        Object[] o = new Object[getColumnCount()];
        for (int i = 0; i < getColumnCount(); i++)
            o[i] = getValueAt(index, i);
        return o;
    }
    
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
package fadingComponents;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Comparator;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import logic.ProgramPresets;
import main_structure.SpreadSheet;

public class FadingTable extends JTable implements FadingComponent {
    
    private SpreadSheet displayedSheet; 
    
    private TableRowSorter<TableModel> sorter;
    
    private float alpha;
    
    public FadingTable(SpreadSheet s) {
        super(new DefaultTableModel(s.getObjectRepresentation(), s.getColumnNames()));
        this.setFont(ProgramPresets.DEFAULT_FONT);
        this.setAutoCreateRowSorter(true);
        displayedSheet = s;
        alpha = 1f; 
        initSorter();
        this.setRowSorter(sorter);
    }
    
    /**
     * Creates a new fading table with an integer indicating the allignment of the cells. 
     * @param s the new spreadsheet. 
     * @param centerAllign JLabel based constant for cell allignment.  
     */
    public FadingTable(SpreadSheet s, int cellAllignment) {
        this(s);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(cellAllignment);
        for (int i = 0; i < displayedSheet.getNumColumns(); i++)
            getColumnModel().getColumn(i).setCellRenderer(renderer);
    }

    /**
     * Initializes the table sorter for sorting by a row. 
     */
    private void initSorter() {
        sorter = new TableRowSorter<TableModel>(this.getModel());
        Comparator<Integer> intCompare = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };
        Comparator<Float> floatCompare = new Comparator<Float>() {
            @Override
            public int compare(Float o1, Float o2) {
                return Float.compare(o1, o2);
            }
        };
        Comparator<String> stringCompare = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        };
        for (int i = 0; i < displayedSheet.getNumColumns(); i++) {
            Object value = displayedSheet.getColumn(i).get(0).getValue();
            if (value instanceof Integer)
                sorter.setComparator(i, intCompare);
            else if (value instanceof Float)
                sorter.setComparator(i, floatCompare);
            else if (value instanceof String)
                sorter.setComparator(i, stringCompare);
        }
    }
    
    /**
     * Adds a row from the parameterized table at the given index to this table. 
     * @param table the table to add the row from.
     * @param index the index of the row to add to this one.
     */
    public void addRow(FadingTable table, int index) {
        Object[] vals = table.getRowAsArray(index);
        ((DefaultTableModel) getModel()).addRow(vals);
    }
    
    public void removeRow(int index) {
        ((DefaultTableModel) this.getModel()).removeRow(index);
    }
    
    /**
     * Returns a string representation of the row at the index IN THE TABLE MODEL!
     * @param index the string representation
     * @return
     */
    public String getRowAsString(int index) {
        String confirmString = "";
        for (int i = 0; i < getColumnCount(); i++) {
            if (i == getColumnCount() - 1) {
                confirmString += getValueAt(index, i);
            } else {
                confirmString += getValueAt(index, i) ;
            }
        }
        return confirmString;
    }
    
    public Object[] getRowAsArray(int index) {
        Object[] o = new Object[getColumnCount()];
        for (int i = 0; i < getColumnCount(); i++)
            o[i] = getValueAt(index, i);
        return o;
    }
    
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
>>>>>>> parent of f93266f... Small refactor. Preparing for new GUI development.:src/main/java/gui_v2/fadingComponents/FadingTable.java
