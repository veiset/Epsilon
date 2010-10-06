/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package epsilon.game;

/**
 *
 * @author Marius
 */
public class Physics {

    // environment variables
    public static double GRAVITY = -490.0;

    public static double calculateGravity(double posY, double pposY, int delta) {
        double dt = delta * 0.001;
        double velocity = (posY-pposY)/(dt);
        double dif = -velocity * dt + GRAVITY * dt * dt;
        System.out.println("Velocity: " + velocity + " PosY: " + posY + " PPosY: " + pposY + " Dif: "+dif);
        return dif;
    }

}
