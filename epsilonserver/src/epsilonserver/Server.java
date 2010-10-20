
package epsilonserver;

/**
 * TODO: make a GUI for the server
 *
 * @author mm
 */
public class Server {

    private Game game;


    /**
     * Constructor
     * Start a server handler with a specified port
     */
    public Server() {
        game = new Game();
        game.start();
    }
    

    /**
     * Programs main method
     * 
     * @param args
     */
    public static void main(String[] args) {
        Server s = new Server();
    }


}
