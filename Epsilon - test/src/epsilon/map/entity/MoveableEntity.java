package epsilon.map.entity;

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
    public abstract void collided(boolean[] hitbox, Entity collidedWith);

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

}
