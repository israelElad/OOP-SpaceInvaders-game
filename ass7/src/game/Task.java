package game;

/**
 * The interface Task.
 *
 * @param <T> the type parameter
 */
public interface Task<T> {
    /**
     * runs the task.
     *
     * @return T
     */
    T run();
}