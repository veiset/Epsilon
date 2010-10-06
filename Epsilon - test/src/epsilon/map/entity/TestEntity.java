package epsilon.map.entity;

import epsilon.game.Input;
import epsilon.game.Physics;
import epsilon.game.Sprite;
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
        int newPosX = posX;
        int newPosY = posY;

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
            newPosX = posX -4;
        } else {
            if (currentSprite != standSprite) {
                currentSprite.resetImage();
                currentSprite = standSprite;
                standSprite.resetImage();
                ticker = 0;
            }
        }

        if (posY-currentSprite.getHeight()<50) {
            //newPosY = posY + 5;
            newPosY = posY - Physics.calculateGravity(posY, pposY, 16);
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

    /*
     * Rendering the object
     *
     * @param g The graphic object the entity is to be drawn on
     */
    public void render(Graphics g, int delta) {
        float coeff = (float)delta/16;

        /**
         * Debugging my smoothing out movement method.
         */
        /*
        System.out.println("Delta: "+delta+" Coefficient: "+coeff);
        System.out.println("X: "+posX+" Y: "+posY+" Real X: "+Math.round(posX+(posX-pposX)*coeff)+" Real Y: "+Math.round(posY+(posY-pposY)*coeff)+" Non-rounded X&Y"+(posX+(posX-pposX)*coeff)+" "+(posY+(posY-pposY)*coeff));
         */
        currentSprite.draw(g, Math.round(posX+(posX-pposX)*coeff), Math.round(posY+(posY-pposY)*coeff));
    }

}
