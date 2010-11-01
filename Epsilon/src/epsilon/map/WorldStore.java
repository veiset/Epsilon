package epsilon.map;

import epsilon.game.Collision;
import epsilon.map.entity.Entity;
import epsilon.map.entity.MoveableEntity;
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
    public void renderAll(Graphics g, int delta, double renderXPosition, double renderYPosition) {

        long x = (int)Math.round(renderXPosition);
        long reminder = x % width;
        long ref = x - reminder;

        while (ref > renderXPosition - width) {
            ref -= width;
        }

        World[] temp = null;

        while (ref < renderXPosition + 800 + width) {
            temp = map.get(ref);
            if (temp != null) {
                for(int i=0;i<temp.length;i++) {
                    temp[i].render(g, delta, renderXPosition, renderYPosition);
                }
            }
            ref += width;
        }

    }

    /**
     * Checks for collision on all world entities on the same 
     *
     * @param e The entity to check for collision
     */
    public void checkCollision (MoveableEntity e) {

        long x = (int)Math.round(e.getXPosition());
        long reminder = x % width;
        long ref = x - reminder;
        
        World[] temp = null;
        Collision c = null;

        while (ref < e.getXPosition() + e.getWidth()) {
            temp = map.get(ref);
            if (temp != null) {
                for(int i=0;i<temp.length;i++) {
                    c = temp[i].collision(e);
                    if (c.collided) {
                        e.collided(c);
                    }
                }
            }
            ref += width;
        }

    }

}
