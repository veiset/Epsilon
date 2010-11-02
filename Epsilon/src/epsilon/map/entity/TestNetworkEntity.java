package epsilon.map.entity;

import epsilon.game.Collision;
import epsilon.game.Sprite;
import epsilon.net.NetworkHandler;
import java.awt.Graphics;

/**
 *
 * @author Marius
 */
public class TestNetworkEntity extends MoveableEntity {

    // keeps track of when to change pictures in the sprite
    protected int ticker;

    // used for checking if the entity can jump
    private boolean touchesGround;

    // the name of the player
    private String name;

    // the different sprites this entity uses
    protected Sprite rightSprite;
    protected Sprite leftSprite;
    protected boolean facingRight = true;
    private boolean isDead = false;

    /**
     * Sets the name and position variables of the entity
     *
     * @param posX the X-axis position of the entity
     * @param posY the Y-axis position of the entity
     * @param playerName the name of the entity
     */
    public TestNetworkEntity(double posX, double posY, String playerName) {

        super(posX, posY);
        ticker = 0;
        touchesGround = false;

        this.name = playerName;

        // Create the different sprites used in this entity, and assign them hitboxes
        HitBox[] hitbox = new HitBox[3];

        hitbox[0] = new HitBox(37, 75, 17, 16);
        hitbox[1] = new HitBox(45,46,5,29);
        hitbox[2] = new HitBox(36,28,19,18);

        String[] s = new String[]{"/pics/sheep_enemy.png"};

        rightSprite = new Sprite(s, false, hitbox);
        leftSprite = new Sprite(s, true, hitbox);

        currentSprite = rightSprite;

    }

    @Override
    public void calculateMovement() {

        newPosX = posX;
        newPosY = posY;

        double[] d = NetworkHandler.getInstance().getPlayerPositionByName(name);
        newPosX = d[0];
        newPosY = d[1];

        //System.out.println("posX: " + newPosX + " posY: " + newPosY);

        if (newPosX > posX) {
            if (currentSprite != rightSprite) {
                currentSprite.resetImage();
                currentSprite = rightSprite;
                rightSprite.resetImage();
                ticker = 0;
                facingRight = true;
            }
        } else if (newPosX < posX) {
            if (currentSprite != leftSprite) {
                currentSprite.resetImage();
                currentSprite = leftSprite;
                leftSprite.resetImage();
                ticker = 0;
                facingRight = false;
            }
        }

        // go to the next picture in the sprite if it is time
        if (ticker < 5) {
            ticker++;
        } else {
            ticker = 0;
            currentSprite.nextImage();
        }
    }

    public boolean facingRight() {
        return facingRight;
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
    public Collision collision(Entity entity) {
        return new Collision(); // yet to be implemented
    }

    @Override
    public void collided(Collision c) {
        //
    }

    @Override
    public synchronized void renderHitBox(Graphics g, double x, double y) {

        double posX = this.posX - x;
        double posY = this.posY - y;

        System.out.println("X: " + posX + " " + this.posX + " Y: " + posY + " " + this.posY);

        g.drawRect((int)posX, (int)posY, this.getWidth(), this.getHeight());

        HitBox[] hitbox = currentSprite.getHitBox();

        for (int i=0;i<hitbox.length;i++) {
            hitbox[i].draw(g, posX, posY);
        }
    }

    /**
     * Getter method for the name
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

}
