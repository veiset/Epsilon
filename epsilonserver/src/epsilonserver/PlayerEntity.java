package epsilonserver;

import java.net.InetAddress;

/**
 * The PlayerEntity class represents a connected player
 *
 * @author mm
 */
public class PlayerEntity {

    // player info
    private String name;
    private InetAddress ip;
    private int port;
    private int posX = 100;
    private int posY = 100;

    // TODO: timeout counter

    /**
     * Overloaded constructor
     *
     * @param name
     * @param ip
     * @param port
     * @param posX
     * @param posY
     */
    public PlayerEntity(String name, InetAddress ip, int port, int posX, int posY) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Overloaded constructor
     *
     * @param name
     * @param posX
     * @param posY
     */
    public PlayerEntity(String name, int posX, int posY) {
        this.name = name;
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Overloaded constructor
     * 
     * @param name
     */
    public PlayerEntity(String name) {
        this.name = name;
    }

    /**
     * Get player name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set a players IP address
     *
     * @param ip
     */
    public void setAddress(InetAddress ip) {
        this.ip = ip;
    }

    /**
     * Get a players IP address
     *
     * @return ip
     */
    public InetAddress getAddress() {
        return ip;
    }

    /**
     * Set a players port
     *
     * @param port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Get a players port
     *
     * @return
     */
    public int getPort() {
        return port;
    }

    /**
     * Get players x position
     *
     * @return posX
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Get players y position
     *
     * @return posY
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Set players x position
     *
     * @param posX
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Set players y position
     *
     * @param posY
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }
  
}
