/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package epsilonclient;

import javax.swing.SwingUtilities;

/**
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
