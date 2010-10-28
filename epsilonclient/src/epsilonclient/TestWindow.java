
package epsilonclient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * Client test GUI
 * Window to contain the painting panel and menu with connection option
 *
 * @author mm
 */
public class TestWindow extends JFrame {

    private TestPanel tp;
    private TestWindow tw = this;
    private ClientHandler clientHandler;
    private Map map;


    /**
     * Constructor
     */
    public TestWindow()  {
        initComponents();
    }


    /**
     * Initialize components contained in window
     */
    private void initComponents() {
        map = new Map();
        tp = new TestPanel(tw, map);
        tw.getContentPane().add(tp, BorderLayout.CENTER);
        setVisible(true);
        setBackground(Color.WHITE);
        setTitle("Network test client");
        setLocation(150, 80);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        makeMenu();
        pack();
    }


    /**
     * Menu with options to connecto to a server and to exit the program
     */
    public void makeMenu() {
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);

        JMenu fileMenu = new JMenu("File");

        JMenuItem item1 = new JMenuItem("Connect");
        item1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {

                String nameInput = (String) JOptionPane.showInputDialog(
                        tw, "Name: ", "Player name", JOptionPane.QUESTION_MESSAGE,
                        null, null, "mm");
                String hostInput = (String) JOptionPane.showInputDialog(
                        tw, "Host:", "Connect to Cinema server", JOptionPane.QUESTION_MESSAGE,
                        null, null, "127.0.0.1");

                if (hostInput == null || hostInput.trim().length() == 0
                        || nameInput == null || nameInput.trim().length() == 0) { return; }

                final String host = hostInput.trim();
                final String name = nameInput.trim();

                clientHandler = new ClientHandler(map, host, name, tp);
                clientHandler.startClient();
                tp.setConnectedState(true);
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


    /**
     * send movement coordinates to server
     */
    public void sendCoordinates(int posX, int posY) {
        clientHandler.sendCoordinates(posX, posY);
    }


}
