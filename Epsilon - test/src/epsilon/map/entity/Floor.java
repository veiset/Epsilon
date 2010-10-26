package epsilon.map.entity;

import epsilon.game.Sprite;
import java.awt.Graphics;

/**
 *
 * @author vz
 */
public class Floor extends World {

    public Floor(int posX, int posY) {
        super(posX, posY);
        currentSprite = new Sprite(new String[]{"/pics/crate.png"});
    }

    @Override
    public void renderHitBox(Graphics g, double x, double y) {

        double posX = this.posX - x;
        double posY = this.posY - y;

        g.drawRect((int)posX, (int)posY, this.getWidth(), this.getHeight());
    }
}
