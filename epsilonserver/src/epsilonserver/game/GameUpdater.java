package epsilonserver.game;

import java.util.TimerTask;

/**
 * Task that updates the game state about 60 times pr. second, every 16th ms.
 *
 * @author Marius
 */
public class GameUpdater extends TimerTask {

    private Game game;
    private boolean running;

    /**
     * Constructor
     *
     * @param game The game instance that this task is supposed to be updating
     */
    public GameUpdater(Game game) {
        this.game = game;
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
            game.updateGame();
            running = false;
        }
    }

    /**
     * Stop gameupdater thread.
     */
    public void stopGameUpdater() {
        running = true;
    }

}