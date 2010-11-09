package epsilon.map.entity;

import epsilon.game.Sprite;
import epsilon.map.Map;

/**
 *
 * @author vz
 */
public class Floor_1 extends World {

    /**
     * Creating a Floor object at a specified position
     *
     * @param posX the top x-position of the floor object
     * @param posY the left y-position of the floor object
     */
    public Floor_1(int posX, int posY, Map m) {
        super(posX, posY, m);
        currentSprite = new Sprite(new String[]{"/pics/crate.png"});
    }
}
