package epsilon.map;

import epsilon.game.Collision;
import epsilon.map.entity.Enemy;
import epsilon.map.entity.Entity;
import epsilon.map.entity.MoveableEntity;
import epsilon.map.entity.PlayerEntity;
import epsilon.map.entity.Shot;
import java.util.ArrayList;

/**
 * Class for managing shot objects.
 *
 * @author vz
 */
public class ShotStore {


    private ArrayList<Shot> shots;

    // referances to the list of renderable and movable entities.
    private ArrayList<Entity> renderEntitiesRef;
    private ArrayList<MoveableEntity> moveableEntitiesRef;

    public ShotStore() {
        shots = new ArrayList<Shot>();
    }

    /**
     * Creating a shotstore
     *
     * @param renderableEntities referance to arraylist with renderable entities
     * @param moveableEntities referance to arraylist with movable entities
     */
    public ShotStore(ArrayList<Entity> renderableEntities, ArrayList<MoveableEntity> moveableEntities) {
        // copying the referances
        renderEntitiesRef = renderableEntities;
        moveableEntitiesRef = moveableEntities;

        shots = new ArrayList<Shot>();
    }

    /**
     * Updating the shots, checking if any shot has traveled their maximum
     * dinstance.
     */
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

    /**
     * Checking if the shot has collided with a movableEntity.
     *
     * @param e MoveableEntity to check collision against.
     */
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

    /**
     * Create a new shot
     *
     * @param xPos current player x position
     * @param yPos current player y position
     * @param facingRight true if player is facing right
     */
    public void addShot(double xPos, double yPos, boolean facingRight) {
        Shot shot = new Shot(xPos, yPos, facingRight);
        shots.add(shot);
        moveableEntitiesRef.add(shot);
        renderEntitiesRef.add(shot);
    }
}
