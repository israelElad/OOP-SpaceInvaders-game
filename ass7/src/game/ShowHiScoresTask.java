package game;

import animation.Animation;
import animation.AnimationRunner;

/**
 * a task showing the highscore table.
 *
 * @author Elad Israel
 * @version 4.0 17/06/2018
 */

public class ShowHiScoresTask implements Task<Void> {

    private AnimationRunner runner;
    private Animation highScoresAnimation;


    /**
     * Instantiates a new Show hi scores task.
     *
     * @param runner              the runner
     * @param highScoresAnimation the high scores animation
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
    }

    /**
     * runs the task.
     *
     * @return T
     */
    public Void run() {
        this.runner.run(this.highScoresAnimation);
        return null;
    }
}