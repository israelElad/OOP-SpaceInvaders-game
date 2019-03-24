package game;

import animation.EndScreen;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import animation.GameLevel;
import animation.AnimationRunner;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;


import java.io.File;
import java.io.IOException;

/**
 * interface name: GameFlow
 * In charge of creating the different levels, and moving from one level to the next.
 *
 * @author Elad Israel
 * @version 4.0 17/06/2018
 */
public class GameFlow {

    private final int frameWidth;
    private final int frameHeight;
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    private Counter score;
    private Counter numOfLives;
    private HighScoresTable highScoresTable;


    /**
     * Constructor.
     *
     * @param ar              the AnimationRunner
     * @param ks              the KeyboardSensor
     * @param frameWidth      the frame width
     * @param frameHeight     the frame height
     * @param numOfLives      the num of lives
     * @param highScoresTable the high scores table
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, final int frameWidth, final int frameHeight, int
            numOfLives, HighScoresTable highScoresTable) {
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.score = new Counter();
        this.numOfLives = new Counter();
        this.numOfLives.increase(numOfLives);
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.highScoresTable = highScoresTable;

    }


    /**
     * Run the level.
     **/
    public void runLevel() {

        GameLevel level = new GameLevel(this.keyboardSensor, this.animationRunner, this.score, this
                .numOfLives, this.frameWidth, this.frameHeight);

        level.initialize();

        while (level.getNumOfLives().getValue() > 0) {
            level.playOneTurn();
            if (level.getPaddle().wasHit()) {
                level.getNumOfLives().decrease(1);
                level.initializeAfterLosingLife();
            }
            if (level.getNumOfAliens().getValue() == 0) {
                level.getNumOfLevel().increase(1);
                level.nextLevel();
            }
        }

        if (level.getNumOfLives().getValue() <= 0) {
            this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, "space",
                    new EndScreen(this.keyboardSensor, this.score, false)));
        } else {
            this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, "space", new EndScreen(this
                    .keyboardSensor, this.score, true)));
        }

        File highscoresFile = new File("highscores");
        if (highScoresTable.isHighScore(this.score)) {
            DialogManager dialog = this.animationRunner.getGui().getDialogManager();
            String name = dialog.showQuestionDialog("Name", "What is your name?", "");
            highScoresTable.add(new ScoreInfo(name, this.score.getValue()));
        }
        try {
            highScoresTable.save(highscoresFile);
        } catch (IOException e) {
            System.err.println("Failed saving file");
        }
        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, "space",
                new HighScoresAnimation(highScoresTable, this.keyboardSensor)));
    }

}