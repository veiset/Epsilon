/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package epsilonserver;

import java.nio.ByteBuffer;
import java.util.StringTokenizer;

/**
 *
 * @author mm
 */
public class PacketParser implements Runnable {

    private GameState gameState;
    private ByteBuffer bb;

    public PacketParser(GameState gameState, ByteBuffer bb) {
        this.gameState = gameState;
        this.bb = bb;
    }


    /**
     * Start a thread and parse the received data
     */
    public void run() {

        String gameStateString = bb.toString();
        
        StringTokenizer part = new StringTokenizer(gameStateString);
        Player p = gameState.getPlayerByName(part.nextToken());

        p.setPosX(Integer.parseInt(part.nextToken()));
        p.setPosY(Integer.parseInt(part.nextToken()));

    }



}
