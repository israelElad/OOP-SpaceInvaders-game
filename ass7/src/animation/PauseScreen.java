package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;

/**
 * Classname: PauseScreen.
 * Display a screen with the message paused -- press space to continue until a key is pressed.
 * An option to pause the game when pressing the p key.
 *
 * @author Elad Israel
 * @version 3.0 20/05/2018
 */
public class PauseScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;

    /**
     * Constructor.
     *
     * @param k the KeyboardSensor.
     */
    public PauseScreen(KeyboardSensor k) {
        this.keyboard = k;
        this.stop = false;
    }

    /**
     * Do one frame of the animation.
     *
     * @param d  the draw surface
     * @param dt amount of seconds passed since the last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.black);
        d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);
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