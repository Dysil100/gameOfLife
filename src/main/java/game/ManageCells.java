package game;

import java.util.*;
import java.util.stream.Collectors;

import static game.Board.*;
import static librairies.StdDraw.show;

public class ManageCells {
    private final Cell[][] allCells = new Cell[WIDTH_SCREEN / UNIT_SIZE][HEIGHT_SCREEN / UNIT_SIZE];
    private final List<Position> lebendePositionen = new ArrayList<>();
    private final HashMap<Position, Cell> cellsTable = new HashMap<>();
    private final List<Cell> lastGeneration = new ArrayList<>();

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
        lebendePositionen.clear();
        for (int i = 0; i < allCells.length; i++) {
            for (int j = 0; j < allCells[i].length; j++) {
                updatestateAt(i, j);
            }
        }
        updatesAllPositions();
    }

    private void updatestateAt(int i, int j) {
        Cell cell = getCellAt(i, j);
        Position position = new Position(i, j);
        if (cell.isAlive()) {
            if (lebendenNachbarn(i, j) == 3 || lebendenNachbarn(i, j) == 2) lebendePositionen.add(position);
        } else {
            if (lebendenNachbarn(i, j) == 3) lebendePositionen.add(position);
        }
    }

    private void updatesAllPositions() {
        synchronized (this) {
            killAll();
            lebendePositionen.forEach(p -> cellsTable.get(p).setAlive(true));
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
            lastGeneration.forEach(Cell::verschwinde);
            List<Cell> thisGeneration = getCells().stream().filter(Cell::isAlive).collect(Collectors.toList());
            thisGeneration.forEach(Cell::erscheinne);
            lastGeneration.clear();
            lastGeneration.addAll(thisGeneration);
        }
    }

    public void setCellsAt(Position position) {
        boolean alive = !cellsTable.get(position).isAlive();
        cellsTable.get(position).setAlive(alive);
        Cell cell = cellsTable.get(position);
        cell.erscheinne();
        show();
    }

    public void killAll() {
        getCells().stream().filter(Cell::isAlive).forEach(c -> c.setAlive(false));
    }

    public void apply() {
        checkRules();
        drawCells();
    }
}