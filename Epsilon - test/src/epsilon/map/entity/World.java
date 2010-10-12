package epsilon.map.entity;


/**
 *
 * @author vz
 */
public abstract class World extends Entity {


    public World(int posX,int posY) {
        super(posX,posY);
    }

    @Override
    public void move() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void collided(boolean[] hitBox, Entity collidedWith) {
    }


}
