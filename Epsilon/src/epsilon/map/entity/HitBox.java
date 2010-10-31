package epsilon.map.entity;

import epsilon.game.Collision;
import java.awt.Graphics;

/**
 * Class describing a hitbox of an entity It uses offset and size to create
 * smaller boxes within a sprite to allow collision detection on parts of a sprite instead of whole sprites
 *
 * @author Marius
 */
public class HitBox {

    // the offset from the top left corner of the sprite
    private int offsetX;
    private int offsetY;

    // the size of the hitbox
    private int width;
    private int height;

    /**
     * Creates a new hitbox based on the offset and size of the hitbox
     *
     * @param offX offset in x direction (right, left) from the top left corner of the sprite
     * @param offY offset in x direction (up, down) from the top left corner of the sprite
     * @param width the width of the hitbox in pixels
     * @param height the height of the hitbox in pixels
     */
    public HitBox(int offX, int offY, int width, int height) {
        offsetX = offX;
        offsetY = offY;

        this.width = width;
        this.height = height;
    }

    /**
     * Checks if two hitboxes collides. It needs the position of it's own
     * entity and the position of the entity of the other hitbox
     *
     * @param box the hitbox that should be checked for collision
     * @param ownPosX the X-Axis position of this hitbox's entity
     * @param ownPosY the Y-Axis position of this hitbox's entity
     * @param otherPosX the X-Axis position of the other hitbox's entity
     * @param otherPosY the Y-Axis position of the other hitbox's entity
     * @return a collision object with information about a collision
     */
    public Collision collidesWith(HitBox box, double ownPosX, double ownPosY, double otherPosX, double otherPosY) {

        Collision c = new Collision();

        double left1, left2;
        double right1, right2;
        double top1, top2;
        double bottom1, bottom2;

        left1 = ownPosX+offsetX;
        left2 = otherPosX + box.getOffsetX();
        right1 = left1 + width;
        right2 = left2 + box.getWidth();
        top1 = ownPosY + offsetY;
        top2 = otherPosY + box.getOffsetY();
        bottom1 = top1 + height;
        bottom2 = top2 + box.getHeight();

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

        // check if the other entity crosses the left side of this hitbox
        if (right2 > left1 && right2 < right1) {
            c.crossedLeft = true;
        }

        // check if the other entity crosses the right side of this hitbox
        if (left2 < right1 && left2 > left1) {
            c.crossedRight = true;
        }

        // check if the other entity crosses the top of this hitbox
        if (bottom2 > top1 && bottom2 < bottom1) {
            c.crossedTop = true;
        }

        // check if the other entity crosses the bottom of this hitbox
        if (top2 < bottom1 && top2 > top1) {
            c.crossedBottom = true;
        }

        c.deltaRight = right1 - left2;
        c.deltaBottom = bottom1 - top2;
        c.deltaTop = bottom2 - top1;
        c.deltaLeft = right2 - left1;

        return c;
    }

    /**
     * Draws the hitbox onto the specified graphics object
     *
     * @param g the graphics object to drawn on
     * @param posX the X-axis position of the entity that has this hitbox
     * @param posY the Y-axis potision of the entity that has this hitbox
     */
    public void draw (Graphics g, double posX, double posY) {
        g.drawRect((int)posX + offsetX, (int) posY + offsetY, width, height);
    }

    /**
     * Getter method for the offsetx variable
     *
     * @return the offset along the X-axis
     */
    public int getOffsetX() {
        return offsetX;
    }

    /**
     * Getter method for the offsety variable
     *
     * @return the offset along the Y-axis
     */
    public int getOffsetY() {
        return offsetY;
    }

    /**
     * Getter method for the width variable
     *
     * @return the width of the hitbox
     */
    public int getWidth() {
        return width;
    }

    /**
     * Getter method for the height variable
     *
     * @return the height of the hitbox
     */
    public int getHeight() {
        return height;
    }

}
