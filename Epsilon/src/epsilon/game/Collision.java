package epsilon.game;

import epsilon.map.entity.Entity;

/**
 * Class that contains information about a collision between two entities
 * It uses public fields and contains wich side of the entity it collides with
 * The delta contains how big the overlap is in each direction as seen from the
 * colliding entity.
 *
 * @author Marius
 */
public class Collision {

    /**
     * Is set to true if there has been a collision
     */
    public boolean collided;

    /**
     * true if the Entity the collidingEntity collided with crossed the left
     * side of the collidingEntity
     */
    public boolean crossedLeft;

    /**
     * true if the Entity the collidingEntity collided with crossed the right
     * side of the collidingEntity
     */
    public boolean crossedRight;

    /**
     * true if the Entity the collidingEntity collided with crossed the top
     * of the collidingEntity
     */
    public boolean crossedTop;

    /**
     * true if the Entity the collidingEntity collided with crossed the top
     * of the collidingEntity
     */
    public boolean crossedBottom;

    /**
     * The entity that was creating the collision object
     */
    public Entity collidingEntity;

    /**
     * The entity that was
     */
    public Entity collidedWith;

    public double deltaLeft;
    public double deltaRight;
    public double deltaTop;
    public double deltaBottom;

    /**
     * Sets up an empty collision object
     */
    public Collision() {
        collided = false;

        crossedLeft = false;
        crossedRight = false;
        crossedTop = false;
        crossedBottom = false;

        collidingEntity = null;
        collidedWith = null;

        deltaLeft = 0;
        deltaRight = 0;
        deltaTop = 0;
        deltaBottom = 0;
    }

    @Override
    public String toString() {

        String result = "";
        result += "Top: " + crossedTop + " " + deltaTop + '\n';
        result += "Bottom: " + crossedBottom + " " + deltaBottom + '\n';
        result += "Left: " + crossedLeft + " " + deltaLeft + '\n';
        result += "Right: " + crossedRight + " " + deltaRight + '\n';
        result += "--------------------------------" + '\n';
        return result;
    }

}
