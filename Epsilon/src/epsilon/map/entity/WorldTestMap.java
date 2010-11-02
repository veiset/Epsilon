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

        worldstore.add(new Floor_1(-400, 440));
        worldstore.add(new Floor_1(-400, 480));
        worldstore.add(new Floor_1(-400, 520));
        worldstore.add(new Floor_1(-400, 560));
        worldstore.add(new Floor_1(-350, 560));
        worldstore.add(new Floor_1(-300, 560));
        worldstore.add(new Floor_1(-250, 560));
        worldstore.add(new Floor_1(-200, 560));
        worldstore.add(new Floor_1(-150, 560));
        worldstore.add(new Floor_1(-100, 520));
        worldstore.add(new Floor_1(-50, 520));
        worldstore.add(new Floor_1(50, 520));
        worldstore.add(new Floor_1(0, 520));
        worldstore.add(new Floor_1(350, 280));
        worldstore.add(new Floor_1(350, 320));
        worldstore.add(new Floor_1(350, 360));
        worldstore.add(new Floor_1(350, 400));
        worldstore.add(new Floor_1(350, 480));
        worldstore.add(new Floor_1(350, 440));
        worldstore.add(new Floor_1(400, 480));
        worldstore.add(new Floor_1(450, 480));
        worldstore.add(new Floor_1(550, 480));
        worldstore.add(new Floor_1(500, 480));
        worldstore.add(new Floor_1(600, 480));
        worldstore.add(new Floor_1(500, 360));
        worldstore.add(new Floor_1(550, 360));
        worldstore.add(new Floor_1(600, 360));
        worldstore.add(new Floor_1(650, 360));
        worldstore.add(new Floor_1(700, 360));
        worldstore.add(new Floor_1(700, 320));
        worldstore.add(new Floor_1(700, 280));
        worldstore.add(new Floor_1(700, 200));
        worldstore.add(new Floor_1(350, 240));
        worldstore.add(new Floor_1(400, 240));
        worldstore.add(new Floor_1(450, 240));
        worldstore.add(new Floor_1(500, 240));
        worldstore.add(new Floor_1(700, 240));
        worldstore.add(new Floor_1(700, 160));
        worldstore.add(new Floor_1(700, 120));
        worldstore.add(new Floor_1(700, 80));
        worldstore.add(new Floor_1(-100, 400));
        worldstore.add(new Floor_1(-150, 400));
        worldstore.add(new Floor_1(-200, 400));
        worldstore.add(new Floor_1(-250, 280));
        worldstore.add(new Floor_1(-300, 280));
        worldstore.add(new Floor_1(-350, 280));
        worldstore.add(new Floor_1(-150, 160));
        worldstore.add(new Floor_1(-100, 160));
        worldstore.add(new Floor_1(-50, 160));
        worldstore.add(new Floor_1(0, 80));
        worldstore.add(new Floor_1(50, 80));
        worldstore.add(new Floor_1(200, 240));
        worldstore.add(new Floor_1(750, 560));
        worldstore.add(new Floor_1(900, 560));
        worldstore.add(new Floor_1(1200, 560));
        worldstore.add(new Floor_1(1150, 560));
        worldstore.add(new Floor_1(1100, 560));
        worldstore.add(new Floor_1(800, 560));
        worldstore.add(new Floor_1(850, 560));

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

        if (shotCooldown > 0) {
            shotCooldown--;
        }
        if (Input.get().attack() && shotCooldown == 0) {
            //sound.close();
            Shot shot = new Shot(playerEntity.getXPosition(), playerEntity.getYPosition(), playerEntity.facingRight());
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

        for (int i = 0; i < temp.length; i++) {
            temp[i].move();
        }
    }

    public double[] getPlayerPosition() {
        return new double[]{playerEntity.getXPosition(), playerEntity.getYPosition()};
    }
}
