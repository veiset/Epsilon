package epsilonserver;

/**
 * The server class starts the program
 * TODO: make a GUI for the server
 *
 * @author mm
 */
public class Server {

    private Game game;

    /**
     * Constructor
     * Creates a game object and starts the gameloop
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
