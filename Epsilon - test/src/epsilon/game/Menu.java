package epsilon.game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 *
 * @author Marius
 */
public class Menu {

    private static Menu menu = new Menu();

    private Font font;
    private MenuPage currentPage;

    private Menu() {
        
        URL url = this.getClass().getResource("/fonts/THECF.TTF");

        File f;
        try {
          f = new File(url.toURI());
        } catch(URISyntaxException e) {
          f = new File(url.getPath());
        }

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, f);
            font = font.deriveFont(48f);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public static Menu get() {
        return menu;
    }

    public void render(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
        g.setFont(font);
        g.drawString("Test test test test :)", 100, 100);
    }

    public void reset() {

    }

    public void update() {
        
    }

}
