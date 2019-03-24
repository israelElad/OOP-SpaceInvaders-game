package levels;


import biuoop.DrawSurface;
import shapes.Rectangle;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;

/**
 * Background with Image.
 * describes a Background of a level or block with image.
 *
 * @author Elad Israel
 * @version 4.0 17/06/2018
 */
public class BackgroundImage implements Background {
    private Image image;

    /**
     * Instantiates a new Background image.
     *
     * @param path the path
     */
    public BackgroundImage(String path) {
        try {
            image = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read image file!");
        }
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d drawSurface
     */
    public void drawOn(DrawSurface d) {
        drawOn(d, null);
    }

    /**
     * Draw Background on drawsurface.
     *
     * @param d         the drawsurface
     * @param rectangle the rectangle
     */
    public void drawOn(DrawSurface d, Rectangle rectangle) {
        if (rectangle == null) {
            d.drawImage(0, 0, image);
        } else {
            d.drawImage((int) rectangle.getUpperLeft().getX(), (int) rectangle.getUpperLeft().getY(), image);
        }
    }


    /**
     * notify the sprite that time has passed.
     *
     * @param dt amount of seconds passed since the last call
     */
    public void timePassed(double dt) {

    }
}
