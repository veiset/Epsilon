

package epsilonserver;

import java.net.InetAddress;

/**
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

    // timeout count

    
    public PlayerEntity(String name, InetAddress ip, int port, int posX, int posY) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.posX = posX;
        this.posY = posY;
    }

    
    public PlayerEntity(String name, int posX, int posY) {
        this.name = name;
        this.posX = posX;
        this.posY = posY;
    }


    public PlayerEntity(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }


    public void setAddress(InetAddress ip) {
        this.ip = ip;
    }


    public InetAddress getAddress() {
        return ip;
    }


    public void setPort(int port) {
        this.port = port;
    }


    public int getPort() {
        return port;
    }


    public int getPosX() {
        return posX;
    }


    public int getPosY() {
        return posY;
    }


    public void setPosX(int posX) {
        this.posX = posX;
    }


    public void setPosY(int posY) {
        this.posY = posY;
    }

    
}
