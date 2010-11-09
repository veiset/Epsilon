package epsilon.map;

import epsilon.game.Collision;
import epsilon.map.entity.Enemy;
import epsilon.map.entity.Entity;
import epsilon.map.entity.MoveableEntity;
import epsilon.map.entity.PlayerEntity;
import epsilon.map.entity.Shot;
import java.util.ArrayList;

/**
 *
 * @author vz
 */
public class ShotStore {

    private ArrayList<Shot> shots;
    private ArrayList<Entity> renderEntitiesRef;
    private ArrayList<MoveableEntity> moveableEntitiesRef;

    public ShotStore() {
        shots = new ArrayList<Shot>();
    }

    public ShotStore(ArrayList<Entity> renderableEntities, ArrayList<MoveableEntity> moveableEntities) {
        renderEntitiesRef = renderableEntities;
        moveableEntitiesRef = moveableEntities;
        shots = new ArrayList<Shot>();
    }

    public void update() {

        // creating temp array to avoid unexpected behaviour when removing elements
        Shot[] tempshot = new Shot[shots.size()];
        shots.toArray(tempshot);

        // checking each shot if it has traveled its distance
        for (int i = 0; i < tempshot.length; i++) {
            Shot shot = tempshot[i];

            if (shot.distanceDone()) {
                // removing the shot from the lists
                shots.remove(shot);
                moveableEntitiesRef.remove(shot);
                renderEntitiesRef.remove(shot);
            }
        }
    }

    public void checkCollided(MoveableEntity e) {

        if (e instanceof Enemy || e instanceof PlayerEntity) {
            Shot[] tempshot = new Shot[shots.size()];
            shots.toArray(tempshot);

            for (int i = 0; i < tempshot.length; i++) {
                Shot shot = tempshot[i];
                Collision c = shot.collision(e);

                if (c.collided) {
                    e.collided(c);
                    shots.remove(shot);
                    moveableEntitiesRef.remove(shot);
                    renderEntitiesRef.remove(shot);
                }
            }
        }
    }

    public void addShot(double xPos, double yPos, boolean facingRight) {
        Shot shot = new Shot(xPos, yPos, facingRight);
        shots.add(shot);
        moveableEntitiesRef.add(shot);
        renderEntitiesRef.add(shot);
    }
}
