/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package epsilonserver;

/**
 *
 * @author mm
 */
public class Server {

    private int port = 6001;
    private ServerHandler sh;


    public Server() {
        sh = new ServerHandler();
        new Thread(sh).start();

    }


}
