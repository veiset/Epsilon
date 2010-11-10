package epsilon.map;

import epsilon.game.Collision;
import epsilon.game.SoundPlayer;
import epsilon.map.entity.Entity;
import epsilon.map.entity.MoveableEntity;
import epsilon.map.entity.Shot;
import epsilon.map.entity.TestPlayerEntity;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * An interface describing the methods that need to be in a map
 *
 * @author Marius
 */
public abstract class Map {

    /*
     * The different lists used for storing entities
     * There are a number of them to keep the iterating over lists to a minimum
     */
    protected ArrayList<Entity> renderableEntities;
    protected ArrayList<MoveableEntity> moveableEntities;
    protected ArrayList<Entity> entities;
    protected WorldStore worldstore;

    // the soundtrack that is played continuously while playing the map
    protected SoundPlayer soundtrack;

    // the entity of the player played on this computer
    protected TestPlayerEntity playerEntity;

    // the background object that is displayed on the map
    protected Background bg;


    public Map(String s) {

        initialiseStatic();
        initialiseNonStatic(s);

    }

    /**
     * Method that renders entities in the map onto the specified graphics
     *
     * @param g the graphics object things should be rendered to.
     * @param delta where you are between gameupdates. Makes sure you move smoothly
     */
    public synchronized void render(Graphics g, int delta) {

        bg.render(g, playerEntity.getXPosition(), playerEntity.getYPosition());

        worldstore.renderAll(g, delta, playerEntity.getXRenderPosition(), playerEntity.getYRenderPosition());

        Entity[] temp = new Entity[renderableEntities.size()];
        renderableEntities.toArray(temp);

        for (int i = 0; i < temp.length; i++) {
            temp[i].render(g, delta, playerEntity.getXRenderPosition(), playerEntity.getYRenderPosition());
            //temp[i].renderHitBox(g, playerEntity.getXRenderPosition(), playerEntity.getYRenderPosition());
        }

    }

    /**
     * Moves the entities on the map
     */
    public void update() {

        MoveableEntity[] temp = new MoveableEntity[moveableEntities.size()];
        moveableEntities.toArray(temp);

        Collision c;

        for (int i = 0; i < temp.length; i++) {
            temp[i].calculateMovement();
            worldstore.checkCollision(temp[i]);
            for (int j = 0; j < temp.length; j++) {
                if (i != j) {
                    c = temp[j].collision(temp[i]);
                    if (c.collided) {
                        temp[i].collided(c);
                    }
                }
            }
        }

        for (int i = 0; i < temp.length; i++) {
            temp[i].move();
        }

    }

    /**
     * Returns the position of the player currently playing the game
     *
     * @return a double array with a length of two, containing the x and y position
     */
    public double[] getPlayerState() {
        return new double[]{playerEntity.getXPosition(), playerEntity.getYPosition()};
    }

    /**
     * 
     */
    public void resetPlayerPosition() {
        playerEntity.resetPosition();
    }

    public synchronized void reset() {

        soundtrack.close();
        String s = playerEntity.getName();
        resetNonStatic();
        initialiseNonStatic(s);
        
    }

    public boolean isDead() {
        if (playerEntity  != null) {
            return playerEntity.isDead();
        }
        return false;
    }

    protected void initialiseNonStatic(String s) {

        entities = new ArrayList<Entity>();
        renderableEntities = new ArrayList<Entity>();
        moveableEntities = new ArrayList<MoveableEntity>();

    }

    protected void initialiseStatic() {
        
        worldstore = new WorldStore(50);

    }

    protected void resetNonStatic() {
        renderableEntities = null;
        moveableEntities = null;
        entities = null;

        bg = null;
        playerEntity = null;
        soundtrack = null;
    }

    public synchronized void addShot(Shot s) {
        renderableEntities.add(s);
        moveableEntities.add(s);
    }

    public synchronized void removeShot(Shot s) {
        renderableEntities.remove(s);
        moveableEntities.remove(s);
    }

}
