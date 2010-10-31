package epsilon.game;

/**
 *
 * @author Marius
 */
public class OptionPage extends MenuPage {

    // only instance of this class
    private static OptionPage page = new OptionPage();

    /**
     * Private constructor used for initialising variables
     */
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

    /**
     * Method that gives access to the single object of this class
     *
     * @return the only instance of this class
     */
    public static OptionPage get() {
        return page;
    }

}
