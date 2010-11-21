package epsilon.menu;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class based on the singleton pattern that returns the Menu used in this game.
 *
 * @author md
 */
public class Menu {

    private static Menu menu = new Menu();

    private Font font;
    private MenuPage currentPage;
    private MenuPage previousPage;

    private Menu() {

        InputStream is = Menu.class.getResourceAsStream("/fonts/agentred.ttf");

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, is);
            font = font.deriveFont(24f);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

         currentPage = OptionPage.get();        
    }

    /**
     * Standard singleton method
     *
     * @return the only instance of this class
     */
    public static Menu get() {
        return menu;
    }


    /**
     * Draws the menu onto the specified graphics object
     *
     * @param g the graphics object the menu should be drawn on
     */
    public void render(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
        g.setFont(font);

        currentPage.drawPage(g, font);
    }

    /**
     * Resets the menu to standard config
     */
    public void reset() {
        currentPage = OptionPage.get();
        currentPage.reset();
    }

    /**
     * Method run by the update thread, makes it change position
     */
    public void update() {
        currentPage.update();
    }

    /**
     * Changes the menuPage currently being shown
     *
     * @param p the MenuPage that the menu should change to
     */
    public void setMenu(MenuPage p) {
        currentPage.reset();
        previousPage = currentPage;
        currentPage = p;
        p.reset();
    }

    /**
     * Changes the page to the previous one
     */
    public void goToPrevious() {
        currentPage = previousPage;
    }

    /**
     * Returns the standard font object set in the Menu constructor
     *
     * @return the current standard font
     */
    public Font getFont() {
        return font;
    }

}
