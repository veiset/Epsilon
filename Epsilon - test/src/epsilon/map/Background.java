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
    private int repeat = 0;
    private int repeatPos = 0;
    private int imageSize = 2000;
    private double speed = 0.2;

    public Background(String ref, double scale) {
        ImageStore s = ImageStore.get();

        bgImage = s.getImage(ref, false, scale);
        bgImageFlipped = s.getImage(ref, true, scale);
        imageSize = bgImage.getWidth(null);
    }

    public void render(Graphics g, double x, double y) {


        repeat = ((int) ((x * speed)) / imageSize);
        repeatPos = repeat*imageSize;

        if ((x*speed) < imageSize+repeatPos || (x*speed) == imageSize+repeatPos) {
            g.drawImage(bgImage, 0 - (int) (x * speed)+repeatPos, 0 - (int) (y * speed), null);
        }
        if ((x*speed+800) > imageSize+repeatPos) {
            g.drawImage(bgImage, 0 - (int) (x * speed)+(repeatPos)+imageSize, 0 - (int) (y * speed), null);
        }

    }
}
