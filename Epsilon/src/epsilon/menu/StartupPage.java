package epsilon.menu;

import epsilon.game.Game;
import epsilon.map.SinglePlayerMap;
import epsilon.map.WorldTestMap;

/**
 * The first menupage that is shown when the game is started. Lets you choose 
 * between game modes and maps.
 *
 * @author md
 */
public class StartupPage extends MenuPage {

    // only instance of this class
    private static StartupPage page = new StartupPage();

    /**
     * Private constructor used for initialising variables
     */
    private StartupPage() {
        super(new String[]{"Single Player", "Multi Player", "World Test Map" , "Exit Game"}, "Choose Gameplay Mode");
    }

    @Override
    public void useSelected() {
        if (selected == 0) {
            Game.get().setMap(new SinglePlayerMap(""));
            Menu.get().setMenu(OptionPage.get());
            Game.get().menuDone();
        } else if (selected == 1) {
            Menu.get().setMenu(NetworkPage.get());
        } else if (selected == 2) {
            Game.get().setMap(new WorldTestMap());
            Menu.get().setMenu(OptionPage.get());
            Game.get().menuDone();
        } else if (selected == 3) {
            Game.get().quit();
        }
    }

    /**
     * Method that gives access to the single object of this class
     *
     * @return the only instance of this class
     */
    public static StartupPage get() {
        return page;
    }

}
