package epsilon.map;

import epsilon.map.entity.Entity;
import epsilon.map.entity.World;
import java.awt.Graphics;
import java.util.HashMap;

/**
 * Class for storing world objects and checking them for collisions.
 * All world objects are static and do not move.
 * Utilises a hashmap.
 *
 * @author Marius
 */
public class WorldStore {

    private int width;
    private HashMap<Long, World[]> map;
    
    /**
     * Initialises the variables and the hashmap
     *
     * @param width the width of each world element in pixels.
     */
    public WorldStore(int width) {
        this.width = width;
        map = new HashMap<Long, World[]>(100);
    }

    /**
     * Adds a world object to the store
     *
     * @param w the world object to be added
     */
    public void add(World w) {

        long x = (int)Math.round(w.getXPosition());
        long reminder = x % width;
        long ref = x - reminder;
        
        if (map.containsKey(ref)) {
            World[] cur = map.get(ref);
            World[] temp = new World[cur.length + 1];

            System.arraycopy(cur, 0, temp, 0, cur.length);
            temp[temp.length-1] = w;
            map.put(ref, temp);
        } else {
            map.put(ref, new World[]{w});
        }
    }

    /**
     * Renders all the world objects currently on the screen
     * Hardcoded for 800x600
     */
    public void renderAll(Graphics g, int delta, double renderXPosition) {

    }

    public void checkCollision (Entity e) {
        
    }

}
