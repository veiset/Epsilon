package epsilon.menu;

import epsilon.game.Game;

/**
 *
 * @author Marius
 */
public class DeathPage extends MenuPage {

    public DeathPage() {
        super(new String[]{"Retry", "Exit Game"}, "You are dead! :)");
    }

    @Override
    public void useSelected() {
        if (selected == 0) {
            
        } else if (selected == 1) {
            Game.get().quit();
        }
    }



}
