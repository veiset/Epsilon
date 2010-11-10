package epsilon.map.entity;

import epsilon.game.Collision;
import epsilon.game.SoundPlayer;
import epsilon.map.Background;
import epsilon.map.Map;
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

    /**
     * Initialises all entities on the map, and all fields in the object
     */
    public NetworkMap (String name) {
        super(name);
    }

    @Override
    public void update() {

        if (playerEntity.isDead()) {
            Menu.get().setMenu(new DeathPage());
        }

        while(NetworkHandler.getInstance().hasNewPlayers()) {
            String s = NetworkHandler.getInstance().getNewPlayer();
            double[] d = NetworkHandler.getInstance().getPlayerStateByName(s);

            if (d != null) {
                TestNetworkEntity n = new TestNetworkEntity(d[0], d[1], s, this);
                renderableEntities.add(n);
                moveableEntities.add(n);
                entities.add(n);
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

            for (int j = 0; j < temp.length; j++) {
                if (i != j) {
                    c = temp[j].collision(temp[i]);
                    if (c.collided) {
                        temp[i].collided(c);
                    }
                }
            }
        }

        for (int i=0;i<temp.length;i++) {
            temp[i].move();
        }

        NetworkHandler.getInstance().sendPlayerAction();
    }

    public void updateWhileMenu() {

        while(NetworkHandler.getInstance().hasNewPlayers()) {
            String s = NetworkHandler.getInstance().getNewPlayer();
            double[] d = NetworkHandler.getInstance().getPlayerStateByName(s);

            if (d != null) {
                TestNetworkEntity n = new TestNetworkEntity(d[0], d[1], s, this);
                renderableEntities.add(n);
                moveableEntities.add(n);
                entities.add(n);
            }
        }

        MoveableEntity[] temp = new MoveableEntity[moveableEntities.size()];
        moveableEntities.toArray(temp);

        Collision c;

        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != playerEntity) {
                temp[i].calculateMovement();

                worldstore.checkCollision(temp[i]);

                if (temp[i] instanceof TestNetworkEntity && !((TestNetworkEntity)temp[i]).exists()) {
                    moveableEntities.remove(temp[i]);
                    renderableEntities.remove(temp[i]);
                    entities.remove(temp[i]);
                }

                for (int j = 0; j < temp.length; j++) {
                    if (i != j) {
                        c = temp[j].collision(temp[i]);
                        if (c.collided) {
                            temp[i].collided(c);
                        }
                    }
                }
            }
        }

        for (int i=0;i<temp.length;i++) {
            temp[i].move();
        }

        NetworkHandler.getInstance().sendPlayerAction();

    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void initialiseNonStatic(String s) {
        
        super.initialiseNonStatic(s);
        renderableEntities = new ArrayList<Entity>();
        moveableEntities = new ArrayList<MoveableEntity>();
        entities = new ArrayList<Entity>();

        bg = new Background("/pics/bg3.png", 1.25);

        playerEntity = new TestPlayerEntity(-70, 400, s, this);

        renderableEntities.add(playerEntity);
        moveableEntities.add(playerEntity);
        entities.add(playerEntity);

        String filename = "/sound/zabutom.lets.shooting.mp3";
        soundtrack = new SoundPlayer(filename);
        //soundtrack.play();
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

    @Override
    public double[] getPlayerState() {
        return new double[]{playerEntity.getXPosition(), playerEntity.getYPosition(), playerEntity.lastShot()};
    }

}
 