package epsilonserver.entity;

import java.net.InetAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Test map containing a list of players
 * @author mm
 */
public class EntityHandler {

    //private ConcurrentHashMap<InetAddress, NetworkEntity> entityList;
    Map<InetAddress, NetworkEntity> entityList;

    /**
     * Private constructor
     * Initialize hashmap containing players
     */
    private EntityHandler() {
        //entityList = new ConcurrentHashMap<InetAddress, NetworkEntity>();
        entityList = Collections.synchronizedMap(new HashMap<InetAddress, NetworkEntity>());
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
    public synchronized void addPlayer(String name, InetAddress ip, String[] posArray) {
        NetworkEntity entity = new NetworkEntity(name, ip, posArray);
        entityList.put(ip, entity);
    }

    /**
     *
     * @return pl
     */
    public synchronized HashMap getGameStateMap() {

        HashMap<InetAddress, String> pl = new HashMap<InetAddress, String>();

        Collection c = entityList.values();
        Iterator it = c.iterator();

        while(it.hasNext()) {
            NetworkEntity ne = (NetworkEntity) it.next();
            InetAddress ip = ne.getAddress();
            String st = ne.getPlayerState();
            pl.put(ip, st);
        }

        return pl;
    }

    /**
     *
     * @param ip
     * @param posArray
     */
    public synchronized void setPlayerCoordinates(InetAddress ip, String[] posArray) {
        NetworkEntity entity = entityList.get(ip);
        entity.setCoordinates(posArray);
    }



    /**
     * Check if player exists
     *
     * @param ip
     * @return
     */
    public synchronized boolean hasPlayer(InetAddress ip) {
        boolean hasPlayer = false;
        if (entityList.containsKey(ip)) {
            hasPlayer = true;
        }
        return hasPlayer;
    }

}