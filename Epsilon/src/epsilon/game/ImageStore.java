package epsilon.game;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 * Storing class for images. Implemented as a singleton.
 * Makes sure that images are only loaded into memory once.
 *
 * @author Marius
 */
public class ImageStore {

    /** The single instance of this class */
    private static ImageStore single = new ImageStore();

    /** Hashmaps saving the images */
    private HashMap<String,Image> images;
    private HashMap<String,Image> flippedImages;

    /** Constructor */
    private ImageStore () {
        images = new HashMap<String,Image>();
        flippedImages = new HashMap<String, Image>();
    }

    /**
     * Get the single instance of this class
     *
     * @return The single instance of this class
     */
    public static ImageStore get() {
        return single;
    }

    /**
     *
     * Creates a new image, and adds it to the image store.
     * Also Caches it and loads it into video memory.
     *
     * @param ref relative reference to the image to be used
     *
     */
    public Image get(String ref) {

        return getImage(ref, false, 1.0);

    }

    /**
     *
     * Creates a new image, and adds it to the image store.
     * Also Caches it and loads it into video memory.
     * Flips the image
     *
     * @param ref relative reference to the image to be used
     *
     */
    public Image getFlipped(String ref) {
        return getImage(ref, true, 1.0);
    }

    /**
     * Utility method to handle resource loading failure
     *
     * @param message The message to display on failure
     */
    private void fail(String message) {
            // we'n't available
            // we dump the message and exit the game

            System.err.println(message);
            System.exit(0);
    }

    /**
     *
     * Creates a new image, and adds it to the image store.
     * Also Caches it and loads it into video memory.
     *
     * @param ref relative reference to the image to be used
     * @param flip indicating wether the image should be flipped
     * @param scale variable defining the scaling of the image
     *
     */
    public Image getImage(String ref, boolean flip, double scale) {
        /** check if the image is already cached */
        if (flip) {
            if (flippedImages.get(ref) != null) {
                return flippedImages.get(ref);
            }
        } else {
            if (images.get(ref) != null) {
                return images.get(ref);
            }
        }

        BufferedImage sourceImage = null;


        try {
                // gets the url of the file
                URL url = this.getClass().getResource(ref);

                if (url == null) {
                        fail("Can't find ref: "+ref);
                }

                // use ImageIO to read the image in
                sourceImage = ImageIO.read(url);

        } catch (IOException e) {
                fail("Failed to load: "+ref);
        }

        //flip and scale the image the Image

        AffineTransform tx = null;

        if (flip) {
            tx = AffineTransform.getScaleInstance(-scale, scale);
            tx.translate(-sourceImage.getWidth(),0);
        } else {
            tx = AffineTransform.getScaleInstance(scale, scale);
            tx.translate(0,0);
        }
        AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
        sourceImage = op.filter(sourceImage, null);

        // create an accelerated image of the right size to store our sprite in
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        Image image = gc.createCompatibleImage(sourceImage.getWidth(),sourceImage.getHeight(),Transparency.TRANSLUCENT);

        // draw our source image into the accelerated image
        image.getGraphics().drawImage(sourceImage,0,0,null);

        // save the accelerated image in the cache
        if (flip) {
            flippedImages.put(ref, image);
        } else {
            images.put(ref, image);
        }

        return image;
    }

}
