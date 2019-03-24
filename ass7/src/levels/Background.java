package levels;

import biuoop.DrawSurface;
import game.Sprite;
import shapes.Rectangle;

/**
 * interface name: Background
 * The Background interface.
 * describes a Background of a level or block.
 *
 * @author Elad Israel
 * @version 4.0 17/06/2018
 */
public interface Background extends Sprite {

    /**
     * Draw Background on drawsurface.
     *
     * @param d the drawsurface
     * @param r the rectangle
     */
    void drawOn(DrawSurface d, Rectangle r);
}