package epsilontest;

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

        TestEntity test = new TestEntity(new String[]{"/pics/guy01.png","/pics/guy02.png","/pics/guy03.png","/pics/guy04.png","/pics/guy05.png"}, 100, 100);

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
