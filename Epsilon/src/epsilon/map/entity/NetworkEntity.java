/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package epsilon.map.entity;

import java.net.InetAddress;

/**
 *
 * @author Marius
 */
public class NetworkEntity extends PlayerEntity {

    private String name;

    public NetworkEntity(int PosX, int PosY, String playerName) {

        super(PosX, PosY);

        this.name = playerName;

        
    }

    @Override
    public void calculateMovement() {

        newPosX = posX;
        newPosY = posY;

        // TODO: get 

        // go to the next picture in the sprite if it is time
        if (ticker < 5) {
            ticker++;
        } else {
            ticker = 0;
            currentSprite.nextImage();
        }
        
    }

}
