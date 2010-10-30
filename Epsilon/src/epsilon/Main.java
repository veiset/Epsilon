
package epsilon;

import epsilon.game.Game;

/**
 * Starting class for the Epsilon game, sets any environment variables
 *
 * @author Marius
 */
public class Main {

    public static void main(String[] args) {
        
        Game g = Game.get();
        g.start();

    }

}
