
package epsilonclient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collection;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.util.Iterator;

/**
 *
 * @author mm
 */
public class TestPanel extends JPanel implements MouseListener, MouseMotionListener {

    private int posX, posY;
    private TestWindow tw;
    private Map map;

    private boolean isConnected = false;


    /**
     * Constructor
     */
    public TestPanel(TestWindow tw, Map map) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        addMouseListener(this);
        addMouseMotionListener(this);
        setOpaque(true);
        this.tw = tw;
        this.map = map;
    }


    /**
     * Painting the graphics object.
     * Using Java2D and AA for smoother objects
     */
    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        g2.clearRect(0, 0, getWidth(), getHeight());

        HashMap<String, PlayerEntity> playerList = map.getPlayerList();
        Collection c = playerList.values();
        Iterator it = c.iterator();

        while (it.hasNext()) {
            PlayerEntity p = (PlayerEntity) it.next();
            System.out.println("Drawing player " + p.getName());
            p.drawPlayer(g2);

        }
    }


    // not in use
    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}


    /**
     * When the mouse is moved over the panel, the x and y coordinates of the
     * mouse pointer is sendt to the server
     */
    public void mouseMoved(MouseEvent e) {
        if (isConnected) {
            tw.sendCoordinates(e.getX(), e.getY());
        }
    }


    /**
     * Set the panel size. change for preferred resoluton
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800,600);
    }


    /**
     * change the isConnected flag
     */
    public void setConnectedState(boolean state) {
        this.isConnected = state;
    }

}
