package epsilon.menu;

import epsilon.game.Input;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 * Abstract class describing
 *
 * @author Marius
 */
public abstract class MenuPage {

    // the items to be displayed
    protected String[] items;

    // the index of the currently selected pageitem
    protected int selected;

    // the title of the page
    protected String title;

    // the errormessage to be displayed. It's "" if no error is to be displayed
    protected String errorMessage;

    /**
     * Initiates a menupage based on a string of pageItems and the title of the page
     *
     * @param pageItems Array containing the text if the items that should be displayed
     * @param pageTitle The title text of the page
     */
    public MenuPage(String[] pageItems, String pageTitle) {

        items = pageItems;
        selected = 0;
        title = pageTitle;
        errorMessage = "";
        
    }

    /**
     * Draws the page onto the specified graphics and with the chosen font
     *
     * @param g the graphic the page is to be drawn at
     * @param f the font to be used in the drawing
     */
    public void drawPage(Graphics2D g, Font f) {

        int y = 0;
        float fontSize = f.getSize2D();
        
        g.setFont(f.deriveFont(fontSize*(float)1.25));
        g.setColor(Color.RED);

        FontMetrics fm = g.getFontMetrics();
        y += 50 + fm.getHeight()/2;

        g.drawString(title, 400-(fm.stringWidth(title)/2), y);
        y += fm.getHeight()/2;

        g.setFont(f);
        g.setColor(Color.GREEN);
        y += 50;

        for (int i=0;i<items.length;i++) {

            if (i == selected) {
                g.setFont(f.deriveFont(Font.BOLD,fontSize*(float)1.25));
            }
            
            fm = g.getFontMetrics();

            y += 45 + fm.getHeight()/2;

            g.drawString(items[i], 400-(fm.stringWidth(items[i])/2), y);

            y += fm.getHeight()/2;
            
            g.setFont(f);
        }

        if (!errorMessage.equals("")) {
            g.setFont(f.deriveFont(Font.ITALIC, fontSize*(float)0.75));
            fm = g.getFontMetrics();
            y += 48 + fm.getHeight()/2;
            g.setColor(Color.orange);
            g.drawString("Error: " + errorMessage, 400-(fm.stringWidth("Error: " +errorMessage))/2, y);
        }

    }

    /**
     * Move the slection to the menu item with one higher index
     */
    public void selectNext() {
        if (selected < items.length - 1) {
            selected++;
        }
    }

    /**
     * Move the slection to the menu item with one lower index
     */
    public void selectPrevious() {
        if (selected > 0) {
            selected--;
        }
    }

    /**
     * Method that specifies  what is to be done when the menuchoicebutton is
     * pressed. The selected variable will store what menuitem is currently
     * hovered above, and it should be checked against
     *
     * @see epsilon.menu.OptionPage
     */
    public abstract void useSelected();

    /**
     * Method that is run whenever the gameupdate loop is running and this
     * menupage is opened.
     */
    public void update() {

        if (Input.get().useMenu()) {
            useSelected();
        } else if (!Input.get().getMenuArrowHandeled()) {
            if (Input.get().jump()) {
                Input.get().handleMenuArrow();
                selectPrevious();
            } else if (Input.get().duck()) {
                Input.get().handleMenuArrow();
                selectNext();
            }
        }

    }

    /**
     * Resets wich item is currently selected
     */
    public void reset() {
        selected = 0;
    }

}
