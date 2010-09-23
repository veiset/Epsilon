package epsilontest;

/**
 *
 * @author Marius
 */
public class RenderThread extends Thread {

    private Game game;

    private long lastLoopTime;

    private boolean running;

    private int currentIt;

    private long totalTime;


    public RenderThread(Game g) {

        game = g;
        lastLoopTime = System.currentTimeMillis();
        running = true;
        g.renderGraphics(0);
        currentIt = 0;
        totalTime = 0;
        
    }

    @Override
    public void run() {
        while (running) {
            long delta = System.currentTimeMillis() - lastLoopTime;
            lastLoopTime = System.currentTimeMillis();
            game.renderGraphics(delta);
            //calcFPS(delta);
            try { Thread.sleep(5); } catch (Exception e) {}
        }
    }
    
    private void calcFPS(long delta) {
        if (currentIt < 100) {
            totalTime += delta;
            currentIt++;
        } else {
            float t = ((float)totalTime)/1000;
            float fps = 100 / t;
            System.out.println("FPS: " + fps);
            totalTime = delta;
            currentIt = 1;
        }
    }

}
