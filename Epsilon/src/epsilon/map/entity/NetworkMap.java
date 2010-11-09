package epsilon.map.entity;

import epsilon.game.Collision;
import epsilon.game.Input;
import epsilon.game.SoundPlayer;
import epsilon.map.Background;
import epsilon.map.Map;
import epsilon.map.WorldStore;
import epsilon.menu.DeathPage;
import epsilon.menu.Menu;
import epsilon.net.NetworkHandler;
import java.util.ArrayList;

/**
 *
 *
 * @author Marius
 */
public class NetworkMap extends Map {

    private int shotCooldown = 0;

    /**
     * Initialises all entities on the map, and all fields in the object
     */
    public NetworkMap (String name) {
        super(name);
    }

    public void update() {

        if (playerEntity.isDead()) {
            Menu.get().setMenu(new DeathPage());
        }

        while(NetworkHandler.getInstance().hasNewPlayers()) {
            String s = NetworkHandler.getInstance().getNewPlayer();
            double[] d = NetworkHandler.getInstance().getPlayerStateByName(s);

            if (d != null) {
                TestNetworkEntity n = new TestNetworkEntity(d[0], d[1], s);
                renderableEntities.add(n);
                moveableEntities.add(n);
                entities.add(n);
            }
        }

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

        Collision c;

        for (int i = 0; i < temp.length; i++) {
            temp[i].calculateMovement();

            worldstore.checkCollision(temp[i]);

            if (temp[i] instanceof TestNetworkEntity && !((TestNetworkEntity)temp[i]).exists()) {
                moveableEntities.remove(temp[i]);
                renderableEntities.remove(temp[i]);
                entities.remove(temp[i]);
            }

            c = temp[i].collision(playerEntity);
            if (c.collided) {
                playerEntity.collided(c);
            }
        }

        for (int i=0;i<temp.length;i++) {
            temp[i].move();
        }

        NetworkHandler.getInstance().sendPlayerAction();

    }

    public void reset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void initialiseNonStatic(String s) {
        super.initialiseNonStatic(s);
        renderableEntities = new ArrayList<Entity>();
        moveableEntities = new ArrayList<MoveableEntity>();
        entities = new ArrayList<Entity>();
        shots = new ArrayList<Shot>();
        worldstore = new WorldStore(50);

        bg = new Background("/pics/bg3.png", 1.25);

        playerEntity = new TestPlayerEntity(-70, 400, s);

        renderableEntities.add(playerEntity);
        moveableEntities.add(playerEntity);
        entities.add(playerEntity);

        String filename = "/sound/zabutom.lets.shooting.mp3";
        soundtrack = new SoundPlayer(filename);
        //soundtrack.play();
    }

    @Override
    public void initialiseStatic() {
        super.initialiseStatic();

        worldstore.add(new Floor(-500, 525));
        worldstore.add(new Floor(-500, 565));
        worldstore.add(new Floor(-450, 565));
        worldstore.add(new Floor(-400, 565));
        worldstore.add(new Floor(-350, 565));
        worldstore.add(new Floor(-300, 565));
        worldstore.add(new Floor(-250, 565));
        worldstore.add(new Floor(-200, 565));

        worldstore.add(new Floor(-150, 565));
        worldstore.add(new Floor(-100, 565));
        worldstore.add(new Floor(-50, 565));
        worldstore.add(new Floor(-50, 525));

        worldstore.add(new Floor(1000, 565));
        worldstore.add(new Floor(1050, 565));
        worldstore.add(new Floor(1100, 565));
        worldstore.add(new Floor(1150, 565));
        worldstore.add(new Floor(1200, 565));
        worldstore.add(new Floor(1250, 565));
        worldstore.add(new Floor(1300, 565));
        worldstore.add(new Floor(1350, 565));
        worldstore.add(new Floor(1400, 565));
        worldstore.add(new Floor(1450, 565));

        worldstore.add(new Floor(80, 505));

        worldstore.add(new Floor(250, 415));
        worldstore.add(new Floor(300, 415));

        worldstore.add(new Floor(500, 385));
        worldstore.add(new Floor(550, 385));

        worldstore.add(new Floor(350, 455));
        worldstore.add(new Floor(500, 495));
    }

}
 