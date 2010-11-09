package epsilon.map.entity;

import epsilon.map.Map;
import java.awt.Graphics;

/**
 * Abstract class with methods all world objects are sharing. 
 * These objects are not movable.
 *
 * @author vz
 */
public abstract class World extends Entity {


    /**
     * World object constructor.
     *
     * @param posX position X
     * @param posY position Y
     */
    public World(int posX,int posY, Map m) {
        super(posX,posY, m);
    }

    @Override
    public void renderHitBox(Graphics g, double x, double y) {
        double posX = this.posX - x;
        double posY = this.posY - y;

        g.drawRect((int)posX, (int)posY, this.getWidth(), this.getHeight());

        HitBox[] hitbox = currentSprite.getHitBox();

        for (int i=0;i<hitbox.length;i++) {
            hitbox[i].draw(g, posX, posY);
        }
    }

}
