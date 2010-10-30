package epsilon.map.entity;

import epsilon.game.Sprite;

/**
 *
 * @author vz
 */
public class Floor_1 extends World {

    public Floor_1(int posX, int posY) {
        super(posX, posY);
        currentSprite = new Sprite(new String[]{"/pics/crate.png"});
    }
}
