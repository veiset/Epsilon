package epsilontest;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.util.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The game class, coordinates the flow of information
 * between the different threads.
 *
 * @author Marius
 */
public class Game extends Canvas {

    // Bufferstrategy for graphics, used for doublebuffering.
    private BufferStrategy strategy;

    // container used for packing
    private JFrame container;

    // panel used to draw on
    private JPanel panel;

    // last loop time
    private long lastUpdateTime;

    //Timertask used to update the game
    private GameUpdater u;

    //utility timer used to update the game
    private Timer t;

    //renderer Thread
    private RenderThread renderer;

    //The map that the game uses
    private Map map;

    /**
     * Constructor, intialises all the graphics elements,
     * and starts the game running. Also creates the threads
     * used by the game
     */
    public Game() {

        // create a frame to contain our game
        container = new JFrame("Test");

        // initialise LoopTimer
        lastUpdateTime = System.currentTimeMillis();

        // get hold the content of the frame and set up the
        // resolution of the game
        panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(800,600));
        panel.setLayout(null);

        // Tell AWT not to bother repainting our canvas since we're
        // going to do that our self in accelerated mode
        setIgnoreRepaint(true);

        this.addKeyListener(Input.get());
        // this.addMouseListener(Input.get());

    }

    /**
     * Startup method, makes all the graphics setup,
     * and starts the rendering and gameupdating
     */
    public void start() {

        // setup our canvas size and put it into the content of the frame
        setBounds(0,0,800,600);
        panel.add(this);

        // finally make the window visible
        container.pack();
        container.setResizable(false);
        container.setVisible(true);

        // create the buffering strategy which will allow AWT
        // to manage our accelerated graphics
        createBufferStrategy(2);
        strategy = getBufferStrategy();

        // initialising the map
        map = new TestMap();

        // Schedule the GameUpdater Task
        u = new GameUpdater(this);
        t = new Timer();
        t.schedule(u, 0, 16);

        // Start the renderer thread, needs to be last in the start method
        renderer = new RenderThread(this);
        renderer.setPriority(RenderThread.NORM_PRIORITY);
        renderer.start();
        
    }

    /**
     * Method used to update the game, mainly used by the updater task
     */
    public void updateGame() {
        //System.out.println(System.currentTimeMillis() - lastUpdateTime);
        lastUpdateTime = System.currentTimeMillis();
        map.update();
    }

    /**
     * Renders the screen. Uses the time since the last rendering to
     * aproximate how far a character has moved, even when the
     * game hasn't been updated
     *
     * @param delta time in milliseconds since last update
     */
    void renderGraphics(long delta) {

        // Get hold of a graphics context for the accelerated
	// surface and blank it out
	Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
	g.fillRect(0,0,800,600);

        // tell the map to draw all entities currently on screen onto the graphics surface
        map.render(g, (int)delta);

	// finally, we've completed drawing so clear up the graphics
	// and flip the buffer over
	g.dispose();
	strategy.show();

    }

}
