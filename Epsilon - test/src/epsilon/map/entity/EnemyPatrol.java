package epsilon.map.entity;

/**
 *
 * @author vz
 */
public class EnemyPatrol extends Enemy {

    public EnemyPatrol(int posX, int posY) {
        super(posX,posY);
    }

    @Override
    public void collided(boolean[] hitbox, Entity collidedWith) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void calculateMovement() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean facingRight() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}