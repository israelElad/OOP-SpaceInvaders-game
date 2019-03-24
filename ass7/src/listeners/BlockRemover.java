package listeners;

import animation.GameLevel;
import game.Alien;
import game.Block;
import shapes.Ball;

/**
 * Classname: BlockRemover.
 * a BlockRemover is in charge of removing blocks from the gameLevel, as well as keeping count
 * of the number of blocks that remain.
 *
 * @author Elad Israel
 * @version 3.0 20/05/2018
 */
public class BlockRemover implements HitListener {
    private GameLevel gameLevel;



    /**
     * Constructor.
     *
     * @param gameLevel          the game level
     */
    public BlockRemover(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
    }

    /**
     * Blocks that are hit and reach 0 hit-points should be removed
     * from the gameLevel.
     *
     * @param beingHit the block that was hit.
     * @param hitter   the ball that hit.
     **/
    public void hitEvent(Block beingHit, Ball hitter) {
            beingHit.removeHitListener(this);
            beingHit.removeFromGame(this.gameLevel);
    }

    /**
     * Blocks that are hit and reach 0 hit-points should be removed
     * from the gameLevel.
     *
     * @param beingHit the block that was hit.
     * @param hitter   the ball that hit.
     **/
    public void hitEvent(Alien beingHit, Ball hitter) {
    }
}