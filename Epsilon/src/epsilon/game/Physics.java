package epsilon.game;

/**
 *
 * @author Marius
 */
public class Physics {

    // environment variables
    public static double GRAVITY = -490.0;

    /**
     * This method takes the current position on the Y-axis and the previous
     * position on the Y-axis and calculates how far it should fall
     * based on the Gravity environment variable and the delta given in the
     * method call
     *
     * @param posY current Y-axis position
     * @param pposY previous Y-axis position
     * @param delta millisecons between the two positions
     * @return the distance gravity would cause it to fall
     */
    public static double calculateGravity(double posY, double pposY, int delta) {
        double dt = delta * 0.001;
        double velocity = (posY-pposY)/(dt);
        double dif = -velocity * dt + GRAVITY * dt * dt;
        return dif;
    }

}
