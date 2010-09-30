/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package epsilonclient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author mm
 */
public class TestWindow extends JFrame {

    private TestPanel tp;


    public TestWindow()  {
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
                try {

                }
                catch (Exception e2) {
                    System.out.println(e2.getMessage());
                }
            }
        });
        fileMenu.add(item1);

        JMenuItem item2 = new JMenuItem("Exit");
        item1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    System.exit(0);
                }
                catch (Exception e2) {
                    System.out.println(e2.getMessage());
                }
            }
        });
        fileMenu.add(item1);
    }

    public void exit() {

    }


}
