package game;

/**
 * Classname: Counter.
 * Counter is a simple class that is used for counting things.
 *
 * @author Elad Israel
 * @version 3.0 20/05/2018
 */
public class Counter {
    private int count;

    /**
     * Constructor - initialize the counter to 0.
     */
    public Counter() {
        this.count = 0;
    }

    /**
     * add number to current count.
     *
     * @param number increase by this number.
     */
    public void increase(int number) {
        this.count += number;
    }

    /**
     * subtract number from current count.
     *
     * @param number decrease by this number.
     */
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * get current count.
     *
     * @return the value of the count
     */
    public int getValue() {
        return this.count;
    }
}