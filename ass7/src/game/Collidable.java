package game;

import shapes.Ball;
import shapes.Point;
import shapes.Rectangle;

/**
 * interface name: Collidable
 * The Collidable interface is used by things that can be collided with.
 * A collidable object must have location and size(collision rectangle)
 * and need to know what to do when a collision occurs.
 *
 * @author Elad Israel
 * @version 3.0 20/05/2018
 */
public interface Collidable {
    /**
     * Return the "collision shape" of the object.
     *
     * @return collision shape- rectangle
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     *
     * @param hitter          the ball that hit the coolidable.
     * @param collisionPoint  the point of collision.
     * @param currentVelocity the velocity of the ball before impact.
     * @return the new velocity the ball should have after the collision.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}