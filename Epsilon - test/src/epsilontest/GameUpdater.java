package epsilontest;

import java.util.TimerTask;

public class GameUpdater extends TimerTask {

    private Game g;
    private boolean running;


    public GameUpdater(Game game) {
        g = game;
        running = false;
    }

    public void run() {
        if (!running) {
            running = true;
            g.updateGame();
            running = false;
        }
    }

}
