

package epsilonclient;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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

    public void paintComponent(Graphics2D g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.drawOval(xpos, ypos, 10, 10);


    }
s



    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseMoved(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
