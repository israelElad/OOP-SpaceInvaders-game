package game;

import shapes.Point;

/**
 * Classname: Velocity
 * Velocity specifies the change in position on the `x` and the `y` axes.
 *
 * @author Elad Israel
 * @version 3.0 20/05/2018
 */
public class Velocity {

    //members
    private double dx;
    private double dy;

    /**
     * Constructor1.
     * Constructs a Velocity using dx(change in X-coordinate) and dy(change in Y-coordinate).
     *
     * @param dx change in X-coordinate.
     * @param dy change in Y-coordinate.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Constructor2.
     * Constructs a Velocity using another Velocity.
     *
     * @param velocity - the new Velocity to set.
     */
    public Velocity(Velocity velocity) {
        this.dx = velocity.getDx();
        this.dy = velocity.getDy();
    }

    /**
     * Constructor3.
     * Constructs a Velocity using an angle and speed.
     *
     * @param angle angle of movement.
     * @param speed speed of movement.
     * @return new velocity from the calculated dx and dy.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        //change in X-coordinate=speed*sin(rad) of the angle
        double dx = speed * Math.sin(Math.toRadians(angle));
        //change in Y-coordinate=speed*cos(rad) of the angle. multiply by -1 to fix and reverse the axes (upside-down).
        double dy = -1 * speed * Math.cos(Math.toRadians(angle));
        return new Velocity(dx, dy);
    }

    /**
     * Access method- Return the dx value of this point.
     *
     * @return dx value of this point
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * sets the dx value of this point.
     *
     * @param dX value to set to this point
     */
    public void setDx(double dX) {
        this.dx = dX;
    }

    /**
     * Access method- Return the dy value of this point.
     *
     * @return dy value of this point
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * sets the dy value of this point.
     *
     * @param dY value to set to this point
     */
    public void setDy(double dY) {
        this.dy = dY;
    }

    /**
     * Takes a point with position (x,y) and return a new point with position (x+dx, y+dy)
     * Actually changing the objects position.
     *
     * @param p point given
     * @param dt amount of seconds passed since the last call
     * @return new point with updated location
     */
    public Point applyToPoint(Point p, double dt) {
        return new Point(p.getX() + dx * dt, p.getY() + dy * dt);
    }
}