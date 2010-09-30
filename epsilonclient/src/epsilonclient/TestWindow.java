/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package epsilonclient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author mm
 */
public class TestWindow extends JFrame {

    private TestPanel tp;
    private TestWindow tw = this;
    private Network net;


    public TestWindow()  {
        net = new Network();
        initComponents();
    }


    private void initComponents() {
        tp = new TestPanel();
        this.getContentPane().add(tp, BorderLayout.CENTER);
        setVisible(true);
        setBackground(Color.WHITE);
        setTitle("Network test client");
        setLocation(150, 80);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        makeMenu();
        pack();
    }


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
                    net.connect(host);
                }
                catch (IOException ioe) {
                    JOptionPane.showMessageDialog(tw, "Could not connect to server.");
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


}
