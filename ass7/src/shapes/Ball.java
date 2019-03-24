package shapes;

import biuoop.DrawSurface;
import game.CollisionInfo;
import game.GameEnvironment;
import animation.GameLevel;
import game.Velocity;
import game.Paddle;
import game.Sprite;

import java.awt.Color;

/**
 * Classname: Ball
 * a Ball (actually, a circle) has size (radius), color, and location (a Point).
 * Balls also know how to draw themselves on a DrawSurface.
 *
 * @author Elad Israel
 * @version 3.0 20/05/2018
 */
public class Ball implements Sprite {
    //members
    private int size;
    private Point point;
    private java.awt.Color fillColor;
    private java.awt.Color drawColor;
    private Velocity velocity;
    private GameEnvironment gameEnvironment;

    /**
     * Constructor 1.
     * Constructs a Ball using center point, radius, and color.
     *
     * @param center    center point of this ball.
     * @param r         radius of this ball.
     * @param fillColor fill color of the ball.
     * @param drawColor draw color of the ball.
     */
    public Ball(Point center, int r, java.awt.Color fillColor, java.awt.Color drawColor) {
        this.size = r;
        this.point = center;
        this.fillColor = fillColor;
        this.drawColor = drawColor;
    }

    /**
     * Constructor 2.
     * Constructs a Ball using x and y coordinates of the center point, radius, and color.
     *
     * @param x         x coordinate of the center point of this ball.
     * @param y         y coordinate of the center point of this ball.
     * @param r         radius of this ball.
     * @param fillColor color of this ball's filling.
     * @param drawColor color of this ball's circumference.
     */
    public Ball(int x, int y, int r, java.awt.Color fillColor, java.awt.Color drawColor) {
        this.size = r;
        this.point = new Point(x, y);
        this.fillColor = fillColor;
        this.drawColor = drawColor;
    }

    /**
     * Access method- Return the x value of this ball.
     *
     * @return x coordinate of the center point of this ball.
     */
    public int getX() {
        return (int) this.point.getX();
    }

    /**
     * Access method- Return the y value of this ball.
     *
     * @return y coordinate of the center point of this ball.
     */
    public int getY() {
        return (int) this.point.getY();
    }

    /**
     * Access method- Return the size(radius) of this ball.
     *
     * @return the size(radius) of this ball.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Access method- Return the ball's fill Color.
     *
     * @return the ball's fill Color.
     */
    public java.awt.Color getFillColor() {
        return this.fillColor;
    }

    /**
     * Access method- Return the ball's draw Color.
     *
     * @return the ball's draw Color.
     */
    public java.awt.Color getDrawColor() {
        return this.drawColor;
    }

    /**
     * setter for gameEnviroment.
     *
     * @param gameEnvironmentToSet gameEnvironment to set
     */
    public void setGameEnvironment(GameEnvironment gameEnvironmentToSet) {
        this.gameEnvironment = gameEnvironmentToSet;
    }

    /**
     * draws this ball on the given DrawSurface.
     *
     * @param surface drawSurface
     */
    public void drawOn(DrawSurface surface) {
        //default color if no color was entered
        if (this.fillColor == null || this.drawColor == null) {
            this.fillColor = Color.black;
            this.drawColor = Color.black;
        }
        surface.setColor(this.fillColor);
        surface.fillCircle((int) this.point.getX(), (int) this.point.getY(), this.size);
        surface.setColor(this.drawColor);
        surface.drawCircle((int) this.point.getX(), (int) this.point.getY(), this.size);

    }

    /**
     * Specify what the ball does when time is passed - moves one step.
     *
     * @param dt amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }

    /**
     * sets the Velocity of the ball using dx and dy.
     *
     * @param dx dx value to set to this ball's velocity
     * @param dy dy value to set to this ball's velocity
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * gets the Velocity of the ball.
     *
     * @return velocity
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * sets the Velocity of the ball using Velocity.
     *
     * @param v Velocity value to set to this ball's velocity
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * calculates where the ball should advance to next:
     * makes sure the ball does not go outside of the screen
     * when it hits the border to the left or to the right, it changes its horizontal direction,
     * and when it hits the border on the top or the bottom, it changes its vertical direction.
     * and then calls applyToPoint that actually moves the ball.
     *
     * @param dt amount of seconds passed since the last call
     */
    public void moveOneStep(double dt) {
        Line trajectory = calculateTrajectory(dt);
        //gets closest collision point to the start of the trajectory line
        CollisionInfo collision;
        try {
            collision = gameEnvironment.getClosestCollision(trajectory);
        } catch (RuntimeException nullPointer) {
            throw new RuntimeException("Ball's gameEnvironment wasn't initialized!");
        }
        //no collision occurred- move the ball regularly to the end of the trajectory
        if (collision == null) {
            this.point = this.velocity.applyToPoint(this.point, dt);
        } else { //collision happened(about to)
            Velocity previous = new Velocity(this.velocity);
            this.velocity = new Velocity(
                    collision.collisionObject().hit(this, collision.collisionPoint(), this.velocity));
            //second check- if the new course of the ball also leads to a collision
            trajectory = calculateTrajectory(dt);
            collision = gameEnvironment.getClosestCollision(trajectory);
            //no collision expected at the new course OR the second collision is with the paddle(makes the ball stuck)
            if (collision == null || collision.collisionObject() instanceof Paddle) {
                this.point = this.velocity.applyToPoint(this.point, dt);
            } else { //collision is expected to occur with the new course
                //changes the ball course to the opposite of where it originally came from.
                this.velocity = new Velocity(previous.getDx() * -1, previous.getDy() * -1);
            }
        }
    }

    /**
     * Calculates the trajectory - "how the ball will move
     * without any obstacles" -- its a line starting at current location, and
     * ending where the velocity will take the ball if no collisions will occur.
     *
     * @param dt amount of seconds passed since the last call
     * @return trajectory line
     */
    public Line calculateTrajectory(double dt) {
        //default values of velocity if the velocity wasn't set before trying to move the ball.
        double dx = 1;
        double dy = 1;
        try {
            dx = this.velocity.getDx();
            dy = this.velocity.getDy();
            //if the velocity wasn't set before trying to move the ball, prints a message and sets the default values.
        } catch (NullPointerException e) {
            System.out.println("Ball's velocity wasn't defined. Velocity is now default values: dx=1, dy=1.");
            this.setVelocity(dx, dy);
        }
        //trajectory ends where the ball would advance to in its next step.
        Point trajectoryEnd = new Point(this.point.getX() + dx * dt, this.point.getY() + dy * dt);
        /* adjusting the trajectory to be longer so that the ball will move to "almost" the hit point, but just
         slightly before it */
        if (this.velocity.getDx() >= 0) {
            trajectoryEnd.setX(trajectoryEnd.getX() + this.size / 2);
        }
        if (this.velocity.getDx() < 0) {
            trajectoryEnd.setX(trajectoryEnd.getX() - this.size / 2);
        }
        if (this.velocity.getDy() >= 0) {
            trajectoryEnd.setY(trajectoryEnd.getY() + this.size / 2);
        }
        if (this.velocity.getDy() < 0) {
            trajectoryEnd.setY(trajectoryEnd.getY() - this.size / 2);
        }
        return new Line(this.point, trajectoryEnd);
    }

    /**
     * adds the ball to the game-as a sprite.
     * also, increases the number of balls in the game.
     *
     * @param g game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.getNumOfBalls().increase(1);
    }

    /**
     * removers the ball from the game-as a sprite.
     * Decrease in numOfBalls is executed in BallsRemover.
     *
     * @param g game
     */
    public void removeFromGame(GameLevel g) {
        g.removeFromShots(this);
        g.removeSprite(this);
    }
}