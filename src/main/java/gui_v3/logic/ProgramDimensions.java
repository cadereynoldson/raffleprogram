package gui_v3.logic;

import javax.tools.Tool;
import java.awt.*;

/**
 * Class for storing various dimensions of panels!
 */
public class ProgramDimensions {

    /** The dimension of the user's screen size */
    public static final Dimension USER_SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    public static final Dimension DEFAULT_SCREEN_SIZE = new Dimension((int) (USER_SCREEN_SIZE.width * 0.8), (int) (USER_SCREEN_SIZE.height * 0.8));

    public static final GridBagConstraints DESCRIPTION_CONSTRAINTS = getDescriptionConstraints();

    public static final GridBagConstraints TOOLBAR_CONSTRAINTS = getToolbarConstraints();

    private static GridBagConstraints getDescriptionConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 2;
        c.insets = new Insets(4, 2, 4, 2);
        c.weightx = 0.1;
        c.weighty = 0.5;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        return c;
    }

    private static GridBagConstraints getToolbarConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 3;
        c.insets = new Insets(0, 0,0, 2);
        c.weightx = 0.1;
        c.weighty = 1;
        c.ipadx = 50;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.VERTICAL;
        return c;
    }

}
