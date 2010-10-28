
package epsilonclient;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Test map containing a list of players
 * @author mm
 */
public class Map {

    private HashMap<String, PlayerEntity> playerList;

    /**
     * Constructor
     * Initialize hashmap containing players
     */
    public Map() {
        playerList = new HashMap<String, PlayerEntity>();
    }

    /**
     * Add a player to the playerlist
     *
     * @param name
     * @param p
     */
    public void addPlayer(String name, PlayerEntity p) {
        playerList.put(name, p);
    }

    /**
     * Get a string representation of all the players
     *
     * @return gameStateString
     */
    @Override
    public String toString() {
        String gameStateString = "";

        Collection c = playerList.values();
        Iterator it = c.iterator();
        while (it.hasNext()) {
            PlayerEntity p = (PlayerEntity) it.next();
            gameStateString += p.getName() + " x " + p.getPosX() + " y " + p.getPosY() + " ";
        }

        return gameStateString;
    }

    /**
     * Get list of players
     *
     * @return playerList;
     */
    public HashMap getPlayerList() {
        return playerList;
    }

    /**
     * Get a player object that has a specified name
     *
     * @param playerName
     * @return
     */
    public PlayerEntity getPlayerByName(String playerName) {
        PlayerEntity p = playerList.get(playerName);
        return p;
    }

}