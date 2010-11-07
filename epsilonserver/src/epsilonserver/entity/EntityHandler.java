package epsilonserver.entity;

import java.net.InetAddress;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Singleton for handling network players. Every network player is
 * registered here in a concurrent hashmap.
 * 
 * @author Magnus Mikalsen
 */
public class EntityHandler {

    // Concurrent hashmap containing NetworkEntity objects. playername is used as key
    private ConcurrentHashMap<String, NetworkEntity> entityList;

    private long tmeoutValue = 5000;

    /**
     * Private constructor
     * Initialize concurrent hashmap containing players
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
     * Get a string containing name and position of every
     * registered player except the the one that has a given name
     * 
     * @param name Player name
     * @return gameStateString Players name, X and Y coordinates
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
     * @return adrArray Array of IP addresses
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
     * @param name Player name
     * @return address Players IP address
     */
    public InetAddress getAddressByName(String name) {
        InetAddress address = entityList.get(name).getAddress();
        return address;
    }

    /**
     * Get a array containing names of registered players
     *
     * @return nameArray Array of player names
     */
    public String[] getNameArray() {
        String[] nameArray = new String[entityList.size()];
        nameArray = (String[]) entityList.keySet().toArray(nameArray);
        return nameArray;

    }

    /**
     * Check to se if the player exists in the entity list. if a player is found
     * then the new coordinates are set on the player. If no player is found
     * then a new NetworkEntity object is created and added to player hashmap.
     *
     * @param ip IP address of player
     * @param name Player name
     * @param posArray Players X and Y coordinates
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
     * @param currentTime Current system time in milliseconds
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