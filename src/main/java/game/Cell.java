package game;

import java.awt.*;
import java.util.Random;

import static game.Board.halfSize;
import static librairies.StdDraw.*;

public class Cell {
    private final int x;
    private final int y;
    private boolean isAlive;
    private final Color c;

    public Cell(int x, int y) {
        this(x, y, false);
    }

    public Cell(int x, int y, Boolean isAlive) {
        this.x = x;
        this.y = y;
        this.isAlive = isAlive;
        c = getColor();
    }

    public void erscheinne() {
        if (isAlive) draw();
        else verschwinde();
    }

    void verschwinde() {
        setPenColor(Color.WHITE);
        filledSquare(x, y, halfSize);
    }

    private void draw() {
        //setPenColor(Color.BLACK);
        setPenColor(c);
        filledCircle(x, y, halfSize);
    }

    private Color getColor() {
        Random r = new Random();
        return new Color(r.nextInt(215)+ 40, r.nextInt(215)+ 40, r.nextInt(215)+ 40);
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
