package epsilon.game;

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
    private int menuChoiceButton;

    // true if a button is pushed
    private boolean jump;
    private boolean duck;
    private boolean left;
    private boolean right;
    private boolean attack;
    private boolean menu;
    private boolean menuChoice;

    // special menu handling
    private boolean menuHandeled;
    private boolean menuDirectionHandeled;

    // handling typing text in the menu
    private boolean typing;
    private StringBuffer currentText;
    private String lastFinishedText;

    private Input() {

        jumpButton = KeyEvent.VK_UP;
        duckButton = KeyEvent.VK_DOWN;
        leftButton = KeyEvent.VK_LEFT;
        rightButton = KeyEvent.VK_RIGHT;
        attackButton = KeyEvent.VK_SPACE;
        menuButton = KeyEvent.VK_ESCAPE;
        menuChoiceButton = KeyEvent.VK_ENTER;

        jump = false;
        duck = false;
        left = false;
        right = false;
        attack = false;
        menu = false;
        menuChoice = false;

        menuHandeled = true;

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
        if (typing) {
            if (e.getKeyCode() == menuChoiceButton) {
                if (currentText.length() > 0) {
                    lastFinishedText = currentText.toString();
                } else {
                    lastFinishedText = "";
                }
                currentText = new StringBuffer();
                typing = false;
            } else if (e.getKeyCode() == menuButton) {
                lastFinishedText = "";
                currentText = new StringBuffer();
                typing = false;
            } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if (currentText.length() > 0) {
                    currentText.deleteCharAt(currentText.length()-1);
                }
            } else if (e.getKeyChar() != KeyEvent.CHAR_UNDEFINED) {
                currentText.append(e.getKeyChar());
            }

        } else {
            if (e.getKeyCode() == attackButton) {
                attack = true;
            } else if (e.getKeyCode() == jumpButton) {
                jump = true;
                menuDirectionHandeled = false;
            } else if (e.getKeyCode() == leftButton) {
                left = true;
            } else if (e.getKeyCode() == rightButton) {
                right = true;
            } else if (e.getKeyCode() == duckButton) {
                duck = true;
                menuDirectionHandeled = false;
            } else if (e.getKeyCode() == menuButton) {
                menu = true;
                menuHandeled = false;
            } else if (e.getKeyCode() == menuChoiceButton) {
                menuChoice = true;
            }
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
        } else if (e.getKeyCode() == menuChoiceButton) {
            menuChoice = false;
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

    /**
     * Setting a new menu button
     *
     * @param newKeyCode The Keycode of the new key to be set
     */
    public void setMenuButton(int newKeyCode) {
        attackButton = newKeyCode;
    }

    /**
     * Checks wether the menu button press has already been handeled
     *
     * @return boolean True if the menu butten press is already handeled
     */
    public boolean getMenuHandeled() {
        return menuHandeled;
    }

    public boolean getMenuArrowHandeled() {
        return menuDirectionHandeled;
    }

    public boolean useMenu() {
        boolean b = menuChoice;
        menuChoice = false;
        return b;
    }

    /**
     * Indicates that the menu keypress has been handeled
     */
    public void handleMenu() {
        menuHandeled = true;
    }

    public void handleMenuArrow() {
        menuDirectionHandeled = true;
    }

    public boolean isTyping() {
        return typing;
    }

    public String getCurrentText() {
        return currentText.toString();
    }

    public String getFinalText() {
        return lastFinishedText;
    }

    public void requestString(String currentString) {
        currentText = new StringBuffer(currentString);
        typing = true;
    }

    public void requestString() {
        requestString("");
    }

}
