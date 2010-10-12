package epsilon.map.entity;

import epsilon.map.Background;
import epsilon.map.Map;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Map made for testing purposes.
 *
 * @author Marius
 */
public class TestMap implements Map {

    ArrayList<Entity> renderableEntities;
    ArrayList<Entity> moveableEntities;
    ArrayList<Entity> entities;

    Entity playerEntity;

    Background bg;

    public TestMap() {

        renderableEntities = new ArrayList<Entity>();
        moveableEntities = new ArrayList<Entity>();
        entities = new ArrayList<Entity>();

        bg = new Background("/pics/bg3.png", 1.25);

        TestEntity test = new TestEntity(180, 100);
        playerEntity = test;

        // TODO: Discuss; should floors have x,y parameter = x*50, y*40 as default?
        // => new Floor(3,4), instead of new Floor(150,160)?
        /*
        renderableEntities.add(new Floor(100, 495));
        renderableEntities.add(new Floor(150, 495));
        renderableEntities.add(new Floor(200, 495));
        renderableEntities.add(new Floor(250, 495));
        renderableEntities.add(new Floor(400, 495));
        renderableEntities.add(new Floor(100, 415));
        renderableEntities.add(new Floor(100, 455));

        renderableEntities.add(new Floor(250, 415));
        renderableEntities.add(new Floor(300, 415));

        renderableEntities.add(new Floor(400, 285));
        renderableEntities.add(new Floor(450, 285));

        */

        //renderableEntities.add(new Floor_1(450, 495));
        renderableEntities.add(new Floor_1(500, 495));

        renderableEntities.add(test);
        moveableEntities.add(test);
        entities.add(test);

    }

    public void render(Graphics g, int delta) {

        bg.render(g, playerEntity.getXPosition()-playerEntity.getXPosition(), playerEntity.getYPosition()-playerEntity.getHeight());
        Entity[] temp = new Entity[renderableEntities.size()];
        renderableEntities.toArray(temp);

        for (int i = 0; i < temp.length; i++) {
            temp[i].render(g, delta, playerEntity.getXRenderPosition(), playerEntity.getYRenderPosition());
            temp[i].renderHitBox(g, playerEntity.getXRenderPosition(), playerEntity.getYRenderPosition());
        }

    }

    public void update() {

        Entity[] temp = new Entity[moveableEntities.size()];
        moveableEntities.toArray(temp);

        for (int i = 0; i < temp.length; i++) {
            temp[i].move();
        }

        // temp collision, simple test
        for (Entity ent : renderableEntities) {
            boolean[] hit = ent.collision(playerEntity);
            if(hit[0]) {
                System.out.println("block!");
                playerEntity.collided(hit, ent);
            }
            //if(ent)
        }
    }
}
