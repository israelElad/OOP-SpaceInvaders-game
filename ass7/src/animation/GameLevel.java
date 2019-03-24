package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;

import levels.SpaceBackground;
import listeners.AlienRemover;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreTrackingListener;
import shapes.Ball;
import shapes.Point;
import shapes.Rectangle;
import game.Velocity;
import game.Alien;
import game.AliensGroup;
import game.Sprite;
import game.NameOfLevelIndicator;
import game.LivesIndicator;
import game.ScoreIndicator;
import game.Block;
import game.Collidable;
import game.Counter;
import game.Paddle;
import game.GameEnvironment;
import game.SpriteCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * Class name: GameLevel
 * A class that will hold the sprites and the collidables, and will be in charge of the animation.
 *
 * @author Elad Israel
 * @version 4.0 17/06/2018
 */
public class GameLevel implements Animation {

    private static final int UP_AND_DOWN_FRAMES_HEIGHT = 25;
    private static final java.awt.Color FRAMES_COLOR = Color.gray;
    private final int frameHeight;
    private final int frameWidth;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Paddle paddle;
    private KeyboardSensor keyboardSensor;
    private Counter numOfAliens;
    private BlockRemover blockRemover;
    private Counter numOfBalls;
    private BallRemover ballRemover;
    private Counter score;
    private Counter numOfLives;
    private AnimationRunner runner;
    private boolean running;
    private Counter numOfLevel;
    private int numOfLevelsSinceDeath;
    private AlienRemover alienRemover;
    private List<List<Alien>> aliens;
    private AliensGroup aliensGroup;
    private double aliensShotCooldown;
    private double paddleShotCooldown;
    private List<Ball> shots;
    private List<Block> shieldBlocks;

    /**
     * Constructor- creates the sprite collection, environment, and keyboard sensor of the game.
     *
     * @param keyboardSensor  the keyboard sensor
     * @param animationRunner the animation runner
     * @param score           the score
     * @param numOfLives      the num of lives
     * @param frameWidth      the frame width
     * @param frameHeight     the frame height
     */
    public GameLevel(KeyboardSensor keyboardSensor, AnimationRunner animationRunner,
                     Counter score, Counter numOfLives, final int frameWidth, final int frameHeight) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.runner = animationRunner;
        this.keyboardSensor = keyboardSensor;
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment(new ArrayList<>());
        this.numOfAliens = new Counter();
        this.numOfBalls = new Counter();
        this.ballRemover = new BallRemover(this);
        this.blockRemover = new BlockRemover(this);
        this.score = score;
        this.numOfLives = numOfLives;
        this.numOfLevel = new Counter();
        this.numOfLevel.increase(1);
        this.numOfLevelsSinceDeath = numOfLevel.getValue();
        this.alienRemover = new AlienRemover(this, numOfAliens);
        this.shots = new ArrayList<>();
        this.shieldBlocks = new ArrayList<>();
    }

    /**
     * add the given collidable to the collidables collection in the environment.
     *
     * @param c given collidable.
     */
    public void addCollidable(Collidable c) {
        try {
            environment.addCollidable(c);
        } catch (RuntimeException nullPointer) {
            throw new RuntimeException("Collidable field wasn't initialized!");
        }
    }

    /**
     * removes the given collidable from the collidables collection in the environment.
     *
     * @param c given collidable.
     */
    public void removeCollidable(Collidable c) {
        try {
            environment.removeCollidable(c);
        } catch (RuntimeException nullPointer) {
            throw new RuntimeException("Collidable field wasn't initialized!");
        }
    }

    /**
     * add the given sprite to the sprite collection.
     *
     * @param s given sprite.
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * removes the given sprite from the sprite collection.
     *
     * @param s given sprite.
     */
    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }

    /**
     * removes alien from matrix in aliensGroup.
     *
     * @param alien to remove
     */
    public void removeFromGroup(Alien alien) {
        for (int column = 0; column < aliens.size(); column++) {
            if (aliens.get(column).contains(alien)) {
                aliens.get(column).remove(alien);
                if (aliens.get(column).isEmpty()) {
                    aliens.remove(column);
                }
            }
        }
    }


    /**
     * removes shot from shots list.
     *
     * @param shot to remove
     */
    public void removeFromShots(Ball shot) {
        if (shots.contains(shot)) {
            shots.remove(shot);
        }
    }

    /**
     * removes Block from blocks of shields list.
     *
     * @param block to remove
     */
    public void removeFromShieldBlocks(Block block) {
        if (shieldBlocks.contains(block)) {
            shieldBlocks.remove(block);
        }
    }


    /**
     * Gets paddle.
     *
     * @return the paddle
     */
    public Paddle getPaddle() {
        return paddle;
    }

    /**
     * Initialize a new game: creates aliens,shields and Paddle and add them to the game.
     */
    public void initialize() {
        Sprite spaceBackground = new SpaceBackground();
        addSprite(spaceBackground);
        this.paddle = initializePaddle();
        initializeIndicatorsAndBlock();
        initializeShields();
        initializeAliens();
    }


    /**
     * creates block to prevent the balls of leaving the screen,
     * and initialize the indicators of the game.
     */
    public void initializeIndicatorsAndBlock() {
        //initialize score sprite
        ScoreIndicator scoreIndicator = new ScoreIndicator(new Rectangle(new Point(0, 0), frameWidth,
                UP_AND_DOWN_FRAMES_HEIGHT, Color.white), this.score);
        scoreIndicator.addToGame(this);

        Block down = new Block(new Rectangle(new Point(-frameWidth, frameHeight + UP_AND_DOWN_FRAMES_HEIGHT),
                frameWidth * 3, UP_AND_DOWN_FRAMES_HEIGHT, FRAMES_COLOR));
        down.addToGame(this);
        down.addHitListener(this.ballRemover);


        LivesIndicator livesIndicator = new LivesIndicator(this.numOfLives);
        livesIndicator.addToGame(this);

        NameOfLevelIndicator nameOfLevelIndicator = new NameOfLevelIndicator("Battle no.", this.numOfLevel);
        nameOfLevelIndicator.addToGame(this);
    }

    /**
     * Creates the blocks(shields) in the bottom-center of the screen- the ones the shots will collide with and destroy.
     */
    public void initializeShields() {
        for (int numOfShield = 0; numOfShield < 3; numOfShield++) {
            for (int row = 0; row < 3; row++) {
                for (int column = 0; column < 30; column++) {
                    Block block = new Block(new Rectangle(new Point(
                            75 + numOfShield * 250 + column * 5, 500 + row * 5), 5, 5, Color.cyan));
                    block.addToGame(this);
                    block.addHitListener(this.blockRemover);
                    block.addHitListener(this.ballRemover);
                    shieldBlocks.add(block);
                }
            }
        }
    }


    /**
     * Creates the paddle(the user).
     *
     * @return paddle to remove by playOneTurn
     */
    public Paddle initializePaddle() {
        final int paddleHeight = 15;
        final int paddleWidth = 70;
        //Paddle
        this.paddle = new Paddle(new Point(frameWidth / 2 - paddleWidth / 2,
                frameHeight - UP_AND_DOWN_FRAMES_HEIGHT), paddleWidth, paddleHeight,
                500, Color.yellow, java.awt.Color.black, keyboardSensor);
        this.paddle.addToGame(this);
        return paddle;
    }

    /**
     * Creates the aliens in the center of the screen- the ones the shots will collide with and destroy.
     */
    public void initializeAliens() {
        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(this.score);
        this.aliensGroup = new AliensGroup();
        this.aliens = aliensGroup.getAliensMatrix();
        for (List<Alien> alienColumn : this.aliens) {
            for (Alien alien : alienColumn) {
                alien.addHitListener(alienRemover);
                alien.addHitListener(scoreTrackingListener);
                numOfAliens.increase(1);
                alien.setVelocity(Velocity.fromAngleAndSpeed(90, 60 + 6 * numOfLevelsSinceDeath));
                alien.addToGame(this);
            }
        }
    }


    /**
     * initializes another level.
     */
    public void nextLevel() {
        numOfLevelsSinceDeath++;
        while (!shots.isEmpty()) {
            shots.get(0).removeFromGame(this);
        }
        while (!shieldBlocks.isEmpty()) {
            shieldBlocks.get(0).removeFromGame(this);
        }
        initializeShields();
        initializeAliens();
    }

    /**
     * Initialize the level after losing life.
     */
    public void initializeAfterLosingLife() {
        this.numOfLevelsSinceDeath = 1;
        while (!shots.isEmpty()) {
            shots.get(0).removeFromGame(this);
        }
        while (this.aliensGroup.getUpperYPoint() > 50) {
            for (List<Alien> alienColumn : this.aliens) {
                for (Alien alien : alienColumn) {
                    alien.getCollisionRectangle().setUpperLeft(new Point(alien.getCollisionRectangle().getUpperLeft()
                            .getX(), alien.getCollisionRectangle().getUpperLeft().getY() - 20));
                    alien.setVelocity(Velocity.fromAngleAndSpeed(90, 60));
                }
            }
        }
        while (this.aliensGroup.getMostLeftXPoint() > 50) {
            for (List<Alien> alienColumn : this.aliens) {
                for (Alien alien : alienColumn) {
                    alien.getCollisionRectangle().setUpperLeft(new Point(alien.getCollisionRectangle().getUpperLeft()
                            .getX() - 20, alien.getCollisionRectangle().getUpperLeft().getY()));
                    alien.setVelocity(Velocity.fromAngleAndSpeed(90, 60));
                }
            }
        }
    }

    /**
     * Should the animation stop.
     *
     * @return boolean
     */
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * Do one frame of the animation.
     *
     * @param d  the draw surface
     * @param dt amount of seconds passed since the last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.keyboardSensor.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboardSensor, "space",
                    new PauseScreen(this.keyboardSensor)));
        }
        aliensShotCooldown -= dt;
        if (aliensShotCooldown <= 0) {
            Alien shooter = aliensGroup.chooseShooter(aliens);
            createAlienShot(shooter);
        }
        paddleShotCooldown -= dt;
        if (this.keyboardSensor.isPressed("space") && paddleShotCooldown <= 0) {
            createPaddleShot();
        }

        //if aliens formation hit the right side
        if (aliensGroup.getMostRightXPoint() >= 800) {
            for (List<Alien> alienColumn : this.aliens) {
                for (Alien alien : alienColumn) {
                    alien.setVelocity(new Velocity(alien.getVelocity().getDx() * -1.1, alien.getVelocity().getDy()
                            * -1.1));
                    Point downMovement = new Point(alien.getCollisionRectangle().getUpperLeft().getX(),
                            alien.getCollisionRectangle().getUpperLeft().getY() + 20);
                    alien.getCollisionRectangle().setUpperLeft(downMovement);
                }
            }
        }
        if (aliensGroup.getMostLeftXPoint() <= 0) { //if aliens formation hit the left side
            for (List<Alien> alienColumn : this.aliens) {
                for (Alien alien : alienColumn) {
                    alien.setVelocity(new Velocity(alien.getVelocity().getDx() * -1.1, alien.getVelocity().getDy()
                            * -1.1));
                    Point downMovement = new Point(alien.getCollisionRectangle().getUpperLeft().getX(),
                            alien.getCollisionRectangle().getUpperLeft().getY() + 20);
                    alien.getCollisionRectangle().setUpperLeft(downMovement);
                }
            }
        }
        if (aliensGroup.getBottomYPoint() >= 500) { //if aliens formation hit the bottom side
            this.getNumOfLives().decrease(1);
            paddle.removeFromGame(this);
            this.running = false;
            initializeAfterLosingLife();
        }
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);
        if (this.numOfAliens.getValue() == 0 || this.paddle.wasHit() || aliensGroup.getBottomYPoint() >= 500) {
            paddle.removeFromGame(this);
            this.running = false;
        }
    }

    /**
     * playing one turn.
     * playOneTurn starts by creating balls and putting the paddle at the bottom of the screen.
     */
    public void playOneTurn() {
        this.paddle.removeFromGame(this);
        this.paddle = initializePaddle();
        this.runner.run(new CountdownAnimation(2, 3, this.sprites)); // countdown before turn starts.
        this.running = true;
        // use our runner to run the current animation -- which is one turn of the game.
        this.runner.run(this);
    }

    /**
     * Gets num of blocks.
     *
     * @return the num of blocks
     */
    public Counter getNumOfAliens() {
        return this.numOfAliens;
    }


    /**
     * Gets num of balls.
     *
     * @return the num of balls
     */
    public Counter getNumOfBalls() {
        return this.numOfBalls;
    }

    /**
     * Gets num of lives.
     *
     * @return the num of lives
     */
    public Counter getNumOfLives() {
        return this.numOfLives;
    }

    /**
     * Gets num of level.
     *
     * @return the num of level
     */
    public Counter getNumOfLevel() {
        return numOfLevel;
    }

    /**
     * create alien shot.
     *
     * @param shooter the shooter
     */
    public void createAlienShot(Alien shooter) {
        this.aliensShotCooldown = 0.5;
        Ball shot = new Ball(new Point(shooter.getCollisionRectangle().getUpperLeft().getX()
                + shooter.getCollisionRectangle().getWidth() / 2, shooter.getCollisionRectangle().getUpperLeft().getY()
                + shooter.getCollisionRectangle().getHeight() + 10), 5, Color.red, Color.BLACK);
        shot.setVelocity(Velocity.fromAngleAndSpeed(180, 350));
        shot.setGameEnvironment(environment);
        shot.addToGame(this);
        shots.add(shot);
    }

    /**
     * create paddle(player) shot.
     */
    public void createPaddleShot() {
        this.paddleShotCooldown = 0.35;
        Ball shot = new Ball(new Point(paddle.getCollisionRectangle().getUpperLeft().getX()
                + paddle.getCollisionRectangle().getWidth() / 2,
                paddle.getCollisionRectangle().getUpperLeft().getY() - 3), 3, Color.white, Color.BLACK);
        shot.setVelocity(Velocity.fromAngleAndSpeed(0, 350));
        shot.setGameEnvironment(environment);
        shot.addToGame(this);
        shots.add(shot);
    }
}
