/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package epsilonserver;

import java.net.InetAddress;
import java.util.ArrayList;

/**
 *
 * @author mm
 */
public class GameState {

    private ArrayList<Player> playerList = new ArrayList<Player>();


    public GameState() {

    }


    public ArrayList getPlayerList() {
        return playerList;
    }
    

    public void addPlayer(String name, InetAddress ip, int port) {
        Player p = new Player(name, ip, port);
        playerList.add(p);
    }


    /**
     * Not currently in use
     */
    public void update() {
        // does nothing right now
    }


    /**
     * Get a string representation of player and their x and y coordinates
     * @return
     */
    @Override
    public String toString() {
        String gameStateString = "";
        
        for (Player p : playerList) {
            gameStateString += p.getName() + " x " + p.getPosX() + " y " + p.getPosY() + " ";
        }
        
        return gameStateString;
    }


    /**
     * return a player object that has a given name
     * @param playerName
     * @return
     */
    public Player getPlayerByName(String playerName) {
        Player player = null;
        for (Player p : playerList) {
            if (p.getName().equals(playerName)) {
                player = p;
            }
        }
        return player;
    }


}
