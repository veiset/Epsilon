package epsilon.map;
import epsilon.map.entity.Entity;
import epsilon.map.entity.Shot;
import java.util.ArrayList;

/**
 * Class for managing shot objects.
 *
 * @author vz
 */
public class ShotStore {


    private ArrayList<Shot> shots;
    private Map m;

    public ShotStore(Map m) {
        shots = new ArrayList<Shot>();
        this.m = m;
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
                m.removeShot(shot);
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
    public void addShot(double xPos, double yPos, boolean facingRight, Entity ent, Map m) {
        Shot shot = new Shot(xPos, yPos, facingRight, ent, m);
        shots.add(shot);
        m.addShot(shot);
    }
}
