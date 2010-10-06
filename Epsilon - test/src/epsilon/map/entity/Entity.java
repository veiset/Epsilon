package epsilon.map.entity;

import java.awt.Graphics;

/**
 *
 * Abstract class that provides a template for all entities in the map
 *
 * @author Marius
 */
public abstract class Entity {

    protected int posX;
    protected int posY;
    protected int pposX;
    protected int pposY;

    /*
     * Constructur for the Entity class
     *
     * @param urls List of images that are going to be used in rendering the entity
     * @param posX The initial x position of the entity
     * @param posY The initial y position of the entity
     */
    public Entity (int posX,int posY) {
        this.posX = posX;
        this.posY = posY;
        this.pposX = posX;
        this.pposY = posY;
    }

    /*
     * Moving an entity on the screen
     *
     * @param posX the new x position of the entity
     * @param posY the new y position of the entity
     */
    protected void move(int posX,int posY) {
        this.pposX = this.posX;
        this.pposY = this.posY;
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Abstract method to move the entity
     */
    public abstract void move();

    /*
     * Rendering the object
     *
     * @param g The graphic object the entity is to be drawn on
     * @param delta an int specifying the number of milliseconds since last gameupdate
     */
    abstract public void render(Graphics g, int delta);

}