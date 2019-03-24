package game;

import animation.GameLevel;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Class name: NameOfLevelIndicator
 * NameOfLevelIndicator sprite that will sit at the top of the screen and indicate the name of the level.
 *
 * @author Elad Israel
 * @version 3.0 20/05/2018
 */
public class NameOfLevelIndicator implements Sprite {
    private String name;
    private Counter numOfLevel;


    /**
     * Constructor.
     *
     * @param name the name
     * @param numOfLevel number of level
     */
    public NameOfLevelIndicator(String name, Counter numOfLevel) {
        this.name = name;
        this.numOfLevel = numOfLevel;
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
        d.setColor(Color.black);
        d.drawText(d.getHeight(), 19, "Level Name: " + this.name + this.numOfLevel.getValue(), lettersSize);
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
