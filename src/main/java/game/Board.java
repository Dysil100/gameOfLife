package game;

import librairies.StdDraw;

import java.awt.*;
import java.awt.event.*;

import static librairies.StdDraw.*;

public class Board {

    private int configurations;
    private final ManageCells manageCells;
    static final int WIDTH_SCREEN = 1100;
    static final int HEIGHT_SCREEN = 600;
    static final int UNIT_SIZE = 10;
    private boolean running;
    int delay = 5;

    public Board() {
        setCanvasSize(WIDTH_SCREEN, HEIGHT_SCREEN);
        setXscale(0, WIDTH_SCREEN);
        setYscale(0, HEIGHT_SCREEN);
        manageCells = new ManageCells();
        start();
    }

    private void start() {
        running = false;
        configurations = 0;
        manageCells.killAll();
        setCells();
    }

    private void pauseText() {
        setPenColor(Color.MAGENTA);
        text(WIDTH_SCREEN / 2, HEIGHT_SCREEN - (2 * UNIT_SIZE), "CHoose a configuration");
        text(WIDTH_SCREEN / 2, HEIGHT_SCREEN - (3.5 * UNIT_SIZE), "(by click on the cells that should be enable)");
        text(WIDTH_SCREEN / 2, HEIGHT_SCREEN - (5 * UNIT_SIZE), "And press Enter to simulate");
        text(UNIT_SIZE * 2, HEIGHT_SCREEN - (2 * UNIT_SIZE), "Configurations; nr: " + configurations);

    }

    private void runText() {
        setPenColor(Color.MAGENTA);
        text(WIDTH_SCREEN / 2, HEIGHT_SCREEN - (2 * UNIT_SIZE), "Press Space to pause the Simulation");
        text(WIDTH_SCREEN / 2, HEIGHT_SCREEN - (3.5 * UNIT_SIZE), "And adjust the actual Configuration if necessary");
        text(WIDTH_SCREEN / 2, HEIGHT_SCREEN - (5 * UNIT_SIZE), "And press Enter to continue the Simulation");
        text(UNIT_SIZE * 2, HEIGHT_SCREEN - (2 * UNIT_SIZE), "Configurations; nr: " + configurations);

    }

    private void drawGrids() {
        setPenColor(Color.CYAN);
        for (int i = 0; i < WIDTH_SCREEN; i += UNIT_SIZE) {
            line(i, HEIGHT_SCREEN, i, 0);
        }
        for (int i = 0; i < HEIGHT_SCREEN; i += UNIT_SIZE) {
            line(WIDTH_SCREEN, i, 0, i);
        }
    }

    private void checkstate() {
        if (manageCells.getCells().stream().noneMatch(Cell::isAlive)) start();
    }

    public void setCells() {
        clearBoard();
        manageCells.drawCells();
        drawGrids();
        pauseText();
        while (!running) {
            if (isMousePressed()) {
                int x = (int) ((mouseX() - (mouseX() % UNIT_SIZE)) / UNIT_SIZE);
                int y = (int) ((mouseY() - (mouseY() % UNIT_SIZE)) / UNIT_SIZE);
                manageCells.setCellsAt(new Position(x, y));
            }
            checkPause();
        }
    }

    private void checkPause() {
        if (isKeyPressed(KeyEvent.VK_SPACE) && running) {
            running = false;
            System.out.println("pause");
            setCells();
        }
        if (isKeyPressed(KeyEvent.VK_ENTER) && !running) {
            running = true;
            run();
        }
    }

    public void run() {
        clearBoard();
        manageCells.drawCells();
        drawGrids();
        while (running) {
            manageCells.checkRules();
            manageCells.drawCells();
            runText();
            configurations++;
            StdDraw.show(delay);
            checkstate();
            checkPause();
        }
    }

    private void clearBoard() {
        clear(Color.WHITE);
        drawGrids();
    }
}