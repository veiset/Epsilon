package epsilon.map.entity;

import epsilon.game.Collision;
import epsilon.game.Sprite;
import epsilon.map.Map;
import epsilon.net.NetworkHandler;

/**
 * Entity used to keep track of network players. Contains information about
 * position, color and name of the players. Should be refactored at some later time
 *
 * @author Marius
 */
public class NetworkEntity extends PlayerEntity {

    private boolean exist = true;

    /**
     * Initialises a new NetworkEntity
     *
     * @param posX the X-axis position of the entity
     * @param posY the Y-axis position of the entity
     * @param playerName the name of the entity
     * @param map the Map this player is playing on
     * @param color the Color this person should be shown with
     */
    public NetworkEntity(double posX, double posY, String playerName, Map map, double color) {

        super(posX, posY, playerName, map, false);
        
        String s, folder;

        HitBox[] hitbox = new HitBox[]{new HitBox(37, 28, 20, 63)};

        if (color == 1) {
            s = "_red";
            folder = "guy/red/";
        } else if (color == 2) {
            s = "_blue";
            folder = "guy/blue/";
        } else if (color == 3) {
            s = "_pink";
            folder = "guy/pink/";
        } else if (color == 4) {
            s = "_green";
            folder = "guy/green/";
        } else {
            s = "";
            folder = "";
        }

        System.out.println("/pics/" + folder + "guy01" + s + ".png");

            rightSprite = new Sprite(new String[]{"/pics/" + folder + "guy01" + s + ".png", "/pics/" + folder + "guy02" + s + ".png", "/pics/" + folder + "guy03" + s + ".png", "/pics/" + folder + "guy04" + s + ".png", "/pics/" + folder + "guy05" + s + ".png"}, false, hitbox);
            standSpriteRight = new Sprite(new String[]{"/pics/" + folder + "guy01" + s + ".png"}, false, hitbox);
            leftSprite = new Sprite(new String[]{"/pics/" + folder + "guy01" + s + ".png", "/pics/" + folder + "guy02" + s + ".png", "/pics/" + folder + "guy03" + s + ".png", "/pics/" + folder + "guy04" + s + ".png", "/pics/" + folder + "guy05" + s + ".png"}, true, hitbox);
            standSpriteLeft = new Sprite(new String[]{"/pics/" + folder + "guy01" + s + ".png"}, true, hitbox);

            currentSprite = standSpriteRight;

    }

    @Override
    public void calculateMovement() {

        newPosX = posX;
        newPosY = posY;

        double[] d = NetworkHandler.getInstance().getPlayerStateByName(super.getName());
        if (d == null) {
            exist = false;
            return;
        }

        if (lastShot() < d[2]) {
            addShot((int)d[2]);
        }

        super.updateShots();

        newPosX = d[0];
        newPosY = d[1];

        if (newPosX > posX) {
            if (currentSprite != rightSprite) {
                currentSprite.resetImage();
                currentSprite = rightSprite;
                rightSprite.resetImage();
                spriteTicker = 0;
                facingRight = true;
            }
        } else if (newPosX < posX) {
            if (currentSprite != leftSprite) {
                currentSprite.resetImage();
                currentSprite = leftSprite;
                leftSprite.resetImage();
                spriteTicker = 0;
                facingRight = false;
            }
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

        // go to the next picture in the sprite if it is time
        if (spriteTicker < 5) {
            spriteTicker++;
        } else {
            spriteTicker = 0;
            currentSprite.nextImage();
        }
    }

    @Override
    public void collided(Collision c) {
        //
    }

    /**
     * Method for checking if the Network entity should still exist
     *
     * @return true if the person should still be here
     */
    public boolean exists() {
        return exist;
    }

}
