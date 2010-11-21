package epsilonserver.game;

import epsilonserver.entity.EntityHandler;
import epsilonserver.net.NetworkHandler;
import java.util.Timer;

/**
 * The game class, coordinates the flow of information
 * between the different threads.
 *
 * Modified by mm
 *
 * @author md
 */
public class Game {

    // last loop time
    private long lastUpdateTime;

    //Timertask used to update the game
    private GameUpdater updater;

    //utility timer used to update the game
    private Timer timer;

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
     * @return The single instance of this class
     */
    public static Game get() {
        return game;
    }

    /**
     * Startup method. Starts the gameupdating.
     */
    public void start() {

        eHandler = EntityHandler.getInstance();
        netHandler = NetworkHandler.getInstance();
        netHandler.startServer();

        // Schedule the GameUpdater Task
        updater = new GameUpdater(this);
        timer = new Timer();
        timer.schedule(updater, 0, 16);

    }

    /**
     * Stop the server and clear all players.
     */
    public void stop() {
        netHandler.stopServer();
        eHandler.clearPlayers();
        updater.stopGameUpdater();
    }

    /**
     * Method used to update the game, mainly used by the updater task.
     * Sets a new lastUpdateTime, checks for timed out players and updates
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
