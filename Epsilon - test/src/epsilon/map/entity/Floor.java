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

    // Method for hitbox for this object (concept):
    //   ______
    //  |      |
    //  |______|
    // 
    public boolean hitBox(int x, int y) {
        // public boolean hitbox(Entity toCheckAgainst) {
        boolean hit = true;

        double left1, left2;
        double right1, right2;
        double top1, top2;
        double bottom1, bottom2;

        left1 = this.posX;
        left2 = x;
        right1 = this.posX; // (+image.width)
        right2 = x;
        top1 = this.posY;
        top2 = y; // (+ offset?)
        bottom1 = this.posY; // (+image.height)
        bottom2 = y; // (+ offset?)

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
