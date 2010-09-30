package epsilontest;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Class based on the singleton design pattern
 * used for handling key and mouse input.
 *
 * @author Marius
 */
public class Input implements MouseListener, KeyListener {

    // the only intialised input object
    private static Input input = new Input();

    // saved names of the resources
    private int jumpButton;
    private int duckButton;
    private int leftButton;
    private int rightButton;
    private int attackButton;
    private int menuButton;

    // true if a button is pushed
    private boolean jump;
    private boolean duck;
    private boolean left;
    private boolean right;
    private boolean attack;
    private boolean menu;

    private Input() {

        jumpButton = KeyEvent.VK_UP;
        duckButton = KeyEvent.VK_DOWN;
        leftButton = KeyEvent.VK_LEFT;
        rightButton = KeyEvent.VK_RIGHT;
        attackButton = KeyEvent.VK_SPACE;
        menuButton = KeyEvent.VK_ESCAPE;

        jump = false;
        duck = false;
        left = false;
        right = false;
        attack = false;
        menu = false;

    }

    /**
     * Get method for the Input singleton
     *
     * @return the only Input object
     */
    public static Input get() {
        return input;
    }

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

    public void keyTyped(KeyEvent e) {
        // deprecated
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == attackButton) {
            attack = true;
        } else if (e.getKeyCode() == jumpButton) {
            jump = true;
        } else if (e.getKeyCode() == leftButton) {
            left = true;
        } else if (e.getKeyCode() == rightButton) {
            right = true;
        } else if (e.getKeyCode() == duckButton) {
            duck = true;
        } else if (e.getKeyCode() == menuButton) {
            menu = true;
        }
    }
    
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == attackButton) {
            attack = false;
        } else if (e.getKeyCode() == jumpButton) {
            jump = false;
        } else if (e.getKeyCode() == leftButton) {
            left = false;
        } else if (e.getKeyCode() == rightButton) {
            right = false;
        } else if (e.getKeyCode() == duckButton) {
            duck = false;
        } else if (e.getKeyCode() == menuButton) {
            menu = false;
        }
    }

    /**
     * Check if the right button is pressed
     *
     * @return true if the right button is currently pressed
     */
    public boolean right() {
        return right;
    }

    /**
     * Check if the left button is pressed
     *
     * @return true if the left button is currently pressed
     */
    public boolean left() {
        return left;
    }

    /**
     * Check if the duck button is pressed
     *
     * @return true if the duck button is currently pressed
     */
    public boolean duck() {
        return duck;
    }

    /**
     * Check if the jump button is pressed
     *
     * @return true if the jump button is currently pressed
     */
    public boolean jump() {
        return jump;
    }

    /**
     * Check if the menu button is pressed
     *
     * @return true if the menu button is currently pressed
     */
    public boolean menu() {
        return menu;
    }

    /**
     * Check if the attack button is pressed
     *
     * @return true if the attack button is currently pressed
     */
    public boolean attack() {
        return attack;
    }

    /**
     * Setting a new right button
     *
     * @param newKeyCode The Keycode of the new key to be set
     */
    public void setRightButton(int newKeyCode) {
        rightButton = newKeyCode;
    }

    /**
     * Setting a new left button
     *
     * @param newKeyCode The Keycode of the new key to be set
     */
    public void setLeftButton(int newKeyCode) {
        leftButton = newKeyCode;
    }

    /**
     * Setting a new jump button
     *
     * @param newKeyCode The Keycode of the new key to be set
     */
    public void setJumpButton(int newKeyCode) {
        jumpButton = newKeyCode;
    }

    /**
     * Setting a new duck button
     *
     * @param newKeyCode The Keycode of the new key to be set
     */
    public void setDuckButton(int newKeyCode) {
        duckButton = newKeyCode;
    }
    /**
     * Setting a new attack button
     *
     * @param newKeyCode The Keycode of the new key to be set
     */
    public void setAttackButton(int newKeyCode) {
        attackButton = newKeyCode;
    }

}
