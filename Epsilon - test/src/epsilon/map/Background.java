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
    private double relativeDistance = 0.5;

    public Background(String ref, double scale) {
        ImageStore s = ImageStore.get();

        repeat = 0;
        repeatPos = 0;
        bgImage = s.getImage(ref, false, scale);
        imageSize = bgImage.getWidth(null);
    }

    public void render(Graphics g, double x, double y) {

        // times the background has been repeated, as x isn't relative to the screen positions
        repeat = ((int) ((x * relativeDistance)) / imageSize);
        // relative position
        repeatPos = repeat*imageSize;

        // render picture while the imageSize is larger than
        if ((x*relativeDistance) < imageSize+repeatPos || (x*relativeDistance) == imageSize+repeatPos) {
            // 0 - (pixels the picture should been draw at, relative to xPos to the player)
            g.drawImage(bgImage, 0 - (int) (x * relativeDistance)+repeatPos, -80 - (int) (y * relativeDistance/10)-100, null);
        }
        // fade a second picture over to get a repeated background
        if ((x*relativeDistance+800) > imageSize+repeatPos) {
            g.drawImage(bgImage, 0 - (int) (x * relativeDistance)+(repeatPos)+imageSize, -80 - (int) (y * relativeDistance/10)-100, null);
        }

    }
}
