package epsilontest;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.util.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
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

    }

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

        // Schedule the GameUpdater Task
        u = new GameUpdater(this);
        t = new Timer();
        t.schedule(u, 0, 16);

        // Start the renderer thread
        renderer = new RenderThread(this);
        renderer.setPriority(RenderThread.NORM_PRIORITY);
        renderer.start();
        
    }

    public void updateGame() {
        //System.out.println(System.currentTimeMillis() - lastUpdateTime);
        lastUpdateTime = System.currentTimeMillis();
    }

    void renderGraphics(long delta) {

        // Get hold of a graphics context for the accelerated
	// surface and blank it out

        Random temp = new Random();
	Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
        switch (temp.nextInt(5)) {
            case 0:
                g.setColor(Color.CYAN); break;
            case 1:
                g.setColor(Color.BLACK); break;
            case 2:
                g.setColor(Color.RED); break;
            case 3:
                g.setColor(Color.BLUE); break;
            case 4:
                g.setColor(Color.DARK_GRAY); break;
        }
	g.fillRect(0,0,800,600);

	// finally, we've completed drawing so clear up the graphics
	// and flip the buffer over
	g.dispose();
	strategy.show();

    }

}
