package epsilon.game;

import epsilon.map.entity.HitBox;
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

        /** the hitbox of the animation */
        private HitBox[] hitbox;

        /** */
        private int offset;

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

                hitbox = new HitBox[]{new HitBox(0, 0, image[0].getWidth(null), image[0].getHeight(null))};

                offset = (int)Math.round(calculateOffset(hitbox)*1.5);
	}

	/**
	 * Create a new sprite based on an list of urls.
         * Loads the images contained in the urls.
	 *
	 * @param urls Array of strings containing urls to the images
         * @param flip Set this as true if you want the images flipped over tye y axis
	 */
	public Sprite(String[] urls, boolean flip, HitBox[] h) {

            ImageStore s = ImageStore.get();

            pos = 0;

            Image[] images = new Image[urls.length];

            for (int i=0;i<urls.length;i++) {
                if (flip) {
                    images[i] = s.getFlipped(urls[i]);
                } else {
                    images[i] = s.get(urls[i]);
                }
            }

            this.image = images;

            if (flip) {
                hitbox = flipHitBox(h);
            } else {
                hitbox = h;
            }

            offset = calculateOffset(hitbox);
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
            //g.drawRect(x, y, getWidth(), getHeight());
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

        /**
         * Gets the hitbox this sprite uses
         */
        public HitBox[] getHitBox() {
            return hitbox;
        }

        /**
         * Moves the hitboxes around in the picture, so that they will be in
         * the correct position if the pictures is flipped over the Y-axis
         *
         * @param hitbox an array of hitbox objects that is used for this picture when it isn't flipped
         * @return a new hitbox array with the correct offsets for a flipped image
         */
        private HitBox[] flipHitBox(HitBox[] hitbox) {
            HitBox[] result = new HitBox[hitbox.length];
            for (int i=0;i<hitbox.length;i++) {
                // black magic happens here
                result[i] = new HitBox(getWidth() - hitbox[i].getOffsetX() - hitbox[i].getWidth(), hitbox[i].getOffsetY(), hitbox[i].getWidth(), hitbox[i].getHeight());
            }
            return result;
        }

        /**
         * Calculates an appropriate offset baes on the hitboxes
         *
         * @param hitbox an array of hitbox objects that should be used for the offset calculation
         * @return the number of pixels the offset should be set at.
         */
        private int calculateOffset(HitBox[] hitbox) {
            int left = 800;
            int right = 0;

            // more black magic happens here
            for (int i=0; i<hitbox.length; i++) {
                if (hitbox[i].getOffsetX() < left) {
                    left = hitbox[i].getOffsetX();
                }
                if (hitbox[i].getOffsetX() +  hitbox[i].getWidth() > right) {
                    right = hitbox[i].getOffsetX() +  hitbox[i].getWidth();
                }
            }
            
            return ((right + left)/2)-getWidth()/2;
        }

        /**
         * Method for getting the offset to be used when flipping the image
         *
         * @return the amount of pixels the image should be moved when flipping the image
         */
        public int getOffset() {
            return offset;
        }
}
