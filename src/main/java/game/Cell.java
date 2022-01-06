package game;

import java.awt.*;

import static game.Board.UNIT_SIZE;
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


    public void draw() {
        if (isAlive) {
            setPenColor(Color.BLACK);
            filledCircle(x, y, UNIT_SIZE / 2);
        } else {
            setPenColor(Color.WHITE);
            filledSquare(x, y, UNIT_SIZE / 2);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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

    public Position getPosition(){
        return new Position(x, y);
    }
}
