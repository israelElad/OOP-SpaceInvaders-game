package game;

import shapes.Point;

/**
 * Classname: CollisionInfo
 * Contains information about a collision- which object collided with any of the collidables
 * in this collection, and the closest collision point that is going to occur.
 *
 * @author Elad Israel
 * @version 3.0 20/05/2018
 */
public class CollisionInfo {

    private Collidable collidableObject;
    private Point collisionPoint;

    /**
     * Constructor.
     *
     * @param collidableObject the object that collided.
     * @param collisionPoint   the point of collision.
     */
    public CollisionInfo(Collidable collidableObject, Point collisionPoint) {
        this.collidableObject = collidableObject;
        this.collisionPoint = collisionPoint;
    }

    /**
     * Getter for collisionPoint.
     *
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * Getter for collisionObject.
     *
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collidableObject;
    }
}