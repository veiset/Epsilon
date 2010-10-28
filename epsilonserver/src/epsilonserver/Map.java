

package epsilonserver;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author mm
 */
public class Map {

    private HashMap<String, PlayerEntity> playerList;


    public Map() {
        playerList = new HashMap<String, PlayerEntity>();
    }


    public void addPlayer(String name, PlayerEntity p) {
        playerList.put(name, p);
    }


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


    public HashMap getPlayerList() {
        return playerList;
    }


    public PlayerEntity getPlayerByName(String playerName) {
        PlayerEntity p = playerList.get(playerName);
        return p;
    }

}
