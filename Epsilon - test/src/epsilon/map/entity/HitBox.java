package epsilon.map.entity;

import java.awt.Graphics;

/**
 *
 * @author Marius
 */
public class HitBox {

    private int offsetX;
    private int offsetY;

    private int width;
    private int height;

    public HitBox(int offX, int offY, int width, int height) {
        offsetX = offX;
        offsetY = offY;

        this.width = width;
        this.height = height;
    }

    public boolean[] collidesWith(HitBox box, double posX, double posY, double bPosX, double BposY) {
        return null; //not yet implemented
    }

    public void draw (Graphics g, double posX, double posY) {
        g.drawRect((int)posX + offsetX, (int) posY + offsetY, width, height);
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
