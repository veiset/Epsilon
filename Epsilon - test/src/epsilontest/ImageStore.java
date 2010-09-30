package epsilontest;

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
 * Storing class for sprites. Implemented as a singleton.
 * Makes sure that sprites are only loaded into memory once.
 *
 * @author Marius
 */
public class ImageStore {

    /** The single instance of this class */
    private static ImageStore single = new ImageStore();
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
     */
    public Image get(String ref) {

	if (images.get(ref) != null) {
            return images.get(ref);
        }
                
            BufferedImage sourceImage = null;

            try {
                    // The ClassLoader.getResource() ensures we get the sprite
                    // from the appropriate place, this helps with deploying the game
                    // with things like webstart. You could equally do a file look
                    // up here.

                    URL url = this.getClass().getResource(ref);

                    //URL url = new URL(ref);

                    if (url == null) {
                            fail("Can't find ref: "+ref);
                    }

                    // use ImageIO to read the image in

                    sourceImage = ImageIO.read(url);
            } catch (IOException e) {
                    fail("Failed to load: "+ref);
            }

            // create an accelerated image of the right size to store our sprite in

            GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
            Image image = gc.createCompatibleImage(sourceImage.getWidth(),sourceImage.getHeight(),Transparency.BITMASK);

            // draw our source image into the accelerated image

            image.getGraphics().drawImage(sourceImage,0,0,null);

        return image;
        
    }

    public Image getFlipped(String ref) {
	if (images.get(ref) != null) {
            return images.get(ref);
        }

            BufferedImage sourceImage = null;

            try {
                    // The ClassLoader.getResource() ensures we get the sprite
                    // from the appropriate place, this helps with deploying the game
                    // with things like webstart. You could equally do a file look
                    // up here.

                    URL url = this.getClass().getResource(ref);

                    //URL url = new URL(ref);

                    if (url == null) {
                            fail("Can't find ref: "+ref);
                    }

                    // use ImageIO to read the image in

                    sourceImage = ImageIO.read(url);
            } catch (IOException e) {
                    fail("Failed to load: "+ref);
            }

            //flip the Image

            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-sourceImage.getWidth(),0);
            AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            sourceImage = op.filter(sourceImage, null);

            // create an accelerated image of the right size to store our sprite in

            GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
            Image image = gc.createCompatibleImage(sourceImage.getWidth(),sourceImage.getHeight(),Transparency.BITMASK);

            // draw our source image into the accelerated image

            image.getGraphics().drawImage(sourceImage,0,0,null);

        return image;
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



}
