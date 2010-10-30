package epsilon.game;

/**
 *
 * @author Marius
 */
public class OptionPage extends MenuPage {

    private static OptionPage page = new OptionPage();

    private OptionPage() {
        super(new String[]{"Mute", "Exit Game"}, "Options");
    }

    @Override
    public void useSelected() {
        if (selected == 0) {
            if (items[0].equals("Mute")) {
                items[0] = "UnMute";
            } else {
                items[0] = "Mute";
            }
        } else if (selected == 1) {
            Game.get().quit();
        }
    }

    public static OptionPage get() {
        return page;
    }

}
