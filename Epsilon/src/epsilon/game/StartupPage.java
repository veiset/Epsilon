package epsilon.game;

import epsilon.map.entity.NetworkMap;
import epsilon.map.entity.TestMap;

/**
 *
 * @author Marius
 */
public class StartupPage extends MenuPage {

    private static StartupPage page = new StartupPage();

    private StartupPage() {
        super(new String[]{"Single Player", "Multi Player", "Exit Game"}, "Choose Gameplay Mode");
    }

    @Override
    public void useSelected() {
        if (selected == 0) {
            Game.get().setMap(new TestMap());
            Menu.get().setMenu(OptionPage.get());
            Game.get().menuDone();
        } else if (selected == 1) {
            Game.get().setMap(new NetworkMap());
            Menu.get().setMenu(OptionPage.get());
            Game.get().menuDone();
        } else if (selected == 3) {
            Game.get().quit();
        }
    }

    public static StartupPage get() {
        return page;
    }

}
