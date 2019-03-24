import animation.AnimationRunner;

import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import animation.MenuAnimation;
import animation.Menu;
import game.Task;
import biuoop.GUI;
import game.GameFlow;
import game.HighScoresTable;
import game.ShowHiScoresTask;

import java.io.File;
import java.io.IOException;

/**
 * Classname: Ass6Game
 * Creates a new arkanoid game, initialize and runs the game.
 *
 * @author Elad Israel
 * @version 4.0 16/06/2018
 */
public class Ass7Game {
    /**
     * When runs, it creates a new game and runs the game.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        final int frameWidth = 800;
        final int frameHeight = 600;
        GUI gui = new GUI("Space-Invaders", frameWidth, frameHeight);
        AnimationRunner animationRunner = new AnimationRunner(gui);

        //loading or creating a highscore file.
        File highscoresFile = new File("highscores");
        HighScoresTable highScoresTable = new HighScoresTable(5);
        if (!highscoresFile.exists()) {
            try {
                highScoresTable.save(highscoresFile);
            } catch (IOException e) {
                System.err.println("Failed saving file");
            }
        } else {
            try {
                highScoresTable.load(highscoresFile);
            } catch (IOException e) {
                System.err.println("Failed loading file");
            }
        }

        Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>("Arkanoid", gui.getKeyboardSensor(), animationRunner);
        KeyPressStoppableAnimation highscoresAnimation = new KeyPressStoppableAnimation(gui.getKeyboardSensor(),
                "space", new HighScoresAnimation(highScoresTable, gui.getKeyboardSensor()));

        menu.addSelection("s", "Game", new Task<Void>() {
            @Override
            public Void run() {
                GameFlow gameFlow = new GameFlow(animationRunner, gui.getKeyboardSensor(), frameWidth,
                        frameHeight, 3, highScoresTable);
                gameFlow.runLevel();
                return null;
            }
        });


        menu.addSelection("h", "Hi scores", new ShowHiScoresTask(animationRunner, highscoresAnimation));

        menu.addSelection("q", "Quit", new Task<Void>() {
            @Override
            public Void run() {
                System.exit(0);
                return null;
            }
        });


        while (true) {
            animationRunner.run(menu);
            // wait for user selection
            Task<Void> task = menu.getStatus();
            task.run();
        }
    }
}