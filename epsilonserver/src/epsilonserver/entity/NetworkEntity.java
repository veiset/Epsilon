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
    private long timeout;

    /**
     * Constructor
     *
     * @param posX
     * @param posY
     * @param playerName
     */
    public NetworkEntity(String playerName, InetAddress ip, String[] posArray) {
        this.posArray = posArray;
        this.name = playerName;
        this.ip = ip;
    }

    /**
     * Get player name
     *
     * @return name
     */
    public String getPlayerName() {
        return name;
    }

    /**
     * get timeout value
     *
     * @return timeout
     */
    public long getTimeoutValue() {
        return timeout;
    }

    /**
     * Set timeout value
     *
     * @param timeout
     */
    public void setTimeoutValue(long timeout) {
        this.timeout = timeout;
    }

    /**
     * Get players IP address
     *
     * @return ip
     */
    public InetAddress getAddress() {
        return ip;
    }

    /**
     * Set players coordinates
     *
     * @param posArray
     */
    public void setCoordinates(String[] posArray) {
        this.posArray = posArray;
    }

    /**
     *  Get a array with player coordinates
     *
     * @return posArray
     */
    public String[] getCoordinates() {
        return posArray;
    }

    /**
     * Get players name and coordinates
     *
     * @return playerState
     */
    public String getPlayerState() {
        String playerState = name + " " + posArray[0] + " " + posArray[1] + " ";
        return playerState;
    }



}
