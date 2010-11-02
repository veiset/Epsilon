package epsilon.map.entity;

import epsilon.game.Collision;
import epsilon.game.Physics;
import epsilon.game.Sprite;

/**
 * Artificial intelligence for a simple enemy-patrol. This enemy walks right
 * and left, and thats about it.
 *
 * @author vz
 */
public class EnemyPatrol extends Enemy {

    private int startXpos;
    private boolean facingRight;
    private HitBox[] hitbox;
    private Sprite spriteFacingLeft;
    private Sprite spriteFacingRight;

    /**
     * EnemyPatrol with starting position, enemy walking 100 pixels to the left
     * and then 100 pixels to the right. Then repeat.
     *
     * @param posX enemy starting posX
     * @param posY enemy starting posY
     */
    public EnemyPatrol(int posX, int posY) {
        super(posX, posY);
        HitBox[] hitbox = new HitBox[1];
        hitbox[0] = new HitBox(0, 0, 100, 100);
        spriteFacingLeft = new Sprite(new String[]{"/pics/sheep_enemy.png"}, false, hitbox);
        spriteFacingRight = new Sprite(new String[]{"/pics/sheep_enemy.png"}, true, hitbox);
        currentSprite = spriteFacingRight;
        startXpos = posX;
        facingRight = true;

    }

    @Override
    public void collided(Collision c) {
    }

    @Override
    public void calculateMovement() {

        // applying gravity!
        if (posY < 480) {
            double temp = Physics.calculateGravity(posY, pposY, 16);
            newPosY = posY - temp;
        }

        // very basic AI
        if (facingRight && (posX < startXpos + 100)) {
            newPosX = posX + 1;
        } else if (facingRight) {
            facingRight = false;
            currentSprite = spriteFacingLeft;
        }
        if (!facingRight && (posX > startXpos - 100)) {
            newPosX = posX - 1;
        } else if (!facingRight) {
            facingRight = true;
            currentSprite = spriteFacingRight;
        }
    }

    @Override
    public boolean facingRight() {
        return true;
    }
}
