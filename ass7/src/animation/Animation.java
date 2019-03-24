package animation;

import biuoop.DrawSurface;


/**
 * interface name: Animation
 * The Animation interface.
 * describes an animation object-
 * any animation should specify what to do in each frame, and notify when to stop the animation.
 *
 * @author Elad Israel
 * @version 4.0 17/06/2018
 */
public interface Animation {
    /**
     * Do one frame of the animation.
     *
     * @param d the draw surface
     * @param dt amount of seconds passed since the last call
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * Should the animation stop.
     *
     * @return boolean
     */
    boolean shouldStop();
}