package epsilonserver.entity;

import java.net.InetAddress;

/**
 * Network player
 * Modified by mm
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
     * @param posX
     * @param posY
     * @param playerName
     */
    public NetworkEntity(String playerName, InetAddress ip, String[] posArray, long updateTime) {
        this.posArray = posArray;
        this.name = playerName;
        this.ip = ip;
        lastUpdateTime = updateTime;
    }

    /**
     * Get player name
     *
     * @return name
     */
    public synchronized String getPlayerName() {
        return name;
    }

    /**
     * get timeout value
     *
     * @return timeout
     */
    public synchronized long getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * Get players IP address
     *
     * @return ip
     */
    public synchronized InetAddress getAddress() {
        return ip;
    }

    /**
     * Set players coordinates
     *
     * @param posArray
     */
    public synchronized void setCoordinates(String[] posArray, long updateTime) {
        this.posArray = posArray;
        lastUpdateTime = updateTime;
    }

    /**
     *  Get a array with player coordinates
     *
     * @return posArray
     */
    public synchronized String[] getCoordinates() {
        return posArray;
    }

    /**
     * Get players name and coordinates
     *
     * @return playerState
     */
    public synchronized String getPlayerState() {
        String playerState = name + " " + posArray[0] + " " + posArray[1] + " ";
        return playerState;
    }



}
