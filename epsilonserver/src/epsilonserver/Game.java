/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package epsilonserver;

import java.util.Timer;

/**
 *
 * @author mm
 */
public class Game {

    // last loop time
    private long lastUpdateTime;

    //Timertask used to update the game
    private GameUpdater u;

    //utility timer used to update the game
    private Timer t;

    private GameState gameState;
    private ServerHandler serverHandler;
    private static int PORT = 6001;


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
        gameState = new GameState();

        serverHandler = new ServerHandler(PORT, gameState);
        new Thread(serverHandler).start();

        // Schedule the GameUpdater Task
        u = new GameUpdater(this);
        t = new Timer();
        t.schedule(u, 0, 16);
        
    }
    

    /**
     * Method used to update the game, mainly used by the updater task
     */
    public void updateGame() {

        //gameState.update();
        serverHandler.sendGameState();
        
    }


}
