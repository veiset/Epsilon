package epsilonclient;

import javax.swing.SwingUtilities;

/**
 * The Client class starts the program
 * 
 * @author mm
 */
public class Client {

    /*
     * It all starts here
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TestWindow();
            }
        });
    }
}
