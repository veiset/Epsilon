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

        renderableEntities.add(test);
        moveableEntities.add(test);
        entities.add(test);

    }

    public void render(Graphics g, int delta) {
        Entity[] temp = new Entity[renderableEntities.size()];
        renderableEntities.toArray(temp);

        for(int i=0;i<temp.length;i++){
            temp[i].render(g, delta);
        }

    }

    public void update() {
        Entity[] temp = new Entity[moveableEntities.size()];
        moveableEntities.toArray(temp);

        for(int i=0;i<temp.length;i++){
            temp[i].move();
        }
    }

}
