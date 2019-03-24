package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


/**
 * Our Menu will need to be displayed on screen, so it will be an Animation. Unlike the other animation loops we had,
 * this one will need to return a value when it is done. We may want to add a nice background to our menu. For this, we
 * will provide it with a method that will accept a background sprite and display it.
 *
 * @param <T> the type parameter
 * @author Elad Israel
 * @version 4.0 17/06/2018
 */
public class MenuAnimation<T> implements Menu<T> {


    private List<String> keys;
    private List<String> messages;
    private List<T> returnVals;
    private String title;
    private KeyboardSensor keyboard;
    private boolean stop;
    private T status;
    private AnimationRunner animationRunner;
    private List<Menu<T>> subMenus;
    private List<Boolean> isSubMenu;


    /**
     * Instantiates a new Menu animation.
     *
     * @param title           the title
     * @param keyboard        the keyboard
     * @param animationRunner the animation runner
     */
    public MenuAnimation(String title, KeyboardSensor keyboard, AnimationRunner animationRunner) {
        this.keys = new ArrayList<String>();
        this.messages = new ArrayList<String>();
        this.returnVals = new ArrayList<T>();
        this.title = title;
        this.keyboard = keyboard;
        this.stop = false;
        this.animationRunner = animationRunner;
        this.subMenus = new ArrayList<>();
        this.isSubMenu = new ArrayList<>();
    }

    /**
     * Add selection to the menu.
     *
     * @param key       the key
     * @param message   the message
     * @param returnVal the return val
     */
    public void addSelection(String key, String message, T returnVal) {
        this.keys.add(key);
        this.messages.add(message);
        this.returnVals.add(returnVal);
        this.subMenus.add(null);
        this.isSubMenu.add(false);
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public T getStatus() {
        if (this.status == null) {
            throw new RuntimeException("status wasn't initialized");
        }
        T tempStatus = this.status;
        //reset fields
        this.status = null;
        this.stop = false;
        return tempStatus;
    }

    /**
     * Do one frame of the animation.
     *
     * @param d  the draw surface
     * @param dt amount of seconds passed since the last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.gray.darker().darker());
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(Color.YELLOW);
        d.drawText(50, 50, this.title, 50);
        d.setColor(Color.WHITE);
        for (int i = 0; i < this.keys.size(); i++) {
            d.drawText(100, 150 + i * 50, "(" + this.keys.get(i) + ") " + this.messages.get(i), 32);
        }
        for (int i = 0; i < this.keys.size(); i++) {
            if (this.keyboard.isPressed(this.keys.get(i))) {
                if (!this.isSubMenu.get(i)) {
                    this.status = this.returnVals.get(i);
                    this.stop = true;
                    break;
                } else {
                    Menu<T> subMenu = this.subMenus.get(i);
                    this.animationRunner.run(subMenu);
                    this.status = subMenu.getStatus();
                    this.stop = true;
                    break;
                }
            }
        }
    }

    /**
     * Should the animation stop.
     *
     * @return boolean
     */
    public boolean shouldStop() {
        return this.stop;
    }
}