package epsilon.map;

import epsilon.game.ImageStore;
import java.awt.Graphics;
import java.awt.Image;

/**
 * 
 * Class used for drawing the background of a map
 *
 * @author Marius
 */
public class Background {

    private Image bgImage;
    private Image bgImageFlipped;

    public Background(String ref, double scale) {
        ImageStore s = ImageStore.get();

        bgImage = s.getImage(ref, false, scale);
        bgImageFlipped = s.getImage(ref, true, scale);
    }

    public void render(Graphics g, double x, double y) {
        g.drawImage(bgImage,0-(int)x,0-(int)y,null);
    }

}
