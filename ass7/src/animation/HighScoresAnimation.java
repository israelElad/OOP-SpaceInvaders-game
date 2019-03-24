package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.HighScoresTable;

import java.awt.Color;

/**
 * The High scores animation.
 *
 * @author Elad Israel
 * @version 4.0 17/06/2018
 */
public class HighScoresAnimation implements Animation {

    private HighScoresTable scores;
    private boolean stop;
    private KeyboardSensor keyboard;


    /**
     * Instantiates a new High scores animation.
     *
     * @param scores   the scores
     * @param keyboard the keyboard
     */
    public HighScoresAnimation(HighScoresTable scores, KeyboardSensor keyboard) {
        this.scores = scores;
        this.keyboard = keyboard;
        this.stop = false;
    }


    /**
     * Do one frame of the animation.
     *
     * @param d  the draw surface
     * @param dt amount of seconds passed since the last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.gray);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(Color.YELLOW);
        d.drawText(50, 50, "High Scores:", 50);

        d.setColor(Color.WHITE);
        d.drawText(100, 150, "Player Name", 32);
        d.setColor(Color.WHITE);
        d.drawText(500, 150, "Score", 32);
        d.drawText(100, 150, "____________________________________", 32);

        for (int i = 0; i < this.scores.getHighScores().size(); i++) {
            d.setColor(Color.BLUE);
            d.drawText(100, 200 + i * 50, this.scores.getHighScores().get(i).getName(), 32);
            d.setColor(Color.BLUE);
            d.drawText(500, 200 + i * 50, "" + this.scores.getHighScores().get(i).getScore(), 32);
        }
        d.setColor(Color.BLACK);
        d.drawText(200, 500, "Press space to continue", 32);
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