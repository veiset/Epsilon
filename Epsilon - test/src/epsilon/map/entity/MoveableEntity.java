package epsilon.map.entity;

import epsilon.game.Collision;

/**
 *
 * @author Marius
 */
public abstract class MoveableEntity extends Entity {

    // used for movement since the move method usually contains several different things.
    protected double newPosX;
    protected double newPosY;


   /**
    * Constructor
    *
    * @param posX The initial x position of the entity
    * @param posY The initial y position of the entity
    */
    public MoveableEntity(double posX, double posY) {
        super(posX , posY);
        this.newPosX = posX;
        this.newPosY = posY;
    }

    /**
     *
     * @param hitbox
     * @param collidedWith
     */
    public abstract void collided(Collision c);

    /**
     * Abstract method to move the entity
     */
    public abstract void calculateMovement();

    /*
     * Finalising movement after all calculations are done
     *
     */
    protected void move() {
        this.pposX = this.posX;
        this.pposY = this.posY;
        this.posX = newPosX;
        this.posY = newPosY;
    }


    public abstract boolean facingRight();

    /**
     *
     */
    public double getNewXPosition() {
        return newPosX;
    }

    public double getNewYPosition() {
        return newPosY;
    }

}
