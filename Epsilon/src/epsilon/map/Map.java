package epsilon.map;

import epsilon.game.Collision;
import epsilon.game.SoundPlayer;
import epsilon.map.entity.Enemy;
import epsilon.map.entity.Entity;
import epsilon.map.entity.MoveableEntity;
import epsilon.map.entity.Shot;
import epsilon.map.entity.PlayerEntity;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * An abstract handling the basic methods that need to be in a map
 * The methods initialiseStatic and initialiseNonStatic should be overriden
 * and start with a call to super, so that all the fields are initialised
 *
 * @author Marius
 */
public abstract class Map {

    /**
     * ArrayList containing all the renderable enteties on the map
     */
    protected ArrayList<Entity> renderableEntities;

    /**
     * ArrayList containing all the moveableEntities on the map
     * All enteties that is added here must extend the MoveableEntity class
     *
     * @see epsilon.map.entity.MoveableEntity
     */
    protected ArrayList<MoveableEntity> moveableEntities;

    /**
     * Complete list off all the Entity objects on the map
     *
     * @see epsilon.map.entity.Entity
     */
    protected ArrayList<Entity> entities;

    /**
     * List used for storing objects of the Class world
     *
     * @see epsilon.map.WorldStore
     * @see epsilon.map.entity.World
     */
    protected WorldStore worldstore;

    /**
     * the soundtrack that is played continuously while playing the map
     *
     * @see epsilon.game.SoundPlayer
     */
    protected SoundPlayer soundtrack;
    protected boolean mute = false;
    /**
     * the entity of the player played on this computer
     *
     * @see epsilon.map.entity.PlayerEntity
     */
    protected PlayerEntity playerEntity;

    /**
     * the background object that is displayed on the map
     *
     * @see epsilon.map.Background
     */
    protected Background bg;


    /**
     * Constructor for the class map.
     *
     * @param name the name of the player playing the map
     */
    public Map(String name) {

        initialiseStatic();
        initialiseNonStatic(name);

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

        for (int i = 0; i < temp.length;i++) {
            if (temp[i] instanceof Enemy) {
                Enemy e = (Enemy) temp[i];
                if (e.isDead()) {
                    renderableEntities.remove(temp[i]);
                    moveableEntities.remove(temp[i]);
                }
            }
        }

    }

    /**
     * Update method that is run while the menu is showing.
     */
    public void updateWhileMenu() {
        
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
     * Resets the player position to the spawn position
     */
    public void resetPlayerPosition() {
        playerEntity.resetPosition();
    }

    /**
     * Resets the map to start values, including player, enemies etc
     */
    public synchronized void reset() {

        soundtrack.close();
        String s = playerEntity.getName();
        resetNonStatic();
        initialiseNonStatic(s);
        
    }

    /**
     * Checks if the player is dead
     *
     * @return true if the player is dead
     */
    public boolean isDead() {
        if (playerEntity  != null) {
            return playerEntity.isDead();
        }
        return false;
    }

    /**
     * Initialises the nonStatic entities on the map
     *
     * @param name the name of the player currently on the map
     */
    protected void initialiseNonStatic(String name) {
        entities = new ArrayList<Entity>();
        renderableEntities = new ArrayList<Entity>();
        moveableEntities = new ArrayList<MoveableEntity>();
    }

    /**
     * Initialises the static entities on the map
     */
    protected void initialiseStatic() {    
        worldstore = new WorldStore(50);
    }

    /**
     * Resets the non static entities on the map
     */
    protected void resetNonStatic() {
        renderableEntities = null;
        moveableEntities = null;
        entities = null;

        bg = null;
        playerEntity = null;
        soundtrack = null;
    }

    /**
     * Adds a new shot to the map, should probably be renamed
     *
     * @param shot the Shot object that should be added to the map
     * @see epsilon.map.entity.Shot
     */
    public synchronized void addShot(Shot shot) {
        if (!mute) {
            // gunfire
            new SoundPlayer("/sound/gunshot.mp3").play();
        }
        renderableEntities.add(shot);
        moveableEntities.add(shot);
    }

    /**
     * Removes a specific Shot object from the map
     *
     * @param shot the Shot object to be removed
     * @see epsilon.map.entity.Shot
     */
    public synchronized void removeShot(Shot shot) {
        renderableEntities.remove(shot);
        moveableEntities.remove(shot);
    }

    /**
     * Returns the Health Points of the current PlayerEntity
     *
     * @return the current HP as an int
     */
    public int getPlayerHp() {
        return playerEntity.getHp();
    }

    public void mute(boolean mute) {
        this.mute = mute;
        if (mute) {
            soundtrack.close();
        }
        else {
            soundtrack.play();
        }
    }

}
