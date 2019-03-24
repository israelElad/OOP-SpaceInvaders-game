package game;


import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Classname: HighScoresTable.
 * A table representing the highscores. Will be presented at the end of game or on demand.
 * using showHighscoreTask.
 *
 * @author Elad Israel
 * @version 4.0 17/06/2018
 */
public class HighScoresTable implements Serializable {
    private List<ScoreInfo> scoresList;
    private int size;

    /**
     * Instantiates a new High scores table.
     * Create an empty high-scores table with the specified size.
     * The size means that the table holds up to size top scores.
     *
     * @param size the size
     */
    public HighScoresTable(int size) {
        scoresList = new ArrayList<>();
        this.size = size;
    }

    /**
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with
     * reading it, an empty table is returned.
     *
     * @param filename the filename
     * @return the high scores table
     */
    public static HighScoresTable loadFromFile(File filename) {
        //creates a new instance of highScoresTable
        HighScoresTable highScoresTable;
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(filename));

            // unsafe down casting, we better be sure that the stream really contains a highScoresTable!
            highScoresTable = (HighScoresTable) objectInputStream.readObject();
        } catch (FileNotFoundException e) { // Can't find file to open
            System.err.println("Unable to find file: " + filename);
            return null;
        } catch (ClassNotFoundException e) { // The class in the stream is unknown to the JVM
            System.err.println("Unable to find class for object in file: " + filename);
            return null;
        } catch (IOException e) { // Some other problem
            System.err.println("Failed reading object");
            e.printStackTrace(System.err);
            return null;
        } finally { //closing the stream!
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
        return highScoresTable;
    }

    /**
     * Add a high-score.
     *
     * @param score the score
     */
    public void add(ScoreInfo score) {
        scoresList.add(getRank(score.getScore()) - 1, score);
        if (this.scoresList.size() > this.size) {
            scoresList.remove(size());
        }
    }

    /**
     * Is high score boolean.
     *
     * @param score the score
     * @return the boolean
     */
    public boolean isHighScore(Counter score) {
        if (this.scoresList.size() < this.size()) {
            return true;
        } else { //scoresList size==size of table
            //new score bigger than lowest on list
            if (score.getValue() > this.scoresList.get(this.scoresList.size() - 1).getScore()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return table size.
     *
     * @return the int
     */
    public int size() {
        return size;
    }

    /**
     * Return the current high scores.
     * The list is sorted such that the highest
     * scores come first.
     *
     * @return the high scores
     */
    public List<ScoreInfo> getHighScores() {
        return this.scoresList;
    }

    /**
     * return the rank of the current score: where will it
     * be on the list if added?
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not
     * be added to the list
     *
     * @param score the score
     * @return the rank
     */
    public int getRank(int score) {
        for (int i = 0; i < this.scoresList.size(); i++) {
            if (score > this.scoresList.get(i).getScore()) {
                return i + 1;
            }
        }
        return this.scoresList.size() + 1;
    }

    /**
     * Clears the table.
     */
    public void clear() {
        this.scoresList.clear();
    }

    /**
     * Load table data from file.
     * Current table data is cleared.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
    public void load(File filename) throws IOException {
        HighScoresTable highScoresTable = loadFromFile(filename);
        if (highScoresTable == null) {
            throw new IOException("Failed Reading File");
        } else {
            this.scoresList = highScoresTable.scoresList;
        }
    }

    /**
     * Save table data to the specified file.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename));
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            System.err.println("Failed saving object");
            e.printStackTrace(System.err);
            throw new IOException(e);
        } finally { //closing the stream!
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
    }
}