package animation;

import biuoop.DrawSurface;
import biuoop.Sleeper;
import game.SpriteCollection;

import java.awt.Color;


/**
 * Classname: CountdownAnimation.
 * The CountdownAnimation will display the given gameScreen,
 * for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1, where each number will
 * appear on the screen for (numOfSeconds / countFrom) secods, before
 * it is replaced with the next one.
 *
 * @author Elad Israel
 * @version 4.0 17/06/2018
 */
public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private int currentCount;
    private SpriteCollection gameScreen;
    private boolean stop;
    private Sleeper sleeper;

    /**
     * Constructor.
     *
     * @param numOfSeconds the num of seconds to delay
     * @param countFrom    count from this number
     * @param gameScreen   the game screen
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.currentCount = countFrom;
        this.gameScreen = gameScreen;
        this.stop = false;
        this.sleeper = new Sleeper();
    }

    /**
     * Do one frame of the animation.
     *
     * @param d  the draw surface
     * @param dt amount of seconds passed since the last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        this.gameScreen.drawAllOn(d);
        //when count reaches 0 it shouldn't draw 0 on the screen.
        if (this.currentCount > 0) {
            d.setColor(Color.decode("#1B76F2"));
            d.drawText((int) (d.getWidth() / 2.05), d.getHeight() / 2, Integer.toString(this.currentCount), 50);
        }
        //not the first time(first time shouldn't sleep because gui wasn't shown yet.
        if (this.currentCount != this.countFrom) {
            this.sleeper.sleepFor((long) ((this.numOfSeconds / this.countFrom) * 1000));
        }
        this.currentCount--;
    }

    /**
     * Should the animation stop.
     *
     * @return boolean
     */
    public boolean shouldStop() {
        //count is over
        if (this.currentCount < 0) {
            return true;
        }
        return this.stop;
    }
}