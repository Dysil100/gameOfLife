package game;

import java.awt.*;

import static game.Board.halfSize;
import static librairies.StdDraw.*;

public class Cell {
    private final int x;
    private final int y;
    private boolean isAlive;

    public Cell(int x, int y) {
        this(x, y, false);
    }

    public Cell(int x, int y, Boolean isAlive) {
        this.x = x;
        this.y = y;
        this.isAlive = isAlive;
    }


    public void erscheinne() {
        if (isAlive) {
            drawDefault();
        } else {
            verschwinde();
        }
    }

    void verschwinde() {
        setPenColor(Color.WHITE);
        filledSquare(x, y, halfSize);
    }

    private void drawDefault() {
        setPenColor(Color.BLACK);
        filledCircle(x, y, halfSize);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public String toString() {
        return "Cell{" +
               "x=" + x +
               ", y=" + y +
               ", isAlive=" + isAlive +
               '}';
    }

}
