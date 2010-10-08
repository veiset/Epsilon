package epsilon.map.entity;

import epsilon.game.Physics;
import epsilon.game.Sprite;
import epsilon.game.Input;

/**
 * Test class that extends entity
 *
 * @author Marius
 */
public class TestEntity extends Entity {

    // keeps track of when to change pictures in the sprite
    private int ticker;

    // the different sprites this entity uses
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
        double newPosX = posX;
        double newPosY = posY;

        if (Input.get().right() && Input.get().left()) {
            if (currentSprite != standSprite) {
                currentSprite.resetImage();
                currentSprite = standSprite;
                standSprite.resetImage();
                ticker = 0;
            }
        } else if(Input.get().right()) {
            if (currentSprite != rightSprite) {
                currentSprite.resetImage();
                currentSprite = rightSprite;
                rightSprite.resetImage();
                ticker = 0;
            }
            newPosX = posX+4;
        } else if (Input.get().left()) {
            if (currentSprite != leftSprite) {
                currentSprite.resetImage();
                currentSprite = leftSprite;
                leftSprite.resetImage();
                ticker = 0;
            }
            newPosX = posX-4;
        } else {
            if (currentSprite != standSprite) {
                currentSprite.resetImage();
                currentSprite = standSprite;
                standSprite.resetImage();
                ticker = 0;
            }
        }

        // Handle falling
        if (posY<600) {
            double temp = Physics.calculateGravity(posY, pposY, 16);
            newPosY = posY-temp;
        } else if (Input.get().jump()) {
            // if it touches the ground, jump!
            newPosY -= 6;
        }

        super.move(newPosX, newPosY);

        // go to the next picture in the sprite if it is time
        if (ticker < 5) {
            ticker++;
        } else {
            ticker = 0;
            currentSprite.nextImage();
        }
    }

    @Override
    public double getXRenderPosition () {
        return posX - 400 + currentSprite.getWidth()/2;
    }

    @Override
    public double getYRenderPosition () {
        //return posY - 300 + currentSprite.getHeight()/2;
        return 0;
    }

    @Override
    public double getXPosition() {
        return posX - currentSprite.getWidth();
    }

    @Override
    public double getYPosition() {
        return posY - currentSprite.getHeight();
    }

    @Override
    public boolean hitbox(Entity entity) {
        return false; // yet to be implemented
    }
}
