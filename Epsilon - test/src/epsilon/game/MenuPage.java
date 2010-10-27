/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package epsilon.game;

import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author Marius
 */
public abstract class MenuPage {

    private String[] items;
    private int selected;
    private String title;

    public MenuPage(String[] pageItems, String pageTitle) {
        items = pageItems;
        selected = 0;
        title = pageTitle;
    }

    public void drawPage(Graphics2D g, Font f) {

        f = f.deriveFont(72f);
        g.drawString(title, 400-(title.length()*72/2), 80);
        
    }

}
