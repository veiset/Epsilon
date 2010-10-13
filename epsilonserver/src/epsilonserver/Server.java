
package epsilonserver;

import java.io.IOException;

/**
 *
 * Simple server that receives object state data and sends it to connected clients.
 * Will expand to checking the validity of the received object state data.
 *
 * @author mm
 */
public class Server {

    private int port = 6001;
    private ServerHandler sh;


    /**
     *
     * Constructor
     *
     * Start a server handler with a specified port
     *
     */
    public Server() {

        try {
            sh = new ServerHandler(port);
        }
        catch(IOException ioe) {
            System.out.println("Datagramsocket could not be created");
            System.out.println(ioe.getMessage());
        }

    }


    /**
     *
     * Start serverhandler thread
     *
     */
    public void startServer() {
        new Thread(sh).start();
    }
    

    /**
     *
     * Programs main method
     *
     * @param args
     */
    public static void main(String[] args) {
        Server s = new Server();
        s.startServer();
    }


}
