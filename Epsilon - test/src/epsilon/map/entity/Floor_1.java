package epsilon.map.entity;

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
     * NOTE: Currently not good enough to be implimented!
     *
     * @param toCheckAgainst
     * @return
     */
    public boolean[] collision(Entity toCheckAgainst) {
        boolean[] hit = new boolean[]{false, false, false, false, false};

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

//        System.out.println(bottom1 + " < " + top2);
//        System.out.println(top1 + " > " + bottom2);
//        System.out.println(right1 + " < " + left2);
//        System.out.println(left1 + " > " + right2);
//        System.out.println("------------------");

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

        hit[0] = true;

        // check if the other entity crosses the left side of this entity
        if (right2 > left1 && right2 < right1) {
            hit[1] = true;
            System.out.println("left");
        }

        // check if the other entity crosses the right side of this entity
        if (left2 < right1 && left2 > left1) {
            hit[2] = true;
            System.out.println("right");
        }

        // check if the other entity crosses the top of this entity
        if (bottom2 > top1 && bottom2 < bottom1) {
            hit[3] = true;
            System.out.println("top");
        }

        // check if the other entity crosses the bottom of this entity
        if (top2 < bottom1 && top2 > top1) {
            hit[4] = true;
            System.out.println("bottom");
        }

        return hit;
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
        double posX = this.posX - x;
        double posY = this.posY - y;

        g.drawRect((int)posX, (int)posY, this.getWidth(), this.getHeight());
    }
}
