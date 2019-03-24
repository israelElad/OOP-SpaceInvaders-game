package game;

import animation.GameLevel;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Class name: LivesIndicator
 * LivesIndicator sprite that will sit at the top of the screen and indicate the number of lives.
 *
 * @author Elad Israel
 * @version 3.0 20/05/2018
 */
public class LivesIndicator implements Sprite {
    private Counter livesCount;

    /**
     * Constructor.
     *
     * @param livesCount the lives count
     */
    public LivesIndicator(Counter livesCount) {
        this.livesCount = livesCount;
    }

    /**
     * Specify what the ScoreIndicator does when time is passed.
     * @param dt amount of seconds passed since the last call
     */
    public void timePassed(double dt) {

    }

    /**
     * draws the ScoreIndicator on the surface.
     *
     * @param d draw surface
     */
    public void drawOn(DrawSurface d) {
        final int lettersSize = 15;
        d.setColor(Color.black);
        d.drawText(100, 19, "Lives: " + this.livesCount.getValue(), lettersSize);
    }

    /**
     * adds the ScoreIndicator to the game-as a sprite.
     *
     * @param g game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
