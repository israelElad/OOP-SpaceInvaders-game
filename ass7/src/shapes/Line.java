package shapes;

/**
 * Classname: Line
 * A line (actually a line-segment) connects two points - a start point and an end point.
 * Lines have lengths, and may intersect with other lines.
 * It can also tell if it is the same as another line segment.
 *
 * @author Elad Israel
 * @version 3.0 20/05/2018
 */
public class Line {

    // Members - what defines a line
    private Point start;
    private Point end;
    private Point inter;

    /**
     * Constructor 1.
     * Constructs a Line using starting point and ending point.
     *
     * @param start starting point of this line.
     * @param end   ending point of this line.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructor 2.
     * Constructs a Line using x coordinate and y coordinate of a starting point
     * and x coordinate and y coordinate of an ending point.
     *
     * @param x1 coordinate X of the starting point.
     * @param y1 coordinate Y of the starting point.
     * @param x2 coordinate X of the ending point.
     * @param y2 coordinate Y of the ending point.
     */

    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Return the length of the line.
     *
     * @return length
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * Returns the middle point of the line.
     *
     * @return middle point
     */
    public Point middle() {
        double midX = (this.start.getX() + this.end.getX()) / 2;
        double midY = (this.start.getY() + this.end.getY()) / 2;
        return new Point(midX, midY);
    }

    /**
     * Returns the starting point of the line.
     *
     * @return start point
     */
    public Point start() {
        return this.start;
    }

    /**
     * Returns the starting point of the line.
     *
     * @return start point
     */
    public Point end() {
        return this.end;
    }

    /**
     * Returns true if the lines intersect (calculates the intersection point in the process),
     * and returns false otherwise:
     * if the lines have the same slope and if don't have the same slope but don't intersect.
     *
     * @param other other line.
     * @return true/false- intersects/not.
     */
    public boolean isIntersecting(Line other) {
        //stores the intersection point's coordinates.
        double interX;
        double interY;
        //stores the coordinates of this line and other line to avoid calling functions a lot and for readability.
        double thisStartX = this.start().getX();
        double thisStartY = this.start().getY();
        double thisEndX = this.end().getX();
        double thisEndY = this.end().getY();
        double otherStartX = other.start().getX();
        double otherStartY = other.start().getY();
        double otherEndX = other.end().getX();
        double otherEndY = other.end().getY();
        //stores the slopes of the two lines
        double thisSlope;
        double otherSlope;

        /*
        formulas explanation:

        definition:
        x1= x of the start of this line
        x2= x of the end of this line
        y1= y of the start of this line
        y2= y of the end of this line
        a1= x of the start of other line
        a2= x of the end of other line
        b1= y of the start of other line
        b2= y of the end of other line

        we have an equation for each line:
        first line(this line): y=m1(x-x1)+y1 when m1 can be: (y2-y1)/(x2-x1)
        second line(other line): y=m2(x-a1)+b1 when m2 can be: (b2-b1)/(a2-a1)
        */

        //at least one of the lines is vertical
        if (thisEndX - thisStartX == 0 || otherEndX - otherStartX == 0) {
            //both vertical- same slope(infinity)
            if ((thisEndX - thisStartX == 0) && (otherEndX - otherStartX == 0)) {
                return false;
            }
            // this line is vertical
            if (thisEndX - thisStartX == 0) {
                //m1=(y2-y1)-(x2-x1)
                otherSlope = (otherEndY - otherStartY) / (otherEndX - otherStartX);
                //X-coordinate of the intersection is any X of this line
                interX = this.start.getX();
                //intersection Y=m2*(x1-a1)+b1
                interY = otherSlope * (interX - otherStartX) + otherStartY;
                //other line is vertical
            } else {
                //m2=(b2-b1)/(a2-a1)
                thisSlope = (thisEndY - thisStartY) / (thisEndX - thisStartX);
                //X-coordinate of the intersection is any X of other line
                interX = other.start.getX();
                //intersection Y=m1*(b1-x1)+y1
                interY = thisSlope * (interX - thisStartX) + thisStartY;
            }
            //no lines are vertical- calculate slopes as explained above(regular formula).
        } else {
            thisSlope = (thisEndY - thisStartY) / (thisEndX - thisStartX);
            otherSlope = (otherEndY - otherStartY) / (otherEndX - otherStartX);

            //the lines are parallel
            if (thisSlope == otherSlope) {
                return false;
            }

            /*
             calculates the X and Y coordinates of the intersection
             X coordinate= (m2x2-y2-m2a1+b1)/(m2-m1) as can be calculated from the formulas above by comparing the Y
             of both equations and then isolating the x of the intersection point.
             Y coordinate= simply placing the X coordinate found in one of the formulas.
             */
            interX = (thisSlope * thisStartX - thisStartY - otherSlope * otherStartX + otherStartY)
                    / (thisSlope - otherSlope);
            interY = thisSlope * interX - thisSlope * thisStartX + thisStartY;
        }
        //creates a point for this intersection point.
        this.inter = new Point(interX, interY);

        //if intersection point is between the limits of the line-segments it's treated as an intersection point.
        return ((this.inter.getX() >= Math.min(thisStartX, thisEndX))
                && (this.inter.getX() <= Math.max(thisStartX, thisEndX))
                && (this.inter.getY() >= Math.min(thisStartY, thisEndY))
                && (this.inter.getY() <= Math.max(thisStartY, thisEndY))
                && (this.inter.getX() >= Math.min(otherStartX, otherEndX))
                && (this.inter.getX() <= Math.max(otherStartX, otherEndX))
                && (this.inter.getY() >= Math.min(otherStartY, otherEndY))
                && (this.inter.getY() <= Math.max(otherStartY, otherEndY)));
    }

    /**
     * Returns the intersection point if the lines intersect, and null otherwise.
     * uses isIntersecting for the calculation.
     *
     * @param other other line
     * @return intersection point if there is one, null otherwise.
     */
    public Point intersectionWith(Line other) {
        if (this.isIntersecting(other)) {
            return this.inter;
        } else {
            return null;
        }
    }

    /**
     * equals - return true if the lines are equal, false otherwise.
     * important! two line that whose starting and ending points are the similar but opposite- aren't equals!
     *
     * @param other other line
     * @return are equals or not(boolean)
     */
    public boolean equals(Line other) {
        return ((this.start.equals(other.start())) && (this.end.equals(other.end())));
    }

    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the
     * start of the line.
     *
     * @param rect rectangle to check intersections
     * @return closest intersection point to start of line
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        java.util.List<Point> intersectionPArr = rect.intersectionPoints(this);
        //no intersection points
        if (intersectionPArr.size() == 0) {
            return null;
        }
        //one intersection point
        if (intersectionPArr.size() == 1) {
            return intersectionPArr.get(0);
        }
        //two intersection points - returns the closest one
        if (intersectionPArr.get(0).distance(this.start) < intersectionPArr.get(1).distance(this.start)) {
            return intersectionPArr.get(0);
        } else {
            return intersectionPArr.get(1);
        }
    }

    /**
     * checks whether a given point is on this line.
     *
     * @param checkedPoint given point to check
     * @return true if is, false if isn't
     */
    public boolean isPointOnTheLine(Point checkedPoint) {
        return (checkedPoint.distance(this.start) + checkedPoint.distance(this.end) == this.start.distance(this.end));
    }
}