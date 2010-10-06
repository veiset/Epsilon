package epsilon.map.entity;

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

    public TestMap() {

        renderableEntities = new ArrayList<Entity>();
        moveableEntities = new ArrayList<Entity>();
        entities = new ArrayList<Entity>();

        TestEntity test = new TestEntity(100, 100);

        // TODO: Discuss; should floors have x,y parameter = x*50, y*40 as default?
        // => new Floor(3,4), instead of new Floor(150,160)?
        renderableEntities.add(new Floor(0, 171));
        renderableEntities.add(new Floor(0, 501));
        renderableEntities.add(new Floor(50, 501));
        renderableEntities.add(new Floor(100, 501));
        renderableEntities.add(new Floor(150, 501));
        renderableEntities.add(new Floor(200, 501));
        renderableEntities.add(new Floor(250, 501));
        renderableEntities.add(new Floor(300, 501));
        renderableEntities.add(new Floor(350, 501));
        renderableEntities.add(new Floor(400, 501));
        renderableEntities.add(new Floor(650, 501));
        renderableEntities.add(new Floor(700, 501));
        renderableEntities.add(new Floor(750, 501));

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
        Entity[] temp = new Entity[renderableEntities.size()];
        renderableEntities.toArray(temp);

        for (int i = 0; i < temp.length; i++) {
            temp[i].render(g, delta);
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
