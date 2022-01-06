package game;

import java.util.*;

import static game.Board.*;
import static librairies.StdDraw.show;

public class ManageCells {
    Cell[][] allCells = new Cell[WIDTH_SCREEN / UNIT_SIZE][HEIGHT_SCREEN / UNIT_SIZE];
    List<Position> lebendePositionen = new ArrayList<>();
    List<Position> nichtLebendePositionen = new ArrayList<>();
    HashMap<Position, Cell> cellsTable = new HashMap<>();

    public ManageCells() {
        for (int i = UNIT_SIZE / 2; i < WIDTH_SCREEN; i += UNIT_SIZE) {
            for (int j = UNIT_SIZE / 2; j < HEIGHT_SCREEN; j += UNIT_SIZE) {

                int x = (i - (UNIT_SIZE / 2)) / UNIT_SIZE;
                int y = (j - (UNIT_SIZE / 2)) / UNIT_SIZE;
                cellsTable.put(new Position(x, y), new Cell(i, j));
                allCells[x][y] = new Cell(i, j);
            }
        }

    }

    public void checkRules() {
        initState();
        for (int i = 0; i < allCells.length; i++) {
            for (int j = 0; j < allCells[i].length; j++) {
                updatestateAt(i, j);
            }
        }
        updatesAllPositions();
        drawCells();
    }

    private void initState() {
        lebendePositionen.removeAll(lebendePositionen);
        nichtLebendePositionen.removeAll(nichtLebendePositionen);
    }

    private void updatestateAt(int i, int j) {
        // TODO: 06.01.22 gewisse Zellen töten oder zum leben bringen.
        Cell cell = getCellAt(i, j);
        Position position = new Position(i, j);
        if (cell.isAlive()) {
            if (lebendenNachbarn(i, j) == 3 || lebendenNachbarn(i, j) == 2) lebendePositionen.add(position);
            else nichtLebendePositionen.add(position);
        } else {
            if (lebendenNachbarn(i, j) == 3) lebendePositionen.add(position);
            else nichtLebendePositionen.add(position);
        }
    }

    private void updatesAllPositions() {
        synchronized (this){
            lebendePositionen.forEach(p ->cellsTable.get(p).setAlive(true));
            nichtLebendePositionen.forEach(p ->cellsTable.get(p).setAlive(false));
        }
    }

    private int lebendenNachbarn(int i, int j) {
        return Optional.of(allNachbarnVon(i, j).stream().filter(Objects::nonNull).filter(Cell::isAlive).count()).orElse(0L).intValue();

    }

    private List<Cell> allNachbarnVon(int i, int j) {
        List<Cell> cells = new ArrayList<>();
        cells.add(cellsTable.get(new Position(i - 1, j)));
        cells.add(cellsTable.get(new Position(i + 1, j)));
        cells.add(cellsTable.get(new Position(i, j - 1)));
        cells.add(cellsTable.get(new Position(i, j + 1)));
        cells.add(cellsTable.get(new Position(i - 1, j + 1)));
        cells.add(cellsTable.get(new Position(i + 1, j + 1)));
        cells.add(cellsTable.get(new Position(i - 1, j - 1)));
        cells.add(cellsTable.get(new Position(i + 1, j - 1)));
        return cells;
    }

    private Cell getCellAt(int i, int j) {
        return cellsTable.get(new Position(i, j));
    }

    public List<Cell> getCells() {
        return cellsTable.values().stream().toList();
    }

    public void drawCells() {
        synchronized (this) {
            getCells().forEach(Cell::draw);
        }
    }

    public void setCellsAt(Position position) {
        boolean alive = !cellsTable.get(position).isAlive();
        cellsTable.get(position).setAlive(alive);
        Cell cell = cellsTable.get(position);
        cell.draw();
        show();
    }

    public void killAll() {
        getCells().forEach(c -> c.setAlive(false));
    }

    public void drawLebende() {
        lebendePositionen.stream().map(p -> cellsTable.get(p)).forEach(c -> {
            c.draw();
            show();
        });
    }
}
