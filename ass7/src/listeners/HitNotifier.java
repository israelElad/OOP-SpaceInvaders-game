package listeners;

/**
 * interface name: HitNotifier
 * The HitNotifier interface indicate that objects that implement it send notifications when they are being hit.
 *
 * @author Elad Israel
 * @version 3.0 20/05/2018
 */
public interface HitNotifier {

    /**
     * Add hl as a listener to hit events.
     *
     * @param hl HitListener to remove
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl HitListener to remove
     */
    void removeHitListener(HitListener hl);
}