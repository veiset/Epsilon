package epsilon.map.entity;

import java.awt.Graphics;

/**
 *
 * @author vz
 */
public abstract class World extends Entity {


    public World(int posX,int posY) {
        super(posX,posY);
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
