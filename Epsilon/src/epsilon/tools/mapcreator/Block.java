/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package epsilon.tools.mapcreator;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author vz
 */
public class Block extends JPanel {

    private int row;
    private int col;

    public Block(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void printJava() {
        System.out.println("worldstore.add(new Floor_1("+((row*50)-400) +","+col*40+"));");
    }

    @Override
    public void paint(Graphics g) {
        g.fillRect(row*20, col*20, 20, 20);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
