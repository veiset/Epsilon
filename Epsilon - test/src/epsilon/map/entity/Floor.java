package epsilon.map.entity;

import epsilon.game.Sprite;

/**
 *
 * @author vz
 */
public class Floor extends World {


    public Floor(int posX, int posY) {
        super(posX,posY);
        currentSprite = new Sprite(new String[]{"/pics/crate.png"});
    }

}
