
package epsilonclient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * Client test GUI
 *
 * Window to contain the painting panel and menu with connection option
 *
 * @author mm
 */
public class TestWindow extends JFrame {

    private TestPanel tp;
    private TestWindow tw = this;
    private Network net;


    /*
     * Constructor
     *
     */
    public TestWindow()  {
        initComponents();
    }


    /*
     * Initialize components contained in window
     *
     */
    private void initComponents() {
        tp = new TestPanel(tw);
        tw.getContentPane().add(tp, BorderLayout.CENTER);
        setVisible(true);
        setBackground(Color.WHITE);
        setTitle("Network test client");
        setLocation(150, 80);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        makeMenu();
        pack();
    }


    /*
     * Menu with options to connecto to a server and to exit the program
     * 
     */
    public void makeMenu() {
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);

        JMenu fileMenu = new JMenu("File");

        JMenuItem item1 = new JMenuItem("Connect");
        item1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {              
                String input = (String) JOptionPane.showInputDialog(
                    tw, "Host:", "Connect to Cinema server", JOptionPane.QUESTION_MESSAGE,
                    null, null, "localhost");
                if (input == null || input.trim().length() == 0) { return; }
                final String host = input.trim();

                try {
                    net = new Network(tw, host);
                    new Thread(net).start();
                }
                catch (SocketException se) {
                    JOptionPane.showMessageDialog(tw, "Could not connect to server");
                }
                catch (UnknownHostException ue) {
                    JOptionPane.showMessageDialog(tw, "Something wrong with the hostname.");
                }
            }
        });
        fileMenu.add(item1);

        JMenuItem item2 = new JMenuItem("Exit");
        item2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    System.exit(0);
                }
                catch (Exception e2) {
                    System.out.println(e2.getMessage());
                }
            }
        });
        fileMenu.add(item2);

        menubar.add(fileMenu);
    }


    /*
     * Send coordinates to painting panel
     *
     */
    public void coordinatesToPaint(int xpos, int ypos) {
        tp.setCoordinates(xpos, ypos);
    }


    /*
     * Send coordinates to the server
     *
     */
    public void coordinatesToServer(int xpos, int ypos) {
        try {
            if (net != null) {
                net.sendCoordinates(xpos, ypos);
            }
            else { System.out.println("Network not initialized"); }
        }
        catch (IOException ex) {
            System.out.println("Could not send packet to server");
        }
    }


    /*
     * Programs main method
     * 
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TestWindow();
            }
        });
    }

}
