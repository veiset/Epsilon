
package epsilonclient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * Painting panel to test network functionality
 * 
 * Move a ball around the panel by sending the coordinates to a server first
 *
 * @author mm
 */
public class TestPanel extends JPanel implements MouseListener, MouseMotionListener {

    private int xpos, ypos;
    private TestWindow tw;


    /**
     *
     * Constructor
     *
     */
    public TestPanel(TestWindow tw) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        addMouseListener(this);
        addMouseMotionListener(this);
        setOpaque(true);
        this.tw = tw;
    }


    /**
     *
     * Painting the graphics object.
     * Using Java2D and AA for smoother objects
     * 
     */
    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        g2.clearRect(0, 0, getWidth(), getHeight());
        g2.setPaint(Color.BLACK);
        g2.fill(new Ellipse2D.Double(xpos, ypos, 20, 20));

    }


    // not in use
    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}


    /**
     *
     * When the mouse is moved over the panel, the x and y coordinates of the
     * mouse pointer is sendt to the server
     *
     */
    public void mouseMoved(MouseEvent e) {
        tw.coordinatesToServer(e.getX(), e.getY());
    }


    /**
     *
     * Set the panel size. change for preferred resoluton
     *
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800,600);
    }


    /**
     * 
     * Set coordinates received from the server and call to repaint the panel
     * 
     */
    public void setCoordinates(int xpos, int ypos) {
        this.xpos = xpos;
        this.ypos = ypos;
        System.out.println("Setting coordinates " + "x: " + xpos + " y: " + ypos);
        repaint();
    }
    

}
