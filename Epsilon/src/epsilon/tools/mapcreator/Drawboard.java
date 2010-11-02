package epsilon.tools.mapcreator;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JPanel;

/**
 * Containing eventlistiners for user-input;
 * mouse events / mouse motion. 
 *
 * @author vz
 * @version 1.0
 */
public class Drawboard extends JPanel implements MouseListener, MouseMotionListener {

    // Local variables
    private Datahistory db;
    private int focus = 4;
    private boolean fill = false;
    private boolean er = false;
    private int offset = 0;

    /**
     * This is the main panel, starting the listiners
     *
     * @param db Datahistory
     */
    public Drawboard(Datahistory db) {
        addMouseListener(this);
        addMouseMotionListener(this);
        this.db = db;
    }

    /**
     * Returning the filling state of the object, hence;
     * if the drawed object should be filled
     *
     * @return fill boolean
     */
    public boolean getFill() {
        if (fill) {
            fill = false;
        } else {
            fill = true;
        }
        return fill;
    }

    /**
     * Checking what of the UI button is focused
     *
     * @return focus int
     */
    public int getFocus() {
        return focus;
    }

    /**
     * Setting new focused button
     *
     * @param focus int
     */
    public void setFocus(int focus) {
        this.focus = focus;
    }

    /**
     * Main paint method
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {


        g.clearRect(0, 0, getWidth(), getHeight());

        for (int i = 0; i < getWidth(); i += 20) {
            g.drawLine(i, 0, i, getHeight());
        }

        for (int i = 0; i < getHeight(); i += 20) {
            g.drawLine(0, i, getWidth(), i);
        }
        db.paint(g);
    }

    /**
     * Mouse button pressed
     * 
     * @param e Mousevent
     */
    public void mousePressed(MouseEvent e) {
    }

    /**
     * @param e MouseEvent
     */
    public void mouseReleased(MouseEvent e) {
        repaint();
    }

    /**
     * Method for clearing the shape objects
     */
    public void clear() {
        db.clear();
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1500, 300);
    }

    /**
     * Removing the last shape object.
     */
    public void back() {
        db.back();
        repaint();
    }

    /**
     * Toggeling the ereaser.
     */
    public void er() {
        if (er) {
            er = false;
        } else {
            er = true;
        }
    }

    /**
     * Getting new positions for the mouse, and updating
     * the shape object with new cordinates. Then calling
     * the repaint method.
     *
     * @param arg0 MouseEvent
     */
    public void mouseDragged(MouseEvent arg0) {
        repaint();
    }

    // Unused part of the 'MouseListener' interface
    public void mouseClicked(MouseEvent e) {
        int row = (e.getX()/20);
        int col = (e.getY()/20);
        if (!db.remove(row,col)) {
            db.add(new Block(row,col));
        }
        repaint();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
    // Unused part of the 'MouseMotionListiner' interface

    public void mouseMoved(MouseEvent arg0) {
    }

    public void setOffset(int i) {
        db.setCurrentDB(i);
        repaint();
    }

 
}
