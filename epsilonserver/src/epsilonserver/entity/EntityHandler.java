package epsilonserver.entity;

import java.net.InetAddress;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Test map containing a list of players
 * 
 * @author mm
 */
public class EntityHandler {

    private ConcurrentHashMap<String, NetworkEntity> entityList;

    private long tmeoutValue = 5000;

    /**
     * Private constructor
     * Initialize hashmap containing players
     */
    private EntityHandler() {
        entityList = new ConcurrentHashMap<String, NetworkEntity>();
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
     *
     * @return INSTANCE
     */
    public static EntityHandler getInstance() {
        return EntityHandlerHolder.INSTANCE;
    }

    /**
     * Get a string containing name and position from every
     * registered player except the the one that has a given name
     * 
     * @param name
     * @return gs
     */
    public String getGameStateString(String name) {

        String gameStateString = "";

        Collection c = entityList.values();
        Iterator it = c.iterator();
        
            while (it.hasNext()) {
                NetworkEntity n = (NetworkEntity) it.next();
                if (!n.getPlayerName().equals(name)) {
                    gameStateString += n.getPlayerState();
                }
            }
            
            return gameStateString;
    }

    /**
     * Get a array containing ip addresses of all registered players
     *
     * @return adrArray
     */
    public InetAddress[] getAddressArray() {       
        InetAddress[] adrArray = new InetAddress[entityList.size()];

        Collection c = entityList.values();
        Iterator it = c.iterator();
        int i = 0;

        while (it.hasNext()) {
            NetworkEntity n = (NetworkEntity) it.next();
            adrArray[i] = n.getAddress();
            i++;
        }
        
        return adrArray;
    }

    /**
     * Get the address of a player with a spesified name
     *
     * @param name
     * @return address
     */
    public  InetAddress getAddressByName(String name) {
        InetAddress address = entityList.get(name).getAddress();
        return address;
    }

    public String[] getNameArray() {
        String[] nameArray = new String[entityList.size()];
        nameArray = (String[]) entityList.keySet().toArray(nameArray);
        return nameArray;

    }

    /**
     * Check to se if the player exists in the entity list. if a player is found
     * then the new coordinates are set on the player. If no player is found
     * then a new player object is created.
     *
     * @param ip
     * @param name
     * @param posArray
     */
    public void createIfAbsent(InetAddress ip, String name, String[] posArray) {
        long updateTime = System.currentTimeMillis();
        boolean contains = entityList.containsKey(name);
        if (contains) {
            entityList.get(name).setCoordinates(posArray, updateTime);
        }
        else {
            NetworkEntity n = new NetworkEntity(name, ip, posArray, updateTime);
            entityList.put(name, n);
        }
    }

    /**
     * Checks timeout value of registered players. if the timeout value
     * exceeds the max timeout value, then the player is removed from the
     * entity list.
     *
     * @param currentTime
     */
    public void checkTimeout(long currentTime) {

        Collection c = entityList.values();
        Iterator it = c.iterator();

        while (it.hasNext()) {
            NetworkEntity n = (NetworkEntity) it.next();
            long lastUpdateTime = n.getLastUpdateTime();

            if ((currentTime - lastUpdateTime) > tmeoutValue) {
                it.remove();
                System.out.println("Removed " + n.getPlayerName());
            }

        }


    }

}