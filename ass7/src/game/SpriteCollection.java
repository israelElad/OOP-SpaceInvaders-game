package game;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * Class name: SpriteCollection
 * a SpriteCollection will hold a collection of sprites.
 *
 * @author Elad Israel
 * @version 3.0 20/05/2018
 */
public class SpriteCollection {
    private List<Sprite> sprites = new ArrayList<>();


    /**
     * add the given sprite to the collection.
     *
     * @param s given sprite.
     */
    public void addSprite(Sprite s) {
        if (!sprites.contains(s)) {
            sprites.add(s);
        }
    }

    /**
     * remove the given sprite from the collection.
     *
     * @param s given sprite.
     */
    public void removeSprite(Sprite s) {
        sprites.remove(s);
    }

    /**
     * call timePassed() on all sprites.
     *
     * @param dt amount of seconds passed since the last call
     */
    public void notifyAllTimePassed(double dt) {
        // Make a copy of the Sprites before iterating over them.
        List<Sprite> spritesCopy = new ArrayList<Sprite>(this.sprites);
        // Notify all Sprites that time passed:
        for (Sprite sprite : spritesCopy) {
            sprite.timePassed(dt);
        }
    }

    /**
     * call drawOn(d) on all sprites.
     *
     * @param d drawSurface
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite : sprites) {
            sprite.drawOn(d);
        }
    }
}