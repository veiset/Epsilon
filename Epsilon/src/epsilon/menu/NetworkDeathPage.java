package epsilon.menu;

import epsilon.game.Game;

/**
 *
 * @author Marius
 */
public class NetworkDeathPage extends MenuPage {

    public NetworkDeathPage() {
        super(new String[]{"Respawn", "Exit"}, "You are dead!!!!!");
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
