package epsilon.game;

import epsilon.menu.Menu;
import epsilon.menu.StartupPage;
import epsilon.map.Map;
import epsilon.map.entity.NetworkMap;
import epsilon.menu.DeathPage;
import epsilon.menu.NetworkDeathPage;
import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
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

    // true if the game is in the menu
    private boolean menu;

    private static Game game = new Game();


    /**
     * Constructor, intialises all the graphics elements,
     * and starts the game running. Also creates the threads
     * used by the game
     */
    private Game() {

        // create a frame to contain our game
        container = new JFrame("Epsilon");
        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        container.addKeyListener(Input.get());
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

        Menu.get().setMenu(StartupPage.get());
        menu = true;

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
        if (Input.get().menu() && !Input.get().getMenuHandeled()) {
            if (!menu) {
                menu = true;
                Input.get().handleMenu();

            } else {
                menu = false;
                Input.get().handleMenu();
            }
        }
        if ((!menu && map != null)) {
            lastUpdateTime = System.currentTimeMillis();
            map.update();
            if (map.isDead()) {
                if (map instanceof NetworkMap) {
                    Menu.get().setMenu(new NetworkDeathPage());
                } else {
                    Menu.get().setMenu(new DeathPage());
                }
                menu = true;
            }
        } else if (menu) {
            Menu.get().update();
            if (map instanceof NetworkMap) {
                ((NetworkMap)map).updateWhileMenu();
            }
        }
    }

    /**
     * Renders the screen. Uses the time since the last rendering to
     * aproximate how far a character has moved, even when the
     * game hasn't been updated
     *
     * @param delta time in milliseconds since last update
     */
    public void renderGraphics(long delta, int fps) {

        // Get hold of a graphics context for the accelerated
	// surface and blank it out
	Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
        //RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        //g.setRenderingHints(renderHints);
        g.setColor(Color.WHITE);
	g.fillRect(0,0,800,600);

        // tell the map to draw all entities currently on screen onto the graphics surface
        if (map != null) {
            map.render(g, (int)delta);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 800, 800);
        }

        if (menu) {
            g.setColor(Color.BLACK);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,(float)0.6));
            g.fillRect(0, 0, 800, 600);
            Menu.get().render(g);
        }

        Font f = getStandardFont();
        g.setFont(f.deriveFont(f.getSize2D()*0.75f));
        FontMetrics fm = g.getFontMetrics();

        if (fps != 0) {
            g.setColor(Color.BLACK);
            g.drawString("FPS: " + fps, 800-5-fm.stringWidth("FPS: " + fps), fm.getHeight()+3);
        }

        if (map != null) {
            g.setColor(Color.RED);
            g.drawString("Health: " + map.getPlayerHp(), 5, fm.getHeight()+3);
        }

	// finally, we've completed drawing so clear up the graphics
	// and flip the buffer over
	g.dispose();
	strategy.show();

    }

    /**
     * Closes the game. Currently uses System.exit(0). Should be avoided
     */
    public void quit() {
        System.exit(0);
    }

    /**
     * Returns the single object of the game class
     *
     * @return the single instance of this class
     */
    public static Game get() {
        return game;
    }

    /**
     * Changes wich map the game is playing
     *
     * @param sMap the new map to be used.
     */
    public void setMap(Map sMap) {
        map = sMap;
    }

    /**
     * Indicates that the Menu handling is done, and that it should start rendering and updating the game again.
     */
    public void menuDone() {
        menu = false;
    }

    /**
     *
     *
     * @return
     */
    public double[] getPlayerState() {
        return map.getPlayerState();
    }

    /**
     * 
     */
    public void resetPlayerPosition() {
        map.resetPlayerPosition();
    }

    /**
     * 
     */
    public void restart() {
        map.reset();
    }

    public static Font getStandardFont() {
        return Menu.get().getFont();
    }

}
