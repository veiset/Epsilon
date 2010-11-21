package epsilon.map.entity;

import epsilon.game.Collision;
import epsilon.game.Sprite;
import epsilon.map.Map;
import java.awt.Graphics;

/**
 *
 * Abstract class that provides a template for all entities in the map
 *
 * @author md
 */
public abstract class Entity {

    /**
     * Current X position from the beginning of the map
     */
    protected double posX;

    /**
     * Current Y position from the beginning of the map
     */
    protected double posY;

    /**
     * Previous X position of the entity. Used for movement and smoothing out rendering
     */
    protected double pposX;

    /**
     * Previous Y position of the entity. Used for movement and smoothing out rendering
     */
    protected double pposY;

    /**
     * Currently rendered Sprite
     */
    protected Sprite currentSprite;

    /**
     * Reference to the map that created the Entity
     */
    protected Map mapReference;

    /*
     * Constructur for the Entity class
     *
     * @param posX The initial x position of the entity
     * @param posY The initial y position of the entity
     * @param map The map that created this Entity
     */
    public Entity (double posX,double posY, Map map) {
        this.posX = posX;
        this.posY = posY;
        this.pposX = posX;
        this.pposY = posY;
        mapReference = map;
    }

    /*
     * Rendering the object
     *
     * @param g The graphic object the entity is to be drawn on
     * @param delta An int specifying the number of milliseconds since last gameupdate
     * @param x The X coordinate of the render position. Usually specified from the current player
     * @param y The Y coordinate of the render position. Usually fixed
     */
    public void render(Graphics g, int delta, double x, double y) {
        double coeff = (double)delta/16;

        double posX = this.posX - x;
        double posY = this.posY - y;
        double pposX = this.pposX - x;
        double pposY = this.pposY - y;

        //Smoothing out frame drawings
        currentSprite.draw(g, (int)Math.round(posX+(posX-pposX)*coeff), (int)Math.round(posY+(posY-pposY)*coeff));
    }

    /**
     * Renders the hitbox of the entity, for debugging purposes
     *
     * @param g the graphics object to be drawn on
     * @param x x position of the hitbox
     * @param y y position of the hitbox
     */
    public abstract void renderHitBox(Graphics g, double x, double y);

    /**
     * Returns the X position of the entity
     *
     * @return X position of the entity
     */
    public double getXRenderPosition() {
        return getXPosition();
    }

    /**
     * Returns the Y position of the entity
     *
     * @return Y position of the entity
     */
    public double getYRenderPosition() {
        return getYPosition();
    }

    /**
     * Returns the X position of the entity
     *
     * @return X position of the entity
     */
    public double getXPosition() {
        return posX;
    }

    /**
     * Returns the Y position of the entity
     *
     * @return Y position of the entity
     */
    public double getYPosition() {
        return posY;
    }

    /**
     * Returns the width of the current Sprite object
     *
     * @return width of the current Sprite in pixels
     * @see epsilon.game.Sprite
     */
     public int getWidth() {
         return currentSprite.getWidth();
     }

     /**
      * Returns the height of the current Sprite object
      *
      * @return height of the current Sprite in pixels
      * @see epsilon.game.Sprite
      */
     public int getHeight() {
         return currentSprite.getHeight();
     }

     /**
      * Provides access to the complete list of hitboxes
      *
      * @return An array of variable length containin the hitboxes fo the current sprite object
      * @see epsilon.game.Sprite
      */
     public HitBox[] getHitbox() {
         return currentSprite.getHitBox();
     }

    /**
     * Standard collision detection
     *
     * @param toCheckAgainst the Entity to check collision against
     * @return A collision object describing the overlap and collisions of the two objects
     */
    public Collision collision(Entity toCheckAgainst) {
        Collision c = new Collision();

        double x = toCheckAgainst.getXPosition();;
        double y = toCheckAgainst.getYPosition();
        double px = x;
        double py = y;

        double ox = this.posX, oy = this.posY, opx = ox, opy = oy;

        if (toCheckAgainst instanceof MoveableEntity) {
            x = ((MoveableEntity)toCheckAgainst).getNewXPosition();
            y = ((MoveableEntity)toCheckAgainst).getNewYPosition();
            px = ((MoveableEntity)toCheckAgainst).getXPosition();
            py = ((MoveableEntity)toCheckAgainst).getYPosition();
        }

        if (this instanceof MoveableEntity) {
            ox = ((MoveableEntity)this).getNewXPosition();
            oy = ((MoveableEntity)this).getNewYPosition();
            opx = this.posX;
            opy = this.posY;
        }


        double left1, left2;
        double right1, right2;
        double top1, top2;
        double bottom1, bottom2;

        // variables ending with 1 is for this object,
        // and variables ending with 2 is for the object toCheckAgainst
        // all offsets should come from the incomming object, and not
        // some magical numbers I made up.

        left1 = ox;
        left2 = x;
        right1 = ox + this.getWidth();
        right2 = x + toCheckAgainst.getWidth();
        top1 = oy;
        top2 = y;
        bottom1 = oy + this.getHeight();
        bottom2 = y + toCheckAgainst.getHeight();

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

        Collision temp;

        c.collidedWith = this;
        c.collidingEntity = toCheckAgainst;


        /*
         * loop through all the hitboxes of each entity and check for collision
         * only ending up with a single collision object with the biggest overlap
         */
        for(HitBox h:this.getHitbox()) {
            for(HitBox k:toCheckAgainst.getHitbox()) {
                temp = h.collidesWith(k, ox, oy, opx, opy, x, y, px, py);
                if (temp.collided) {
                    c.collided = true;

                    c.crossedBottom = (c.crossedBottom || temp.crossedBottom);
                    c.crossedTop = (c.crossedTop || temp.crossedTop);
                    c.crossedLeft = (c.crossedLeft || temp.crossedLeft);
                    c.crossedRight = (c.crossedRight || temp.crossedRight);

                    c.deltaBottom = Math.max(temp.deltaBottom, c.deltaBottom);
                    c.deltaTop = Math.max(temp.deltaTop, c.deltaTop);
                    c.deltaLeft = Math.max(temp.deltaLeft, c.deltaLeft);
                    c.deltaRight = Math.max(temp.deltaRight, c.deltaRight);
                }

            }
        }

        return c;
    }
}