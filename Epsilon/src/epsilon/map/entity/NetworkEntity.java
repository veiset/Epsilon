package epsilon.map.entity;

/**
 * 
 *
 * @author Marius
 */
public class NetworkEntity extends PlayerEntity {

    // the name of the network player. used for lookup in the network class
    private String name;

    /**
     * Sets the name and position variables of the entity
     *
     * @param posX the X-axis position of the entity
     * @param posY the Y-axis position of the entity
     * @param playerName the name of the entity
     */
    public NetworkEntity(int posX, int posY, String playerName) {

        super(posX, posY);

        this.name = playerName;

    }

    @Override
    public void calculateMovement() {

        newPosX = posX;
        newPosY = posY;

        // TODO: get new position from the network class, and place it in newPosX and newPosY

        if (newPosX > posX) {
            if (currentSprite != rightSprite) {
                newPosX += (currentSprite.getOffset());
                currentSprite.resetImage();
                currentSprite = rightSprite;
                rightSprite.resetImage();
                ticker = 0;
                facingRight = true;
            }
        } else if (newPosX < posX) {
            if (currentSprite != leftSprite) {
                newPosX += (currentSprite.getOffset());
                currentSprite.resetImage();
                currentSprite = leftSprite;
                leftSprite.resetImage();
                ticker = 0;
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
                ticker = 0;
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
}
