package animation;

/**
 * menu interface
 * When the game starts, the user will see a screen stating the game name (Arkanoid), and a list
 * of several options of what to do next.
 *
 * @param <T> the type parameter
 * @author Elad Israel
 * @version 4.0 17/06/2018
 */

public interface Menu<T> extends Animation {

    /**
     * Add selection to the menu.
     *
     * @param key       the key
     * @param message   the message
     * @param returnVal the return val
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * Gets status.
     *
     * @return the status
     */
    T getStatus();
}