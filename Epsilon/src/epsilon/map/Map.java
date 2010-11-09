package epsilon.map;

import java.awt.Graphics;

/**
 * An interface describing the methods that need to be in a map
 *
 * @author Marius
 */
public interface Map {

    /**
     * Method that renders entities in the map onto the specified graphics
     *
     * @param g the graphics object things should be rendered to.
     * @param delta where you are between gameupdates. Makes sure you move smoothly
     */
    public void render(Graphics g, int delta);

    /**
     * Moves the entities on the map
     * 
     */
    public void update();

    /**
     * Returns the position of the player currently playing the game
     *
     * @return a double array with a length of two, containing the x and y position
     */
    public double[] getPlayerPosition();

    /**
     *
     */
    public void resetPlayerPosition();

    public void reset();

    public boolean isDead();

}
