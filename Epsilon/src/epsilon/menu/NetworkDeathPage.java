package epsilon.menu;

import epsilon.game.Game;

/**
 * Standardpage that shows when you die on a Network Map.
 *
 * @author md
 */
public class NetworkDeathPage extends MenuPage {

    /**
     * Creates a new Network Death page. Sets the available options and an attempt at a humorous message.
     * Failed miserably
     */
    public NetworkDeathPage() {
        super(new String[]{"Respawn", "Exit"}, "Press respawn to continue");
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
