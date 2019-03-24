package listeners;

import game.Alien;
import game.Block;
import shapes.Ball;

/**
 * interface name: HitListener
 * Objects that want to be notified of hit events, should implement the HitListener interface,
 * and register themselves with a HitNotifier object using its addHitListener method.
 *
 * @author Elad Israel
 * @version 3.0 20/05/2018
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     *
     * @param beingHit the object that is being hit.
     * @param hitter   the object that hit.
     */
    void hitEvent(Block beingHit, Ball hitter);

    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     *
     * @param beingHit the object that is being hit.
     * @param hitter   the object that hit.
     */
    void hitEvent(Alien beingHit, Ball hitter);
}