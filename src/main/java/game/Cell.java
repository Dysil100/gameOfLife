package game;

import java.awt.*;
import java.util.Random;

import static game.Board.delay;
import static game.Board.halfSize;
import static librairies.StdAudio.note;
import static librairies.StdAudio.play;
import static librairies.StdDraw.*;

public class Cell {
    private final int x;
    private final int y;
    private boolean isAlive;
    private final Color c;
    private Random r;
    private double hz;

    public Cell(int x, int y) {
        this(x, y, false);
    }

    public Cell(int x, int y, Boolean isAlive) {
        this.x = x;
        this.y = y;
        this.isAlive = isAlive;
        c = getColor();
        hz = getHz();
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
        r = new Random();
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

    public void talk(int size) {
        play(note(hz, 0.1/size, 2));
    }

    private double getHz() {
        return r.nextInt(700);
    }
}
