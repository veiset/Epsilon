package epsilonserver.entity;

import java.net.InetAddress;

/**
 * Network player class containing information about a player.
 * 
 * Modified by Magnus Mikalsen
 *
 * @author Marius
 */
public class NetworkEntity {

    private String name;
    private String[] posArray;
    private InetAddress ip;
    private long lastUpdateTime;

    /**
     * Constructor
     *
     * @param posX Players X position
     * @param posY Players Y position
     * @param playerName Player name
     * @param updateTime Time of creation
     * @param ip Players IP address
     */
    public NetworkEntity(String playerName, InetAddress ip, String[] posArray, long updateTime) {
        this.posArray = posArray;
        this.name = playerName;
        this.ip = ip;
        lastUpdateTime = updateTime;
    }

    /**
     * Constructor
     *
     * @param playerName Player name
     * @param ip Players IP address
     * @param updateTime Time of creation
     */
    public NetworkEntity(String playerName, InetAddress ip, long updateTime) {
        name = playerName;
        this.ip = ip;
        lastUpdateTime = updateTime;

        String[] p = new String[2];

        p[0] = "-200";
        p[1] = "-800";

        posArray = p;
    }

    /**
     * Get player name
     *
     * @return name Player name
     */
    public synchronized String getPlayerName() {
        return name;
    }

    /**
     * Get the players last update time in milliseconds.
     *
     * @return lastUpdateTime Last time player was updated in milliseconds
     */
    public synchronized long getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * Get players IP address.
     *
     * @return ip Players IP address
     */
    public synchronized InetAddress getAddress() {
        return ip;
    }

    /**
     * Set new player coordinates and set new update time.
     *
     * @param posArray Player X and Y coordinates
     * @param updateTime  Time of update in milliseconds
     */
    public synchronized void setCoordinates(String[] posArray, long updateTime) {
        this.posArray = posArray;
        lastUpdateTime = updateTime;
    }

    /**
     * Get a array with player coordinates.
     *
     * @return posArray Player X and Y coordinates
     */
    public synchronized String[] getCoordinates() {
        return posArray;
    }

    /**
     * Get a string containing the players name, x and y coordinates
     *
     * @return playerState String with name, x and y coordinates
     */
    public synchronized String getPlayerState() {
        String playerState = name + " " + posArray[0] + " " + posArray[1] + " ";
        return playerState;
    }

}
