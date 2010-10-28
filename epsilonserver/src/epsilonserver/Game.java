package epsilonserver;

import java.util.Timer;

/**
 * The game class, coordinates the flow of information
 * between the different threads.
 *
 * Modified by mm
 *
 * @author Marius
 */
public class Game {

    // last loop time
    private long lastUpdateTime;

    //Timertask used to update the game
    private GameUpdater u;

    //utility timer used to update the game
    private Timer t;

    private Map map;
    private ServerHandler serverHandler;

    /**
     * Constructor
     */
    public Game() {

        // initialise LoopTimer
        lastUpdateTime = System.currentTimeMillis();
    }

    /**
     * Startup method. Starts the gameupdating
     */
    public void start() {

        // initialising the map
        map = new Map();
        
        serverHandler = new ServerHandler(map);
        serverHandler.startServer();

        // Schedule the GameUpdater Task
        u = new GameUpdater(this);
        t = new Timer();
        t.schedule(u, 0, 16);
        
    }
    
    /**
     * Method used to update the game, mainly used by the updater task
     */
    public void updateGame() {
        
        serverHandler.sendGameState();
        
    }

}
