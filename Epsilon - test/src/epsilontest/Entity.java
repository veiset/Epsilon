package epsilontest;

import java.awt.Graphics;

/**
 *
 * Abstract class that provides a template for all entities in the map
 *
 * @author Marius
 */
public abstract class Entity {

    private Sprite sprite;
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
    public Entity (String[] urls, int posX,int posY) {
        sprite = new Sprite(urls);
        this.posX = posX;
        this.posY = posY;
        this.pposX = posX;
        this.pposY = posY;
    }

    /*
     * Rendering the object
     *
     * @param g The graphic object the entity is to be drawn on
     */
    public void render(Graphics g, int delta) {
        sprite.draw(g, posX, posY);
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

    /**
     * Move the sprite to the next image
     */
    public void nextImage() {
        sprite.nextImage();
    }

}