package epsilon.tools.mapcreator;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class for manging the objects that should
 * be drawn onto the drawboard. Also
 * responsible for file I/O.
 *
 * @author vz
 * @version 0.1
 */
public class Datahistory {

    // local variables
    ArrayList<Block> database;

    /**
     * The constructor for Datahistory
     */
    public Datahistory() {
        database = new ArrayList<Block>();
    }

    /**
     * Adding a shape to the 'database' of
     * shape objects.
     *
     * @param shape
     */
    public void add(Block block) {
        database.add(block);
    }

    public boolean remove(int row, int col) {
        Iterator<Block> itr = database.iterator();
        while (itr.hasNext()) {
            Block block = itr.next();
            if (block.getRow() == row && block.getCol() == col) {
                database.remove(block);
                return true;
            }
        }
        return false;
    }

    /**
     * Drawing all shape objects
     *
     * @param g Graphic
     */
    public void paint(Graphics g) {
        Iterator<Block> itr = database.iterator();
        while (itr.hasNext()) {
            Block block = itr.next();
            g.setColor(Color.BLACK);
            block.paint(g);
        }
    }

    /**
     * Saving a file to disk using a fileoutput stream.
     * Used for a serializable object.
     *
     * @param file
     * @throws java.lang.Exception
     */
    public void save(File file) throws Exception {
        FileOutputStream fileoutput = new FileOutputStream(file);
        ObjectOutputStream objOS = new ObjectOutputStream(fileoutput);
        objOS.writeObject(database);
        objOS.close();
        fileoutput.close();
    }

    /**
     * Reading a file from disk (containing an serialized arraylist)
     * and using the arraylist for later usage.
     *
     * @param file
     * @throws java.lang.Exception
     */
    public void load(File file) throws Exception {
        FileInputStream fileinput = new FileInputStream(file);
        ObjectInputStream objIS = new ObjectInputStream(fileinput);
        database = (ArrayList) objIS.readObject();
        objIS.close();
        fileinput.close();
    }

    /**
     * Removing the last added object from the arraylist.
     */
    public void back() {
        if (database.size() > 0) {
            database.remove(database.size() - 1);
        }
    }

    /**
     * Removing all the objects in the arraylist.
     */
    public void clear() {
        database.clear();
    }

    public void printall() {
        Iterator<Block> itr = database.iterator();
        while (itr.hasNext()) {
            Block block = itr.next();
            block.printJava();
        }
    }
}
