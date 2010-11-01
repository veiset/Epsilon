package epsilonserver.game;

import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 *
 * @author mm
 */
public class ServerGUI {

    private ServerGUI sgui = this;
    private JTextArea logBox = new JTextArea();

    /**
     *
     */
    public ServerGUI() {
        setLookAndFeel();
        initComponents();
    }

    /**
     * Initialize GUI components
     */
    private void initComponents() {

    }

    private void makeMenu() {

    }

    /**
     * Set swing look and feel to Nimbus
     */
    public void setLookAndFeel() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
    		}
            }
    	}
        catch(Exception e) {
    		// do stuff
    	}
    }
}
