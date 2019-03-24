package game;

import animation.GameLevel;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import shapes.Ball;
import shapes.Line;
import shapes.Point;
import shapes.Rectangle;

/**
 * ClassName: Paddle
 * The Paddle is the player in the game. It is a rectangle that is controlled by the arrow keys,
 * and moves according to the player key presses.
 * It implements the Sprite and the Collidable interfaces.
 * It  also knows how to move to the left and to the right.
 *
 * @author Elad Israel
 * @version 3.0 20/05/2018
 */
public class Paddle implements Sprite, Collidable {

    private static final int SIDE_FRAMES_WIDTH = 25;
    private static final int FRAME_WIDTH = 800;
    private double speed;
    private Rectangle paddle;
    private KeyboardSensor keyboardSensor;
    private Boolean wasHit;


    /**
     * Construct the Paddle using position point, width, height, fill color, draw color.
     * also receives the keyboardSensor and sets it.
     *
     * @param upperLeft      point(position)
     * @param width          of the paddle
     * @param height         of the paddle
     * @param speed          of the paddle
     * @param fillColor      of the paddle
     * @param drawColor      of the paddle
     * @param keyboardSensor passed in order to identify the movements of the Paddle.
     */
    public Paddle(Point upperLeft, double width, double height, double speed, java.awt.Color fillColor, java.awt.Color
            drawColor, KeyboardSensor keyboardSensor) {
        this.paddle = new Rectangle(upperLeft, width, height, fillColor);
        this.keyboardSensor = keyboardSensor;
        this.speed = speed;
        this.wasHit = false;
    }

    /**
     * moves the paddle to the left.
     *
     * @param dt amount of seconds passed since the last call
     */
    public void moveLeft(double dt) {
        //reached left edge(block)
        if (paddle.getUpperLeft().getX() - speed * dt <= SIDE_FRAMES_WIDTH) {
            return;
        }
        paddle.changePosition(new Point(paddle.getUpperLeft().getX() - speed * dt, paddle.getUpperLeft().getY()));
    }

    /**
     * moves the paddle to the right.
     *
     * @param dt amount of seconds passed since the last call
     */
    public void moveRight(double dt) {
        //reached right edge(block)
        if (paddle.getUpperLeft().getX() + paddle.getWidth() + speed * dt >= FRAME_WIDTH - SIDE_FRAMES_WIDTH) {
            return;
        }
        paddle.changePosition(new Point(paddle.getUpperLeft().getX() + speed * dt, paddle.getUpperLeft().getY()));
    }

    /**
     * Specify what the paddle does when time is passed - moves left or right if pressed.
     *
     * @param dt amount of seconds passed since the last call
     */
    public void timePassed(double dt) {
        if (keyboardSensor.isPressed(keyboardSensor.LEFT_KEY)) {
            moveLeft(dt);
        }
        if (keyboardSensor.isPressed(keyboardSensor.RIGHT_KEY)) {
            moveRight(dt);
        }
    }

    /**
     * draws the paddle on the surface.
     *
     * @param d draw surface
     */
    public void drawOn(DrawSurface d) {
        this.paddle.drawOn(d);
    }

    /**
     * Return the "collision shape" of the object.
     *
     * @return collision shape- rectangle
     */
    public Rectangle getCollisionRectangle() {
        return this.paddle;
    }

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     *
     * @param hitter          the ball that hit the paddle.
     * @param collisionPoint  the point of collision.
     * @param currentVelocity the velocity of the ball before impact.
     * @return the new velocity the ball should have after the collision.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        wasHit = true;

        Line upperEdge = this.paddle.getUpperEdge();
        // divides the paddle's upper edge to 5 equally-spaced regions
        double upperEdgeRegionLength = upperEdge.length() / 5;
        //calculates the speed using Pythagoras (sqrt(dx^2+dy^2))=speed.
        double currentSpeed = Math.sqrt(Math.pow(currentVelocity.getDx(), 2) + Math.pow(currentVelocity.getDy(), 2));

        // calculates the 5 regions
        Line leftMostRegion = new Line(upperEdge.start(), new Point(upperEdge.start().getX() + upperEdgeRegionLength,
                upperEdge.start().getY()));
        Line leftMiddleRegion = new Line(new Point(upperEdge.start().getX() + upperEdgeRegionLength,
                upperEdge.start().getY()), new Point(upperEdge.start().getX() + 2 * upperEdgeRegionLength,
                upperEdge.start().getY()));
        Line middleRegion = new Line(new Point(upperEdge.start().getX() + 2 * upperEdgeRegionLength,
                upperEdge.start().getY()), new Point(upperEdge.start().getX() + 3 * upperEdgeRegionLength,
                upperEdge.start().getY()));
        Line rightMiddleRegion = new Line(new Point(upperEdge.start().getX() + 3 * upperEdgeRegionLength,
                upperEdge.start().getY()), new Point(upperEdge.start().getX() + 4 * upperEdgeRegionLength,
                upperEdge.start().getY()));
        Line rightMostRegion = new Line(new Point(upperEdge.start().getX() + 4 * upperEdgeRegionLength,
                upperEdge.start().getY()), new Point(upperEdge.start().getX() + 5 * upperEdgeRegionLength,
                upperEdge.start().getY()));

        //deals with a collision according to the region(detailed explanation above)
        if (leftMostRegion.isPointOnTheLine(collisionPoint)) {
            return Velocity.fromAngleAndSpeed(300, currentSpeed);
        }
        if (leftMiddleRegion.isPointOnTheLine(collisionPoint)) {
            return Velocity.fromAngleAndSpeed(330, currentSpeed);
        }
        if (middleRegion.isPointOnTheLine(collisionPoint)) {
            return new Velocity(currentVelocity.getDx(), -1 * currentVelocity.getDy());
        }
        if (rightMiddleRegion.isPointOnTheLine(collisionPoint)) {
            return Velocity.fromAngleAndSpeed(30, currentSpeed);
        }
        if (rightMostRegion.isPointOnTheLine(collisionPoint)) {
            return Velocity.fromAngleAndSpeed(60, currentSpeed);
        }
        if (this.paddle.getLeftEdge().isPointOnTheLine(collisionPoint)) {
            return new Velocity(-1 * currentVelocity.getDx(), currentVelocity.getDy());
        }
        if (this.paddle.getRightEdge().isPointOnTheLine(collisionPoint)) {
            return new Velocity(-1 * currentVelocity.getDx(), currentVelocity.getDy());
        }
        return currentVelocity;
    }

    /**
     * adds the Paddle to the game-as a sprite and as a Collidable.
     *
     * @param g game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * adds the Paddle to the game-as a sprite and as a Collidable.
     *
     * @param g game
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
        g.removeCollidable(this);
    }

    /**
     * Was the paddle hit -boolean.
     *
     * @return the boolean
     */
    public Boolean wasHit() {
        return this.wasHit;
    }
}