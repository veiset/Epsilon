package epsilon.map.entity;

import epsilon.game.Collision;

/**
 *
 * @author vz
 */
public class EnemyPatrol extends Enemy {

    public EnemyPatrol(int posX, int posY) {
        super(posX,posY);
    }

    @Override
    public void collided(Collision c) {
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