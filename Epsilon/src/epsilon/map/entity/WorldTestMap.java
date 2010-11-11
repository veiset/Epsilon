/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package epsilon.map.entity;

import epsilon.game.Input;
import epsilon.game.SoundPlayer;
import epsilon.map.Background;
import epsilon.map.Map;

/**
 *
 * @author Marius
 */
public class WorldTestMap extends Map {

    private int shotCooldown = 0;

    /**
     * Initialises all entities on the map, and all fields in the object
     */
    public WorldTestMap() {

        super("");

    }

    @Override
    public void update() {

        super.update();

    }

    /**
     * Method for adding floor objects to the worldstore.
     *
     * @see epsilon.tools.mapcreator;
     */
    private void populateWorld() {
        worldstore.add(new Floor_1(-400,440, this));
        worldstore.add(new Floor_1(-400,480, this));
        worldstore.add(new Floor_1(-400,520, this));
        worldstore.add(new Floor_1(-400,560, this));
        worldstore.add(new Floor_1(-350,560, this));
        worldstore.add(new Floor_1(-300,560, this));
        worldstore.add(new Floor_1(-250,560, this));
        worldstore.add(new Floor_1(-200,560, this));
        worldstore.add(new Floor_1(-150,560, this));
        worldstore.add(new Floor_1(-100,520, this));
        worldstore.add(new Floor_1(-50,520, this));
        worldstore.add(new Floor_1(50,520, this));
        worldstore.add(new Floor_1(0,520, this));
        worldstore.add(new Floor_1(350,280, this));
        worldstore.add(new Floor_1(350,320, this));
        worldstore.add(new Floor_1(350,360, this));
        worldstore.add(new Floor_1(350,400, this));
        worldstore.add(new Floor_1(350,480, this));
        worldstore.add(new Floor_1(350,440, this));
        worldstore.add(new Floor_1(400,480, this));
        worldstore.add(new Floor_1(450,480, this));
        worldstore.add(new Floor_1(550,480, this));
        worldstore.add(new Floor_1(500,480, this));
        worldstore.add(new Floor_1(600,480, this));
        worldstore.add(new Floor_1(500,360, this));
        worldstore.add(new Floor_1(550,360, this));
        worldstore.add(new Floor_1(600,360, this));
        worldstore.add(new Floor_1(650,360, this));
        worldstore.add(new Floor_1(700,360, this));
        worldstore.add(new Floor_1(700,320, this));
        worldstore.add(new Floor_1(700,280, this));
        worldstore.add(new Floor_1(700,200, this));
        worldstore.add(new Floor_1(350,240, this));
        worldstore.add(new Floor_1(400,240, this));
        worldstore.add(new Floor_1(450,240, this));
        worldstore.add(new Floor_1(500,240, this));
        worldstore.add(new Floor_1(700,240, this));
        worldstore.add(new Floor_1(700,160, this));
        worldstore.add(new Floor_1(700,120, this));
        worldstore.add(new Floor_1(700,80, this));
        worldstore.add(new Floor_1(750,560, this));
        worldstore.add(new Floor_1(800,560, this));
        worldstore.add(new Floor_1(-200,440, this));
        worldstore.add(new Floor_1(-250,440, this));
        worldstore.add(new Floor_1(-300,360, this));
        worldstore.add(new Floor_1(-350,360, this));
        worldstore.add(new Floor_1(-300,440, this));
        worldstore.add(new Floor_1(-300,400, this));
        worldstore.add(new Floor_1(-150,280, this));
        worldstore.add(new Floor_1(-100,280, this));
        worldstore.add(new Floor_1(-50,280, this));
        worldstore.add(new Floor_1(-400,200, this));
        worldstore.add(new Floor_1(-350,200, this));
        worldstore.add(new Floor_1(-300,200, this));
        worldstore.add(new Floor_1(-100,120, this));
        worldstore.add(new Floor_1(-50,120, this));
        worldstore.add(new Floor_1(850,520, this));
        worldstore.add(new Floor_1(900,480, this));
        worldstore.add(new Floor_1(950,440, this));
        worldstore.add(new Floor_1(1000,400, this));
        worldstore.add(new Floor_1(1050,360, this));
        worldstore.add(new Floor_1(1150,360, this));
        worldstore.add(new Floor_1(1150,400, this));
        worldstore.add(new Floor_1(1150,440, this));
        worldstore.add(new Floor_1(1200,360, this));
        worldstore.add(new Floor_1(1250,360, this));
        worldstore.add(new Floor_1(1200,440, this));
        worldstore.add(new Floor_1(1150,480, this));
        worldstore.add(new Floor_1(1150,520, this));
        worldstore.add(new Floor_1(1200,520, this));
        worldstore.add(new Floor_1(1250,520, this));
        worldstore.add(new Floor_1(1300,520, this));
        worldstore.add(new Floor_1(1400,440, this));
        worldstore.add(new Floor_1(1400,480, this));
        worldstore.add(new Floor_1(1400,520, this));
        worldstore.add(new Floor_1(1450,440, this));
        worldstore.add(new Floor_1(1500,440, this));
        worldstore.add(new Floor_1(1500,400, this));
        worldstore.add(new Floor_1(1500,360, this));
        worldstore.add(new Floor_1(1400,360, this));
        worldstore.add(new Floor_1(1450,360, this));
        worldstore.add(new Floor_1(1400,400, this));
        worldstore.add(new Floor_1(1600,360, this));
        worldstore.add(new Floor_1(1650,360, this));
        worldstore.add(new Floor_1(1700,360, this));
        worldstore.add(new Floor_1(1600,400, this));
        worldstore.add(new Floor_1(1600,440, this));
        worldstore.add(new Floor_1(1650,440, this));
        worldstore.add(new Floor_1(1700,440, this));
        worldstore.add(new Floor_1(1750,440, this));
        worldstore.add(new Floor_1(1750,480, this));
        worldstore.add(new Floor_1(1750,520, this));
        worldstore.add(new Floor_1(1700,520, this));
        worldstore.add(new Floor_1(1650,520, this));
        worldstore.add(new Floor_1(1600,520, this));
        worldstore.add(new Floor_1(1850,400, this));
        worldstore.add(new Floor_1(1850,440, this));
        worldstore.add(new Floor_1(1850,480, this));
        worldstore.add(new Floor_1(1850,520, this));
        worldstore.add(new Floor_1(1950,360, this));
        worldstore.add(new Floor_1(1950,400, this));
        worldstore.add(new Floor_1(1950,440, this));
        worldstore.add(new Floor_1(1950,480, this));
        worldstore.add(new Floor_1(1950,520, this));
        worldstore.add(new Floor_1(2000,520, this));
        worldstore.add(new Floor_1(2150,360, this));
        worldstore.add(new Floor_1(2150,400, this));
        worldstore.add(new Floor_1(2150,440, this));
        worldstore.add(new Floor_1(2150,480, this));
        worldstore.add(new Floor_1(2150,520, this));
        worldstore.add(new Floor_1(2200,520, this));
        worldstore.add(new Floor_1(2250,520, this));
        worldstore.add(new Floor_1(2300,520, this));
        worldstore.add(new Floor_1(2300,480, this));
        worldstore.add(new Floor_1(2300,440, this));
        worldstore.add(new Floor_1(2300,400, this));
        worldstore.add(new Floor_1(2300,360, this));
        worldstore.add(new Floor_1(2250,360, this));
        worldstore.add(new Floor_1(2200,360, this));
        worldstore.add(new Floor_1(2400,360, this));
        worldstore.add(new Floor_1(2400,440, this));
        worldstore.add(new Floor_1(2400,400, this));
        worldstore.add(new Floor_1(2400,480, this));
        worldstore.add(new Floor_1(2400,520, this));
        worldstore.add(new Floor_1(2450,400, this));
        worldstore.add(new Floor_1(2500,440, this));
        worldstore.add(new Floor_1(2550,480, this));
        worldstore.add(new Floor_1(2600,520, this));
        worldstore.add(new Floor_1(2600,440, this));
        worldstore.add(new Floor_1(2600,480, this));
        worldstore.add(new Floor_1(2600,400, this));
        worldstore.add(new Floor_1(2600,360, this));
        worldstore.add(new Floor_1(1000,200, this));
        worldstore.add(new Floor_1(1000,160, this));
        worldstore.add(new Floor_1(1000,120, this));
        worldstore.add(new Floor_1(1000,80, this));
        worldstore.add(new Floor_1(1000,40, this));
        worldstore.add(new Floor_1(1050,80, this));
        worldstore.add(new Floor_1(1100,120, this));
        worldstore.add(new Floor_1(1150,80, this));
        worldstore.add(new Floor_1(1200,40, this));
        worldstore.add(new Floor_1(1200,80, this));
        worldstore.add(new Floor_1(1200,120, this));
        worldstore.add(new Floor_1(1200,160, this));
        worldstore.add(new Floor_1(1200,200, this));
        worldstore.add(new Floor_1(1300,40, this));
        worldstore.add(new Floor_1(1300,80, this));
        worldstore.add(new Floor_1(1300,120, this));
        worldstore.add(new Floor_1(1300,160, this));
        worldstore.add(new Floor_1(1300,200, this));
        worldstore.add(new Floor_1(1350,80, this));
        worldstore.add(new Floor_1(1400,120, this));
        worldstore.add(new Floor_1(1450,80, this));
        worldstore.add(new Floor_1(1500,40, this));
        worldstore.add(new Floor_1(1500,80, this));
        worldstore.add(new Floor_1(1500,120, this));
        worldstore.add(new Floor_1(2700,360, this));
        worldstore.add(new Floor_1(2750,400, this));
        worldstore.add(new Floor_1(2800,440, this));
        worldstore.add(new Floor_1(2850,480, this));
        worldstore.add(new Floor_1(2900,520, this));
        worldstore.add(new Floor_1(2950,520, this));
        worldstore.add(new Floor_1(3000,520, this));
        worldstore.add(new Floor_1(3050,520, this));
        worldstore.add(new Floor_1(1500,200, this));
        worldstore.add(new Floor_1(1500,160, this));
        worldstore.add(new Floor_1(300,280, this));
        worldstore.add(new Floor_1(1600,120, this));
        worldstore.add(new Floor_1(1700,40, this));
        worldstore.add(new Floor_1(1700,80, this));
        worldstore.add(new Floor_1(1700,120, this));
        worldstore.add(new Floor_1(1700,160, this));
        worldstore.add(new Floor_1(1700,200, this));
        worldstore.add(new Floor_1(1750,80, this));
        worldstore.add(new Floor_1(1800,120, this));
        worldstore.add(new Floor_1(1850,80, this));
        worldstore.add(new Floor_1(1900,40, this));
        worldstore.add(new Floor_1(1900,80, this));
        worldstore.add(new Floor_1(1900,120, this));
        worldstore.add(new Floor_1(1900,160, this));
        worldstore.add(new Floor_1(1900,200, this));
        worldstore.add(new Floor_1(2000,40, this));
        worldstore.add(new Floor_1(2000,120, this));
        worldstore.add(new Floor_1(2000,80, this));
        worldstore.add(new Floor_1(2000,160, this));
        worldstore.add(new Floor_1(2000,200, this));
        worldstore.add(new Floor_1(2050,200, this));
        worldstore.add(new Floor_1(2100,200, this));
        worldstore.add(new Floor_1(2150,160, this));
        worldstore.add(new Floor_1(2150,120, this));
        worldstore.add(new Floor_1(2150,80, this));
        worldstore.add(new Floor_1(2100,40, this));
        worldstore.add(new Floor_1(2050,40, this));
        worldstore.add(new Floor_1(2250,120, this));
        worldstore.add(new Floor_1(2350,40, this));
        worldstore.add(new Floor_1(2350,80, this));
        worldstore.add(new Floor_1(2400,160, this));
        worldstore.add(new Floor_1(2500,160, this));
        worldstore.add(new Floor_1(2550,80, this));
        worldstore.add(new Floor_1(2550,40, this));
        worldstore.add(new Floor_1(2650,40, this));
        worldstore.add(new Floor_1(2700,40, this));
        worldstore.add(new Floor_1(2750,40, this));
        worldstore.add(new Floor_1(2750,80, this));
        worldstore.add(new Floor_1(2700,120, this));
        worldstore.add(new Floor_1(2650,160, this));
        worldstore.add(new Floor_1(2650,200, this));
        worldstore.add(new Floor_1(2700,200, this));
        worldstore.add(new Floor_1(2750,200, this));
        worldstore.add(new Floor_1(2800,200, this));
        worldstore.add(new Floor_1(2850,200, this));
        worldstore.add(new Floor_1(2450,200, this));
        worldstore.add(new Floor_1(2400,120, this));
        worldstore.add(new Floor_1(2500,120, this));
        worldstore.add(new Floor_1(2050,520, this));
        worldstore.add(new Floor_1(1300,360, this));
        worldstore.add(new Floor_1(1350,360, this));
        worldstore.add(new Floor_1(1550,360, this));
        worldstore.add(new Floor_1(1750,360, this));
        worldstore.add(new Floor_1(1800,360, this));
        worldstore.add(new Floor_1(1850,360, this));
        worldstore.add(new Floor_1(1900,360, this));
        worldstore.add(new Floor_1(2000,360, this));
        worldstore.add(new Floor_1(2050,360, this));
        worldstore.add(new Floor_1(2100,360, this));
        worldstore.add(new Floor_1(2350,360, this));
        worldstore.add(new Floor_1(3200,480, this));
        worldstore.add(new Floor_1(3250,480, this));
        worldstore.add(new Floor_1(3300,480, this));
    }

    @Override
    public void initialiseNonStatic(String s) {
        super.initialiseNonStatic(s);
        
        bg = new Background("/pics/bg3.png", 1.25);

        TestPlayerEntity test = new TestPlayerEntity(-70, 400, "", this, true);
        playerEntity = test;

        populateWorld();

        renderableEntities.add(test);
        moveableEntities.add(test);
        entities.add(test);

        // Test MP3 playing
        String filename = "/sound/zabutom.lets.shooting.mp3";
        soundtrack = new SoundPlayer(filename);
        soundtrack.play();
    }

    @Override
    public void initialiseStatic() {
        super.initialiseStatic();
        populateWorld();
    }
}
