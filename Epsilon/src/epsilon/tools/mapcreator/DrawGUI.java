package epsilon.tools.mapcreator;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;

/**
 * Creating the GUI, and the drawboard. This
 * is the main class, and should be run at
 * first. This creates object referances
 * to the other classes.
 *
 * @author vz
 * @version 0.10
 */
public class DrawGUI {

    private Datahistory db;
    private JFileChooser fc;
    private JToggleButton[][] buttons;
    private ButtonGroup[] groups;

    /**
     * Starting the program, and creating object referances.
     */
    public DrawGUI()
    {
        buttons = new JToggleButton[2][5];
        groups = new ButtonGroup[2];
        db = new Datahistory();
        fc = new JFileChooser();
        makeFrame();
    }

    /**
     * Main method, for initilizing the program
     *
     * @param args String[]
     */
    public static void main(String[] args)
    {
        new DrawGUI(); // initialize the program
    }

    /**
     * The method for creating the frames
     */
    public void makeFrame()
    {
        // Creating the frame, drawboard and the menus
        JFrame mainFrame = new JFrame("Graphics demo");
        final Drawboard drawBoard = new Drawboard(db);
        makeMenu(mainFrame,drawBoard);

        //toolbar(mainFrame,drawBoard);

        Container contentPane = mainFrame.getContentPane();
        contentPane.add(drawBoard);

        mainFrame.setBackground(Color.white);
        mainFrame.pack();
        mainFrame.setVisible(true);

        // Creating a windowListiner for closing, so the program is properly shut down.
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); } /* windowClosing */ }
       );
    }

    /**
     * Creating the menus, with inner functions.
     *
     * @param mainFrame
     * @param drawBoard
     */
    private void makeMenu(final JFrame mainFrame, final Drawboard drawBoard)
    {

        JMenuBar menubar = new JMenuBar();
        mainFrame.setJMenuBar(menubar);
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Mode");

        // Creating MenuItem with accordingly icon
        JMenuItem item1 = new JMenuItem("Clear");
        // Action listiner for file-opening
        item1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    db.clear();
                    drawBoard.repaint();
                }
                catch (Exception e2) {
                    System.out.println(e2.getMessage());
                }
            }
        });
        fileMenu.add(item1);


        // Action listiner for file-saving
        JMenuItem item2 = new JMenuItem("Show");
        item2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    db.printall();
                    drawBoard.repaint();
                }
                catch (Exception e2) {
                    System.out.println(e2.getMessage());
                }
            }
        });
        fileMenu.add(item2);
        
        fileMenu.add(new JSeparator());


        // Adding both menu-groups to the menubar
        menubar.add(fileMenu);
    }

    /**
     * Selecting a file from disk. Throwing an
     * expection if anything bad happens.
     *
     * @return
     * @throws java.lang.Exception
     */
    private File getFile() throws Exception
    {
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            return file;
        }
        return null;
    }

}
