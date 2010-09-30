package epsilontest;

import java.awt.Graphics;
import java.awt.Image;

/**
 * Class used to store images and manage animation.
 *
 * @author Marius
 */
public class Sprite {

	/** The images to be drawn for this sprite */
	private Image[] image;

        /** Current position in the animation */
        private int pos;

	/**
	 * Create a new sprite based on an list of urls.
         * Loads the images contained in the urls.
	 *
	 * @param urls Array of strings containing urls to the images
	 */
	public Sprite(String[] urls) {

                ImageStore s = ImageStore.get();

                pos = 0;

                Image[] images = new Image[urls.length];
                
                for (int i=0;i<urls.length;i++) {
                    images[i] = s.get(urls[i]);
                }

		this.image = images;
	}

	/**
	 * Get the width of the drawn sprite
	 *
	 * @return The width in pixels of this sprite
	 */
	public int getWidth() {
		return image[0].getWidth(null);
	}

	/**
	 * Get the height of the drawn sprite
	 *
	 * @return The height in pixels of this sprite
	 */
	public int getHeight() {
		return image[0].getHeight(null);
	}

	/**
	 * Draw the sprite onto the graphics context provided
	 *
	 * @param g The graphics context on which to draw the sprite
	 * @param x The x location at which to draw the sprite
	 * @param y The y location at which to draw the sprite
	 */
	public synchronized void draw(Graphics g,int x,int y) {
		g.drawImage(image[pos],x,y,null);
	}

        /**
         * Go to the next image of the sprite for rendering
         */
        public synchronized void nextImage() {
            if (pos < image.length-1) {
                pos++;
            } else {
                pos = 0;
            }
        }

        /**
         *  Reset the sprite to the first image
         */
        public void resetImage() {
            pos = 0;
        }



}
