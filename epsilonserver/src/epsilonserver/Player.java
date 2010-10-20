/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package epsilonserver;

import java.net.InetAddress;

/**
 *
 * @author mm
 */
public class Player {

    // player info
    private String name;
    private InetAddress ip;
    private int port;
    private int posX;
    private int posY;

    // timeout

    
    public Player(String name, InetAddress ip, int port) {
        this.name = name;
        this.ip = ip;
        this.port = port;
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
