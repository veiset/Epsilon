package epsilontest;

import java.awt.Graphics;

/**
 * Test class that extends entity
 *
 * @author Marius
 */
public class TestEntity extends Entity {

    // keeps track of when to change pictures in the sprite
    private int ticker;

    // the different sprites this entity uses
    private Sprite currentSprite;
    private Sprite rightSprite;
    private Sprite standSprite;
    private Sprite leftSprite;

    /**
     * Constructor for the entity that initialises sprites
     *
     * @param posX The starting X position of the entity
     * @param posY The starting Y position of the entity
     */
    public TestEntity(int posX,int posY) {
        super(posX, posY);
        ticker = 0;
        rightSprite = new Sprite(new String[]{"/pics/guy01.png","/pics/guy02.png","/pics/guy03.png","/pics/guy04.png","/pics/guy05.png"});
        leftSprite = new Sprite(new String[]{"/pics/guy01.png","/pics/guy02.png","/pics/guy03.png","/pics/guy04.png","/pics/guy05.png"},true);
        standSprite = new Sprite(new String[]{"/pics/guy01.png"});
        currentSprite = standSprite;
    }

    @Override
    public void move() {
        // handle input, and chose the right sprite for the job
        if (Input.get().right() && Input.get().left()) {
            if (currentSprite != standSprite) {
                currentSprite.resetImage();
                currentSprite = standSprite;
                standSprite.resetImage();
                ticker = 0;
            }
            super.move(posX, posY);
        } else if(Input.get().right()) {
            if (currentSprite != rightSprite) {
                currentSprite.resetImage();
                currentSprite = rightSprite;
                rightSprite.resetImage();
                ticker = 0;
            }
            super.move(posX+4, posY);
        } else if (Input.get().left()) {
            if (currentSprite != leftSprite) {
                currentSprite.resetImage();
                currentSprite = leftSprite;
                leftSprite.resetImage();
                ticker = 0;
            }
            super.move(posX -4, posY);
        } else {
            if (currentSprite != standSprite) {
                currentSprite.resetImage();
                currentSprite = standSprite;
                standSprite.resetImage();
                ticker = 0;
            }
            super.move(posX, posY);
        }

        // go to the next picture in the sprite if it is time
        if (ticker < 5) {
            ticker++;
        } else {
            ticker = 0;
            currentSprite.nextImage();
        }
    }

    /*
     * Rendering the object
     *
     * @param g The graphic object the entity is to be drawn on
     */
    public void render(Graphics g, int delta) {
        currentSprite.draw(g, posX, posY);
    }

}
