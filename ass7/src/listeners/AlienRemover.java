package listeners;

import animation.GameLevel;
import game.Alien;
import game.Block;
import game.Counter;
import shapes.Ball;

/**
 * Classname: AlienRemover.
 * removes Aliens from game.
 *
 * @author Elad Israel
 * @version 3.0 20/05/2018
 */
public class AlienRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingAliens;


    /**
     * Constructor.
     *
     * @param gameLevel       the game level
     * @param remainingAliens the remaining blocks
     */
    public AlienRemover(GameLevel gameLevel, Counter remainingAliens) {
        this.gameLevel = gameLevel;
        this.remainingAliens = remainingAliens;
    }

    /**
     * Blocks that are hit and reach 0 hit-points should be removed
     * from the gameLevel.
     *
     * @param beingHit the block that was hit.
     * @param hitter   the ball that hit.
     **/
    public void hitEvent(Block beingHit, Ball hitter) {
    }

    /**
     * Aliens that are hit should be removed
     * from the gameLevel.
     *
     * @param beingHit the alien that was hit.
     * @param hitter   the shot that hit.
     **/
    public void hitEvent(Alien beingHit, Ball hitter) {
        //alien shot alien- remove only the shot(ignore the shot)
        if (hitter.getVelocity().getDy() == 350) {
            hitter.removeFromGame(this.gameLevel);
        } else {
            beingHit.removeHitListener(this);
            beingHit.removeFromGame(this.gameLevel);
            hitter.removeFromGame(this.gameLevel);
            remainingAliens.decrease(1);
        }
    }
}