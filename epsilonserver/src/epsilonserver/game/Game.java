package epsilonserver.game;

import epsilonserver.entity.EntityHandler;
import epsilonserver.net.NetworkHandler;
import java.util.Timer;

/**
 * The game class, coordinates the flow of information
 * between the different threads.
 *
 * Modified by Magnus Mikalsen
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

    private EntityHandler eHandler;
    private NetworkHandler netHandler;

    private static Game game = new Game();

    /**
     * Constructor
     */
    private Game() {

        // initialise LoopTimer
        lastUpdateTime = System.currentTimeMillis();
    }
    
   /**
     * Returns the single object of the game class
     *
     * @return game The single instance of this class
     */
    public static Game get() {
        return game;
    }

    /**
     * Startup method. Starts the gameupdating
     */
    public void start() {

        eHandler = EntityHandler.getInstance();
        netHandler = NetworkHandler.getInstance();
        netHandler.startServer();

        // Schedule the GameUpdater Task
        u = new GameUpdater(this);
        t = new Timer();
        t.schedule(u, 0, 16);

    }

    /**
     * Stop the server and clear all players.
     */
    public void stop() {
        netHandler.stopServer();
        eHandler.clearPlayers();
        u.stopGameUpdater();
    }

    /**
     * Method used to update the game, mainly used by the updater task.
     * Sets a new lastUpdateTime, checks for timedout players and updates
     * registered players.
     */
    public void updateGame() {

        // Get system time
        lastUpdateTime = System.currentTimeMillis();

        // Remove timed out players
        eHandler.checkTimeout(lastUpdateTime);

        // Update registered players
        netHandler.updateClients();

    }

}
