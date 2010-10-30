package epsilon.game;

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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static StartupPage get() {
        return page;
    }

}
