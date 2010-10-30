package epsilon.game;

/**
 *
 *
 * @author Marius
 */
public class NetworkPage extends MenuPage {

    private static NetworkPage page = new NetworkPage();

    private NetworkPage() {
        super(new String[]{"IP address: ", "Back"}, "Network setup");
    }

    @Override
    public void useSelected() {
        if (selected == 0) {
            
        } else if (selected == 1) {
            Menu.get().goToPrevious();
        }
    }

    public static NetworkPage get() {
         return page;
    }

}
