/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package epsilontest;

/**
 *
 * @author Marius
 */
public class Physics {

    // environment variables
    public static double GRAVITY = -490.0;

    public static int calculateGravity(int posY, int pposY, int delta) {
        double dt = delta * 0.001;
        double velocity = (posY-pposY)/(dt);
        /*
        if (velocity > -50) {
            velocity -= 3;
        }
         *
         */
        double dif = velocity * dt + GRAVITY * dt * dt;
        System.out.println("Velocity: " + velocity + " PosY: " + posY + " Dif: "+dif);
        return (int)0;
    }

}
