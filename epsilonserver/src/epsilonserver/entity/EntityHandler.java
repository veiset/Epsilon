package epsilonserver.entity;

import java.net.InetAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Test map containing a list of players
 * @author mm
 */
public class EntityHandler {

    private HashMap<InetAddress, NetworkEntity> entityList;

    /**
     * Private constructor
     * Initialize hashmap containing players
     */
    private EntityHandler() {
        entityList = new HashMap<InetAddress, NetworkEntity>();
    }

    /**
     * Inner class to create a instance of EntityHandler, which is
     * loaded when EntityHandler.getInstance() method is called or
     * when INSTANCE is accessed.
     */
    private static class EntityHandlerHolder {
        public static final EntityHandler INSTANCE = new EntityHandler();
    }

    /**
     * Get instance of EntityHandler
     * @return INSTANCE
     */
    public static EntityHandler getInstance() {
        return EntityHandlerHolder.INSTANCE;
    }

    /**
     * Add a player to the playerlist
     * @param name
     * @param p
     */
    public void addPlayer(InetAddress ip, NetworkEntity nEntity) {
        entityList.put(ip, nEntity);
    }

    /**
     * Get a string representation of all the players
     * @return gameStateString
     */
    public String getGameStateString(InetAddress ip) {
        String gameStateString = "";

        Collection c = entityList.values();
        Iterator it = c.iterator();

        while (it.hasNext()) {
            NetworkEntity ne = (NetworkEntity) it.next();
            if (!ne.getAddress().equals(ip)) {
                double[] posArray = ne.getCoordinates();
                gameStateString += ne.getPlayerName() + " " + posArray[0] + " " + posArray[1] + " ";
            }
        }

        return gameStateString;
    }

    /**
     * Get list of players
     * @return playerList;
     */
    public HashMap getPlayerList() {
        return entityList;
    }

    /**
     * Get a player object that has a specified ip adress
     * @param ip
     * @return p
     */
    public NetworkEntity getPlayerByIP(InetAddress ip) {
        NetworkEntity p = entityList.get(ip);
        return p;
    }

}