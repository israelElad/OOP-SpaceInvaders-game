package levels;


import biuoop.DrawSurface;
import game.Sprite;

import java.awt.Color;

/**
 * Classname: SpaceBackground
 * background class for Alien level.
 *
 * @author Elad Israel
 * @version 3.0 20/05/2018
 */
public class SpaceBackground implements Sprite {
    /**
     * draw the sprite to the screen.
     *
     * @param d drawSurface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.black);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

    }

    /**
     * notify the sprite that time has passed.
     * @param dt amount of seconds passed since the last call
     */
    public void timePassed(double dt) {

    }
}

