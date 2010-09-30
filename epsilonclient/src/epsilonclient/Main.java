

package epsilonclient;

import javax.swing.SwingUtilities;


/**
 *
 * @author mm
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {


        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TestWindow();
            }
        });

    }

}
