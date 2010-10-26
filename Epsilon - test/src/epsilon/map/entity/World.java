package epsilon.map.entity;

import epsilon.game.Collision;


/**
 *
 * @author vz
 */
public abstract class World extends Entity {


    public World(int posX,int posY) {
        super(posX,posY);
    }

    /**
     * Very basic collision detection test
     *
     * @param toCheckAgainst
     * @return
     */
    @Override
    public Collision collision(Entity toCheckAgainst) {
        Collision c = new Collision();

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
        left2 = x;
        right1 = this.posX + this.getWidth(); // (+image.width)
        right2 = x + toCheckAgainst.getWidth();
        top1 = this.posY;
        top2 = y; // (+ offset?)
        bottom1 = this.posY + this.getHeight(); // (+image.height)
        bottom2 = y + toCheckAgainst.getHeight(); // (+ offset?)

        if (bottom1 < top2) {
            return c;
        }
        if (top1 > bottom2) {
            return c;
        }

        if (right1 < left2) {
            return c;
        }
        if (left1 > right2) {
            return c;
        }

        c.collided = true;
        c.collidedWith = this;
        c.collidingEntity = toCheckAgainst;

        // check if the other entity crosses the left side of this entity
        if (right2 > left1 && right2 < right1) {
            c.crossedLeft = true;
        }

        // check if the other entity crosses the right side of this entity
        if (left2 < right1 && left2 > left1) {
            c.crossedRight = true;
        }

        // check if the other entity crosses the top of this entity
        if (bottom2 > top1 && bottom2 < bottom1) {
            c.crossedTop = true;
        }

        // check if the other entity crosses the bottom of this entity
        if (top2 < bottom1 && top2 > top1) {
            c.crossedBottom = true;
        }

        c.deltaRight = right1 - left2;
        c.deltaBottom = bottom1 - top2;
        c.deltaTop = bottom2 - top1;
        c.deltaLeft = right2 - left1;

        return c;
    }

}
