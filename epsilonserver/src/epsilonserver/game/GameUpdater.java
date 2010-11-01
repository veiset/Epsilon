package epsilonserver.game;

import java.util.TimerTask;

/**
 * Task that updates the game state about 60 times pr. second, every 16th ms
 *
 * @author Marius
 */
public class GameUpdater extends TimerTask {

    private Game g;
    private boolean running;

    /**
     * Constructor
     *
     * @param game the game instance that this tast is supposed to be updating
     */
    public GameUpdater(Game game) {
        g = game;
        running = false;
    }

    /**
     * Method that is run every 16 ms. Only updates the gamestate
     * if the previous version has finished running.
     */
    @Override
    public void run() {
        if (!running) {
            running = true;
            g.updateGame();
            running = false;
        }
    }

}