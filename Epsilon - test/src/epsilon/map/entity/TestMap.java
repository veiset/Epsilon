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

        bg = new Background("/pics/bg1.png", 1.25);

        TestEntity test = new TestEntity(100, 100);
        playerEntity = test;

        // TODO: Discuss; should floors have x,y parameter = x*50, y*40 as default?
        // => new Floor(3,4), instead of new Floor(150,160)?
        renderableEntities.add(new Floor(0, 171));
        renderableEntities.add(new Floor(123, 171));
        renderableEntities.add(new Floor(246, 171));
        renderableEntities.add(new Floor(369, 171));
        renderableEntities.add(new Floor(677, 171));
        renderableEntities.add(new Floor(0, 461));
        renderableEntities.add(new Floor(0, 421));
        renderableEntities.add(new Floor(0, 381));

        renderableEntities.add(new Floor(150, 381));
        renderableEntities.add(new Floor(200, 381));

        renderableEntities.add(test);
        moveableEntities.add(test);
        entities.add(test);

    }

    public void render(Graphics g, int delta) {

        bg.render(g, playerEntity.getXPosition(), playerEntity.getYPosition());
        Entity[] temp = new Entity[renderableEntities.size()];
        renderableEntities.toArray(temp);

        for (int i = 0; i < temp.length; i++) {
            temp[i].render(g, delta, playerEntity.getXRenderPosition(), playerEntity.getYRenderPosition());
        }

    }

    public void update() {
        Entity[] temp = new Entity[moveableEntities.size()];
        moveableEntities.toArray(temp);

        for (int i = 0; i < temp.length; i++) {
            temp[i].move();
        }
    }
}
