package game;

import animation.GameLevel;
import biuoop.DrawSurface;
import shapes.Point;
import shapes.Rectangle;

import java.awt.Color;

/**
 * Class name: ScoreIndicator
 * ScoreIndicator sprite that will sit at the top of the screen and indicate the score.
 *
 * @author Elad Israel
 * @version 3.0 20/05/2018
 */
public class ScoreIndicator implements Sprite {
    private Rectangle scoreRectangle;
    private Counter scoreCount;

    /**
     * Constructor.
     *
     * @param rectangle  the rectangle
     * @param scoreCount the score count
     */
    public ScoreIndicator(Rectangle rectangle, Counter scoreCount) {
        this.scoreRectangle = rectangle;
        this.scoreCount = scoreCount;
    }

    /**
     * Specify what the ScoreIndicator does when time is passed.
     *
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
        Point upperLeft = this.scoreRectangle.getUpperLeft();
        double width = this.scoreRectangle.getWidth();
        double height = this.scoreRectangle.getHeight();
        this.scoreRectangle.drawOn(d);
        d.setColor(Color.black);
        d.drawText((int) (upperLeft.getX() + width / 2.2),
                (int) (upperLeft.getY() + height / 1.3), "Score: " + this.scoreCount.getValue(), lettersSize);
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
