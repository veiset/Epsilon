package epsilon.map.entity;

import epsilon.game.Input;
import epsilon.game.SoundPlayer;
import epsilon.map.Background;
import epsilon.map.Map;

/**
 * Map made for testing purposes, now used as a single player map
 *
 * @author Marius
 */
public class TestMap extends Map {

    // enemies
    private Enemy enemy;
    private int shotCooldown = 0;

    /**
     * Initialises all entities on the map, and all fields in the object
     */
    public TestMap(String s) {

        super("");

    }

    @Override
    public void update() {

        super.update();

    }

    @Override
    protected void initialiseNonStatic(String s) {
        
        super.initialiseNonStatic(s);

        bg = new Background("/pics/bg3.png", 1.25);

        TestPlayerEntity test = new TestPlayerEntity(-70, 400, "", this, true);
        playerEntity = test;

        enemy = new EnemyPatrol(-300, 100, this);
        renderableEntities.add(enemy);
        moveableEntities.add(enemy);
        entities.add(enemy);

        renderableEntities.add(test);
        moveableEntities.add(test);
        entities.add(test);

        // Test MP3 playing
        String filename = "/sound/zabutom.lets.shooting.mp3";
        soundtrack = new SoundPlayer(filename);
        soundtrack.play();

    }

    @Override
    protected void initialiseStatic() {

        super.initialiseStatic();

         worldstore.add(new Floor_1(-500, 525, this));
        worldstore.add(new Floor_1(-500, 565, this));
        worldstore.add(new Floor_1(-450, 565, this));
        worldstore.add(new Floor_1(-400, 565, this));
        worldstore.add(new Floor_1(-350, 565, this));
        worldstore.add(new Floor_1(-300, 565, this));
        worldstore.add(new Floor_1(-250, 565, this));
        worldstore.add(new Floor_1(-200, 565, this));
        worldstore.add(new Floor_1(-150, 565, this));
        worldstore.add(new Floor_1(-100, 565, this));
        worldstore.add(new Floor_1(-50, 565, this));
        worldstore.add(new Floor_1(-50, 525, this));

        worldstore.add(new Floor_1(1000, 565, this));
        worldstore.add(new Floor_1(1050, 565, this));
        worldstore.add(new Floor_1(1100, 565, this));
        worldstore.add(new Floor_1(1150, 565, this));
        worldstore.add(new Floor_1(1200, 565, this));
        worldstore.add(new Floor_1(1250, 565, this));
        worldstore.add(new Floor_1(1300, 565, this));
        worldstore.add(new Floor_1(1350, 565, this));
        worldstore.add(new Floor_1(1400, 565, this));
        worldstore.add(new Floor_1(1450, 565, this));

        worldstore.add(new Floor_1(80, 505, this));

        worldstore.add(new Floor_1(250, 415, this));
        worldstore.add(new Floor_1(300, 415, this));

        worldstore.add(new Floor_1(500, 385, this));
        worldstore.add(new Floor_1(550, 385, this));


        worldstore.add(new Floor_1(350, 455, this));
        worldstore.add(new Floor_1(500, 495, this));

    }
}
