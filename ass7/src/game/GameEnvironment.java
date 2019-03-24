package game;

import shapes.Line;
import shapes.Point;

/**
 * interface name: GameEnvironment
 * A collection of the objects a Ball can collide with.
 * The ball will know the game environment, and will use it to check for collisions and direct its movement.
 *
 * @author Elad Israel
 * @version 3.0 20/05/2018
 */
public class GameEnvironment {

    private java.util.List<Collidable> collidableObjects;

    /**
     * Constructor.
     *
     * @param collidableObjects a collection of the objects a Ball can collide with.
     */
    public GameEnvironment(java.util.List<Collidable> collidableObjects) {
        this.collidableObjects = collidableObjects;
    }

    /**
     * add the given collidable to the environment.
     *
     * @param c given collidable
     */
    public void addCollidable(Collidable c) {
        if (!collidableObjects.contains(c)) {
            collidableObjects.add(c);
        }
    }

    /**
     * removes the given collidable from the environment.
     *
     * @param c given collidable
     */
    public void removeCollidable(Collidable c) {
        collidableObjects.remove(c);
    }


    /**
     * Getter for collidableObjects.
     *
     * @return a collection of the objects a Ball can collide with
     */
    public java.util.List<Collidable> getCollidable() {
        return collidableObjects;
    }


    /**
     * Assuming an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     *
     * @param trajectory a line representing the balls movement in the next step.
     * @return either null if no collision was found, or the CollisionInfo.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        if (this.collidableObjects.isEmpty()) {
            return null;
        }
        //first intersection point
        Point firstInterP;
        //first intersection object
        Collidable firstInterO;
        //find index of the first collidable that intersects with trajectory
        int i = 0;
        while ((i < this.collidableObjects.size())
                && (trajectory.closestIntersectionToStartOfLine(collidableObjects.get(i).getCollisionRectangle())
                == null)) {
            i++;
        }
        //reached the end of the List and no collision was found with any of the Collidable objects
        if (i == this.collidableObjects.size()) {
            return null;
            //found first intersection with a Collidable. Doesn't have to be a collision yet(may not be the closest).
        } else {
            firstInterP = trajectory.closestIntersectionToStartOfLine(collidableObjects.get(i).getCollisionRectangle());
            firstInterO = this.collidableObjects.get(i);
        }
        //goes through all collidableObjects and finds all collisionPoints, and checks which one is the closest.
        Point closestInterP = firstInterP;
        Collidable closestInterO = firstInterO;
        for (; i < this.collidableObjects.size(); i++) {
            Point interPWithCurrentO = trajectory.closestIntersectionToStartOfLine(collidableObjects.get(i)
                    .getCollisionRectangle());
            //found another collisionPoint
            if (interPWithCurrentO != null) {
                //checks whether its the closest collisionPoint that was found(yet)
                if (interPWithCurrentO.distance(trajectory.start()) < closestInterP.distance(trajectory.start())) {
                    closestInterP = interPWithCurrentO;
                    closestInterO = this.collidableObjects.get(i);
                }
            }
        }
        return new CollisionInfo(closestInterO, closestInterP);
    }
}