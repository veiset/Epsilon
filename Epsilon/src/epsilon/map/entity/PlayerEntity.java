package epsilon.map.entity;

import epsilon.game.Collision;
import epsilon.game.Input;
import epsilon.game.Physics;
import epsilon.game.Sprite;
import epsilon.map.Map;
import epsilon.map.ShotStore;
import java.awt.Graphics;

/**
 * Entity describing the person playing on this computer
 *
 * @author md
 */
public class PlayerEntity extends MoveableEntity {

    /**
     * Ticker used to counting when the picture of the sprite should be changed.
     */
    protected int spriteTicker;

    /**
     * The Sprite used when moving right
     */
    protected Sprite rightSprite;

    /**
     * The Sprite used when not moving, but was last moving to the right
     */
    protected Sprite standSpriteRight;

    /**
     * The Sprite used when moving left
     */
    protected Sprite leftSprite;

    /**
     * The Sprite used when not moving, but the entity was last moving to the left
     */
    protected Sprite standSpriteLeft;

    /**
     * Is true if the entity was last facing to the right
     */
    protected boolean facingRight = true;

    // used for checking if the entity can jump
    private boolean touchesGround;
    // the name of the player
    private String name;
    private double origPosX;
    private double origPosY;
    
    private boolean isDead = false;

    private ShotStore shots;
    private int shotTimer;
    private int lastShot;

    private int hp;

    /**
     * Constructor for the entity that initialises sprites
     *
     * @param posX The starting X position of the entity
     * @param posY The starting Y position of the entity
     * @param name The name of the player
     * @param map The map used by this Entity
     * @param setSprites is true if this Entity should use the standard Sprites
     */
    public PlayerEntity(double posX, double posY, String name, Map map, boolean setSprites) {
        super(posX, posY, map);
        spriteTicker = 0;
        touchesGround = false;
        origPosX = posX;
        origPosY = posY;

        this.name = name;
        
        if (setSprites) {
            // Create the different sprites used in this entity, and assign them hitboxes
            HitBox[] hitbox = new HitBox[1];

            hitbox[0] = new HitBox(37, 28, 20, 63);


            rightSprite = new Sprite(new String[]{"/pics/guy01.png", "/pics/guy02.png", "/pics/guy03.png", "/pics/guy04.png", "/pics/guy05.png"}, false, hitbox);
            standSpriteRight = new Sprite(new String[]{"/pics/guy01.png"}, false, hitbox);
            leftSprite = new Sprite(new String[]{"/pics/guy01.png", "/pics/guy02.png", "/pics/guy03.png", "/pics/guy04.png", "/pics/guy05.png"}, true, hitbox);
            standSpriteLeft = new Sprite(new String[]{"/pics/guy01.png"}, true, hitbox);

            currentSprite = standSpriteRight;
        }

        shots = new ShotStore(mapReference);
        lastShot = 0;
        shotTimer = 0;
        hp = 100;
    }

    @Override
    public void calculateMovement() {

        // handle input, and chose the right sprite for the job
        newPosX = posX;
        newPosY = posY;

        shotTimer++;

        // checking if the player is dead
        if (!isDead) {
            if (Input.get().right() && !Input.get().left()) {
                if (currentSprite != rightSprite) {
                    newPosX += (currentSprite.getOffset());
                    currentSprite.resetImage();
                    currentSprite = rightSprite;
                    rightSprite.resetImage();
                    spriteTicker = 0;
                    facingRight = true;
                }
                newPosX += 4;
            } else if (Input.get().left() && !Input.get().right()) {
                if (currentSprite != leftSprite) {
                    newPosX += (currentSprite.getOffset());
                    currentSprite.resetImage();
                    currentSprite = leftSprite;
                    leftSprite.resetImage();
                    spriteTicker = 0;
                    facingRight = false;
                }
                newPosX -= 4;
            } else {
                if (currentSprite != standSpriteRight && currentSprite != standSpriteLeft) {
                    currentSprite.resetImage();
                    // if the guy was moving right, he should be face right when stopped
                    if (currentSprite == rightSprite) {
                        currentSprite = standSpriteRight;
                        standSpriteRight.resetImage();
                        facingRight = true;
                    } else { // last moved left, animation should be inverted
                        currentSprite = standSpriteLeft;
                        standSpriteLeft.resetImage();
                        facingRight = false;
                    }
                    spriteTicker = 0;
                }
            }

            // shots
            if (Input.get().attack() && shotTimer - lastShot > 30) {
                //sound.close();
                addShot(0);
            }
            updateShots();


            // checking if the player has falled down below the floor threshold.
            // If player posY is larger or equal to 598, the player dies.
            if (posY >= 598) {
                isDead = true;
            } // Handle falling
            else if (posY < 600 && !touchesGround) {
                double temp = Physics.calculateGravity(posY, pposY, 16);
                newPosY = posY - temp;
            } else if (Input.get().jump()) {
                // if it touches the ground, jump!
                newPosY -= 7;
            }

            // go to the next picture in the sprite if it is time
            if (spriteTicker < 5) {
                spriteTicker++;
            } else {
                spriteTicker = 0;
                currentSprite.nextImage();
            }

            touchesGround = false;
        }
    }

    public boolean facingRight() {
        return facingRight;
    }

    @Override
    public double getXRenderPosition() {
        return posX - 400 + currentSprite.getWidth() / 2;
    }

    @Override
    public double getYRenderPosition() {
        //return posY - 300 + currentSprite.getHeight()/2;
        return 0;
    }

    @Override
    public void renderHitBox(Graphics g, double x, double y) {

        double posX = this.posX - x;
        double posY = this.posY - y;

        g.drawRect((int) posX, (int) posY, this.getWidth(), this.getHeight());

        HitBox[] hitbox = currentSprite.getHitBox();

        for (int i = 0; i < hitbox.length; i++) {
            hitbox[i].draw(g, posX, posY);
        }
    }

    @Override
    public void collided(Collision c) {

        if (c.collidedWith instanceof World || c.collidedWith instanceof NetworkEntity || c.collidedWith instanceof Enemy) {

            // overlap between the two entities in pixels
            double dty = c.deltaTop;
            double dby = c.deltaBottom;

            // movement if this entity collides on the left side of something
            if (c.crossedLeft && pposX < posX && dty > 8 && dby > 6) {
                newPosX = pposX;
                c = c.collidingEntity.collision(this);
            } else if (c.crossedRight && pposX > posX && dty > 8 && dby > 6) {
                newPosX = pposX;
                c = c.collidingEntity.collision(this);
            } else if (c.crossedTop && posY > pposY) {
                newPosY -= dty;
                posY = newPosY;
                touchesGround = true;
            } else if (c.crossedBottom && posY < pposY) {
                newPosY = pposY;
            }
        } else if (c.collidedWith instanceof Shot && ((Shot) c.collidedWith).getShooter() != this) {
            hp -= 20;
            if (hp <= 0) {
                isDead = true;
            }
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

    /**
     * Resets the PlayerEntity to the original position, and resets momentum to 0
     */
    public void resetPosition() {
        posX = origPosX;
        newPosX = origPosX;
        pposX = origPosX;

        posY = origPosY;
        newPosY = origPosY;
        pposY = origPosY;

        isDead = false;
        hp = 100;
    }

    /**
     * Resets the PlayerEntity to the specified position, and resets momentum to 0
     */
    public void resetPosition(double xPos, double yPos) {
        posX = xPos;
        newPosX = xPos;
        pposX = xPos;

        posY = yPos;
        newPosY = yPos;
        pposY = yPos;

        isDead = false;
        hp = 100;
    }

    /**
     * Checks if the player isDead
     *
     * @return true if the player is dead
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * The tick when the Player last fired a shot
     *
     * @return an int specifying the last tick when this player fired a shot
     */
    public int lastShot() {
        return lastShot;
    }

    /**
     * Fires a shot from this Player
     *
     * @param lastShot an int specifying when the last shot occured, if it is 0, it uses the current tick from this clients ticker
     */
    protected void addShot(int lastShot) {
        if (facingRight) {
            shots.addShot(posX + 75, posY + 45, facingRight, this, mapReference);
        } else {
            shots.addShot(posX + 15, posY + 45, facingRight, this, mapReference);

        }
        if (lastShot != 0) {
            this.lastShot = lastShot;
        } else {
            this.lastShot = shotTimer;
        }
    }

    /**
     * Updates the shots, checks if they should have been removed
     */
    protected void updateShots() {
        shots.update();
    }

    /**
     * Gets the current health of the player
     *
     * @return an int between 0 and 100 indicating the health in a %
     */
    public int getHp() {
        return hp;
    }

}
