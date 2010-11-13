package epsilon.map.entity;

import epsilon.game.Collision;
import epsilon.game.Sprite;
import epsilon.map.Map;
import java.awt.Graphics;

/**
 * Gunshot objects.
 *
 * @author vz
 */
public class Shot extends MoveableEntity {

    private boolean headingRight = false;
    private double distanceTravled = 0;
    private double distanceMax = 600;
    private int speed = 12;
    private Entity shotBy;

    /**
     * Gunshot objects.
     *
     * @param posX start position X
     * @param posY start position Y
     * @param headingRight what direction the object is heading
     */
    public Shot(double posX, double posY, boolean headingRight, Entity shooter, Map m) {
        super(posX, posY, m);

        HitBox[] h = new HitBox[1];
        h[0] = new HitBox(0, 0, 14, 5);

        // the initial position of the shot, in relation to the player that shot it.
        if (headingRight) {
            currentSprite = new Sprite(new String[]{"/pics/smallbullet.png"},false,h);
        } else {
            currentSprite = new Sprite(new String[]{"/pics/smallbullet.png"},true,h);
        }
        this.headingRight = headingRight;

        shotBy = shooter;

    }

    @Override
    public void renderHitBox(Graphics g, double x, double y) {
        double renderPosX = this.posX - x;
        double renderPosY = this.posY - y;

        g.drawRect((int) renderPosX, (int) renderPosY, this.getWidth(), this.getHeight());
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

    @Override
    public void collided(Collision c) {
        // if collieded the dinstance is done, and the shot is removed
        if (!shotBy.equals(c.collidedWith) && !(c.collidedWith instanceof Shot )) {
            distanceTravled = distanceMax+1;
        }
    }

    /**
     * The Entity that fired this shot
     *
     * @return the entity that fired this shot
     */
    public Entity getShooter() {
        return shotBy;
    }

    /**
     * Makes sure that this shot is removed the next time the update method is run.
     */
    public void remove() {
        distanceTravled = distanceMax+1;
    }

}
