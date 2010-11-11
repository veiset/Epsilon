package epsilon.map.entity;

import epsilon.game.Collision;
import epsilon.map.Map;

/**
 * Abstract class containing the basic methods needed to make a moveable entity
 *
 * @author Marius
 */
public abstract class MoveableEntity extends Entity {

    /**
     * Variable containing the new X-position calculated so far
     */
    protected double newPosX;

    /**
     * Variable containing the new Y-position calculated so far
     */
    protected double newPosY;


   /**
    * Creates a new moveable entity setting the new position.
    *
    * @param posX The initial x position of the entity
    * @param posY The initial y position of the entity
    * @param map The Map that this Entity belongs to
    */
    public MoveableEntity(double posX, double posY, Map map) {
        super(posX , posY, map);
        this.newPosX = posX;
        this.newPosY = posY;
    }

    /**
     * Method that is run if this Entity has collided when moving
     * 
     * @param c The Collision object describing the collision itself
     */
    public abstract void collided(Collision c);

    /**
     * Abstract method to move the entity
     * This method should only change the newPosX and newPosY variables, and try
     * to avoid changing the posX and posY variables.
     * Should be run before the Collision detection, and the move() method
     * should be run after all calculations are finished.
     */
    public abstract void calculateMovement();

    /*
     * Finalising movement after all calculations are done
     */
    public void move() {
        this.pposX = this.posX;
        this.pposY = this.posY;
        this.posX = newPosX;
        this.posY = newPosY;
    }


    /**
     * Checks if the Entity is facing right
     *
     * @return true if the entity is facing right
     */
    public abstract boolean facingRight();

    /**
     * Getter method for the newPosX variable
     *
     * @return the new x position calculated so far
     */
    public double getNewXPosition() {
        return newPosX;
    }

    /**
     * Getter method for the newPosY variable
     *
     * @return the new y position calculated so far
     */
    public double getNewYPosition() {
        return newPosY;
    }

}
