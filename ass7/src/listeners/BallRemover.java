package listeners;

import animation.GameLevel;
import game.Alien;
import game.Block;
import shapes.Ball;


/**
 * class name: BallRemover
 * BallRemover is in charge of removing balls from the gameLevel, as well as keeping count
 * of the number of balls that remain.
 *
 * @author Elad Israel
 * @version 3.0 20/05/2018
 */
public class BallRemover implements HitListener {
    private GameLevel gameLevel;

    /**
     * constructor.
     *
     * @param gameLevel the game level
     */
    public BallRemover(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
    }

    /**
     * whenever a special block that will sit at (or slightly below) the bottom of the screen is hit,
     * it will function as a "death region".
     * the BallRemover is registered as a listener of the death-region block, so that BallRemover will be
     * notified whenever a ball hits the death-region. Whenever this happens, the BallRemover will remove the ball
     * from the gameLevel and update the balls counter.
     *
     * @param beingHit the death region block
     * @param hitter   the ball that hits the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.gameLevel);
    }

    /**
     * Aliens that are hit should be removed
     * from the gameLevel.
     *
     * @param beingHit the alien that was hit.
     * @param hitter   the shot that hit.
     **/
    public void hitEvent(Alien beingHit, Ball hitter) {
    }
}