package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * wrap an existing animation and add a "waiting-for-key" behavior to it.
 *
 * @author Elad Israel
 * @version 4.0 17/06/2018
 */
public class KeyPressStoppableAnimation implements Animation {
    private Animation decoratedAnimation;
    private KeyboardSensor sensor;
    private String key;
    private boolean stop;
    private boolean isAlreadyPressed;

    /**
     * wrap an existing animation and add a "waiting-for-key" behavior to it.
     *
     * @param sensor    the sensor
     * @param key       the key
     * @param animation the animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.decoratedAnimation = animation;
        this.sensor = sensor;
        this.key = key;
        this.stop = false;
        this.isAlreadyPressed = true;
    }

    /**
     * Do one frame of the animation.
     *
     * @param d  the draw surface
     * @param dt amount of seconds passed since the last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        this.stop = false;
        if (this.sensor.isPressed(key)) {
            //the key was pressed before the animation started - ignore the key press
            if (this.isAlreadyPressed) {
                return;
            }
            this.stop = true;
        }
        this.isAlreadyPressed = false;
        this.decoratedAnimation.doOneFrame(d, dt);
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