package epsilon.map.entity;

import epsilon.game.Sprite;

/**
 *
 * @author vz
 */
public class Floor extends World {

    public Floor(int posX, int posY) {
        super(posX, posY);
        currentSprite = new Sprite(new String[]{"/pics/crate.png"});
    }

    /**
     * Very basic collision detection test
     * NOTE: Currently not good enough to be implimented!
     *
     * @param toCheckAgainst
     * @return
     */
    public boolean hitbox(Entity toCheckAgainst) {
        boolean hit = true;

        int x = (int) toCheckAgainst.getXPosition();
        int y = (int) toCheckAgainst.getYPosition();

        double left1, left2;
        double right1, right2;
        double top1, top2;
        double bottom1, bottom2;

        // variables ending with 1 is for this object,
        // and variables ending with 2 is for the object toCheckAgainst
        // all offsets should come from the incomming object, and not
        // some magical numbers I made up.
        
        left1 = this.posX;
        left2 = x+100;
        right1 = this.posX+50; // (+image.width)
        right2 = x+200;
        top1 = this.posY+150;
        top2 = y+50; // (+ offset?)
        bottom1 = this.posY; // (+image.height)
        bottom2 = y+350; // (+ offset?)

        if (bottom1 < top2) {
            hit = false;
        }
        if (top1 > bottom2) {
            hit = false;
        }

        if (right1 < left2) {
            hit = false;
        }
        if (left1 > right2) {
            hit = false;
        }

        return hit;
    }
}
