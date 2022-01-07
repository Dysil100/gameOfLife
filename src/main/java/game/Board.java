package game;

import librairies.StdDraw;

import java.awt.*;
import java.awt.event.*;

import static librairies.StdDraw.*;

public class Board {

    private int Generationen;
    private final ManageCells manageCells;
    static final int WIDTH_SCREEN = 1100;
    static final int HEIGHT_SCREEN = 600;
    static final int UNIT_SIZE = 6;
    static final int halfSize = UNIT_SIZE / 2;
    private boolean running;
    int delay = 20;
    int widthMidle = WIDTH_SCREEN / 2;
    int heigthMidle = HEIGHT_SCREEN / 2;

    public Board() {
        setCanvasSize(WIDTH_SCREEN, HEIGHT_SCREEN);
        setXscale(0, WIDTH_SCREEN);
        setYscale(0, HEIGHT_SCREEN);
        manageCells = new ManageCells();
        start();
    }

    private void start() {
        text(widthMidle, heigthMidle + (10 * UNIT_SIZE), "Welcome to: The Conway's Game of Life");
        text(widthMidle, heigthMidle + (6*UNIT_SIZE), "How two simple rules lead to more complex systems");
        text(widthMidle, heigthMidle - (6 * UNIT_SIZE), "Touch the Screen to simulate");
        show(500);
        running = false;
        Generationen = 0;
        manageCells.killAll();
        setCells();
    }

    public void setCells() {
        running = false;
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

    private void clearBoard() {
        clear(Color.WHITE);
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

    private void pauseText() {
        setPenColor(Color.MAGENTA);
        text(widthMidle, HEIGHT_SCREEN - (UNIT_SIZE), "CHoose a configuration");
        text(widthMidle, HEIGHT_SCREEN - (4 * UNIT_SIZE), "(by click on the cells that should be enable)");
        text(widthMidle, HEIGHT_SCREEN - (7 * UNIT_SIZE), "And press Enter to simulate");
        showGeneration();
    }

    private void showGeneration() {
        text(UNIT_SIZE * 20, HEIGHT_SCREEN - (2 * UNIT_SIZE), "Generationen nr: " + Generationen);
    }

    private void checkPause() {
        if (isKeyPressed(KeyEvent.VK_SPACE) && running) setCells();
        if (isKeyPressed(KeyEvent.VK_SPACE) && !running) run();
    }

    private void checkEvents() {
        checkPause();
        if (isKeyPressed(KeyEvent.VK_ESCAPE) && running || manageCells.getCells().stream().noneMatch(Cell::isAlive))
            start();
    }

    public void run() {
        running = true;
        while (running) {
            clearBoard();
            manageCells.apply();
            runText();
            Generationen++;
            StdDraw.show(delay);
            checkEvents();
        }
    }

    private void runText() {
        setPenColor(Color.MAGENTA);
        text(widthMidle, HEIGHT_SCREEN - (UNIT_SIZE), "Press Space to pause the Simulation");
        text(widthMidle, HEIGHT_SCREEN - (4 * UNIT_SIZE), "And adjust the actual Configuration if necessary");
        text(widthMidle, HEIGHT_SCREEN - (7 * UNIT_SIZE), "And press Enter to continue the Simulation");
        showGeneration();
    }
}