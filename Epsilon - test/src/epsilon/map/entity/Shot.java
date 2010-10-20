package epsilon.map.entity;

import epsilon.game.SoundPlayer;
import epsilon.game.Sprite;
import java.awt.Graphics;

/**
 *
 * @author vz
 */
public class Shot extends MoveableEntity {

    private boolean headingRight = false;
    private double distanceTravled = 0;
    private double distanceMax = 300;
    private int speed = 9;

    public Shot(double posX, double posY, boolean headingRight) {
        super(posX, posY);

        HitBox[] h = new HitBox[1];
        h[0] = new HitBox(0, 0, 14, 5);

        // the initial position of the shot, in relation to the player that shot it.
        if (headingRight) {
            newPosX += 80;
            currentSprite = new Sprite(new String[]{"/pics/smallbullet.png"},false,h);
        } else {
            newPosX += 15;
            currentSprite = new Sprite(new String[]{"/pics/smallbullet.png"},true,h);
        }
        newPosY += 45;
        this.headingRight = headingRight;


        // gunfire
        new SoundPlayer("/sound/gunshot.mp3").play();

    }

    @Override
    public void renderHitBox(Graphics g, double x, double y) {
        double renderPosX = this.posX - x;
        double renderPosY = this.posY - y;

        g.drawRect((int) renderPosX, (int) renderPosY, this.getWidth(), this.getHeight());
    }

    @Override
    public boolean[] collision(Entity entity) {
        return new boolean[]{false, false, false, false, false};
    }

    @Override
    public void collided(boolean[] hitbox, Entity collidedWith) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void calculateMovement() {
        if (headingRight) {
            newPosX += speed;
        } else {
            newPosX -= speed;
        }
        distanceTravled += speed;
    }

    @Override
    public boolean facingRight() {
        return headingRight;
    }

    /**
     * Checking if the shot has traveled the max distance allowed.
     *
     * @return true if it has overgone the maximum distance
     */
    public boolean distanceDone() {
        return (distanceTravled > distanceMax);
    }
}
