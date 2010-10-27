package epsilon.game;

/**
 *
 * @author Marius
 */
public class optionPage extends MenuPage {

    optionPage() {
        super(new String[]{"Test 1", "Test 2"}, "Options");
    }

    @Override
    public void useSelected() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
