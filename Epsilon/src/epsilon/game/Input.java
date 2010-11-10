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

    // Unused eventhandlers
    /**
     * Unused eventhandler
     *
     * @param e
     */
    public void mouseClicked(MouseEvent e) {}

    /**
     * Unused eventhandler
     *
     * @param e
     */
    public void mousePressed(MouseEvent e) {}

    /**
     * Unused eventhandler
     *
     * @param e
     */
    public void mouseReleased(MouseEvent e) {}

    /**
     * Unused eventhandler
     *
     * @param e
     */
    public void mouseEntered(MouseEvent e) {}

    /**
     * Unused eventhandler
     *
     * @param e
     */
    public void mouseExited(MouseEvent e) {}

    /**
     * Unused eventhandler
     *
     * @param e
     */
    public void keyTyped(KeyEvent e) {}

    /**
     * Eventhandler used for handeling keyboard input
     *
     * @param e the eventhandler opbect
     */
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

    /**
     * Eventhandler used for handeling keyboard input
     *
     * @param e the eventhandler object
     */
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

    /**
     * Checks if the arrow button press is already handeled
     *
     * @return
     */
    public boolean getMenuArrowHandeled() {
        return menuDirectionHandeled;
    }

    /**
     * Indicates that the menu option has been pressed
     *
     * @return
     */
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

    /**
     * Indicates that the arrow keypress has been handeled
     */
    public void handleMenuArrow() {
        menuDirectionHandeled = true;
    }

    /**
     * Checks if the Input class is handling String input
     *
     * @return true if the Input class is currently handling a string input
     */
    public boolean isTyping() {
        return typing;
    }

    /**
     * The current text that is entered so far in the string input.
     * Only guaranteed to return text is isTyping is true
     *
     * @return the text entered so far in the string input
     */
    public String getCurrentText() {
        return currentText.toString();
    }

    /**
     * The final string input.
     * Only guaranteed to contain the text if the requeststring method
     * has been run and the istyping method returns false
     *
     * @return The final string of the string input
     */
    public String getFinalText() {
        return lastFinishedText;
    }

    /**
     * Requests input. The input handler will pause all other input until the
     * string has been entered, and either the escape or enter key is pressed
     *
     * @param currentString an optional string if the field already contains text
     */
    public void requestString(String currentString) {
        currentText = new StringBuffer(currentString);
        typing = true;
    }

    /**
     * Requests input. The input handler will pause all other input until the
     * string has been entered, and either the escape or enter key is pressed
     */
    public void requestString() {
        requestString("");
    }

}
