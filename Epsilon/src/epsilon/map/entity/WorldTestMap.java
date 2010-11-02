/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package epsilon.map.entity;

import epsilon.game.Input;
import epsilon.game.SoundPlayer;
import epsilon.map.Background;
import epsilon.map.Map;
import epsilon.map.WorldStore;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Marius
 */
public class WorldTestMap implements Map {

    /*
     * The different lists used for storing entities
     * There are a number of them to keep the iterating over lists to a minimum
     */
    ArrayList<Entity> renderableEntities;
    ArrayList<MoveableEntity> moveableEntities;
    ArrayList<Entity> entities;
    ArrayList<Shot> shots;
    WorldStore worldstore;

    // the soundtrack that is played continuously while playing the map
    SoundPlayer soundtrack;

    // the entity of the player played on this computer. Used for calculating rendering positions
    PlayerEntity playerEntity;

    // the background object used on this map
    Background bg;

    private int shotCooldown = 0;

    /**
     * Initialises all entities on the map, and all fields in the object
     */
    public WorldTestMap() {

        renderableEntities = new ArrayList<Entity>();
        moveableEntities = new ArrayList<MoveableEntity>();
        entities = new ArrayList<Entity>();
        shots = new ArrayList<Shot>();
        worldstore = new WorldStore(50);

        bg = new Background("/pics/bg3.png", 1.25);

        PlayerEntity test = new PlayerEntity(-70, 400, "");
        playerEntity = test;

        worldstore.add(new Floor_1(-500, 525));

        renderableEntities.add(test);
        moveableEntities.add(test);
        entities.add(test);

        // Test MP3 playing
        String filename = "/sound/zabutom.lets.shooting.mp3";
        soundtrack = new SoundPlayer(filename);
        soundtrack.play();

    }

    public void render(Graphics g, int delta) {

        bg.render(g, playerEntity.getXPosition(), playerEntity.getYPosition());

        worldstore.renderAll(g, delta, playerEntity.getXRenderPosition(), playerEntity.getYRenderPosition());

        Entity[] temp = new Entity[renderableEntities.size()];
        renderableEntities.toArray(temp);

        for (int i = 0; i < temp.length; i++) {
            temp[i].render(g, delta, playerEntity.getXRenderPosition(), playerEntity.getYRenderPosition());
            temp[i].renderHitBox(g, playerEntity.getXRenderPosition(), playerEntity.getYRenderPosition());
        }

    }

    public void update() {

        if (shotCooldown>0) {
            shotCooldown--;
        }
        if (Input.get().attack() && shotCooldown == 0) {
            //sound.close();
            Shot shot = new Shot(playerEntity.getXPosition(),playerEntity.getYPosition(),playerEntity.facingRight());
            moveableEntities.add(shot);
            renderableEntities.add(shot);
            shots.add(shot);
            shotCooldown += 30;
        }

        // creating temp array to avoid unexpected behaviour when removing elements
        Shot[] tempshot = new Shot[shots.size()];
        shots.toArray(tempshot);

        // checking each shot if it has traveled its distance
        for (int i = 0; i < tempshot.length; i++) {
            Shot shot = tempshot[i];
            if (shot.distanceDone()) {
                // removing the shot from the lists
                shots.remove(shot);
                renderableEntities.remove(shot);
                moveableEntities.remove(shot);
            }
        }

        MoveableEntity[] temp = new MoveableEntity[moveableEntities.size()];
        moveableEntities.toArray(temp);

        for (int i = 0; i < temp.length; i++) {
            temp[i].calculateMovement();
        }

        worldstore.checkCollision(playerEntity);

        for (int i=0;i<temp.length;i++) {
            temp[i].move();
        }
    }

    public double[] getPlayerPosition() {
        return new double[]{playerEntity.getXPosition(), playerEntity.getYPosition()};
    }

}
