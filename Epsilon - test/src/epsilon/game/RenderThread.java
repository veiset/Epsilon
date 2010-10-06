package epsilon.game;

/**
 * Rendering thread for the game with codename Epsilon
 *
 * @author Marius
 */
public class RenderThread extends Thread {

    // link to the game object of the running thread
    private Game game;

    // last time the rendering loop ran, in milliseconds from a specific time, used for fps calculations and smoothing out rendering
    private long lastLoopTime;

    // set the variable to false to stop rendering
    private boolean rendering;

    // used to limit the updating of fps to every 100 frames
    private int currentIt;
    private long totalTime;

    // turns on and off FPS calculation
    private boolean calcFPS = false;


    /**
     * Constructor of the render thread, intialises all the variables needed for controlling it.
     * Also renders the first picture.
     *
     * @param g the game object this thread is supposed to render.
     */
    public RenderThread(Game g) {

        game = g;
        lastLoopTime = System.currentTimeMillis();
        rendering = true;
        g.renderGraphics(0);
        currentIt = 0;
        totalTime = 0;
        
    }

    /**
     * Starts rendering while the renderiing boolean remains true
     */
    @Override
    public void run() {
        while (rendering) {
            long delta = System.currentTimeMillis() - lastLoopTime;
            lastLoopTime = System.currentTimeMillis();
            game.renderGraphics(delta);
            if(calcFPS){calcFPS(delta);}
            try { Thread.sleep(5); } catch (Exception e) {}
        }
    }

    /**
     * Calculates the FPS. Only updates every 100 runs of the rendering thread.
     *
     * @param delta
     */
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
