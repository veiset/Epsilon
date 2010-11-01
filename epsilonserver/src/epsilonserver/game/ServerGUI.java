package epsilonserver.game;

import epsilonserver.net.NetworkHandler;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.text.DefaultCaret;

/**
 * Simple GUI class for the server
 * @author mm
 */
public class ServerGUI extends JFrame {

    private ServerGUI sgui = this;
    private JTextArea logBox = new JTextArea();

    /**
     * Constructor
     */
    private ServerGUI() {
        setLookAndFeel();
        initComponents();
    }

    private static class ServerGUIHolder {
        public static final ServerGUI INSTANCE = new ServerGUI();
    }

    public static ServerGUI getInstance() {
        return ServerGUIHolder.INSTANCE;
    }


    /**
     * Initialize GUI components
     */
    private void initComponents() {
        Container contentpane = this.getContentPane();
        contentpane.setLayout(new BorderLayout());
        setVisible(true);
        setBackground(Color.GRAY);
        setTitle("Epsilon server");
        setLocation(150, 80);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        makeMenu();

        JPanel mainPanel = new JPanel(new GridLayout(1,1));
        JComponent configPanel = makeConfigPanel();
        mainPanel.add(configPanel);
        contentpane.add(mainPanel, BorderLayout.CENTER);
        pack();

        setLogMessage("Server log: ");
    }

    /**
     * Add a server config panel
     */
    private JComponent makeConfigPanel() {
        JPanel configPanel = new JPanel(new BorderLayout());

        // just some info
        JPanel infoPanel = new JPanel(new FlowLayout());
        JLabel infoLabel = new JLabel("Epsilon server configuration");
        infoPanel.add(infoLabel);

        JPanel panel = new JPanel(new GridLayout(1,2));
        JPanel panel2 = new JPanel(new FlowLayout());

        JButton startButton = new JButton("Start Server");
        startButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                Game g = Game.get();
                g.start();
            }
        });
        panel2.add(startButton);

        JButton stopButton = new JButton("Stop Server");
        stopButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                NetworkHandler.getInstance().stopServer();
            }
        });
        panel2.add(stopButton);

        panel.add(panel2);

        // Add a scrolling text area
        logBox.setEditable(false);
        logBox.setRows(20);
        logBox.setColumns(40);
        DefaultCaret caret = (DefaultCaret) logBox.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        panel.add(new JScrollPane(logBox));

        configPanel.add(infoPanel, BorderLayout.NORTH);
        configPanel.add(panel, BorderLayout.CENTER);
        return configPanel;
    }

    /**
     * Add a simple top menu
     */
    private void makeMenu() {
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);

        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");

        JMenuItem item1 = new JMenuItem("Close");
        item1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(item1);

        JMenuItem item3 = new JMenuItem("About");
        item3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(sgui, "Epsilon server");
            }
        });
        helpMenu.add(item3);

        menubar.add(fileMenu);
        menubar.add(helpMenu);
    }

    /**
     * Add a message to the text area
     * @param message
     */
    public void setLogMessage(String message) {
        logBox.append(message + "\n");
    }

    /**
     * Set a preferred frame size
     * @return Dimension
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(930,620);
    }

    /**
     * Set swing look and feel to Nimbus
     */
    public void setLookAndFeel() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
    		}
            }
    	}
        catch(Exception e) {
    		setLogMessage("Could not load nimbus look");
    	}
    }

}
