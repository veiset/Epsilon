package epsilon.menu;

import epsilon.game.Game;

/**
 * Standardpage that shows when you die on a Network Map.
 *
 * @author Marius
 */
public class NetworkDeathPage extends MenuPage {

    /**
     * Creates a new Network Death page. Sets the available options and an attempt at a humorous message.
     * Failed miserably
     */
    public NetworkDeathPage() {
        super(new String[]{"Respawn", "Exit"}, "You are dead!!!!!/nYou should try nto doing that");
    }

    @Override
    public void useSelected() {
        if (selected == 0) {
            Game.get().resetPlayerPosition();
            Menu.get().setMenu(OptionPage.get());
            Game.get().menuDone();
        } else if (selected == 1) {
            Game.get().quit();
        }
    }

}
