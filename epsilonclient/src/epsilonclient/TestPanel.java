

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
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author mm
 */
public class TestPanel extends JPanel implements MouseListener, MouseMotionListener {

    private int xpos, ypos;


    public TestPanel() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        addMouseListener(this);
        addMouseMotionListener(this);
        setOpaque(true);
    }

    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        g2.clearRect(0, 0, getWidth(), getHeight());
        g2.setPaint(Color.BLACK);
        g2.fill(new Ellipse2D.Double(xpos, ypos, 20, 20));


    }




    public void mouseClicked(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseDragged(MouseEvent e) {}

    public void mouseMoved(MouseEvent e) {
        xpos = e.getX();
        ypos = e.getY();
        //System.out.println("x: " + xpos + "  " + "y: " + ypos);
        repaint();
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800,600);
    }

}
