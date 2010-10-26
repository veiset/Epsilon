package epsilon.map.entity;

import epsilon.game.Collision;
import epsilon.game.Sprite;
import java.awt.Graphics;

/**
 *
 * @author vz
 */
public class Floor_1 extends World {

    public Floor_1(int posX, int posY) {
        super(posX, posY);
        currentSprite = new Sprite(new String[]{"/pics/crate.png"});
    }

    /**
     * Very basic collision detection test
     *
     * @param toCheckAgainst
     * @return
     */
    public Collision collision(Entity toCheckAgainst) {
        boolean[] hit = new boolean[]{false, false, false, false, false};
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

        hit[0] = true;
        c.collided = true;
        c.collidedWith = this;
        c.collidingEntity = toCheckAgainst;

        // check if the other entity crosses the left side of this entity
        if (right2 > left1 && right2 < right1) {
            hit[1] = true;
            c.crossedLeft = true;
        }

        // check if the other entity crosses the right side of this entity
        if (left2 < right1 && left2 > left1) {
            hit[2] = true;
            c.crossedRight = true;
        }

        // check if the other entity crosses the top of this entity
        if (bottom2 > top1 && bottom2 < bottom1) {
            hit[3] = true;
            c.crossedTop = true;
        }

        // check if the other entity crosses the bottom of this entity
        if (top2 < bottom1 && top2 > top1) {
            hit[4] = true;
            c.crossedBottom = true;
        }

        c.deltaRight = right1 - left2;
        c.deltaBottom = bottom1 - top2;
        c.deltaTop = bottom2 - top1;
        c.deltaLeft = right2 - left1;

        System.out.print(c);

        return c;
    }

    public boolean[] collision2(Entity toCheckAgainst) {
        boolean[] hit = new boolean[]{false, false, false, false};

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
            return hit;
        }
        if (top1 > bottom2) {
            return hit;
        }

        if (right1 < left2) {
            return hit;
        }
        if (left1 > right2) {
            return hit;
        }

        //check wich side
        if (right2 > left1 && right2 < right1) {
            System.out.println("left");
        }

        if (left2 > right1 && left2 < left1) {
            System.out.println("right");
        }

        return hit;
    }

    @Override
    public void renderHitBox(Graphics g, double x, double y) {
        double renderPosX = this.posX - x;
        double renderPosY = this.posY - y;

        g.drawRect((int)renderPosX, (int)renderPosY, this.getWidth(), this.getHeight());
    }
}
