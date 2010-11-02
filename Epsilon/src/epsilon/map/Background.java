package epsilon.map;

import epsilon.game.ImageStore;
import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * Class used for drawing the background of a map. The background is moving
 * slower than the player object.
 *
 * @author vz
 */
public class Background {

    // background image
    private Image bgImage;

    // start values
    private int repeat = 0;
    private int repeatPos = 0;

    // image width
    private int imageSize = 2000;

    // relative distance is how fast the background will move relative to
    // the x and y positions the Background.render() method will get as input.
    private double relativeDistance = 0.5;

    /**
     * Initializing the background handler.
     *
     * @param ref path of the image
     * @param scale scale of the image
     */
    public Background(String ref, double scale) {
        ImageStore s = ImageStore.get();

        repeat = 0;
        repeatPos = 0;
        bgImage = s.getImage(ref, false, scale);
        imageSize = bgImage.getWidth(null);
    }

    /**
     * Updates the background image relative to where the player position is.
     * Using an offset to move the background slower than the player, creating
     * more feeling of depth.
     *
     * @param g graphics object to render background on
     * @param x x-position
     * @param y y-position
     */
    public void render(Graphics g, double x, double y) {

        // times the background has been repeated, as x isn't relative to the screen positions
        repeat = ((int) ((x * relativeDistance)) / imageSize);

        // background scrolling fix if you go to the left. 
        // repeat = 0 is an issue when going left.
        if (x*relativeDistance < 0) { repeat += -1; }
        // relative position
        repeatPos = repeat*imageSize;

        // render picture while the imageSize is larger than
        //System.out.println(((x*relativeDistance)));
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
