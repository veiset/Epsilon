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
    private int repeat;
    private int repeatPos;
    private int imageSize;;
    private double speed;

    public Background(String ref, double scale) {
        ImageStore s = ImageStore.get();

        repeat = 0;
        repeatPos = 0;
        speed = 0.5;

        bgImage = s.getImage(ref, false, scale);
        imageSize = bgImage.getWidth(null);
    }

    public void render(Graphics g, double x, double y) {


        repeat = ((int) ((x * speed)) / imageSize);
        repeatPos = repeat*imageSize;

        if ((x*speed) < imageSize+repeatPos || (x*speed) == imageSize+repeatPos) {
            g.drawImage(bgImage, 0 - (int) (x * speed)+repeatPos, 0 - (int) (y * speed)-100, null);
        }
        if ((x*speed+800) > imageSize+repeatPos) {
            g.drawImage(bgImage, 0 - (int) (x * speed)+(repeatPos)+imageSize, 0 - (int) (y * speed)-100, null);
        }

    }
}
