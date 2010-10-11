package epsilon.map.entity;

import epsilon.game.Physics;
import epsilon.game.Sprite;
import epsilon.game.Input;
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
    private Sprite rightSprite;
    private Sprite standSpriteRight;
    private Sprite leftSprite;
    private Sprite standSpriteLeft;

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
        standSpriteRight = new Sprite(new String[]{"/pics/guy01.png"});
        standSpriteLeft = new Sprite(new String[]{"/pics/guy01.png"},true);

        currentSprite = standSpriteRight;
    }

    @Override
    public void move() {

        // handle input, and chose the right sprite for the job
        double newPosX = posX;
        double newPosY = posY;

        if(Input.get().right() && !Input.get().left()) {
            if (currentSprite != rightSprite) {
                currentSprite.resetImage();
                currentSprite = rightSprite;
                rightSprite.resetImage();
                ticker = 0;
            }
            newPosX = posX+4;
        } else if (Input.get().left() && !Input.get().right()) {
            if (currentSprite != leftSprite) {
                currentSprite.resetImage();
                currentSprite = leftSprite;
                leftSprite.resetImage();
                ticker = 0;
            }
            newPosX = posX-4;
        } else {
            if (currentSprite != standSpriteRight && currentSprite != standSpriteLeft) {
                currentSprite.resetImage();
                // if the guy was moving right, he should be face right when stopped
                if (pposX < posX) {
                    currentSprite = standSpriteRight;
                    standSpriteRight.resetImage();
                } else { // last moved left, animation should be inverted
                    currentSprite = standSpriteLeft;
                    standSpriteLeft.resetImage();
                }
                ticker = 0;
            }
        }

        // Handle falling
        if (posY<500) {
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
    public boolean collision(Entity entity) {
        return false; // yet to be implemented
    }

    @Override
    public void renderHitBox(Graphics g, double x, double y) {

        double posX = this.posX - x;
        double posY = this.posY - y;

        g.drawRect((int)posX, (int)posY, this.getWidth(), this.getHeight());
    }
}
