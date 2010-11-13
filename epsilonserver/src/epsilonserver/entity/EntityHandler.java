package epsilonserver.entity;

import epsilonserver.game.ServerGUI;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
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

    // Time before we kick the player
    private long tmeoutValue = 5000;

    // List of color identificators
    private ArrayList<String> colorList;

    // Random number generator
    private Random random;

    /**
     * Private constructor
     * Initialize concurrent hashmap containing players
     */
    private EntityHandler() {
        entityList = new ConcurrentHashMap<String, NetworkEntity>();

        // Create a list of colors
        colorList = new ArrayList<String>();
        
        // Add colors to the list
        setColors();

        // Create random number generator
        random = new Random();
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
     * @return Instance of EntityHandler
     */
    public static EntityHandler getInstance() {
        return EntityHandlerHolder.INSTANCE;
    }

    /**
     * Add colors to the player color list
     * Colors added are red, blue, pink and green.
     */
    private void setColors() {
        colorList.clear();

        colorList.add("1"); // RED
        colorList.add("2"); // BLUE
        colorList.add("3"); // PINK
        colorList.add("4"); // GREEN
    }

    /**
     * Get a string containing game state of every
     * registered player except the the one that has a given name.
     * 
     * @param name Name of player we don't want information about
     * @return Player state information
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
     * Get a array containing IP addresses of all registered players.
     *
     * @return Array of IP addresses
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
     * Get the address of a player with a specified name.
     *
     * @param name Name of player we want the IP address of
     * @return IP address belonging to given player
     */
    public InetAddress getAddressByName(String name) {
        InetAddress address = entityList.get(name).getAddress();
        return address;
    }

    /**
     * Get a array containing names of registered players.
     *
     * @return Array of player names
     */
    public synchronized String[] getNameArray() {
        String[] nameArray = new String[entityList.size()];
        nameArray = (String[]) entityList.keySet().toArray(nameArray);
        return nameArray;
    }

    /**
     * Set a new game state on a player
     *
     * @param name Name of player
     * @param actionArray Array of player actions
     */
    public void setPlayerState(String name, String[] actionArray) {
        long updateTime = System.currentTimeMillis();

        NetworkEntity n = entityList.get(name);
        if (n != null) {
            entityList.get(name).setPlayerAction(actionArray, updateTime);
        }
        
    }

    /**
     * Create a new player if the player does not already exist.
     *
     * @param name Name of player
     * @param ip IP address belonging to player
     * @return True if player was added, false if player already exists
     */
    public synchronized boolean createIfAbsent(String name, InetAddress ip) {
        long updateTime = System.currentTimeMillis();

        boolean playerAdded;
        boolean contains = entityList.containsKey(name);

        if (!contains && !colorList.isEmpty()) {

            // Pick a random color from colorlist
            // and remove the color
            int length = colorList.size();
            int colorIdx = random.nextInt(length-1);
            String color = colorList.get(colorIdx);
            colorList.remove(colorIdx);

            NetworkEntity n = new NetworkEntity(name, ip, updateTime, color);
            entityList.put(name, n);
            ServerGUI.getInstance().setSystemMessage("Player " + name + " has connected");
            playerAdded = true;
        }
        else {
            playerAdded = false;
        }

        return playerAdded;
    }

    /**
     * Checks timeout value of registered players. If the timeout value
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
                // Get the color the player was using and
                // put it back in the colorlist
                String color = n.getColor();
                colorList.add(color);
                colorList.trimToSize();
                it.remove();

                ServerGUI.getInstance().setSystemMessage("Player " + n.getPlayerName() + " has timed out");
            }
        }

    }

    /**
     * Remove all network entities and reset color array.
     */
    public void clearPlayers() {
        entityList.clear();
        setColors();
    }

}