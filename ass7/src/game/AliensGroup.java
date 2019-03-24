package game;

import levels.Background;
import levels.BackgroundImage;
import shapes.Point;
import shapes.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Classname: AliensGroup.
 * creates an aliens matrix(group) and implement their related methods.
 *
 * @author Elad Israel
 * @version 4.0 17/06/2018
 */
public class AliensGroup {
    private List<List<Alien>> aliensMatrix;


    /**
     * Instantiates a new Aliens group.
     */
    public AliensGroup() {
        this.aliensMatrix = new ArrayList<>();
        final int numOfRows = 5;
        final int numOfColumns = 10;
        final int alienWidth = 40;
        final int alienHeight = 30;
        final int startingHeightOfBlocks = 50;
        final int startingWidthOfBlocks = 50;
        final int gap = 10;
        Background alienBackground = new BackgroundImage("alien.png");

        for (int column = 0; column < numOfColumns; column++) {
            List<Alien> aliensColumn = new ArrayList<>();
            for (int row = 0; row < numOfRows; row++) {
                Alien alien = new Alien(new Rectangle(new Point(column * alienWidth + startingWidthOfBlocks
                        + gap * column, row * alienHeight + startingHeightOfBlocks + gap * row),
                        alienWidth, alienHeight), alienBackground);
                aliensColumn.add(alien);
            }
            aliensMatrix.add(aliensColumn);
        }
    }


    /**
     * Choose random alien shooter shooter.
     *
     * @param aliens the aliens
     * @return the alien
     */
    public Alien chooseShooter(List<List<Alien>> aliens) {
        Random rnd = new Random();
        int randomColumn = rnd.nextInt(aliens.size());
        List<Alien> column = aliens.get(randomColumn);
        while (column.isEmpty()) {
            randomColumn = rnd.nextInt(aliens.size());
            column = aliens.get(randomColumn);
        }
        return column.get(column.size() - 1);
    }

    /**
     * Gets aliens matrix.
     *
     * @return the aliens matrix
     */
    public List<List<Alien>> getAliensMatrix() {
        return aliensMatrix;
    }

    /**
     * Get most right x point of the aliens group.
     *
     * @return the double
     */
    public double getMostRightXPoint() {
        return this.aliensMatrix.get(aliensMatrix.size() - 1).get(0).getCollisionRectangle().getUpperLeft().getX() + 40;
    }

    /**
     * Get most left x point of the aliens group.
     *
     * @return the double
     */
    public double getMostLeftXPoint() {
        return this.aliensMatrix.get(0).get(0).getCollisionRectangle().getUpperLeft().getX();
    }

    /**
     * Get upper y point of the aliens group.
     *
     * @return the double
     */
    public double getUpperYPoint() {
        double minY = 600;
        for (List<Alien> column : aliensMatrix) {
            double minYInColumn = column.get(0).getCollisionRectangle().getUpperLeft().getY();
            if (minYInColumn < minY) {
                minY = minYInColumn;
            }
        }
        return minY;
    }

    /**
     * Get bottom y point of the aliens group.
     *
     * @return the double
     */
    public double getBottomYPoint() {
        double maxY = 0;
        for (List<Alien> column : aliensMatrix) {
            double maxYInColumn = column.get(column.size() - 1).getCollisionRectangle().getUpperLeft().getY() + 30;
            if (maxYInColumn > maxY) {
                maxY = maxYInColumn;
            }
        }
        return maxY;
    }
}