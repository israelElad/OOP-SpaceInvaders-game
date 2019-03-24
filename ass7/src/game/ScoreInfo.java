package game;

import java.io.Serializable;

/**
 * Contains the information about the score.
 *
 * @author Elad Israel
 * @version 4.0 17/06/2018
 */
public class ScoreInfo implements Serializable {

    private String name;
    private int score;

    /**
     * Instantiates a new Score info.
     *
     * @param name  the name
     * @param score the score
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return this.score;
    }
}