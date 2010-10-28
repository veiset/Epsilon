package epsilon.map.entity;

import epsilon.game.Collision;
import java.awt.Graphics;

/**
 *
 * @author vz
 */
public abstract class Enemy extends MoveableEntity {

    public Enemy(int posX, int posY) {
        super(posX,posY);
    }

    @Override
    public void renderHitBox(Graphics g, double x, double y) {
    }

    @Override
    public Collision collision(Entity entity) {
        return new Collision();
    }

}
