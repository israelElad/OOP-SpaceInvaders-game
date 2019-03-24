package shapes;

import biuoop.DrawSurface;

import java.util.ArrayList;


/**
 * Classname: Rectangle
 * A Rectangle has size, color, and location (a Point).
 * it also has edges and fill and draw colors.
 * Rectangles also know how to draw themselves on a DrawSurface.
 *
 * @author Elad Israel
 * @version 1.0 20/04/2018
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;
    private Line upperEdge;
    private Line lowerEdge;
    private Line leftEdge;
    private Line rightEdge;
    private java.awt.Color fillColor;

    /**
     * Constructor3
     * construct a Rectangle using upper-left point ,width and height, and fill and draw colors. and sets the edges.
     *
     * @param upperLeft upper-left corner
     * @param width     of the rectangle
     * @param height    of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        setEdges();
        this.fillColor = null;
    }

    /**
     * Constructor3
     * construct a Rectangle using upper-left point ,width and height, and fill and draw colors. and sets the edges.
     *
     * @param upperLeft upper-left corner
     * @param width     of the rectangle
     * @param height    of the rectangle
     * @param fillColor of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height, java.awt.Color fillColor) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        setEdges();
        this.fillColor = fillColor;

    }

    /**
     * sets the edges of the Rectangle accourding to the upper left corner received.
     */
    private void setEdges() {
        Point upperRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY());
        Point lowerLeft = new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.getHeight());
        Point lowerRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY() + this.height);
        this.upperEdge = new Line(this.upperLeft, upperRight);
        this.lowerEdge = new Line(lowerLeft, lowerRight);
        this.leftEdge = new Line(this.upperLeft, lowerLeft);
        this.rightEdge = new Line(upperRight, lowerRight);
    }

    /**
     * Setter for the upperLeft point- change the rectangle position and reset the edges.
     *
     * @param newUpperLeft to set.
     */
    public void changePosition(Point newUpperLeft) {
        this.upperLeft = newUpperLeft;
        setEdges();
    }

    /**
     * Getter of the rectangle's width.
     *
     * @return the width of the rectangle
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Sets width.
     *
     * @param widthToSet the width
     */
    public void setWidth(double widthToSet) {
        this.width = widthToSet;
    }

    /**
     * Getter of the rectangle's height.
     *
     * @return the height of the rectangle
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Sets height.
     *
     * @param heightToSet the height
     */
    public void setHeight(double heightToSet) {
        this.height = heightToSet;
    }

    /**
     * Getter of the upper-left point of the rectangle.
     *
     * @return the upper-left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Sets upper left.
     *
     * @param upperLeftToSet the upper left
     */
    public void setUpperLeft(Point upperLeftToSet) {
        this.upperLeft = upperLeftToSet;
        setEdges();
    }

    // Returns the upper-left point of the rectangle.

    /**
     * Gets the lower edge(line) of the rectangle.
     *
     * @return lower line
     */
    public Line getLowerEdge() {
        return this.lowerEdge;
    }

    /**
     * Gets the upper edge(line) of the rectangle.
     *
     * @return upper line
     */
    public Line getUpperEdge() {
        return this.upperEdge;
    }

    /**
     * Gets the left edge(line) of the rectangle.
     *
     * @return left line
     */
    public Line getLeftEdge() {
        return this.leftEdge;
    }

    /**
     * Gets the right edge(line) of the rectangle.
     *
     * @return right line
     */
    public Line getRightEdge() {
        return this.rightEdge;
    }

    /**
     * Access method- Return the fill color of this rectangle.
     *
     * @return the fill color of this rectangle.
     */
    public java.awt.Color getFillColor() {
        return this.fillColor;
    }

    /**
     * draws this Rectangle on the given DrawSurface.
     *
     * @param surface drawSurface
     */
    public void drawOn(DrawSurface surface) {
        if (this.fillColor != null) {
            surface.setColor(this.fillColor);
            surface.fillRectangle((int) this.upperLeft.getX(), (int) this.upperLeft.getY(),
                    (int) this.width, (int) this.height);
        }
    }

    /**
     * Return a (possibly empty) List of intersection points of the rectangle with the specified line.
     *
     * @param line the line to check with.
     * @return list of intersection points
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        java.util.List<Point> intersectionPArr = new ArrayList<>();
        if (this.upperEdge.isIntersecting(line)) {
            intersectionPArr.add(this.upperEdge.intersectionWith(line));
        }
        if (this.lowerEdge.isIntersecting(line)) {
            intersectionPArr.add(this.lowerEdge.intersectionWith(line));
        }
        if (this.leftEdge.isIntersecting(line)) {
            intersectionPArr.add(this.leftEdge.intersectionWith(line));
        }
        if (this.rightEdge.isIntersecting(line)) {
            intersectionPArr.add(this.rightEdge.intersectionWith(line));
        }
        return intersectionPArr;
    }

}